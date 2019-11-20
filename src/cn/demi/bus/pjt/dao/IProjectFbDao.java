package cn.demi.bus.pjt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.pjt.po.ProjectFb;

public interface IProjectFbDao extends IBaseDao<ProjectFb> {
	
	/**
	 * 根据项目id删除分包详细信息
	 * @param pjId
	 */
	void deleteByProjectId(String pjId);
	/**
	 * 获取分包详情集合
	 * @param pjId
	 */
	List<ProjectFb> listByProjectId(String pjId);
}

