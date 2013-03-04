package com.paul.leave.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paul.leave.service.LeaveFlowService;
import com.paul.leave.service.LeaveService;
import com.paul.leave.valueObject.Leave;
import com.paul.workflow.plugins.service.ActivitiPluginService;
import com.paul.workflow.service.ActFlowService;

@Service("leaveFlowService")
@Transactional(readOnly = true)
public class LeaveFlowServiceImpl implements LeaveFlowService {
	
	private static final Logger LOGGER = Logger.getLogger(LeaveFlowServiceImpl.class);

	@Autowired(required = true)
	private ActFlowService actFlowService;
	
	@Autowired(required = true)
	private ActivitiPluginService activitiPluginService;
	
	@Autowired(required = true)
	private LeaveService leaveService;
	
	@Autowired(required = true)
	private HistoryService historyService;
	
	public static String VACATION_APPROVED_KEY = "vacationApproved";
	
	public static String EMPLOYEE_NAME_KEY = "employeeName";
	
	public static String RESEND_REQUEST_KEY = "resendRequest";
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
	public void startProcess(Leave leave) {
		leaveService.save(leave);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(EMPLOYEE_NAME_KEY, leave.getEmployeeName());
		LOGGER.info(leave.getId());
		actFlowService.startProcessInstanceById(leave.getEmployeeName(), leave.getProcessDefinitionId(), leave.getId(), variables);
	}
	
	public Leave getLeaveToTaskForm(String taskId)
	{
//		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
//											processInstanceId(task.getProcessInstanceId()).singleResult();
		Task task = actFlowService.getTask(taskId);
		LOGGER.info("task.." + task);
		HistoricProcessInstance historicProcessInstance = actFlowService.getHistoricProcessInstance(task.getProcessInstanceId());
		String leaveId = historicProcessInstance.getBusinessKey();
		LOGGER.info("leaveId... " + leaveId);
		Leave leave =leaveService.findByLeaveCode(leaveId);
		return leave;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
	public void handleTaskForm(Leave leave) {
		Map<String, Object> variables = actFlowService.getVariables(leave.getTaskId());
		variables.put(VACATION_APPROVED_KEY, leave.getVacationApproved());
		variables.put(RESEND_REQUEST_KEY, leave.getResendRequest());
		actFlowService.commitTask(leave.getTaskId(), null,variables);
		
		leaveService.update(leave);
	}

	public List<HistoricProcessInstance> getHistoricProcessinstanceList() {
		return historyService.createHistoricProcessInstanceQuery()
				  .finished()
				  .orderByProcessDefinitionId().asc()
				  .orderByProcessInstanceEndTime().desc()
				  .list();
	}

}
