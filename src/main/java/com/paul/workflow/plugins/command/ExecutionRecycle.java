package com.paul.workflow.plugins.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.activiti.engine.impl.Condition;
import org.activiti.engine.impl.bpmn.behavior.ParallelGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

public class ExecutionRecycle extends ExecutionExtBase{
	private String taskDeleteReason="recycleTask";
	private ExecutionEntity execution;
	private HistoricTaskInstanceEntity hisTask;
	private ExecutionEntity concurrentRoot;
	private Stack<ActivityImpl> stack;
	private boolean autoAssign=true;
	public ExecutionRecycle(ExecutionEntity execution,Stack<ActivityImpl> stack,HistoricTaskInstanceEntity hisTask) {
		this.execution=execution;
		this.hisTask=hisTask;
		this.stack=stack;
	}

	public ExecutionRecycle(ExecutionEntity execution,Stack<ActivityImpl> stack,HistoricTaskInstanceEntity hisTask,boolean autoAssign) {
		this.execution=execution;
		this.hisTask=hisTask;
		this.stack=stack;
		this.autoAssign=autoAssign;
	}
	public void take(){
		ActivityImpl recycleActivity=execution.getProcessDefinition().findActivity(hisTask.getTaskDefinitionKey());
	    notifyEnd(execution);
		removeTask(execution,taskDeleteReason);
		execution.setActivity(recycleActivity);
		notifyStart(execution);
		createTask(execution, recycleActivity,autoAssign);
		execution.setActive(true);
	}
	
	public void take(boolean isContinue){
		ActivityImpl activityImpl = execution.getActivity();
		logger.info((String)activityImpl.getProperty("name"));
		if (!isContinue) {
			boolean isStop=false;
			if (activityImpl.getActivityBehavior() instanceof UserTaskActivityBehavior) {
				// 创建
				isStop=true;
			}
			createActivity(execution, activityImpl, isStop,autoAssign);
			if(isStop){
				return;
			}
		}else{
			removeTask(execution,taskDeleteReason);
		}
		notifyEnd(execution);
		List<PvmTransition> transitionsToTake = new ArrayList<PvmTransition>();
		if (activityImpl.getActivityBehavior() instanceof ParallelGatewayActivityBehavior) {
			//
			concurrentRoot = ((execution.isConcurrent() && !execution.isScope()) ? execution.getParent() : execution);
			lockConcurrentRoot(execution);
			List<ActivityExecution> joinedExecutions = findActiveConcurrentExecutions(execution,concurrentRoot,activityImpl);
			
			int nbrOfExecutionsToJoin = execution.getActivity().getOutgoingTransitions().size();
			int nbrOfExecutionsJoined = joinedExecutions.size();
			if (nbrOfExecutionsToJoin == nbrOfExecutionsJoined) {
				if(!(execution.isActive())&&execution.isConcurrent()){
					ActivityImpl hisAct=execution.getProcessDefinition().findActivity(hisTask.getTaskDefinitionKey());
					execution.setActivity(hisAct);
					ExecutionRecycle executionExt=new ExecutionRecycle(execution,stack,hisTask,autoAssign);
					executionExt.take(false);
					
				}else{
					transitionsToTake = execution.getActivity().getIncomingTransitions();
					List<ExecutionEntity> concurrentActiveExecutions = new ArrayList<ExecutionEntity>();
					List<ExecutionEntity> concurrentInActiveExecutions = new ArrayList<ExecutionEntity>();
					for (ExecutionEntity executiontmp : concurrentRoot.getExecutions()) {
						if (executiontmp.isActive()) {
							concurrentActiveExecutions.add(executiontmp);
						} else {
							concurrentInActiveExecutions.add(executiontmp);
						}
					}

					if ((transitionsToTake.size() == 1) && (concurrentInActiveExecutions.isEmpty())) {
						for (ActivityExecution executionEntity : joinedExecutions) {
							ExecutionEntity tmp=(ExecutionEntity)executionEntity;
							notifyEnd(tmp);
							removeTask(tmp,taskDeleteReason);
							tmp.remove();
						}
						concurrentRoot.setActive(true);
						concurrentRoot.setActivity((ActivityImpl) transitionsToTake.get(0).getSource());
						concurrentRoot.setConcurrent(false);
						ExecutionRecycle executionExt=new ExecutionRecycle(concurrentRoot,stack,hisTask,autoAssign);
						executionExt.take(false);
					} else {
						for (PvmTransition tmpPvmTransition : transitionsToTake) {
							ExecutionEntity outgoingExecution = execution.createExecution();
							outgoingExecution.setActive(true);
							outgoingExecution.setScope(false);
							outgoingExecution.setConcurrent(true);
							outgoingExecution.setActivity((ActivityImpl) tmpPvmTransition.getSource());
							ExecutionRecycle executionExt=new ExecutionRecycle(outgoingExecution,stack,hisTask,autoAssign);
							executionExt.take(false);
						}
						concurrentRoot.setConcurrent(true);
						concurrentRoot.setActive(false);
						concurrentRoot.setScope(true);
						concurrentRoot.setActivity(null);
					}
				}
			}
		} else {
			// 路径选择
			for (PvmTransition pvmTransition : activityImpl.getIncomingTransitions()) {
				// 条件判断,路径选择
				Condition condition = (Condition) pvmTransition.getProperty(BpmnParse.PROPERTYNAME_CONDITION);
				if (condition == null || condition.evaluate(execution)) {
					String id=pvmTransition.getSource().getId();
					for (ActivityImpl tmp : stack) {
						if(tmp.getId().equalsIgnoreCase(id)){
							transitionsToTake.add(pvmTransition);
							break;
						}
					}
				}
			}
			//选择路径，合并后如何回退
			for (PvmTransition tmpPvmTransition : transitionsToTake) {
				execution.setActivity((ActivityImpl)tmpPvmTransition.getSource());
				ExecutionRecycle executionExt=new ExecutionRecycle(execution,stack,hisTask,autoAssign);
				executionExt.take(false);
			}
		}
	}
}
