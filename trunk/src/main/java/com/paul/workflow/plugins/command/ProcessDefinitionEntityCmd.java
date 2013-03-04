package com.paul.workflow.plugins.command;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.deploy.DeploymentCache;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

public class ProcessDefinitionEntityCmd implements Command<ProcessDefinitionEntity>{
	private String ProcessDefinitionId;
	public ProcessDefinitionEntityCmd(String ProcessDefinitionId){
		this.ProcessDefinitionId=ProcessDefinitionId;
	}
	public ProcessDefinitionEntity execute(CommandContext commandContext) {
		// TODO Auto-generated method stub
		DeploymentCache deploymentCache = Context.getProcessEngineConfiguration().getDeploymentCache();
		ProcessDefinitionEntity processDefinition = deploymentCache.findDeployedProcessDefinitionById(ProcessDefinitionId);
		return processDefinition;
	}

}
