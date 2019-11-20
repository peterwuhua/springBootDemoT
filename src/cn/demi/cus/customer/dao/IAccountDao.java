package cn.demi.cus.customer.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.po.Account;

public interface IAccountDao extends IBaseDao<Account> {
	/**
	 * Create on : Danlee Li 2016年12月12日 下午3:58:26 <br>
	 * Description :  根据客户Id获取账户信息<br>
	 * @param cusId
	 * @return
	 * @throws GlobalException
	 */
	public Account listByCusId(String cusId) throws GlobalException;
	/**
	 * Create on : Danlee Li 2017年4月6日 上午11:16:39 <br>
	 * Description :  根据登录名称查找登录信息<br>
	 * @param loginName 用户登录名
	 * @return
	 * @throws GlobalException
	 */
	public Account find(String loginName) throws GlobalException;
	
	
}

