package com.paul.workflow.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.paul.workflow.service.command.AppServiceCommand;



public interface ActFlowService extends BaseService{
	
	/**
	 * 部署默认流程
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Deployment deployProcess(String zipFileName) throws IOException;
	
	/**
	 * 流程部署
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Deployment deployProcess(CommonsMultipartFile file) throws IOException;
	/**
	 * 通过流程实例ID，取得运中活动节点记录
	 * @param processInstanceId
	 * @return
	 */
	public List<String> findRunActList(String processInstanceId);
	/**
	 * 根据流程实例ID，取流程实例记录
	 * @param processInstanceId
	 * @return
	 */
	public HistoricProcessInstance getHistoricProcessInstance(String processInstanceId);
	/**
	 * 查看任务
	 * @param taskId
	 * @return
	 */
	public Task getTask(String taskId);
	/**
	 * 根据流程实例ID，查看历史任务列表
	 * @param processInstanceId
	 * @return
	 */
	public List<HistoricTaskInstance> findHisTaskList(String processInstanceId);
	/**
	 * 查看历史任务
	 * @param taskId
	 * @return
	 */
	public HistoricTaskInstance getHisTask(String taskId);
	/**
	 * 根据流程模板ID，取得流程模板详细信息(数据库)
	 * @param processDefinitionId
	 * @return
	 */
	public ProcessDefinition getProcessDefinition(String processDefinitionId);
	/**
	 * 根据流程模板ID，取得流程模板详细信息(xml详细信息)
	 * @param processDefinitionId
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionXml(String processDefinitionId);
	/**
	 * 提交任务
	 * @param taskId
	 * @param message
	 * @param variables
	 */
	public void commitTask(String taskId, String message, Map<String, Object> variables);
	/**
	 * 根据流程Key启动最新流程，并回调业务数据处理
	 * @param processKey
	 * @param businessKey
	 * @param variables
	 * @param appServiceCommand
	 */
	public void startProcessInstance(String authenticatedUserId,String processKey,String businessKey,Map<String, Object> variables,AppServiceCommand<Object, ProcessInstance> appServiceCommand);
	
	/**
	 * 根据流程Key启动最新流程，不回调业务数据处理
	 * @param processKey
	 * @param businessKey
	 * @param variables
	 */
	public void startProcessInstanceByKey(String authenticatedUserId,String processKey,String businessKey,Map<String, Object> variables);
	
	public void startProcessInstanceById(String authenticatedUserId,String processId,String businessKey,Map<String, Object> variables);
	
	public void deleteProcessInstance(String procInstId,String reason);
	
	public Map<String, Object> getVariables(String taskId);
}
