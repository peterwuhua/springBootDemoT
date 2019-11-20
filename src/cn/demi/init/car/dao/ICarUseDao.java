package cn.demi.init.car.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.init.car.po.CarUse;

public interface ICarUseDao extends IBaseDao<CarUse> {
	
	/**
	 * 生成编号
	 * @return
	 */
	String createCode();
	
	/**
	 * 删除所有设备信息
	 */
	public void deleteByBusId(String busId);
	
	List<CarUse> findByBusId(String busId);
}

