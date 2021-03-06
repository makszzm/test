package com.sky.service.interceptor;

import java.util.Map;

import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxMessageInterceptor;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.exception.WxErrorException;

public class WxInterceptor implements WxMessageInterceptor {

	@Override
	public boolean intercept(WxXmlMessage wxMessage, Map<String, Object> context, IService iService)
			throws WxErrorException {
		return true;
	}

}
