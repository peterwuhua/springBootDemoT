package cn.demi.cus.customer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.dao.IAccountRoleDao;
import cn.demi.cus.customer.po.AccountRole;

@Repository("cus.account_roleDao")
public class AccountRoleDaoImpl extends BaseDaoImpl<AccountRole> implements IAccountRoleDao {

	@Override
	public void deleteByAccountId(String accountId) throws GlobalException {
		super.deleteAll(this.listByAccountId(accountId));
	}

	@Override
	public List<AccountRole> listByAccountId(String accountId) throws GlobalException {
		List<QueryCondition> querylist = new ArrayList<QueryCondition>();
		querylist.add(new QueryCondition("account.id", QueryCondition.EQ,accountId));
		return super.list(querylist);
	}
}
