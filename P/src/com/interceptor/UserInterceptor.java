package com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.common.tool.util.DateUtil;

public class UserInterceptor extends HandlerInterceptorAdapter {
	private static final String[] IGNORE_URI = { "/reg", "/user/login", "/user/getAllUser","/user/reg","/user/userReg", "/admin/admin_login", "/userLogin",
			"adminLogin","/userLogout","/adminLogout","/init" };


	@Override
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean flag = false;
		String url = request.getRequestURL().toString();
		//System.out.println(">>>: " + url);
		for (String s : IGNORE_URI) {
			if (url.contains(s)) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			String str = (String) request.getSession().getAttribute("admin_login");
			String username = (String) request.getSession().getAttribute("username");
			if (str != null) {
				flag = true;
			}
			System.out.println(username+" >>> " + url +" # " + DateUtil.getCurrentDatetime() + " @ " + getIpAddr(request));
		}
		if (!flag) {
			String login_url = "../user/login";
			response.sendRedirect(login_url);
		}
		return flag;

	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
    }
}
