package cn.demi.bus.pjt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.pjt.po.Scheme;

public interface ISchemeDao extends IBaseDao<Scheme> {
	
	List<Scheme> listByPjId(String pjId);
	/**
	 * 自送样 获取唯一方案
	 * @param pjId
	 * @return
	 */
	Scheme find4Zsy(String pjId);
	/**
	 * 根据项目id删除方案信息
	 * @param pjId
	 */
	void deleteByProjectId(String pjId);
}

