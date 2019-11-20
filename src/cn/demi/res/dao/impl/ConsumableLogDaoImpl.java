package cn.demi.res.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.demi.res.dao.IConsumableLogDao;
import cn.demi.res.po.ConsumableLog;

@Repository("res.consumableLogDao")
public class ConsumableLogDaoImpl extends BaseDaoImpl<ConsumableLog> implements IConsumableLogDao {

	@Override
	public List<ConsumableLog> listByConsumableId(String consumableId) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("consumable.id", QueryCondition.EQ, consumableId));
		return super.list(queryList, null, -1, -1);
	}
}
