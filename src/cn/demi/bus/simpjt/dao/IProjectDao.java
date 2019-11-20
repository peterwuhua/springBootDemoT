package cn.demi.bus.simpjt.dao;

import cn.demi.bus.simpjt.po.Project;
import cn.core.framework.common.dao.IBaseDao;

public interface IProjectDao extends IBaseDao<Project> {
	/**
	 * 根据编号获取项目信息
	 * @param no
	 * @return
	 */
	Project findByNo(String no);
}

