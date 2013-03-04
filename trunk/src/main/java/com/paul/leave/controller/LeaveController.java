package com.paul.leave.controller;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.paul.leave.service.LeaveFlowService;
import com.paul.leave.valueObject.Leave;
import com.paul.workflow.service.ActFlowService;


@Controller
public class LeaveController {
	
	
	private static final Logger LOGGER = Logger.getLogger(LeaveController.class);
	
	@Autowired(required = true)
	private ActFlowService actFlowService;
	
	@Autowired(required = true)
	private RepositoryService repositoryService;
	
	@Autowired(required = true)
	private RuntimeService runtimeService;
	
	@Autowired(required = true)
	private TaskService taskService;
	
	@Autowired(required = true)
	private FormService formService;
	
	@Autowired(required = true)
	private LeaveFlowService leaveFlowService;
	
	private static String EMPLOYEE_NAME = "kermit";
	
	public static String LEAVE_ID_KEY = "leaveId";
	
	public LeaveController() {}
	
	@RequestMapping( value = "/leave/start")
	public String start() {
		
		return "leave/start-leave";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/leave/start-process")
	public ModelAndView startProcess(@RequestParam("processDefinitionId") String processDefinitionId, ModelMap modelMap) {
		
		String formKey =formService.getStartFormData(processDefinitionId).getFormKey();
		Assert.notNull(formKey, "formKey不能为空！");
		LOGGER.info("formKey......." + formKey);
		Leave leave = new Leave();
		leave.setProcessDefinitionId(processDefinitionId);
		leave.setEmployeeName(EMPLOYEE_NAME);
		return new ModelAndView(formKey,"leave",leave);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/leave/start-process")
	public void startProcess(@ModelAttribute("leave") Leave leave,
			BindingResult result, SessionStatus status, PrintWriter printWriter) {
//		messageStorage.addMessage(Customer);
		
//		customerValidator.validate(customer, result);
		
		if (result.hasErrors()) {
			//if validator failed
			printWriter.write(result.toString());
//			return new ModelAndView("leave/leaveForm", "leave", leave);
		} else {
			leaveFlowService.startProcess(leave);
			status.setComplete();
			printWriter.write("success");
			//form success
//			return new ModelAndView("forward:instance-list.do");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/leave/get-taskForm")
	public ModelAndView getTaskForm(@RequestParam("taskId") String taskId) {
		
		
		
		Leave leave = leaveFlowService.getLeaveToTaskForm(taskId);
		leave.setTaskId(taskId);
		
		String formKey = formService.getTaskFormData(taskId).getFormKey();
		LOGGER.info("formKey......." + formKey);
		return new ModelAndView(formKey,"leave",leave);
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/leave/handle-task-form")
	public void handleTaskForm(@ModelAttribute("leave") Leave leave,
			BindingResult result, SessionStatus status, PrintWriter printWriter
			) {
//		messageStorage.addMessage(Customer);
		
//		customerValidator.validate(customer, result);
		
		if (result.hasErrors()) {
			//if validator failed
			LOGGER.info(result.toString());
			LOGGER.info(leave.getNumberOfDays() + leave.toString());
			status.setComplete();
			printWriter.write(result.toString());
//			return new ModelAndView("leave/leaveForm", "leave", leave);
		} else {
			leaveFlowService.handleTaskForm(leave);
			status.setComplete();
			printWriter.write("good");
			//form success
//			return new ModelAndView("forward:my-task-list.do");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/leave/show-diagram")
	public void showDiagram(HttpServletRequest request, HttpServletResponse response) throws Exception {

		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			response.setContentType("image/png");
			String processDefinitionId = request.getParameter("processDefinitionId");
			String processInstanceId = request.getParameter("processInstanceId");
			//
			List<String> highLightedActivities = Collections.<String> emptyList();
			
			if (StringUtils.isNotBlank(processInstanceId)) {
				processDefinitionId = actFlowService.getHistoricProcessInstance(processInstanceId).getProcessDefinitionId();
				highLightedActivities = actFlowService.findRunActList(processInstanceId);
			}
			ProcessDefinitionEntity processDefinition = actFlowService.getActivitiPluginService()
								.getProcessDefinition(processDefinitionId);
			inputStream = ProcessDiagramGenerator.generateDiagram(processDefinition, "png", highLightedActivities);
			outputStream = response.getOutputStream();
			BufferedImage image = ImageIO.read(inputStream);
			ImageIO.write(image, "png", outputStream);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		finally
		{
			if(outputStream != null)
				outputStream.close();
			if(inputStream != null)
				inputStream.close();
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/leave/claim-task")
	public void claimTask(@RequestParam("taskId") String taskId, PrintWriter printWriter) {
		
		
		LOGGER.info("claim task......." + taskId);
		taskService.claim(taskId, EMPLOYEE_NAME);
		printWriter.print("success");
//		return "forward:my-task-list.do";
		
	}
	
	@RequestMapping(value = "/leave/my-task-list")
	public ModelAndView showMyTaskList() {
		return new ModelAndView("/leave/my-task-list");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/leave/unsigned-task-list")
	public ModelAndView unsignedTaskList() {
		return new ModelAndView("/leave/unsigned-task-list");
	}
	
	@RequestMapping(value = "/leave/instance-list")
	public ModelAndView instanceList() {
		return new ModelAndView("/leave/instance-list");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/leave/history-instance-list")
	public ModelAndView historyInstanceList() {
		return new ModelAndView("/leave/history-instance-list");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/leave/start-leave")
	public ModelAndView processList() {
		return new ModelAndView("/leave/process-list");
	}

	@RequestMapping(method = RequestMethod.POST, value = "/leave/start-submit2")
	public ModelAndView startSubmit2(@ModelAttribute("leave") Leave leave,
			BindingResult result, SessionStatus status) {
//		messageStorage.addMessage(Customer);
		
//		customerValidator.validate(customer, result);
		
		if (result.hasErrors()) {
			//if validator failed
			return new ModelAndView("leave/leaveForm", "leave", leave);
		} else {
//			LOGGER.info(customerService.addCustomer(customer));
			status.setComplete();
			//form success
			return new ModelAndView("leave/leaveSuccess", "leave", leave);
		}
	}
	
	
	
	@ModelAttribute("processDefinitionList")
	public List<ProcessDefinition> populateProcessDefinitionList() {
		
		List<ProcessDefinition> processDefinitionList = repositoryService
	      .createProcessDefinitionQuery()
	      .processDefinitionKey("Vocation-flow")
	      .orderByProcessDefinitionKey().asc()
	      .orderByProcessDefinitionVersion().desc()
	      .list();
		
		return processDefinitionList;
	}
	
	@ModelAttribute("processInstanceList")
	public List<ProcessInstance> populateProcessInstanceList() {
		
		List<ProcessInstance> processInstanceList = runtimeService
	      .createProcessInstanceQuery()
	      .orderByProcessInstanceId().desc()
	      .list();
		
		return processInstanceList;
	}
	
	@ModelAttribute("historicProcessinstanceList")
	public List<HistoricProcessInstance> populateHistoricProcessinstanceList() {
		
		return leaveFlowService.getHistoricProcessinstanceList();
	}
	
	@ModelAttribute("tasksList")
	public List<Task> populateTaskList() {
		
		List<Task> tasksList = taskService.createTaskQuery().taskAssignee(EMPLOYEE_NAME).orderByTaskCreateTime().desc().list();
		
		return tasksList;
	}
	
	@ModelAttribute("unassignedTaskList")
	public List<Task> populateUnassignedTaskList() {
		
		List<Task> tasksList = taskService.createTaskQuery().taskCandidateUser(EMPLOYEE_NAME).list();
		
		return tasksList;
	}
	
	@ModelAttribute("javaSkillsList")
	public Map<String,String> populateJavaSkillList() {
		
		//Data referencing for java skills list box
		Map<String,String> javaSkill = new LinkedHashMap<String,String>();
		javaSkill.put("Hibernate", "Hibernate");
		javaSkill.put("Spring", "Spring");
		javaSkill.put("Apache Wicket", "Apache Wicket");
		javaSkill.put("Struts", "Struts");
		
		return javaSkill;
	}

	@ModelAttribute("countryList")
	public Map<String,String> populateCountryList() {
		
		//Data referencing for java skills list box
		Map<String,String> country = new LinkedHashMap<String,String>();
		country.put("US", "United Stated");
		country.put("CHINA", "China");
		country.put("SG", "Singapore");
		country.put("MY", "Malaysia");
		
		return country;
	}
}
