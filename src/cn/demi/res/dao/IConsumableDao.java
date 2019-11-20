package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.Consumable;

public interface IConsumableDao extends IBaseDao<Consumable> {
	public List<String[]> countConsumable(String name);
}

