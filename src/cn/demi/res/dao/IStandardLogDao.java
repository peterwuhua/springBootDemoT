package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.StandardLog;

public interface IStandardLogDao extends IBaseDao<StandardLog> {

	public List<StandardLog> listByStandardId(String standardId);
}

