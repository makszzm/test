package com.sky.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sky.common.util.MenuUtil;
import com.sky.po.ValidateModel;
import com.sky.service.WxMessageDeal;
import com.soecode.wxtools.api.WxConfig;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.exception.AesException;
import com.soecode.wxtools.util.xml.XStreamTransformer;

@Controller
@RequestMapping("/weChat")
public class WeChatController {
	@Autowired
	private WxMessageDeal wxMessageDeal;

	@RequestMapping(method = { RequestMethod.GET })
	public void validate(ValidateModel vModel, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();
		if (wxMessageDeal.validate(vModel)) {
			out.print(vModel.getEchostr());
		}
	}

	@RequestMapping(method = { RequestMethod.POST })
	public void validate(HttpServletRequest request, HttpServletResponse response) throws IOException, AesException {
		PrintWriter out = response.getWriter();
		// 获取encrypt_type 消息加解密方式标识
		String encrypt_type = request.getParameter("encrypt_type");
		WxXmlMessage wxXmlMessage = XStreamTransformer.fromXml(WxXmlMessage.class, request.getInputStream());
		if (encrypt_type != null && "aes".equals(encrypt_type)) {
			// String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String msg_signature = request.getParameter("msg_signature");
			// 微信服务器推送过来的加密消息是XML格式。使用WxXmlMessage中的decryptMsg()解密得到明文。
			wxXmlMessage = WxXmlMessage.decryptMsg(request.getInputStream(), WxConfig.getInstance(), timestamp, nonce,
					msg_signature);
			WxXmlOutMessage xmlOutMsg = wxMessageDeal.deal(wxXmlMessage);
			out.print(WxXmlOutMessage.encryptMsg(WxConfig.getInstance(), xmlOutMsg.toXml(), timestamp, nonce));// 返回给用户。
		} else {
			WxXmlOutMessage xmlOutMsg = wxMessageDeal.deal(wxXmlMessage);
			out.print(xmlOutMsg.toXml());
		}
	}

	@RequestMapping("/createMenu")
	public void createMenu() throws IOException {
		MenuUtil.createMenu(new WxService());
	}
}
