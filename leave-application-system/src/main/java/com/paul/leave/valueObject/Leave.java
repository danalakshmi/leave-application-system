package com.paul.leave.valueObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "LEAVE")
public class Leave implements java.io.Serializable{

	private static final long serialVersionUID = -4260996056340122226L;
	
	private String id;
	private String employeeName;
	private int numberOfDays;
	private String vacationMotivation;
	private boolean vacationApproved;
	private String managerMotivation;
	
	private String processDefinitionId;
	private String taskId;
	private boolean resendRequest;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "EMPLOYEE_NAME", nullable = false, length = 255)
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Column(name = "NUMBER_OF_DAYS", nullable = false, precision = 5, scale = 0)
	public int getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	
	@Column(name = "VACATION_MOTIVATION", nullable = false, length = 500)
	public String getVacationMotivation() {
		return vacationMotivation;
	}
	public void setVacationMotivation(String vacationMotivation) {
		this.vacationMotivation = vacationMotivation;
	}
	
	@Column(name = "VACATION_APPROVED", nullable = false, length = 255)
	public boolean getVacationApproved() {
		return vacationApproved;
	}
	public void setVacationApproved(boolean vacationApproved) {
		this.vacationApproved = vacationApproved;
	}
	
	@Column(name = "MANAGER_VACATION", nullable = true, length = 500)
	public String getManagerMotivation() {
		return managerMotivation;
	}
	public void setManagerMotivation(String managerMotivation) {
		this.managerMotivation = managerMotivation;
	}
	
	@Transient
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	
	@Transient
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@Transient
	public boolean getResendRequest() {
		return resendRequest;
	}
	public void setResendRequest(boolean resendRequest) {
		this.resendRequest = resendRequest;
	}
	
	
}