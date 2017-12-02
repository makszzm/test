package com.sky.common.session;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;

public class SessionUtil {
	private static Logger logger = Logger.getLogger(SessionThread.class);

	public static void execute(SessionThread sessionThread, TaskExecutor taskExecutor) {
		logger.info("开启Session线程,时间:" + new Date());
		taskExecutor.execute(sessionThread);
	}
}
