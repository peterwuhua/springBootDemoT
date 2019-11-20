package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.ConsumableLog;

public interface IConsumableLogDao extends IBaseDao<ConsumableLog> {

	public List<ConsumableLog> listByConsumableId(String consumableId);
}

