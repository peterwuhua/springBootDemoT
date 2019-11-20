package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.Supplier;

public interface ISupplierDao extends IBaseDao<Supplier> {
	public List<String[]> countStandard(String name);
}

