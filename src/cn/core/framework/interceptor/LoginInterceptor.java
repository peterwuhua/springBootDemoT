package cn.core.framework.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import cn.core.framework.log.Logger;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.service.ILogRecordService;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	public static Logger log = Logger.getLogger(LoginInterceptor.class);
	@Autowired
	ILogRecordService logRecordService;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url = urlPathHelper.getLookupPathForRequest(request);
		if("/login.do".equals(url)||"/logout.do".equals(url)){
			 String[] stringArrays = new String[6];
			 stringArrays[0] = request.getRemoteAddr();//地址
			 stringArrays[1] = "系统管理";//操作模块
			if("/login.do".equals(url)) {
		        stringArrays[2] ="登陆";//操作动作
		        stringArrays[3] ="系统登陆";//描述
		        stringArrays[4] = "login";//操作方法
			}else {
				stringArrays[2] ="退出";//操作动作
		        stringArrays[3] ="系统退出";//描述
		        stringArrays[4] = "logout";//操作方法
			}
			if(null!=CurrentUtils.getCurrent()){
				stringArrays[5] =CurrentUtils.getCurrent().getKey();//用户
				 logRecordService.add4Login(stringArrays);
			}else if(null!=request.getParameter("loginname")){
				stringArrays[5] ="【"+request.getParameter("loginname")+"】";//用户
				logRecordService.add4Login(stringArrays);
			}
			return super.preHandle(request, response, handler);
		}
		if("/register.do".equals(url)) {
			return super.preHandle(request, response, handler);
		}
		/*
		if(SecurityUtils.getSubject().isAuthenticated()) {  
            return true;//已经登录过  
        }else{
			log.info("无登录凭证，跳转登录页面");
			response.sendRedirect(request.getContextPath()+"/login.do");
			return false;
		}*/
		
		return super.preHandle(request, response, handler);
	}
}
