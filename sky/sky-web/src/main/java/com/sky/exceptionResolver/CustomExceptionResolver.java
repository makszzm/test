package com.sky.exceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.sky.common.exception.CustomException;

public class CustomExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0, HttpServletResponse arg1, Object handler,
			Exception ex) {
		// handler就是处理器适配器要执行handler对象（只有method)

		// 解析出异常类型
		// String message = null;
		// if (ex instanceof CustomException) {
		// message = ((CustomException) ex).getMessage();
		// } else {
		// message = "未知错误";
		// }
		// 上边的代码变为
		CustomException customException = null;
		if (ex instanceof CustomException) {
			customException = (CustomException) ex;
		} else {
			customException = new CustomException("未知错误");
		}
		String message = customException.getMessage();
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", message);
		// 指定错误页面
		mv.setViewName("error");
		return mv;
	}

}
