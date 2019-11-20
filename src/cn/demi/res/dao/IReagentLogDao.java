package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.ReagentLog;

public interface IReagentLogDao extends IBaseDao<ReagentLog> {

	public List<ReagentLog> listByReagentId(String reagentId);
}

