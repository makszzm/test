package com.sky.service.handler;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.common.session.SessionMap;
import com.sky.common.util.MessageUtil;
import com.sky.common.util.StatusConst;
import com.sky.po.User;
import com.sky.service.UserInfDeal;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxMessageHandler;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.exception.WxErrorException;

@Service
public class WxHandler implements WxMessageHandler {
	private static Logger logger = Logger.getLogger(WxHandler.class);
	static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private UserInfDeal userInfDeal;

	@Override
	public WxXmlOutMessage handle(WxXmlMessage wxMessage, Map<String, Object> context, IService iService)
			throws WxErrorException {
		/**
		 * 获取用户的Id和状态 以及消息类型和事件类型以及消息内容
		 */
		String openId = wxMessage.getFromUserName();
		String status = SessionMap.getSessionStatus(openId);
		WxXmlOutMessage xmlOutMsg = null;
		String messageType = wxMessage.getMsgType();
		String eventType = wxMessage.getEvent();
		String content = wxMessage.getContent();
		switch (messageType) {
		/**
		 * 如果是文本类型
		 */
		case MessageUtil.MESSAGE_TEXT:
			switch (status) {
			// 如果用户是开始状态
			case StatusConst.START_STATUS:
				if (!(content.equals(StatusConst.BIRTHDAY_STATUS) || content.equals(StatusConst.PERSONAL_STATUS))) {
					xmlOutMsg = constructMessage(MessageUtil.initMessage(), openId, wxMessage.getToUserName());
				} else {
					if (content.equals(StatusConst.BIRTHDAY_STATUS)) {
						xmlOutMsg = constructMessage(
								"请输入您的生日信息，\n如果是国历生日请输入:张三,G,1991-01-01\n如果是农历生日请输入:李四,N,1991-1-1（第一个1表示农历的月，第二个1表示农历的日，请不要输入腊月之类的字）\n请填写真实姓名，谢谢",
								openId, wxMessage.getToUserName());
						SessionMap.saveData(StatusConst.USER_STATUS, content, openId);
					} else if (content.equals(StatusConst.PERSONAL_STATUS)) {
						User user = userInfDeal.selectByPrimaryKey(openId);
						String appellation = user.getAppellation();
						if (appellation == null || appellation.equals("")) {
							xmlOutMsg = constructMessage("请录入生日信息后再点击查看", openId, wxMessage.getToUserName());
						} else {
							xmlOutMsg = constructMessage("您的身份是:" + appellation+".请不要轻易透露~~~", openId, wxMessage.getToUserName());
						}
					}
				}
				break;
			case StatusConst.BIRTHDAY_STATUS:
				if(content.equals("0")){
					SessionMap.saveData(StatusConst.USER_STATUS, StatusConst.START_STATUS, openId);
					xmlOutMsg = constructMessage(MessageUtil.initMessage(), openId, wxMessage.getToUserName());
					break;
				}
				String result = dealBirthday(openId, content);
				if (result.equals("录入成功")) {
					SessionMap.saveData(StatusConst.USER_STATUS, StatusConst.START_STATUS, openId);
				} else {
					SessionMap.saveData(StatusConst.USER_STATUS, StatusConst.BIRTHDAY_STATUS, openId);
					result += "您可以重新录入，或者按0返回上一级.";
				}
				xmlOutMsg = constructMessage(result, openId, wxMessage.getToUserName());
			}
			break;
			/**
			 * 如果是事件类型
			 */
		case MessageUtil.MESSAGE_EVENT:
			if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType))
				xmlOutMsg = constructMessage(MessageUtil.initMessage(), openId, wxMessage.getToUserName());
		}
		/*
		 * xmlOutMsg =
		 * WxXmlOutMessage.TEXT().content("恭喜你猜对了").toUser(wxMessage.
		 * getFromUserName()) .fromUser(wxMessage.getToUserName()).build();
		 */
		return xmlOutMsg;
	}

	private WxXmlOutMessage constructMessage(String message, String toUser, String fromUser) {
		return WxXmlOutMessage.TEXT().content(message).toUser(toUser).fromUser(fromUser).build();
	}

	private String dealBirthday(String openId, String content) {
		String[] contents = content.split(",");
		if (contents.length != 3) {
			return "输入的信息有误，请输入姓名，生日类别，生日日期.";
		}
		String name = contents[0];
		if (name.length() <= 0) {
			return "姓名长度至少一位.";
		}
		String type = contents[1];
		if (!(type.equalsIgnoreCase("n") || type.equalsIgnoreCase("g"))) {
			return "生日类别只能是N或者G.";
		}
		String birthday = contents[2];
		try {
			chineseDateFormat.parse(birthday);
		} catch (Exception e) {
			logger.error(e, e);
			return "生日日期格式错误.";
		}
		User user = userInfDeal.selectByPrimaryKey(openId);
		// 如果用户已经存在，那么久更新,否则插入
		try {
			if (null == user) {
				user = new User();
				user.setBirthday(type + birthday);
				user.setId(openId);
				user.setName(name);
				userInfDeal.insertSelective(user);
			} else {
				user.setBirthday(type + birthday);
				user.setId(openId);
				user.setName(name);
				userInfDeal.updateByPrimaryKey(user);
			}
			return "录入成功.";
		} catch (Exception e) {
			return "录入失败,请联系管理员.";
		}

	}
}
