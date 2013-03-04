package com.paul.workflow.plugins.command;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionManager;
/**
 * 强制结束流程，保留审批记录
 * @author Paul
 *
 */
public class EndProcessInstanceCommand implements Command<Void>{
	protected String processInstanceId;
	protected String deleteReason;
	
	public EndProcessInstanceCommand(String processInstanceId, String deleteReason) {
		this.processInstanceId = processInstanceId;
		this.deleteReason = deleteReason;
	}
	public Void execute(CommandContext commandContext) {
		// TODO Auto-generated method stub
		ExecutionManager executionManager=commandContext.getExecutionManager();
		executionManager.deleteProcessInstance(processInstanceId, deleteReason, false);
		return null;
	}

}
