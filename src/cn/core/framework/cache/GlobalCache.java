package cn.core.framework.cache;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;

import cn.core.framework.utils.current.CurrentUtils;
/**
 * 全局的数据缓存对象，缓存域包含两个部分：<br>
 * 1：内存和硬盘缓存<br>
 * 2：session缓存（如果用户尚未登录的情况下，使用session缓存<br>
 * <strong>Create on : 2016年11月2日 上午10:00:47 </strong> <br>
 * <strong>Description : </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public class GlobalCache {
	private Integer count = 0;
	private static GlobalCache cache = new GlobalCache();
	private static Map<String, Object> cacheMap = new HashMap<String, Object>();
	private static Map<String, Long> lastGetTime = new HashMap<String, Long>();
	/**
	 * <strong>Create on :Carson Liu 2016年11月2日 上午10:01:28 </strong> <br>
	 * <strong>Description : 构造器</strong> <br>
	 */
	private GlobalCache() {
	}
	/**
	 * <strong>Create on :Carson Liu 2016年11月2日 上午10:01:25 </strong> <br>
	 * <strong>Description : 放值</strong> <br>
	 * @param ticket ticket
	 * @param key key
	 * @param value  value
	 */
	public void put(String ticket, String key, Object value) {
		cache.putInCache(ticket + "_" + key, value);
	}
	/**
	 * <strong>Create on :Carson Liu 2016年11月2日 上午10:04:15 </strong> <br>
	 * <strong>Description : 放值</strong> <br>
	 * @param string  key
	 * @param value value
	 */
	private void putInCache(String string, Object value) {
		count++;
		cacheMap.put(string, value);
		lastGetTime.put(string, System.currentTimeMillis());
	}
	/**
	 * <strong>Create on :Carson Liu 2016年11月2日 上午10:04:22 </strong> <br>
	 * <strong>Description : 取值</strong> <br>
	 * @param ticket 
	 * @param key  缓存对象标识，之后以此来获取对象
	 * @return Object 
	 */
	public Object get(String ticket, String key) {
		return cache.getFromCache(ticket + "_" + key);
	}
	/**
	 * <strong>Create on : Carson Liu 2016年11月2日 上午10:04:28 </strong> <br>
	 * <strong>Description : 取值</strong> <br>
	 * @param string  缓存对象标识，之后以此来获取对象
	 * @return Cache 对象
	 */
	private Object getFromCache(String string) {
		lastGetTime.put(string, System.currentTimeMillis());
		return cacheMap.get(string);
	}

	/**
	 * <strong>Create on : Carson Liu 2016年11月2日 上午10:04:40 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param request 用户的Request请求对象，以此判断用户是否登录，如果登录的话，将缓存对象到缓存池内，负责缓存到session之内
	 * @param key 缓存对象标识，之后以此来获取对象
	 * @param value 需要进行缓存的对象
	 */
	public void put(HttpServletRequest request, String key, Object value) {
		String ticket = getTicket(request);
		if (StringUtils.isBlank(ticket)) {
			WebUtils.setSessionAttribute(request, key, value);
		} else {
			cache.putInCache(ticket + "_" + key, value);
		}
	}

	/**
	 * <strong>Create on : Carson Liu 2016年11月2日 上午10:04:58 </strong> <br>
	 * <strong>Description : 移除指定的缓存对象</strong> <br>
	 * @param request 用户的Request请求对象，以此判断用户是否登录，如果是已登录用户，将同时从用户的缓存域之内移除对象
	 * @param key  需要移除的对象key
	 */
	public void remove(HttpServletRequest request, String key) {
		String ticket = getTicket(request);
		request.getSession().removeAttribute(key);
		if (StringUtils.isNotBlank(ticket)) {
			cache.flushEntry(ticket + "_" + key);
			;
		}
	}
	/**
	 * <strong>Create on : Carson Liu 2016年11月2日 上午10:05:14 </strong> <br>
	 * <strong>Description : 刷新</strong> <br>
	 * @param string 被移除对象
	 */
	private void flushEntry(String string) {
		count--;
		cacheMap.remove(string);
	}

	/**
	 * <strong>Create on : Carson Liu 2016年11月2日 上午10:05:35 </strong> <br>
	 * <strong>Description :获取当前对象 </strong> <br>
	 * @param request 用户的Request请求对象，以此来判断用户是否登录，如果是已登录用户，那么将优先从对象缓存域内获取用户对象
	 * @param key 当前用户对象
	 * @return 当前对象
	 */
	public Object get(HttpServletRequest request, String key) {
		String ticket = getTicket(request);
		if (StringUtils.isNotBlank(ticket)) {
			Object value = cache.getFromCache(ticket + "_" + key);
			if (null != value) {
				return value;
			}
		}

		Object o = WebUtils.getSessionAttribute(request, key);
		return o;
	}
	/**
	 * <strong>Create on :Carson Liu 2016年11月2日 上午10:07:27 </strong> <br>
	 * <strong>Description : 获取Ticket</strong> <br>
	 * @param request 用户的Request请求对象，以此来判断用户是否登录，如果是已登录用户，那么将优先从对象缓存域内获取用户对象
	 * @return  Ticket
	 */
	private String getTicket(HttpServletRequest request) {
		String ticket = request.getParameter(CurrentUtils.TOCKET_COOKIE_KEY);
		if (StringUtils.isBlank(ticket)) {
			Cookie ticketCookie = WebUtils.getCookie(request,
					CurrentUtils.TOCKET_COOKIE_KEY);
			if (null != ticketCookie) {
				ticket = ticketCookie.getValue();
			}
		}
		return ticket;
	}

	public static GlobalCache getInstance() {
		return cache;
	}
}
