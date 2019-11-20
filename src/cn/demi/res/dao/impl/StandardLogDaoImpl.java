package cn.demi.res.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.demi.res.dao.IStandardLogDao;
import cn.demi.res.po.StandardLog;

@Repository("res.standardLogDao")
public class StandardLogDaoImpl extends BaseDaoImpl<StandardLog> implements IStandardLogDao {

	@Override
	public List<StandardLog> listByStandardId(String standardId) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("standard.id", QueryCondition.EQ, standardId));
		return super.list(queryList, null, -1, -1);
	}
}
