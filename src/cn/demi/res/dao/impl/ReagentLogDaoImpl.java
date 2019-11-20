package cn.demi.res.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.demi.res.dao.IReagentLogDao;
import cn.demi.res.po.ReagentLog;

@Repository("res.reagentLogDao")
public class ReagentLogDaoImpl extends BaseDaoImpl<ReagentLog> implements IReagentLogDao {

	@Override
	public List<ReagentLog> listByReagentId(String reagentId) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("reagent.id", QueryCondition.EQ, reagentId));
		return super.list(queryList, null, -1, -1);
	}
}
