package com.paul.workflow.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.paul.workflow.service.ActFlowService;
import com.paul.workflow.service.command.AppServiceCommand;

@Service("actFlowService")
@Transactional(readOnly = true)
public class ActFlowServiceImpl extends BaseServiceImpl implements ActFlowService {
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
	public Deployment deployProcess(CommonsMultipartFile file) throws IOException{
		// TODO Auto-generated method stub
		DeploymentBuilder deployment=getRepositoryService().createDeployment();
		String fileName=file.getOriginalFilename();
		String ext=StringUtils.substringAfterLast(fileName, ".");
		if(ext.equals("bar")){
			deployment.name(file.getOriginalFilename());
			deployment.addZipInputStream(new ZipInputStream(file.getInputStream()));
		}else{
			deployment.addInputStream(file.getOriginalFilename(), file.getInputStream());
		}
		Deployment deployment2=deployment.deploy();
		return deployment2;
	}
	
	public HistoricProcessInstance getHistoricProcessInstance(String processInstanceId){
		HistoricProcessInstanceQuery query=getHistoryService().createHistoricProcessInstanceQuery();
		query.processInstanceId(processInstanceId);
		HistoricProcessInstance processInstance=query.singleResult();
		return processInstance;
	}
	
	public List<String> findRunActList(String processInstanceId){
		ExecutionQuery query=getRuntimeService().createExecutionQuery();
		query.processInstanceId(processInstanceId);
		List<Execution> list=query.list();
		List<String> actList=new ArrayList<String>();
		if(list!=null&&list.size()>0){
			for (Execution execution : list) {
				ExecutionEntity impl=(ExecutionEntity)execution;
				actList.add(impl.getActivityId());
			}
		}
		return actList;
	}
	
	public Task getTask(String taskId){
		TaskQuery query=getTaskService().createTaskQuery();
		query.taskId(taskId);
		Task task=query.singleResult();
		return task;
	}
	
	public List<HistoricTaskInstance> findHisTaskList(String processInstanceId){
		HistoricTaskInstanceQuery query=getHistoryService().createHistoricTaskInstanceQuery();
		query.processInstanceId(processInstanceId);
		query.orderByHistoricActivityInstanceStartTime();
		query.desc();
		List<HistoricTaskInstance> list=query.list();
		return list;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
	public void commitTask(String taskId, String message, Map<String, Object> variables) {
		Task task=getTask(taskId);
		if(task!=null){
			if(StringUtils.isNotBlank(task.getAssignee())){
				getIdentityService().setAuthenticatedUserId(task.getAssignee());
			}
			if(StringUtils.isNotBlank(message)){
				getTaskService().getTaskComments(taskId);
				getTaskService().addComment(taskId, task.getProcessInstanceId(), message);
			}
			getIdentityService().setAuthenticatedUserId(null);
			if(variables!=null){
				getTaskService().complete(taskId, variables);
			}else{
				getTaskService().complete(taskId);
			}
		}
	}
	
	public HistoricTaskInstance getHisTask(String taskId){
		HistoricTaskInstanceQuery query=getHistoryService().createHistoricTaskInstanceQuery();
		query.taskId(taskId);
		HistoricTaskInstance task=query.singleResult();
		return task;
	}
	
	public ProcessDefinition getProcessDefinition(String processDefinitionId){
		ProcessDefinitionQuery query=getRepositoryService().createProcessDefinitionQuery();
		query.processDefinitionId(processDefinitionId);
		ProcessDefinition processDefinition=query.singleResult();
		return processDefinition;
	}
	
	public ProcessDefinition getProcessDefinitionXml(String processDefinitionId){
		return getActivitiPluginService().getProcessDefinition(processDefinitionId);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
	public void startProcessInstance(String authenticatedUserId,String processKey,String businessKey,Map<String, Object> variables, AppServiceCommand<Object, ProcessInstance> appServiceCommand) {
		ProcessInstance processInstance;
		try {
			getIdentityService().setAuthenticatedUserId(authenticatedUserId);
			if(variables!=null){
				processInstance=getRuntimeService().startProcessInstanceByKey(processKey,businessKey,variables);
			}else{
				processInstance=getRuntimeService().startProcessInstanceByKey(processKey,businessKey);
			}		
			appServiceCommand.execute(processInstance);
		} catch (RuntimeException e) {
			throw e;
		}
		finally
		{
			getIdentityService().setAuthenticatedUserId(null);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
	public void startProcessInstanceByKey(String authenticatedUserId,String processKey,String businessKey,Map<String, Object> variables) {
		try {
			getIdentityService().setAuthenticatedUserId(authenticatedUserId);
			if(variables!=null){
				getRuntimeService().startProcessInstanceByKey(processKey,businessKey,variables);
			}else{
				getRuntimeService().startProcessInstanceByKey(processKey,businessKey);
			}		
		} catch (RuntimeException e) {
			throw e;
		}
		finally
		{
			getIdentityService().setAuthenticatedUserId(null);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
	public void startProcessInstanceById(String authenticatedUserId,String processId,String businessKey,Map<String, Object> variables) {
		try {
			getIdentityService().setAuthenticatedUserId(authenticatedUserId);
			if(variables!=null){
				getRuntimeService().startProcessInstanceById(processId,businessKey,variables);
			}else{
				getRuntimeService().startProcessInstanceById(processId,businessKey);
			}		
		} catch (RuntimeException e) {
			throw e;
		}
		finally
		{
			getIdentityService().setAuthenticatedUserId(null);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
	public void deleteProcessInstance(String procInstId, String reason) {
		Date endTime=getHistoricProcessInstance(procInstId).getEndTime();
		if(endTime==null){
			getRuntimeService().deleteProcessInstance(procInstId,reason);
		}
		getHistoryService().deleteHistoricProcessInstance(procInstId);
	}
	
	public Map<String, Object> getVariables(String taskId) {
		return getTaskService().getVariables(taskId);
	}

	public static void main(String[] args) {
		String ext=StringUtils.substringAfterLast("a.b.c.txt", ".");
		System.out.println(ext);
	}



}
