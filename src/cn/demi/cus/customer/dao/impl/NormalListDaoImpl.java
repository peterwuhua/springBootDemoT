package cn.demi.cus.customer.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.cus.customer.dao.INormalListDao;
import cn.demi.cus.customer.po.NormalList;

@Repository("cus.normalListDao")
public class NormalListDaoImpl extends BaseDaoImpl<NormalList> implements INormalListDao {

	@Override
	public List<String> queryByCondition(Map<String, Object> map) {
		if(map.get("cates") == null)
		{
			map.put("cates","-1"); //返回没有
		}
		String jpql = "select fname from v_cus_normalList where cates "+"= '"+map.get("cates").toString()+"'";
		return getEntityManager().createNativeQuery(jpql).getResultList();
	}
}
