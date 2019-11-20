package cn.demi.init.std.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.constant.Constants;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.po.Item;

@Repository("init.itemDao")
public class ItemDaoImpl extends BaseDaoImpl<Item> implements IItemDao {

	@SuppressWarnings("unchecked")
	@Override
	public Item findByName(String name) {
		String hql="FROM "+getEntityName(Item.class)+" WHERE isDel='"+Po.N+"' AND name like '"+name+"' order by sort desc";
		List<Item> list=getEntityManager().createQuery(hql).getResultList();
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
	@Override
	public List<Item> listByPid(String pid) {
		StringBuffer jpql = new StringBuffer("FROM "+ getEntityName(Item.class) + " WHERE  parent.id = '"+pid+"'");
		jpql.append(" AND isDel= "+Po.N);
		jpql.append("  order by sort asc");
		return list(jpql.toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Item> listByPname(String name) {
		String hql="FROM "+getEntityName(Item.class)+" WHERE isDel='"+Po.N+"' AND parent.name like '"+name+"'  order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}
	@Override
	public List<Item> listByIds(String ids, String isNow) {
		StringBuffer jpql = new StringBuffer("FROM "+ getEntityName(Item.class) + " WHERE id in('"+ids.replace(",", "','")+"')");
		jpql.append(" AND isDel= "+Po.N);
		jpql.append(" AND isNow='"+isNow+"'");
		jpql.append(" order by sort asc");
		return list(jpql.toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public String findCode(String itemIds) {
		String hql="SELECT code FROM "+getEntityName(Item.class)+" WHERE isDel='"+Po.N+"' AND code is not null AND code!='' AND id in('"+itemIds.replace(",", "','")+"')";
		List<String> list=(List<String>) super.query(hql).getResultList();
		if(null!=list && list.size()>0) {
			return list.get(0);
		}else {
			return "";
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Float getMaxHours(String ids) {
		String hql="SELECT MAX(saveHour) FROM "+getEntityName(Item.class)+" WHERE isDel='"+Po.N+"' AND id in('"+ids.replace(",", "','")+"')";
		List<Object> list=getEntityManager().createQuery(hql).getResultList();
		if(list!=null&&list.size()>0&&!StrUtils.isNull(list.get(0))) {
			float v;
			try {
				v = Float.valueOf(list.get(0).toString());
				return v;
			} catch (NumberFormatException e) {
				return (float) 0.0;
			}
			
		}else {
			return (float) 0.0;
		}
	}
	@Override
	public boolean checkIsNow(String ids) {
		StringBuffer jpql = new StringBuffer("FROM "+ getEntityName(Item.class) + " WHERE id in('"+ids.replace(",", "','")+"')");
		jpql.append(" AND isDel= "+Po.N);
		jpql.append(" AND isNow='"+Constants.F+"'");
		List<Item> l=list(jpql.toString());
		if(null!=l && l.size()>0) {
			return false;
		}else {
			return true;
		}
	}
	@Override
	public float getSumPrice(String ids) {
		String hql="SELECT SUM(price) FROM "+getEntityName(Item.class)+" WHERE isDel='"+Po.N+"' AND id in('"+ids.replace(",", "','")+"')";
		Object obj=getEntityManager().createQuery(hql).getSingleResult();
		if(obj!=null) {
			float v;
			try {
				v = Float.valueOf(obj.toString());
				return v;
			} catch (NumberFormatException e) {
				return (float) 0.0;
			}
		}else {
			return (float) 0.0;
		}
	}
}
