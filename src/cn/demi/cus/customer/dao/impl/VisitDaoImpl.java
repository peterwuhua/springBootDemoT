package cn.demi.cus.customer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.dao.IVisitDao;
import cn.demi.cus.customer.po.Visit;

@Repository("cus.visitDao")
public class VisitDaoImpl extends BaseDaoImpl<Visit> implements IVisitDao {

	@Override
	public List<Visit> listBycusId(String... cusId) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("customer.id in ('"+toString(cusId).replace(",", "','")+"')" ));
		return super.list(queryList, null);
	}
}
