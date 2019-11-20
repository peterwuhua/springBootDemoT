package cn.demi.res.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.demi.res.dao.IMaintenanceDao;
import cn.demi.res.po.Maintenance;

@Repository("res.maintenanceDao")
public class MaintenanceDaoImpl extends BaseDaoImpl<Maintenance> implements IMaintenanceDao {

	@Override
	public List<Maintenance> apparaMaintenanceListByAppId(String id) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("appara.id", QueryCondition.EQ,
				id));
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ,
				Po.N));
		List<OrderCondition> orderList = new ArrayList<OrderCondition>();
		orderList.add(new OrderCondition(OrderCondition.ORDER_DESC, "create_time"));
		return super.list(queryList, null, -1, -1);
	}
}
