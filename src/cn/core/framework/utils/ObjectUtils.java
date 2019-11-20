package cn.core.framework.utils;

public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {
	/**
	 * 判断非空
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @return 对象非空 返回true，否则返回false
	 */
	public static Boolean isNotNull(Object obj) {
		return null != obj ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * 判断对象为空
	 * 
	 * @param obj
	 *            需要判断的对象
	 * @return 对象为空 返回true，否则返回false
	 */
	public static boolean isNull(Object obj) {
		return !ObjectUtils.isNotNull(obj);
	}
}
