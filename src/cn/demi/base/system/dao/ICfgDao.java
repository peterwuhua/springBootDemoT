package cn.demi.base.system.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.base.system.po.Cfg;

public interface ICfgDao extends IBaseDao<Cfg> {
	/**
	 * 根据类型和人获取配置
	 * @param userId
	 * @param type
	 * @return
	 */
	public Cfg find(String userId,String type);
	/**
	 * 根据类型获取配置
	 * @param type
	 * @return
	 */
	public Cfg find(String type);
}

