package cn.core.framework.utils;

import java.util.UUID;

/**
 * UUID工具类
 * 
 * @author 2014年6月14日
 */
public class UUIDUtils {
	/**
	 * 获取UUID
	 * 
	 * @return 返回生成的UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
