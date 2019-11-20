package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.Reagent;

public interface IReagentDao extends IBaseDao<Reagent> {
	
	public List<String[]> countReagent(String name);
}

