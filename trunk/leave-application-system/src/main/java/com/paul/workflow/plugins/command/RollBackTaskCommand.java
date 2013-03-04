package com.paul.workflow.plugins.command;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmException;
/**
 * 任务回退(回退到上一环节办理人)
 * @author Paul
 *
 */
public class RollBackTaskCommand implements Command<Void>{
	private String taskId;
	private boolean autoAssign=true;
	public RollBackTaskCommand(String taskId) {
		// TODO Auto-generated constructor stub
		this.taskId=taskId;
	}

	public RollBackTaskCommand(String taskId,boolean autoAssign) {
		// TODO Auto-generated constructor stub
		this.taskId=taskId;
		this.autoAssign=autoAssign;
	}
	public Void execute(CommandContext commandContext) {
		// TODO Auto-generated method stub
		TaskEntity taskEntity=commandContext.getTaskManager().findTaskById(taskId);
		
		if(taskEntity!=null){
			ExecutionEntity executionEntity=commandContext.getExecutionManager().findExecutionById(taskEntity.getExecutionId());
			ExecutionRollBack executionExt=new ExecutionRollBack(executionEntity,autoAssign);
			executionExt.take(true);
		}else{
			throw new PvmException("ERR-2001任务已经结束，不能回退！！");
		}
		return null;
	}
}
