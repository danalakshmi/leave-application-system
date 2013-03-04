package com.paul.workflow.plugins.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.Condition;
import org.activiti.engine.impl.ExecutionQueryImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务回收(当一下环节还未办理时，可进行回收)
 * @author Paul
 *
 */
public class RecycleTaskCommand implements Command<Void>{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private String taskId;
	private HistoricTaskInstanceEntity historicTask;
	private boolean autoAssign=true;
	public RecycleTaskCommand(String taskId){
		this.taskId=taskId;
	}
	
	public RecycleTaskCommand(String taskId,boolean autoAssign){
		this.taskId=taskId;
		this.autoAssign=autoAssign;
	}
	public Void execute(CommandContext commandContext){
		historicTask=commandContext.getHistoricTaskInstanceManager().findHistoricTaskInstanceById(taskId);
		//查看流程是否结束
		HistoricProcessInstance processInstance=commandContext.getHistoricProcessInstanceManager().findHistoricProcessInstance(historicTask.getProcessInstanceId());
		String activityId=historicTask.getTaskDefinitionKey();
		if(processInstance.getEndTime()!=null){
			throw new ActivitiException("ERR-1001:流程已结束不能回收任务！！"); 
		}
		ExecutionEntity hisTaskExecution=commandContext.getExecutionManager().findExecutionById(historicTask.getExecutionId());
		if(hisTaskExecution!=null&&hisTaskExecution.isActive()&&hisTaskExecution.getActivityId().equals(activityId)){
			throw new ActivitiException("ERR-1002:处于当前办理环节，不能回收任务！！"); 
		}
		LinkedList<Execution> nextExecutions = new LinkedList<Execution>();
		if(hisTaskExecution!=null){
			nextExecutions.push(hisTaskExecution);
		}else{
			ExecutionQuery executionQuery=new ExecutionQueryImpl(commandContext);
			executionQuery.processInstanceId(historicTask.getProcessInstanceId());
			List<Execution> runExecuteList=executionQuery.list();
			Map<String, ActivityImpl> activityMap=new HashMap<String, ActivityImpl>();
			for (Execution execution : runExecuteList) {
				ExecutionEntity impl=(ExecutionEntity)execution;
				if(!activityMap.containsKey(impl.getActivityId())){
					activityMap.put(impl.getActivityId(), impl.getActivity());
					nextExecutions.add(execution);
				}
			}
		}
		//查看当前回撤节点下一办理环节是否处于执行状态
		ExecutionEntity execution=null;
		int count=nextExecutions.size();
		List<ExecutionEntity> selExecuteList=new ArrayList<ExecutionEntity>();
		
		List<Stack<ActivityImpl>> activityList=new ArrayList<Stack<ActivityImpl>>();
		while (!nextExecutions.isEmpty()) {
			execution=(ExecutionEntity)nextExecutions.removeFirst();
			ActivityImpl currentActivity=execution.getActivity();
			Stack<ActivityImpl> stacks=new Stack<ActivityImpl>();
			stacks.push(currentActivity);
			activityList.add(stacks);
			boolean isNextTmp=getPath(stacks,execution,activityId,false);
			if(isNextTmp){
				selExecuteList.add(execution);
			}
		}
		if(selExecuteList.size()==count){
			if(selExecuteList.size()>1){
				for (ExecutionEntity executionEntity : selExecuteList) {
					if(!executionEntity.isActive()&&executionEntity.isConcurrent()){
						throw new ActivitiException("ERR-1003:下一任务已办理，无法回收！！"); 
					}
				}
			}
			execution=selExecuteList.get(0);
			ExecutionEntity runExecution=commandContext.getExecutionManager().findExecutionById(execution.getId());
			ExecutionRecycle executionExt=new ExecutionRecycle(runExecution,activityList.get(0),historicTask,autoAssign);
			executionExt.take(true);
		}else{
			throw new ActivitiException("ERR-1003:下一任务已办理，无法回收！！"); 
		}
		return null;
	}
	

	
	private boolean getPath(Stack<ActivityImpl> stacks,ExecutionEntity executionEntity,String actId,boolean isContinue){
		if(stacks.size()>0){
			ActivityImpl tmp=stacks.peek();
			logger.debug((String)tmp.getProperty("name"));
			if(!(tmp.getId().equals(actId))){
				List<PvmTransition> pvmTransitionList=tmp.getIncomingTransitions();
				if(pvmTransitionList.size()>0){
					int count=0;
					for (ActivityImpl activityImpl : stacks) {
						if(activityImpl.getActivityBehavior() instanceof UserTaskActivityBehavior){
							count++;
						}
					}
					if(count<=2){
						for (PvmTransition pvmTransition : pvmTransitionList) {
							//条件判断,路径选择
							ActivityImpl pvmActivity=(ActivityImpl)pvmTransition.getSource();
							Condition condition = (Condition) pvmTransition.getProperty(BpmnParse.PROPERTYNAME_CONDITION);
							if (condition == null|| condition.evaluate(executionEntity)) {
								
								if(!isContinue){
									stacks.push(pvmActivity);
									isContinue=getPath(stacks,executionEntity,actId,isContinue);
								}else{
									break;
								}
							}
						}
					}
					
				}
			}else{
				if(tmp.getId().equals(actId)){
					int count=0;
					for (ActivityImpl activityImpl : stacks) {
						if(activityImpl.getActivityBehavior() instanceof UserTaskActivityBehavior){
							count++;
						}
					}
					if(executionEntity.isConcurrent()){
						
					}
					if(count<=2){
						isContinue=true;
					}
				}
			}
			if(!isContinue){
				logger.debug("pop"+","+isContinue+","+(String)tmp.getProperty("name"));
				stacks.pop();
			}
		}
		
		return isContinue;
	}
	/**
	 * 
	 * @param executionEntity
	 * @param currentActivity
	 * @param activityId
	 * @param val
	 * @return
	 */
	private int[] cycleActivity(ExecutionEntity executionEntity,ActivityImpl currentActivity,String activityId,int val[]){
		List<PvmTransition> pvmTransitionList=currentActivity.getIncomingTransitions();
		if(pvmTransitionList==null||pvmTransitionList.size()==0){
			val[0]=0;
			val[1]=1;
			return val;
		}
		for (PvmTransition pvmTransition : pvmTransitionList) {
			//条件判断,路径选择
			ActivityImpl pvmActivity=(ActivityImpl)pvmTransition.getSource();
			Condition condition = (Condition) pvmTransition.getProperty(BpmnParse.PROPERTYNAME_CONDITION);
			if (condition == null|| condition.evaluate(executionEntity)) {
				if(pvmActivity.getActivityBehavior() instanceof UserTaskActivityBehavior){
					val[0]++;
				}
				if(!(pvmActivity.getId().equals(activityId))){
					val=cycleActivity(executionEntity,pvmActivity,activityId,val);
				}else{
					val[1]=0;
					break;
				}
				//logger.info((String)pvmActivity.getProperty("name")+","+val[0]+","+val[1]);
	        }else{
	        	if(val[1]==1){
	        		if(val[0]>0){
		        		val[0]--;
		        	}
	        	}
	        }
		}
		return val;
	}
}
