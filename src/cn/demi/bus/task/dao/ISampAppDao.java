package cn.demi.bus.task.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.task.po.SampApp;

public interface ISampAppDao extends IBaseDao<SampApp> {
	/**
	 * 删除所有设备信息
	 */
	public void deleteByBusId(String busId);
	
	List<SampApp> findByBusId(String busId);
}

