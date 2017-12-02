package com.sky.service;

import com.sky.po.ValidateModel;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;

public interface WxMessageDeal {
	public WxXmlOutMessage deal(WxXmlMessage wxXmlMessage);

	public boolean validate(ValidateModel vModel);
}
