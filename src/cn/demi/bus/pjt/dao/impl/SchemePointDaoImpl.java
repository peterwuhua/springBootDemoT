package cn.demi.bus.pjt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.pjt.dao.ISchemePointDao;
import cn.demi.bus.pjt.po.SchemePoint;

@Repository("bus.schemePointDao")
public class SchemePointDaoImpl extends BaseDaoImpl<SchemePoint> implements ISchemePointDao {

	@Override
	public void deleteBySchId(String schId) {
		String hql="DELETE FROM  "+getEntityName(SchemePoint.class)+" WHERE scheme.id='"+schId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchemePoint> listBySchId(String schId) {
		String hql="FROM  "+getEntityName(SchemePoint.class)+" WHERE isDel='"+Po.N+"' AND scheme.id='"+schId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchemePoint> listBySchId(String schId, String sampTypeId) {
		String hql="FROM  "+getEntityName(SchemePoint.class)+" WHERE isDel='"+Po.N+"' AND scheme.id='"+schId+"' AND sampTypeId in('"+sampTypeId.replace(",", "','")+"') order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchemePoint> listBySchId2(String schId,String sampTypeIds) {
		String hql="FROM  "+getEntityName(SchemePoint.class)+" WHERE isDel='"+Po.N+"' AND scheme.id='"+schId+"' AND sampTypeId not in('"+sampTypeIds.replace(",", "','")+"') order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@Override
	public void deleteByProjectId(String pjId) {
		String hql="DELETE FROM  "+getEntityName(SchemePoint.class)+" WHERE projectId='"+pjId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findMaxSort(String schId) {
		String hql="FROM  "+getEntityName(SchemePoint.class)+" WHERE isDel='"+Po.N+"' AND scheme.id='"+schId+"' order by sort DESC";
		List<SchemePoint> spList= getEntityManager().createQuery(hql).getResultList();
		if(null!=spList&&spList.size()>0) {
			return spList.get(0).getSort();
		}else {
			return 0;
		}
	}
}
