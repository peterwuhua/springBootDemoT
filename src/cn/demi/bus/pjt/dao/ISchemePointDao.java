package cn.demi.bus.pjt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.pjt.po.SchemePoint;

public interface ISchemePointDao extends IBaseDao<SchemePoint> {
	/**
	 * 根据方案id删除方案详细信息
	 * @param schId
	 */
	void deleteBySchId(String schId);
	/**
	 * 根据项目id删除所有方案详细信息
	 * @param pid
	 */
	void deleteByProjectId(String pjId);
	/**
	 * 获取方案详情集合
	 * @param schId
	 */
	List<SchemePoint> listBySchId(String schId);
	/**
	 * 获取方案 下点位序号
	 * @param schId
	 */
	int findMaxSort(String schId);
	/**
	 * 获取方案详情集合
	 * 除过 声
	 * @param schId
	 * @param sampTypeIds 声类型ids
	 */
	List<SchemePoint> listBySchId2(String schId,String sampTypeIds);
	/**
	 * 获取 某类 样品 的 点位集合
	 * @param schId
	 * @param sampTypeId
	 */
	List<SchemePoint> listBySchId(String schId,String sampTypeId);
	
}

