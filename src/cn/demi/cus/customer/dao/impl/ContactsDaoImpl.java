package cn.demi.cus.customer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.dao.IContactsDao;
import cn.demi.cus.customer.po.Contacts;
/**
 * Create on : 2016年11月15日 下午2:43:25  <br>
 * Description : ContactsDaoImpl <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Repository("cus.contactsDao")
public class ContactsDaoImpl extends BaseDaoImpl<Contacts> implements IContactsDao {

	@Override
	public List<Contacts> listBycusId(String... cusId) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("customer.id in ('"+toString(cusId).replace(",", "','")+"')" ));
		return super.list(queryList, null);
	}

	@Override
	public Contacts findBycusId(String cusId, String person) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("customer.id = '"+cusId+"'" ));
		queryList.add(new QueryCondition("name like '"+person.trim()+"'" ));
		List<Contacts> lst=super.list(queryList, null);
		if(null!=lst&&lst.size()>0) {
			return lst.get(0);
		}else {
			return null;
		}
	}
}
