package com.paul.workflow.plugins.command;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.Condition;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.bpmn.behavior.NoneStartEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.PvmException;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

public class ExecutionRollBack extends ExecutionExtBase{
	private String taskDeleteReason="rollBackTask";
	private ExecutionEntity execution;
	private ExecutionEntity concurrentRoot;
	private boolean autoAssign=true;
	public ExecutionRollBack(ExecutionEntity execution) {
		this.execution=execution;
	}

	public ExecutionRollBack(ExecutionEntity execution,boolean autoAssign) {
		this.execution=execution;
		this.autoAssign=autoAssign;
	}
	public void take(boolean isContinue){
		ActivityImpl activityImpl = execution.getActivity();
		if(activityImpl.getActivityBehavior() instanceof NoneStartEventActivityBehavior){
			throw new PvmException("ERR-2002：开始节点，不能回退！！");
		} 
		logger.info((String)activityImpl.getProperty("name"));
		if (!isContinue) {
			notifyStart(execution);
			if (activityImpl.getActivityBehavior() instanceof UserTaskActivityBehavior) {
				// 创建
				createTask(execution, activityImpl,autoAssign);
				execution.setActivity(activityImpl);
				execution.setActive(true);
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
			List<ExecutionEntity> joinedExecutions=getAllChildExecutions(concurrentRoot);
			int nbrOfExecutionsToJoin = execution.getActivity().getOutgoingTransitions().size();
			transitionsToTake = execution.getActivity().getIncomingTransitions();
			if(nbrOfExecutionsToJoin>1&&joinedExecutions.size()>0){
				//当为并发中的节点，将子任务结束
				for (ExecutionEntity executionEntity : joinedExecutions) {
					notifyEnd(executionEntity);
					removeTask(executionEntity,taskDeleteReason);
					executionEntity.remove();
				}
				concurrentRoot.setActive(true);
				concurrentRoot.setActivity((ActivityImpl) transitionsToTake.get(0).getSource());
				concurrentRoot.setConcurrent(false);
				ExecutionRollBack executionExt=new ExecutionRollBack(concurrentRoot,autoAssign);
				executionExt.take(false);
			}else if(nbrOfExecutionsToJoin==1&&joinedExecutions.size()==0){
				//当为join结束回退，先前任务并发启动
				for (PvmTransition tmpPvmTransition : transitionsToTake) {
					ExecutionEntity outgoingExecution = execution.createExecution();
					outgoingExecution.setActive(true);
					outgoingExecution.setScope(false);
					outgoingExecution.setConcurrent(true);
					outgoingExecution.setActivity((ActivityImpl) tmpPvmTransition.getSource());
					ExecutionRollBack executionExt=new ExecutionRollBack(outgoingExecution,autoAssign);
					executionExt.take(false);
				}
				concurrentRoot.setConcurrent(true);
				concurrentRoot.setActive(false);
				concurrentRoot.setScope(true);
				concurrentRoot.setActivity(null);
			}
		} else {
			// 路径选择
			for (PvmTransition pvmTransition : activityImpl.getIncomingTransitions()) {
				// 条件判断,路径选择
				Condition condition = (Condition) pvmTransition.getProperty(BpmnParse.PROPERTYNAME_CONDITION);
				if (condition == null || condition.evaluate(execution)) {
					transitionsToTake.add(pvmTransition);
				}
			}
			PvmTransition selTransition=null;
			if (transitionsToTake.size() == 1) {
				selTransition=transitionsToTake.get(0);
				
			}else if(transitionsToTake.size()>1){
				//取最近执行的节点
				HistoricActivityInstanceQueryImpl query=new HistoricActivityInstanceQueryImpl(Context.getCommandContext());
				query.orderByHistoricActivityInstanceEndTime();
				query.executionId(execution.getId());
				query.finished();
				query.desc();
				List<HistoricActivityInstance> list=query.list();
				HistoricActivityInstance pre=null;
				HistoricActivityInstance next=null;
				
				for (PvmTransition tmpTransition : transitionsToTake) {
					for (HistoricActivityInstance historicActivityInstance : list) {
						if(tmpTransition.getSource().getId().equals(historicActivityInstance.getActivityId())){
							next=historicActivityInstance;
							break;
						}
					}
					if(pre!=null){
						if(pre.getEndTime().before(next.getEndTime())){
							pre=next;
							selTransition=tmpTransition;
						}
					}else{
						pre=next;
					}
				}
			}
			execution.setActivity((ActivityImpl)selTransition.getSource());
			ExecutionRollBack executionExt=new ExecutionRollBack(execution,autoAssign);
			executionExt.take(false);
		}
	}
}
