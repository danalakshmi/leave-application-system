package com.paul.index;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	private static final Logger LOGGER = Logger.getLogger(IndexController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/home/index")
	public ModelAndView index() {
		LOGGER.info("GO TO HOME PAGE...");
		return new ModelAndView("console/index");
	}
}
