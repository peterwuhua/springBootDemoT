package cn.demi.init.std.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.init.std.dao.IPstandardDao;
import cn.demi.init.std.po.Pstandard;

@Repository("init.pstandardDao")
public class PstandardDaoImpl extends BaseDaoImpl<Pstandard> implements IPstandardDao {

	@SuppressWarnings("unchecked")
	@Override
	public Pstandard findByCode(String code) {
		String hql = "FROM "+getEntityName(Pstandard.class)+" WHERE isDel="+Po.N+" AND code like '"+code+"'";
		List<Pstandard> imList=getEntityManager().createQuery(hql).getResultList();
		if(null!=imList&&imList.size()>0) {
			return imList.get(0);
		}else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pstandard> listBySampTyle(String sampTyleIds) {
		String hql = "FROM "+getEntityName(Pstandard.class)+" WHERE isDel="+Po.N+" AND sampTypeId in('"+sampTyleIds.replace(",", "','")+"') order by sort asc";
		List<Pstandard> imList=getEntityManager().createQuery(hql).getResultList();
		return imList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pstandard> listBySampTyle(String ids, String sampTyleIds) {
		if(null==ids || null==sampTyleIds) {
			return null;
		}
		String hql = "FROM "+getEntityName(Pstandard.class)+" WHERE isDel="+Po.N+" AND id in('"+ids.replace(",", "','")+"') AND sampTypeId in('"+sampTyleIds.replace(",", "','")+"') order by sort asc";
		List<Pstandard> imList=getEntityManager().createQuery(hql).getResultList();
		return imList;
	}
}
