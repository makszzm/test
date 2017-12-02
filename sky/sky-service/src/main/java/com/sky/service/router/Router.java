package com.sky.service.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.service.handler.WxHandler;
import com.sky.service.matcher.WxMatcher;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxMessageRouter;

@Service
public class Router {
	@Autowired
	private WxMatcher wxMatcher;
	@Autowired
	private WxHandler wxHandler;
	public WxMessageRouter getRouter(IService iService){
		WxMessageRouter router = new WxMessageRouter(iService);
		router.rule().matcher(wxMatcher).handler(wxHandler).end();
		return router;
	}
}
