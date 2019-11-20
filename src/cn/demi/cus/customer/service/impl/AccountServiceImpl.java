package cn.demi.cus.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.dao.IAccountDao;
import cn.demi.cus.customer.po.Account;
import cn.demi.cus.customer.service.IAccountService;
import cn.demi.cus.customer.vo.AccountVo;

@Service("cus.accountService")
public class AccountServiceImpl extends BaseServiceImpl<AccountVo,Account> implements
		IAccountService {
	@Autowired
	private IAccountDao accountDao;
	
	@Override
	public AccountVo find(String loginName) throws GlobalException {
		Account account = accountDao.find(loginName);
		if (null == account) return null; 
		AccountVo vo = po2Vo(account);
		return vo;
	}
}
