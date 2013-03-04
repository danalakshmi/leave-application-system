package com.paul.workflow.plugins.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.history.handler.ActivityInstanceEndHandler;
import org.activiti.engine.impl.history.handler.ActivityInstanceStartHandler;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmException;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paul.common.util.spring.SpringContextUtil;
import com.paul.workflow.dao.ActFlowDao;

public class ExecutionExtBase {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected List<ExecutionEntity> getAllChildExecutions(ExecutionEntity execution) {
		List<ExecutionEntity> childExecutions = new ArrayList<ExecutionEntity>();
		for (ExecutionEntity childExecution : execution.getExecutions()) {
			childExecutions.add(childExecution);
			childExecutions.addAll(getAllChildExecutions(childExecution));
		}
		return childExecutions;
	}
	
	public List<ActivityExecution> findActiveConcurrentExecutions(ExecutionEntity execution,ExecutionEntity concurrentRoot,PvmActivity activity) {
		List<ActivityExecution> activeConcurrentExecutionsInActivity = new ArrayList<ActivityExecution>();
		List<? extends ActivityExecution> concurrentExecutions = getAllChildExecutions(concurrentRoot);
		if(concurrentExecutions.size()>0){
			for (ActivityExecution concurrentExecution : concurrentExecutions) {
				if (concurrentExecution.isActive()) {
					activeConcurrentExecutionsInActivity.add(concurrentExecution);
				}
			}
		}else{
			if (execution.isActive()) {
				activeConcurrentExecutionsInActivity.add(execution);
			}
		}
		return activeConcurrentExecutionsInActivity;
	}
	
	protected void createActivity(ExecutionEntity execution,ActivityImpl recycleActivity,boolean isTask,boolean autoAssign){
		notifyStart(execution);
		execution.setActivity(recycleActivity);
		execution.setActive(true);
		if(isTask){
			try {
				//根据流程创建任务，分配任务
				if(!autoAssign&&(recycleActivity.getActivityBehavior() instanceof UserTaskActivityBehavior)){
					createAssignHandleTask(execution,recycleActivity);
				}else{
					recycleActivity.getActivityBehavior().execute(execution);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new PvmException("couldn't execute activity <"+recycleActivity.getProperty("type")+" id=\""+recycleActivity.getId()+"\" ...>: "+e.getMessage(), e);
			}
		}
		
	}
	
	
	/**
	 * 创建任务，并手动查找最近人员信息进行分配
	 * @param execution
	 * @param recycleActivity
	 * @throws Exception
	 */
	private void createAssignHandleTask(ExecutionEntity execution,ActivityImpl recycleActivity) throws Exception{
		UserTaskActivityBehavior activityBehavior=(UserTaskActivityBehavior)recycleActivity.getActivityBehavior();
		TaskDefinition taskDefinition=activityBehavior.getTaskDefinition();
	    TaskEntity task = TaskEntity.createAndInsert(execution);
	    task.setExecution((ActivityExecution)execution);
	    task.setTaskDefinition(taskDefinition);
	    logger.info(task.getProcessDefinitionId());
	    
	    if (taskDefinition.getNameExpression() != null) {
	      String name = (String) taskDefinition.getNameExpression().getValue(execution);
	      task.setName(name);
	    }

	    if (taskDefinition.getDescriptionExpression() != null) {
	      String description = (String) taskDefinition.getDescriptionExpression().getValue(execution);
	      task.setDescription(description);
	    }
	    
	    if(taskDefinition.getDueDateExpression() != null) {
	      Object dueDate = taskDefinition.getDueDateExpression().getValue(execution);
	      if(dueDate != null) {
	        if(!(dueDate instanceof Date)) {
	          throw new ActivitiException("Due date expression does not resolve to a Date: " + 
	                  taskDefinition.getDueDateExpression().getExpressionText());
	        }
	        task.setDueDate((Date) dueDate);
	      }
	    }
	    //取该节点最近人员信息
	    ActFlowDao actFlowDao=(ActFlowDao)SpringContextUtil.getBean("actFlowDao");
	    List<String> list=actFlowDao.findTaskAssingUserList(execution.getProcessInstanceId(), task.getTaskDefinitionKey());
	    if(list!=null&&list.size()>0){
	    	task.setAssignee(list.get(0));
	    	logger.info(task.getName()+":handleAssign:"+task.getAssignee());
	    }
	    // All properties set, now firing 'create' event
	    //task.fireEvent(TaskListener.EVENTNAME_CREATE);
	}
	/**
	 * 创建任务
	 * 
	 * @param hisTaskExecution
	 * @param recycleActivity
	 */
	protected void createTask(ExecutionEntity hisTaskExecution,ActivityImpl recycleActivity,boolean autoAssign){		
		try {
			if(!autoAssign&&(recycleActivity.getActivityBehavior() instanceof UserTaskActivityBehavior)){
				createAssignHandleTask(hisTaskExecution,recycleActivity);
			}else{
				recycleActivity.getActivityBehavior().execute(hisTaskExecution);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PvmException("couldn't execute activity <"+recycleActivity.getProperty("type")+" id=\""+recycleActivity.getId()+"\" ...>: "+e.getMessage(), e);
		}
	}
	/**
	 * 回收任务
	 * @param execution
	 */
	public void removeTask(ExecutionEntity execution,String taskDeleteReason){
		TaskQueryImpl taskQueryImpl=new TaskQueryImpl(Context.getCommandContext());
		taskQueryImpl.executionId(execution.getId());
		//logger.info(execution.getActivityId());
		taskQueryImpl.taskDefinitionKey(execution.getActivityId());
		TaskEntity taskEntity=(TaskEntity)taskQueryImpl.singleResult();
		if(taskEntity!=null){
			Context.getCommandContext().getTaskManager().deleteTask(taskEntity, taskDeleteReason, false);
			
		}
	}
	
	public void notifyEnd(ExecutionEntity execution){
		ActivityInstanceEndHandler handler=new ActivityInstanceEndHandler();
		handler.notify(execution);
	}
	
	public void notifyStart(ExecutionEntity execution){
		ActivityInstanceStartHandler handler=new ActivityInstanceStartHandler();
		handler.notify(execution);
	}
	
	protected void lockConcurrentRoot(ActivityExecution execution) {
		ActivityExecution concurrentRoot = null;
		if (execution.isConcurrent()) {
			concurrentRoot = execution.getParent();
		} else {
			concurrentRoot = execution;
		}
		((ExecutionEntity) concurrentRoot).forceUpdate();
	}	
}
