package cn.core.framework.utils.current;

import javax.servlet.http.HttpServletRequest;

import cn.core.framework.cache.GlobalCache;

public class CacheUtils {

	public static final String CURRENT_CACHE_KEY = Constant.User.TOCKET_COOKIE_KEY;

	public static final Current getCurrent(HttpServletRequest request) {
		return (Current) GlobalCache.getInstance().get(request,
				CURRENT_CACHE_KEY);
	}

	public static final String getAccountId(HttpServletRequest request) {
		return getCurrent(request).getAccountId();
	}

	public static final String getUserId(HttpServletRequest request) {
		return getCurrent(request).getUserName();
	}

	public static final String getUserName(HttpServletRequest request) {
		return getCurrent(request).getUserName();
	}

}
