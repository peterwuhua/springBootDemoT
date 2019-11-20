package cn.demi.cus.customer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.demi.cus.customer.dao.IClientDao;
import cn.demi.cus.customer.po.Client;
/**
 * <strong>Create on : 2016年11月15日 下午2:36:37 </strong> <br>
 * <strong>Description :ClientDaoImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>danlee_Li</strong><br>
 */
@Repository("cus.clientDao")
public class ClientDaoImpl extends BaseDaoImpl<Client> implements IClientDao {

	@Override
	public Client findByName(String name) {
		List<QueryCondition> conditions = new ArrayList<>();
		conditions.add(new QueryCondition("name",QueryCondition.EQ, name));
		return super.query0(conditions, null);
	}
}
