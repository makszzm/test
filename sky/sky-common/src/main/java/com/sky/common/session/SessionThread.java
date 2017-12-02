package com.sky.common.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SessionThread implements Runnable {
	private static Logger logger = Logger.getLogger(SessionThread.class);

	@Override
	public void run() {
		long currentTime = System.currentTimeMillis();
		if (SessionMap.sessionMap.isEmpty()) {
			logger.info("目前没有session");
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			Map<String, Session> map = SessionMap.sessionMap;
			List<String> openIds = new ArrayList<String>();
			for (Map.Entry<String, Session> entry : map.entrySet()) {
				Session session = entry.getValue();
				String openId = entry.getKey();
				if (currentTime > session.getCreateTime() + session.getValidaTime()) {
					openIds.add(openId);
				}
			}
			for (String openId : openIds) {
				logger.info(openId+"过期");
				map.remove(openId);
			}
		}
	}

}
