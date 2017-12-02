package com.sky.service.matcher;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.soecode.wxtools.api.WxMessageMatcher;
import com.soecode.wxtools.bean.WxXmlMessage;

@Service
public class WxMatcher implements WxMessageMatcher {
	private Logger logger = Logger.getLogger(WxMatcher.class);
	// 答案是Matcher，如果匹配Matcher返回true；反之，false。
	public boolean match(WxXmlMessage message) {
		String content = message.getContent();
		logger.info("获取到信息:"+content);
		return true;
	}
}