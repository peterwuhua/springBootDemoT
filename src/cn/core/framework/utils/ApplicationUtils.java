package cn.core.framework.utils;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.jxpath.JXPathContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <strong>Author : </strong> <br>
 * <strong>Create on : 2015年3月28日 下午5:36:09 </strong> <br>
 * <strong>Log : </strong> <br>
 * <div> 用一句话描述该类用于干什么 </div>
 */
public class ApplicationUtils {
	private static Map<String, Object> config = XMLUtils.xml2Map("resources/config.xml");

	private static ServletContext servletContext;

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext) {
		ApplicationUtils.servletContext = servletContext;
	}

	public static Object getBean(String beanName) {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		return wac.getBean(beanName);
	}

	// @SuppressWarnings("rawtypes")
	public static Object getValue(String name) {

		// if(null == servletContext){
		return getValue2(name);
		// }
		/*
		 * if(name.indexOf(".")==-1) return servletContext.getAttribute(name);
		 * 
		 * String configName = name.substring(0,name.indexOf(".")); String
		 * configPath = name.substring(name.indexOf(".")+1); configPath =
		 * configPath.replace(".", "/"); Map config = (Map)
		 * servletContext.getAttribute(configName); JXPathContext jxPath =
		 * JXPathContext.newContext(config); return jxPath.getValue(configPath);
		 */
	}
	
	public static Object getValue2(String name) {
		if (name.indexOf(".") == -1)
			return null;

		String configName = name.substring(0, name.indexOf("."));
		String configPath = name.substring(name.indexOf(".") + 1);
		configPath = configPath.replace(".", "/");
		JXPathContext jxPath = null;
		if ("setting".equals(configName)) {
			return null;
		} else if ("config".equals(configName)) {
			jxPath = JXPathContext.newContext(config);
			return jxPath.getValue(configPath);
		}
		return null;
	}
}
