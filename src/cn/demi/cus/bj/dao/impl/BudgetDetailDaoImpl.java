package cn.demi.cus.bj.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.cus.bj.dao.IBudgetDetailDao;
import cn.demi.cus.bj.po.BudgetDetail;

@Repository("cus.budgetDetailDao")
public class BudgetDetailDaoImpl extends BaseDaoImpl<BudgetDetail> implements IBudgetDetailDao {

	@Override
	public void deleteByBudId(String budId) {
		String hql="DELETE FROM  "+getEntityName(BudgetDetail.class)+" WHERE budget.id='"+budId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}
}
