package cn.demi.init.std.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.init.std.dao.IItemMethodDao;
import cn.demi.init.std.po.ItemMethod;

@Repository("init.item_methodDao")
public class ItemMethodDaoImpl extends BaseDaoImpl<ItemMethod> implements IItemMethodDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemMethod> listByItemId(String itemId) {
		String hql = "FROM "+getEntityName(ItemMethod.class)+" WHERE isDel="+Po.N+" AND method.isDel="+Po.N+" AND item.id='"+itemId+"'";
		List<ItemMethod> imList=getEntityManager().createQuery(hql).getResultList();
		return imList;
	}

	@Override
	public List<ItemMethod> listByMethodId(String methodId) {
		String hql = "FROM "+getEntityName(ItemMethod.class)+" WHERE isDel="+Po.N+" AND item.isDel="+Po.N+" AND  method.id='"+methodId+"'";
		@SuppressWarnings("unchecked")
		List<ItemMethod> imList=getEntityManager().createQuery(hql).getResultList();
		return imList;
	}

	@Override
	public void deleteByItemId(String itemId) {
		String hql="DELETE FROM  "+getEntityName(ItemMethod.class)+" WHERE item.id='"+itemId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}

	@Override
	public void deleteByMethodId(String methodId) {
		String hql="DELETE FROM  "+getEntityName(ItemMethod.class)+" WHERE method.id='"+methodId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}

	@Override
	public ItemMethod findByMethodIdAndItemId(String methodId, String itemId) {
		String hql = "FROM "+getEntityName(ItemMethod.class)+" WHERE isDel="+Po.N+" AND method.id='"+methodId+"' AND item.id='"+itemId+"'";
		@SuppressWarnings("unchecked")
		List<ItemMethod> imList=getEntityManager().createQuery(hql).getResultList();
		if(null!=imList&&imList.size()>0) {
			return imList.get(0);
		}else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemMethod> listByItemIds(String itemIds) {
		String hql = "FROM "+getEntityName(ItemMethod.class)+" WHERE isDel="+Po.N+" AND method.isDel="+Po.N+" AND item.id in ('"+itemIds.replace(",", "','")+"')";
		List<ItemMethod> imList=getEntityManager().createQuery(hql).getResultList();
		return imList;
	}

	@Override
	public float findMaxHours(String itemIds) {
		String sql="select max(it.bcsj) from v_init_item_method it join v_bus_im im on it.item_id=im.item_id AND it.method_id=im.method_id where im.item_id in('"+itemIds.replace(",", "','")+"')";
		Object sh=super.queryBysql(sql).getSingleResult();
		if(null!=sh) {
			return Float.valueOf(sh.toString());
		}else {
			return 0;
		}
	}
}
