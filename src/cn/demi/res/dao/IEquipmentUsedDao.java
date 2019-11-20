package cn.demi.res.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.EquipmentUsed;

public interface IEquipmentUsedDao extends IBaseDao<EquipmentUsed>{
	/**
	 * 根据业务Id 删除记录
	 * @param busId
	 */
	void deleteByBusId(String busId);
}
