package cn.demi.base.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountRoleDao;
import cn.demi.base.system.po.AccountRole;
/**
 * <strong>Create on : 2016年11月2日 下午5:05:49 </strong> <br>
 * <strong>Description : AccountRoleDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.accountRoleDao")
public class AccountRoleDaoImpl extends BaseDaoImpl<AccountRole> implements
		IAccountRoleDao {

	@Override
	public void deleteByAccountId(String accountId) throws GlobalException {
		super.deleteAll(listByAccountId(accountId));
	}
	@Override
	public void deleteExceptAdmin(String accountId) throws GlobalException {
		if (StrUtils.isEmpty(accountId))
			throw new GlobalException("无效的账户accountId" + accountId);
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("account.id", QueryCondition.EQ,accountId));
		String loginName = getCurrent().getLoginName();
		if(!"suadmin".equals(loginName)){
			queryList.add(new QueryCondition("role.code NOT IN ('999','998')"));
		}
		super.deleteAll(super.list(queryList, null));
	}
	

	@Override
	public List<AccountRole> listByAccountId(String accountId)
			throws GlobalException {
		if (StrUtils.isEmpty(accountId))
			throw new GlobalException("无效的账户accountId" + accountId);
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("account.id", QueryCondition.EQ,
				accountId));
		return super.list(queryList, null);
	}

	@Override
	public List<AccountRole> listByRoleId(String roleId) throws GlobalException {
		if (StrUtils.isEmpty(roleId))
			throw new GlobalException("roleId 为空");
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("role.id", QueryCondition.EQ, roleId));
		return super.list(queryList, null);
	}

	@Override
	public List<AccountRole> list(String dep, String roleCode, String orgCode)
			throws GlobalException {
		if (StrUtils.isEmpty(roleCode))
			throw new GlobalException("角色编码不能为空");
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("role.code", QueryCondition.EQ,
				roleCode));
		if (!StrUtils.isEmpty(orgCode))
			queryList.add(new QueryCondition("account.org.code",
					QueryCondition.EQ, orgCode));
		if (!StrUtils.isEmpty(dep))
			queryList.add(new QueryCondition("account.dep", QueryCondition.EQ,
					dep));

		return super.list(queryList);
	}

	@Override
	public String getRoleIds(String accountId) throws GlobalException {
		List<AccountRole> list = listByAccountId(accountId);
		String roleIds = "";
		for(int i=0,j=list.size();i<j;i++){
			if(i!=0) roleIds +=",";
			roleIds +=list.get(i).getRole().getId();
		}
		return roleIds;
	}
}
