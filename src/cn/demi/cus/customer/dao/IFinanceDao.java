package cn.demi.cus.customer.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.po.Finance;

/**
 * Create on : 2016年11月15日 下午2:48:18  <br>
 * Description : 账户信息DAO <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public interface IFinanceDao extends IBaseDao<Finance> {
	
	public List<Finance> listBycusId(String... cusId) throws GlobalException;
	
}

