package cn.demi.res.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.demi.res.dao.IApparaRecordDao;
import cn.demi.res.po.ApparaRecord;

/** <strong>Create on : 2017年2月28日 下午1:25:03 </strong> <br>
 * <strong>Description : </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
@Repository("res.apparaRecordDao")
public class ApparaRecordDaoImpl extends BaseDaoImpl<ApparaRecord> implements IApparaRecordDao {

	@Override
	public List<ApparaRecord> apparaRecordListByAppId(String id, String type){
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("type", QueryCondition.EQ,
				type));
		queryList.add(new QueryCondition("appara.id", QueryCondition.EQ,
				id));
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ,
				Po.N));
		List<OrderCondition> orderList = new ArrayList<OrderCondition>();
		orderList.add(new OrderCondition(OrderCondition.ORDER_DESC, "date"));
		return super.list(queryList, null, -1, -1);
	}

	@Override
	public List<ApparaRecord> listOrderByDate(String type,String appId) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ,
				Po.N));
		queryList.add(new QueryCondition("type", QueryCondition.EQ,
				type));
		queryList.add(new QueryCondition("appara.id", QueryCondition.EQ,
				appId));
		List<OrderCondition> orderList = new ArrayList<OrderCondition>();
		orderList.add(new OrderCondition("date", OrderCondition.ORDER_DESC));
		return super.list(queryList, orderList, -1, -1);
	}

	@Override
	public List<ApparaRecord> listByTime(String startTime, String endTime,String type) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("date >='"+startTime+ "' and date <='" + endTime+"'"));
		queryList.add(new QueryCondition("type", QueryCondition.EQ,
				type));
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ,
				Po.N));
		return super.list(queryList, null, -1, -1);
	}
	
	
}
