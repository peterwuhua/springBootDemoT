package cn.demi.cus.customer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.customer.dao.IClientPointDao;
import cn.demi.cus.customer.po.ClientPoint;

@Repository("cus.clientPointDao")
public class ClientPointDaoImpl extends BaseDaoImpl<ClientPoint> implements IClientPointDao {

	@Override
	public List<ClientPoint> listBycusId(String... cusId) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("client.id in ('"+toString(cusId).replace(",", "','")+"')" ));
		return super.list(queryList, null);
	}

	@Override
	public ClientPoint findBycusId(String cusId, String pointName) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("client.id = '"+cusId+"'" ));
		queryList.add(new QueryCondition("name like '"+pointName.trim()+"'" ));
		List<ClientPoint> lst=super.list(queryList, null);
		if(null!=lst&&lst.size()>0) {
			return lst.get(0);
		}else {
			return null;
		}
	}

	@Override
	public ClientPoint findBycusId(String cusId, String sampTypeId,String sampType, String pointName) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("client.id = '"+cusId+"'" ));
		if(!StrUtils.isBlankOrNull(sampTypeId)) {
			queryList.add(new QueryCondition("sampTypeId = '"+sampTypeId+"'" ));
		}
		if(!StrUtils.isBlankOrNull(sampType)) {
			queryList.add(new QueryCondition("sampType = '"+sampType+"'" ));
		}
		queryList.add(new QueryCondition("name like '"+pointName.trim()+"'" ));
		List<ClientPoint> lst=super.list(queryList, null);
		if(null!=lst&&lst.size()>0) {
			return lst.get(0);
		}else {
			return null;
		}
	}
}
