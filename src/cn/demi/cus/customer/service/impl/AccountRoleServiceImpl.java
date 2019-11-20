package cn.demi.cus.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.dao.IAccountRoleDao;
import cn.demi.cus.customer.po.AccountRole;
import cn.demi.cus.customer.service.IAccountRoleService;
import cn.demi.cus.customer.vo.AccountRoleVo;

@Service("cus.account_roleService")
public class AccountRoleServiceImpl extends BaseServiceImpl<AccountRoleVo,AccountRole> implements
		IAccountRoleService {
	@Autowired private IAccountRoleDao accountRoleDao;
	@Override
	public List<AccountRoleVo> listByAccountId(String accountId) throws GlobalException {
		return toVoList(accountRoleDao.listByAccountId(accountId));
	}
}
