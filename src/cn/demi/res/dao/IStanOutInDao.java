package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.StanOutIn;

public interface IStanOutInDao extends IBaseDao<StanOutIn> {
	
	public List<String[]> countReagent(String name);

	@Override
	public void add(StanOutIn p);
}

