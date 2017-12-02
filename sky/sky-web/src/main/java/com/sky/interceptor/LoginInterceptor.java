package com.sky.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * ��½��֤������
 * 
 * @author zhouzhimin
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	// 进入Handler方法之前执行
	// 用于身份认证和身份授权
	// 比如身份认证，如果认证不通过表示当前用户没有登陆，需要此方法拦截不再向下执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取请求的url
		String url = request.getRequestURI();
		// 判断url是否是公开地址(实际使用时将公开地址配置到配置文件中)
		// 这里公开地址是登陆提交的地址
		if (url.indexOf("login.action") >= 0) {
			return true;
		}
		// 判断session
		HttpSession session = request.getSession();
		// 从session中取出用的身份信息
		String username = (String) session.getAttribute("username");
		if (username != null) {
			// 身份信息存在,放行
			return true;
		}
		// 执行这里表示用户身份需要认证,跳转到登陆页面
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);

		return false;
	}

	// 进入handler方法之后，返回ModelAndView之前执行
	// 应用场景是从modelAndView出发：将公用的模型数据(比如菜单导航)在这里传递到视图,也可以在这里统一指定视图
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("HandlerIntegerceptor1...postHandler");
	}

	// 执行Handler完成执行此方法
	// 应用场景：统一异常处理 统一日志处理
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("HandlerIntegerceptor1...afterCompletion");
	}

}
