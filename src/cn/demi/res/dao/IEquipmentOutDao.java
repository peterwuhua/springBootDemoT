package cn.demi.res.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.EquipmentOut;

public interface IEquipmentOutDao extends IBaseDao<EquipmentOut> {
	/**
	 * 根据业务Id删除出库记录
	 * 采样安排 使用
	 * @param busId
	 */
	void deleteByBusId(String busId);
}

