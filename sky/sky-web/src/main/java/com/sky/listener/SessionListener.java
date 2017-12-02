package com.sky.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sky.common.session.SessionThread;
import com.sky.common.session.SessionUtil;

public class SessionListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		SessionThread sessionThread = (SessionThread) ctx.getBean("sessionThread");
		TaskExecutor taskExecutor = (TaskExecutor) ctx.getBean("taskExecutor");
		SessionUtil.execute(sessionThread, taskExecutor);
	}
}
