package com.paul.leave.service;

import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;

import com.paul.leave.valueObject.Leave;

public interface LeaveFlowService {
	
	public void startProcess(Leave leave);
	
	public Leave getLeaveToTaskForm(String taskId);
	
	public void handleTaskForm(Leave leave);
	
	public List<HistoricProcessInstance> getHistoricProcessinstanceList();

}
