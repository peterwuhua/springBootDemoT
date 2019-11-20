package cn.core.framework.common.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.current.Current;
import cn.core.framework.utils.current.CurrentUtils;
/**
 * <strong>Create on : 2016年11月2日 上午10:08:16 </strong> <br>
 * <strong>Description : 最基础的Action接口，用于处理统一的Action相关操作</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public class Action {
	public Logger log = Logger.getLogger(this.getClass());
	@Autowired(required = false) public HttpServletRequest request;
	@Autowired(required = false) public HttpServletResponse response;
	public static final String SERVER_BASE = (String)ApplicationUtils.getValue("config.server.base");
	
	protected void debug(Object obj) {
		log.debug("[ACTION-DEBUG]" + obj);
	}
	/**
	 * <strong>Create on : 2016年11月17日 上午8:57:41 </strong> <br>
	 * <strong>Description :统一异常处理机制 </strong> <br>
	 * @param request request
	 * @param ex ex
	 * @return String
	 */
	 @ExceptionHandler  
	public ModelAndView exp(Exception e) {
		String msg = "其它异常";
		if (e instanceof SQLException) {
			msg = "操作数据库异常";
		} else if (e instanceof ClassCastException) {
			msg = "数据转换异常";
		} else if (e instanceof NumberFormatException) {
			msg = "数字格式化异常";
		} else if (e instanceof ClassNotFoundException) {
			msg = "找不到类异常";
		} else if (e instanceof ArrayIndexOutOfBoundsException) {
			msg = "下标越界异常";
		} else if (e instanceof IOException) {
			msg = "IO异常";
		} else if (e instanceof NoSuchMethodException) {
			msg = "找不到方法异常";
		} else if (e instanceof NullPointerException) {
			msg = "空指针异常";
		}else if (e instanceof ArithmeticException) {
			msg = "运算异常";
		}else if (e instanceof IllegalArgumentException) {
			msg = "方法参数异常";
		}else if (e instanceof FileNotFoundException) {
			msg = "文件未找到异常";
		}else if (e instanceof UnauthorizedException) {
			msg = "无此权限";
		}else{
			msg = e.getMessage();
		}
		
		ModelAndView mav = new ModelAndView("/exception/exception");
		mav.addObject("msg", msg);
		mav.addObject("ex", e);
		log.warn(msg, e);
		return mav;
	}
	/**
	 * <strong>Create on : Carson Liu 2016年11月2日 上午10:08:35 </strong> <br>
	 * <strong>Description : 获取系统真实路径</strong> <br>
	 * @param request  request参数
	 * @return 系统真实路径
	 */
	public String getRealPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}
	
	/**
	 * <strong>Create on : Carson Liu 2016年11月2日 上午10:09:12 </strong> <br>
	 * <strong>Description : 获取当前登录current对象</strong> <br>
	 * @return 当前登录的current对象
	 */
	public Current getCurrent() {
		return CurrentUtils.getCurrent();
	}

}
