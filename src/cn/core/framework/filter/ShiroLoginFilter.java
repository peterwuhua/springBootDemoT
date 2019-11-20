package cn.core.framework.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import cn.demi.base.system.service.IAccountService;

/**
 * <strong>Create on : 2016年11月23日 上午8:18:39 </strong> <br>
 * <strong>Description : Shiro</strong> <br>
 *
 * @author <strong>Carson Liu</strong><br>
 * @version <strong> v 1.0.0 </strong> <br>
 */
public class ShiroLoginFilter implements Filter {
	@SuppressWarnings("unused")
	private IAccountService accountService;

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = httpRequest.getRequestURI();
		String contextPath = httpRequest.getContextPath();
		Set<String> expSet = new HashSet<String>();

		expSet.add(contextPath + "/logout.do");
		expSet.add(contextPath + "/login.do");
		expSet.add(contextPath + "/");
		if (expSet.contains(uri)) {
			chain.doFilter(request, response);
			return;
		} else if (uri.contains(".js") || uri.contains(".css") || uri.contains(".woff2") || uri.contains(".png")
				|| uri.contains(".jpg") || uri.contains(".doc") || uri.contains(".docx") || uri.contains(".xls")
				|| uri.contains(".xlsx") || uri.contains(".pdf") || uri.contains(".mp4")) {

			chain.doFilter(request, response);
			return;
		} else if (uri.contains("/app_web/") || uri.contains("/bus_web/") || uri.contains("/app_task_xc/")
				|| uri.contains("/app_task_xc_zw/") || uri.contains("/app_office/") || uri.contains("/app_count_web/")
				|| uri.contains("/app_init/") || uri.contains("/app_file_web/") || uri.contains("/app_appara_web/")
				|| uri.contains("app_res_web") || uri.contains("/app_bus_project/") || uri.contains("/app_bus_task/")
				|| uri.contains("/app_office_qj/") || uri.contains("/app_office_car/")) {
			chain.doFilter(request, response);
			return;
		} else if (uri.contains("/poserver.zz")) {
			chain.doFilter(request, response);
			return;
		}

		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			chain.doFilter(request, response);
		} else {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect(contextPath + "/login.do");
			return;
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

}
