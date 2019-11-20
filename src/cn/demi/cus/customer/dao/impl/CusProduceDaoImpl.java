package cn.demi.cus.customer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.dao.ICusProduceDao;
import cn.demi.cus.customer.po.CusProduce;

/**
 * Create on : 2016年11月15日 下午2:45:29  <br>
 * Description : CusProduceDaoImpl <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Repository("cus.cus_produceDao")
public class CusProduceDaoImpl extends BaseDaoImpl<CusProduce> implements ICusProduceDao {

	@Override
	public List<CusProduce> listBycusId(String cusId) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("customer.id", QueryCondition.EQ,cusId));
		return super.list(queryList, null);
	}

	@Override
	public CusProduce findByProId(String proId) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("produce.id", QueryCondition.EQ,proId));
		List<CusProduce> list = super.list(queryList, null);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<CusProduce> listBycusIds(String... cusId) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("customer.id in ('"+toString(cusId).replace(",", "','")+"')" ));
		return super.list(queryList, null);
	}
}
