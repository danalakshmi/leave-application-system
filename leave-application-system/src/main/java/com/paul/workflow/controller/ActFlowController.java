package com.paul.workflow.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.paul.leave.controller.LeaveController;
import com.paul.workflow.service.ActFlowService;

@Controller
public class ActFlowController {
	
	private static final Logger LOGGER = Logger.getLogger(LeaveController.class);
	
	@Autowired(required = true)
	private ActFlowService actFlowService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/flow/deploy-process")
	public String deployProcess(@RequestParam("zipFileName") String zipFileName) throws IOException
	{
		actFlowService.deployProcess(zipFileName);
		return "leave/start-leave";
	}

}
