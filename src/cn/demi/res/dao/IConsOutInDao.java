package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.ConsOutIn;

public interface IConsOutInDao extends IBaseDao<ConsOutIn> {
	
	public List<String[]> countReagent(String name);
}

