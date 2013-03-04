package com.paul.common.util.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ContextLoaderListener子类.
 * 把ApplicationContext赋给SpringContextUtil的静态变量Context.
 */
public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {
    public void contextInitialized(ServletContextEvent event) {
    	ServletContext context = event.getServletContext();
        super.contextInitialized(event);
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        SpringContextUtil.setApplicationContext(ctx);
    }
}
