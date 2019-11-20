package cn.core.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.core.framework.log.Logger;

public class PermissionInterceptor extends HandlerInterceptorAdapter{
	public static Logger log = Logger.getLogger(PermissionInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) 
			return super.preHandle(request, response, handler);
		
		HandlerMethod methodHandler = (HandlerMethod) handler;  
		
		RequiresPermissions requiresPermissions = methodHandler.getMethodAnnotation(RequiresPermissions.class);  
		if(null!=requiresPermissions)
			return super.preHandle(request, response, handler);
		
		CheckPermission checkPermission = methodHandler.getMethodAnnotation(CheckPermission.class);  
		if(null==checkPermission)
			return super.preHandle(request, response, handler);
		
		String uri = request.getRequestURI();
		uri = uri.substring(1,uri.lastIndexOf("."));
		String permission = uri.replace("/", ":");
		SecurityUtils.getSubject().checkPermissions(permission);
		return super.preHandle(request, response, handler);
	}
}
