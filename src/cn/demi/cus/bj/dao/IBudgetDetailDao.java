package cn.demi.cus.bj.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.cus.bj.po.BudgetDetail;

public interface IBudgetDetailDao extends IBaseDao<BudgetDetail> {
	
	/**
	 * 根据id删除详细信息
	 * @param schId
	 */
	void deleteByBudId(String schId);
}

