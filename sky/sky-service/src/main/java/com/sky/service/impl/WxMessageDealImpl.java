package com.sky.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.po.ValidateModel;
import com.sky.service.WxMessageDeal;
import com.sky.service.router.Router;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;

@Service
public class WxMessageDealImpl implements WxMessageDeal {
	@Autowired
	private IService iService;
	@Autowired
	private Router router;
	private Logger logger = Logger.getLogger(WxMessageDealImpl.class);

	@Override
	public WxXmlOutMessage deal(WxXmlMessage wxXmlMessage) {
		try {
			logger.info("消息：\n " + wxXmlMessage.toString());
			// 添加规则；这里的规则是指所有消息都交给交给DemoHandler处理
			// 注意！！每一个规则，必须由end()或者next()结束。不然不会生效。
			// end()是指消息进入该规则后不再进入其他规则。 而next()是指消息进入了一个规则后，如果满足其他规则也能进入，处理。
			// 创建一个路由器
			// 把消息传递给路由器进行处理，得到最后一个handler处理的结果
			WxXmlOutMessage xmlOutMsg = router.getRouter(iService).route(wxXmlMessage);
			if (xmlOutMsg != null) {
				return xmlOutMsg;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}

	@Override
	public boolean validate(ValidateModel vModel) {
		return iService.checkSignature(vModel.getSignature(), vModel.getTimestamp(), vModel.getNonce(),
				vModel.getEchostr());
	}

}
