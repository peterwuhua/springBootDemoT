package cn.demi.res.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.demi.res.dao.ISupplierEvaluateDao;
import cn.demi.res.po.SupplierEvaluate;

@Repository("res.supplierEvaluateDao")
public class SupplierEvaluateDaoImpl extends BaseDaoImpl<SupplierEvaluate> implements ISupplierEvaluateDao {

	@Override
	public List<SupplierEvaluate> supplierEvaluateListBySupId(String id) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("supplier.id", QueryCondition.EQ,
				id));
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ,
				Po.N));
		return super.list(queryList, null, -1, -1);
	}

}
