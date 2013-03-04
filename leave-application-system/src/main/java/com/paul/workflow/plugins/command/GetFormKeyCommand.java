package com.paul.workflow.plugins.command;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.form.DefaultFormHandler;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.task.TaskDefinition;

public class GetFormKeyCommand implements Command<String>{
	private String taskId;
	public GetFormKeyCommand(String taskId) {
		this.taskId = taskId;
	}

	public String execute(CommandContext commandContext) {
		// TODO Auto-generated method stub
		HistoricTaskInstanceEntity historicTask=commandContext.getHistoricTaskInstanceManager().findHistoricTaskInstanceById(taskId);
		if(historicTask==null){
			throw new ActivitiException("No task found for taskId '" + taskId +"'");
		}
		String taskDefinitionKey=historicTask.getTaskDefinitionKey();
		ProcessDefinitionEntity processDefinition = Context.getProcessEngineConfiguration().getDeploymentCache().findDeployedProcessDefinitionById(historicTask.getProcessDefinitionId());
		TaskDefinition taskDefinition = processDefinition.getTaskDefinitions().get(taskDefinitionKey);
		DefaultFormHandler taskFormHandler =(DefaultFormHandler)taskDefinition.getTaskFormHandler();
		return taskFormHandler.getFormKey();
	}

}
