package cn.demi.init.std.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.init.std.po.Method;

public interface IMethodDao extends IBaseDao<Method> {
	/**
	 * 根据标准id获取方法集合
	 * @param standId
	 */
	List<Method> listByStandId(String standId);
	/**
	 * 根据标准id删除方法
	 * @param standId
	 */
	void deleteByStandId(String standId);
	/**
	 * 根据标准id假删除方法
	 * @param standId
	 */
	void update4Del(String standId);
}

