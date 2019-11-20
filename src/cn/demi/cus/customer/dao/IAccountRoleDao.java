package cn.demi.cus.customer.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.po.AccountRole;

public interface IAccountRoleDao extends IBaseDao<AccountRole> {
	
	/**
	 * Create on : Danlee Li 2016年12月12日 下午3:06:55 <br>
	 * Description :清楚当前客户具备的角色信息  <br>
	 * @param accountId
	 * @throws GlobalException
	 */
	public void deleteByAccountId(String accountId) throws GlobalException;
	/**
	 * Create on : Danlee Li 2016年12月12日 下午3:07:57 <br>
	 * Description : 获取当前客户所具备的角色信息 <br>
	 * @param accountId
	 * @return
	 * @throws GlobalException
	 */
	public List<AccountRole> listByAccountId(String accountId)
			throws GlobalException;
}

