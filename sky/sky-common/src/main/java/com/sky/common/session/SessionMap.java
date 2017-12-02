package com.sky.common.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.sky.common.util.StatusConst;

public class SessionMap {
	private static Logger logger = Logger.getLogger(SessionMap.class);
	public static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

	private static Session getSession(String openId) {
		return sessionMap.get(openId);
	}

	public static Object getSessionData(String openId, String name) {
		createSession(openId);
		return getSession(openId).getSessionData().get(name);
	}

	public static void remove(String openId) {
		sessionMap.remove(openId);
	}

	public static void saveData(String name, Object value, String openId) {
		createSession(openId);
		getSession(openId).getSessionData().put(name, value);
	}

	/**
	 * 创建或者更新Session
	 * 
	 * @param openId
	 */
	public static void createSession(String openId) {
		logger.info("create Session:" + openId);
		if (!sessionMap.containsKey(openId)) {
			Session session = new Session();
			session.setValidaTime(10 * 60 * 1000);
			session.setCreateTime(System.currentTimeMillis());
			session.getSessionData().put(StatusConst.USER_STATUS, StatusConst.START_STATUS);
			sessionMap.put(openId, session);
		} else {
			Session session = sessionMap.get(openId);
			session.setValidaTime(10 * 60 * 1000);
			session.setCreateTime(System.currentTimeMillis());
		}
	}

	public static void removeData(String openId) {
		sessionMap.remove(openId);
	}

	public static String getSessionStatus(String openId) {
		createSession(openId);
		return (String) getSessionData(openId, StatusConst.USER_STATUS);
	}
}
