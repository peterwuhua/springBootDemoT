package cn.demi.cus.customer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.dao.IFinanceDao;
import cn.demi.cus.customer.po.Finance;
/**
 * Create on : 2016年11月15日 下午2:45:46  <br>
 * Description : FinanceDaoImpl <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Repository("cus.financeDao")
public class FinanceDaoImpl extends BaseDaoImpl<Finance> implements IFinanceDao {

	@Override
	public List<Finance> listBycusId(String... cusId) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("customer.id in ('"+toString(cusId).replace(",", "','")+"')" ));
		return super.list(queryList, null);
	}
}
