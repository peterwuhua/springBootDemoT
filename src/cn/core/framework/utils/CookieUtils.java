package cn.core.framework.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Create on : 2016年11月3日 上午8:45:15  <br>
 * Description : CookieUtils <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public class CookieUtils {
	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:43:36 <br>
	 * Description :  设置cookie（接口方法）<br>
	 * @param response response
	 * @param name cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期 以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:46:07 <br>
	 * Description :  根据名字获取cookie（接口方法）<br>
	 * @param request request
	 * @param name cookie名字
	 * @return Cookie
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:46:58 <br>
	 * Description :  removeCookieByName <br>
	 * @param request request
	 * @param response response
	 * @param name cookie名字
	 */
	public static void removeCookieByName(HttpServletRequest request,
			HttpServletResponse response, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = cookieMap.get(name);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:47:30 <br>
	 * Description : 将cookie封装到Map里面（非接口方法） <br>
	 * @param request request
	 * @return Map
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
