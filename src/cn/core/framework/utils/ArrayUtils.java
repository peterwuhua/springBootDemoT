package cn.core.framework.utils;

public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

	/**
	 * 验证最小长度，数组长度必须大于等于指定值
	 * 
	 * @param array
	 *            需要验证的数组
	 * @param min
	 *            指定数组的最小长度
	 * @return 返回验证结果
	 */
	public static Boolean minLength(Object[] array, Integer min) {
		Boolean flag = Boolean.FALSE;
		if (ObjectUtils.isNotNull(array)) {
			if (array.length >= min) {
				flag = Boolean.TRUE;
			}
		}
		return flag;
	}

	/**
	 * 验证最大，数组长度必须小于等于指定值
	 * 
	 * @param array
	 *            需要验证的数组
	 * @param max
	 *            指定数组的最大长度
	 * @return 返回验证结果
	 */
	public static Boolean maxLength(Object[] array, Integer max) {
		Boolean flag = Boolean.FALSE;
		if (ObjectUtils.isNotNull(array)) {
			if (array.length <= max) {
				flag = Boolean.TRUE;
			}
		}
		return flag;
	}

}
