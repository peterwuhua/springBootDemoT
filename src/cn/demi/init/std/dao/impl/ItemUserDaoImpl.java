package cn.demi.init.std.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.base.system.po.Org;
import cn.demi.init.std.dao.IItemUserDao;
import cn.demi.init.std.po.ItemUser;

@Repository("init.itemUserDao")
public class ItemUserDaoImpl extends BaseDaoImpl<ItemUser> implements IItemUserDao {

	@SuppressWarnings("unchecked")
	@Override
	public ItemUser findUser(String itemId, String orgId) {
		String hql="FROM "+getEntityName(ItemUser.class)+" WHERE isDel='"+Po.N+"' AND itemId='"+itemId+"' AND orgId='"+orgId+"' order by sort asc limit 0,1";
		List<ItemUser> list=getEntityManager().createQuery(hql).getResultList();
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Org> find4Org(String itemId) {
		String hql="FROM "+getEntityName(Org.class)+" WHERE isDel='"+Po.N+"' AND id in(select distinct(orgId) FROM "+getEntityName(ItemUser.class)+" WHERE isDel='"+Po.N+"' AND itemId in('"+itemId+"')) order by sort asc ";
		List<Org> list=getEntityManager().createQuery(hql).getResultList();
		return list;
	}
}
