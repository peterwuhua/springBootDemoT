package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.Standard;

public interface IStandardDao extends IBaseDao<Standard> {
	public List<String[]> countStandard(String name);
	
	Standard find(String name,String no);
}

