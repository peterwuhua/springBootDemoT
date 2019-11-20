package cn.demi.bus.pjt.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.pjt.po.Project;

public interface IProjectDao extends IBaseDao<Project> {
	/**
	 * 根据编号获取项目信息
	 * @param no
	 * @return
	 */
	Project findByNo(String no);
}

