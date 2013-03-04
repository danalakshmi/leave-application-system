package com.paul.workflow.plugins.service;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

public interface ActivitiPluginService {
	/**
	 * 获取角析xml流程对象
	 * @param processDefId
	 * @return
	 */
	public ProcessDefinitionEntity getProcessDefinition(String processDefId);
	/**
	 * 任务回退
	 * @param taskId
	 */
	public void rollBackTask(String taskId);
	/**
	 * 任务回退(true：自动分配，false:查找之前的执行人员)
	 * @param taskId
	 * @param autoAssign
	 */
	public void rollBackTask(String taskId,boolean autoAssign);
	/**
	 * 任务回收
	 * @param taskId
	 */
	public void recycleTask(String taskId);
	/**
	 * 任务回收(true：自动分配，false:查找之前的执行人员)
	 * @param taskId
	 */
	public void recycleTask(String taskId,boolean autoAssign);
	/**
	 * 取任务节点formKey
	 * @param taskId
	 */
	public String getFormKey(String taskId);
	/**
	 * 强制结束流程实例
	 * @param processInstanceId
	 * @param deleteReason
	 */
	public void endProcessInstance(String processInstanceId, String deleteReason);
}
