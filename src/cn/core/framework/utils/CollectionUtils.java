package cn.core.framework.utils;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("rawtypes")
public class CollectionUtils {

	/**
	 * 判断指定集合是否为空，并且长度等于0
	 * 
	 * @param coll
	 *            需要判定的集合
	 * @return true 集合为空集合 false 非空集合
	 */
	public static Boolean isEmpty(Collection coll) {
		Boolean flag = Boolean.FALSE;
		if (ObjectUtils.isNull(coll)) {
			flag = Boolean.TRUE;
		} else if (coll.size() < 1) {
			flag = Boolean.TRUE;
		}
		return flag;
	}

	/**
	 * 判断指定的集合是否不为空，并且长度大于0
	 * 
	 * @param coll
	 *            需要判定的集合
	 * @return true 集合非空 false 集合为空
	 */
	public static Boolean isNotEmpty(Collection coll) {
		return (!CollectionUtils.isEmpty(coll));
	}

	/**
	 * 删除容器中的所有值为null的对象
	 * 
	 * @param list
	 *            需要进行删除重组的对象
	 * @return 删除null值之后的对象
	 */
	public static <T> List<T> deleteNull(List<T> list) {
		for (T t : list) {
			if (ObjectUtils.isNull(t)) {
				list.remove(t);
			}
		}
		return list;
	}
}
