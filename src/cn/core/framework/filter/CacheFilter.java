package cn.core.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <strong>Create on : 2016年11月23日 上午8:18:39 </strong> <br>
 * <strong>Description :缓存</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public class CacheFilter implements Filter {

	private FilterConfig filterConfig;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) resp;
	    String uri = request.getRequestURI();  //获取用户想访问的资源
	    String ext = uri.substring(uri.lastIndexOf(".")+1);//得到用户想访问的资源的后缀名
	    String time = filterConfig.getInitParameter(ext);//得到资源需要缓存的时间
	    if(time!=null){
	      long t = Long.parseLong(time)*3600*1000;
	      //设置缓存
	      response.setDateHeader("expires", System.currentTimeMillis() + t);
	    }
	    chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
