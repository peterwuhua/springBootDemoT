package cn.demi.cus.bj.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.cus.bj.dao.IBudgetDao;
import cn.demi.cus.bj.po.Budget;

@Repository("cus.budgetDao")
public class BudgetDaoImpl extends BaseDaoImpl<Budget> implements IBudgetDao {
}
