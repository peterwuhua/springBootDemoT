package cn.demi.qlt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.StrUtils;
import cn.demi.qlt.dao.INsItemDao;
import cn.demi.qlt.po.NsItem;

@Repository("qlt.nsItemDao")
public class NsItemDaoImpl extends BaseDaoImpl<NsItem> implements INsItemDao {

	@Override
	public void delete4Ct(String ysId) {
		String hql = "FROM "+getEntityName(NsItem.class)+" WHERE item.id='"+ysId+"'";
		@SuppressWarnings("unchecked")
		List<NsItem> imList=getEntityManager().createQuery(hql).getResultList();
		if(null!=imList) {
			for (NsItem it : imList) {
				String sub = "DELETE FROM "+getEntityName(NsItem.class)+" WHERE item.id='"+it.getId()+"'";
				getEntityManager().createQuery(sub).executeUpdate();
				super.delete(it);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NsItem> listByItemId(String pId) {
		String hql =null;
		if(StrUtils.isBlankOrNull(pId)) {
			hql = "FROM "+getEntityName(NsItem.class)+" WHERE isDel='"+Po.N+"' AND item.id is null";
		}else {
			hql = "FROM "+getEntityName(NsItem.class)+" WHERE isDel='"+Po.N+"' AND item.id='"+pId+"'";
		}
		hql+=" ORDER BY code,sort ASC";
		List<NsItem> imList=getEntityManager().createQuery(hql).getResultList();
		return imList;
	}
}
