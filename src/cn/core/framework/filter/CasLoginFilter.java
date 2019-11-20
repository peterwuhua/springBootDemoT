package cn.core.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;

import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.vo.AccountVo;
/**
 * <strong>Create on : 2016年11月23日 上午8:18:39 </strong> <br>
 * <strong>Description : 单点登录</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public class CasLoginFilter implements Filter{
	private IAccountService accountService;
	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;  
		
		Object object = httpRequest.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
		if (object != null) {
			Assertion assertion = (Assertion) object;
			String loginName = assertion.getPrincipal().getName();
			try {
				AccountVo accountVo = accountService.find(loginName);
				CurrentUtils.setCurrent(accountVo);
			} catch (GlobalException e) {
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void destroy() {
		
	}
	
}
