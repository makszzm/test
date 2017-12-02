package com.sky.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	// 登陆
	@RequestMapping("/login")
	public String login(HttpSession session, String username, String password) throws Exception {
		// 调用service进行用户身份认证
		session.setAttribute("username", username);
		// 重定向到商品列表页面
		return "redirect:items/queryItems.action";
	}

	// 退出
	@RequestMapping("/logout")
	public String logout(HttpSession session) throws Exception {
		// 调用service进行用户身份认证
		session.invalidate();
		// 重定向到商品列表页面
		return "redirect:items/queryItems.action";
	}
}
