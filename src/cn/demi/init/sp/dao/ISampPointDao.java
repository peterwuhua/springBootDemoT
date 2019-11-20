package cn.demi.init.sp.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.init.sp.po.SampPoint;

public interface ISampPointDao extends IBaseDao<SampPoint> {
	/**
	 * 获取样品下点位信息
	 * @param sampId
	 * @param name
	 * @return
	 */
	SampPoint findByName(String sampId,String name);
}

