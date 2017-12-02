package com.sky.common.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
	private Map<String, Object> sessionData = new HashMap<String, Object>();
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 有效时间
	 */
	private long validaTime;

	public Map<String, Object> getSessionData() {
		return sessionData;
	}

	public void setSessionData(Map<String, Object> sessionData) {
		this.sessionData = sessionData;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getValidaTime() {
		return validaTime;
	}

	public void setValidaTime(long validaTime) {
		this.validaTime = validaTime;
	}

}
