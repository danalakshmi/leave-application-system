package com.paul.workflow.plugins.service.impl;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paul.workflow.plugins.command.EndProcessInstanceCommand;
import com.paul.workflow.plugins.command.GetFormKeyCommand;
import com.paul.workflow.plugins.command.ProcessDefinitionEntityCmd;
import com.paul.workflow.plugins.command.RecycleTaskCommand;
import com.paul.workflow.plugins.command.RollBackTaskCommand;
import com.paul.workflow.plugins.service.ActivitiPluginService;

@Service("activitiPluginService")
public class ActivitiPluginServiceImpl implements ActivitiPluginService{
	
	@Autowired(required = true)
	private ProcessEngineConfiguration processEngineConfiguration;

	public ProcessDefinitionEntity getProcessDefinition(String processDefId){
		ProcessDefinitionEntityCmd cmd=new ProcessDefinitionEntityCmd(processDefId);
		return getCommandExecutor().execute(cmd);
	}
	
	public void recycleTask(String taskId){
		RecycleTaskCommand cmd=new RecycleTaskCommand(taskId);
		getCommandExecutor().execute(cmd);
	}
	
	public void recycleTask(String taskId, boolean autoAssign) {
		// TODO Auto-generated method stub
		RecycleTaskCommand cmd=new RecycleTaskCommand(taskId,autoAssign);
		getCommandExecutor().execute(cmd);
	}
	
	public void rollBackTask(String taskId){
		RollBackTaskCommand cmd=new RollBackTaskCommand(taskId);
		getCommandExecutor().execute(cmd);
	}
	
	public void rollBackTask(String taskId,boolean autoAssign){
		RollBackTaskCommand cmd=new RollBackTaskCommand(taskId,autoAssign);
		getCommandExecutor().execute(cmd);
	}
	
	public void endProcessInstance(String processInstanceId, String deleteReason) {
		// TODO Auto-generated method stub
		EndProcessInstanceCommand cmd=new EndProcessInstanceCommand(processInstanceId,deleteReason);
		getCommandExecutor().execute(cmd);
	}
	
	
	public String getFormKey(String taskId) {
		// TODO Auto-generated method stub
		GetFormKeyCommand cmd=new GetFormKeyCommand(taskId);
		return getCommandExecutor().execute(cmd);
	}	
	private CommandExecutor getCommandExecutor(){
		SpringProcessEngineConfiguration configuration=(SpringProcessEngineConfiguration)processEngineConfiguration;
		CommandExecutor commandExecutor=configuration.getCommandExecutorTxRequired();
		return commandExecutor;
	}
	

	public ProcessEngineConfiguration getProcessEngineConfiguration() {
		return processEngineConfiguration;
	}

	public void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration) {
		this.processEngineConfiguration = processEngineConfiguration;
	}	
}
