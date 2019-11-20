package cn.demi.cus.customer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.dao.IAccountDao;
import cn.demi.cus.customer.po.Account;

@Repository("cus.accountDao")
public class AccountDaoImpl extends BaseDaoImpl<Account> implements IAccountDao {

	@Override
	public Account listByCusId(String cusId) throws GlobalException {
		List<QueryCondition> querylist = new ArrayList<QueryCondition>();
		querylist.add(new QueryCondition("customer.id", QueryCondition.EQ,cusId));
		List<Account> list =super.list(querylist);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Account find(String loginName) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("loginName",QueryCondition.EQ, loginName));
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ, Po.N));
		return super.query0(queryList, null);
	}
}
