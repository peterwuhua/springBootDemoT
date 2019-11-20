package cn.core.framework.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.core.framework.utils.XMLUtils;
/**
 * <strong>Create on : 2016年11月2日 下午1:03:31 </strong> <br>
 * <strong>Description : SpringListener</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong></strong><br>
 */
@Component("Initializable")
public class SpringListener extends
		org.springframework.web.context.ContextLoaderListener {
	private static Logger log = LoggerFactory.getLogger(SpringListener.class);
	@Override
	public void contextInitialized(ServletContextEvent event) {
		log.info("--------------Spring contextInitialized--------------");
		ServletContext servletContext = event.getServletContext();
		try {
			servletContext.setAttribute("config",XMLUtils.xml2Map("resources/config.xml"));
		} catch (Exception e) {
			log.info("加载 config 文件异常", e);
		}
		super.contextInitialized(event);
	}

}
