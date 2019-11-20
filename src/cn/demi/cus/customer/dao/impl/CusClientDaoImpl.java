package cn.demi.cus.customer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.demi.cus.customer.dao.ICusClientDao;
import cn.demi.cus.customer.po.CusClient;
/**
 * Create on : 2016年11月15日 下午2:43:56  <br>
 * Description : CusClientDaoImpl <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Repository("cus.cus_clientDao")
public class CusClientDaoImpl extends BaseDaoImpl<CusClient> implements ICusClientDao {

	@Override
	public List<CusClient> findByClientId(String clientId) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("client.id", QueryCondition.EQ,clientId));
		List<CusClient> list = super.list(queryList, null);
		if(null!=list&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public List<CusClient> listBycusId(String cusId) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("customer.id", QueryCondition.EQ,cusId));
		return super.list(queryList, null);
	}

	@Override
	public List<CusClient> listBycusIds(String ...cusId) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("customer.id in ('"+toString(cusId).replace(",", "','")+"')" ));
		return super.list(queryList, null);
	}

	@Override
	public CusClient findByClientIdAndCustId(String clientId, String custId) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("client.id", QueryCondition.EQ,clientId));
		queryList.add(new QueryCondition("customer.id", QueryCondition.EQ,custId));
		List<CusClient> list = super.list(queryList, null);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
