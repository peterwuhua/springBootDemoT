package cn.demi.bus.pjt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.pjt.dao.ICustPointDao;
import cn.demi.bus.pjt.po.CustPoint;

@Repository("bus.custPointDao")
public class CustPointDaoImpl extends BaseDaoImpl<CustPoint> implements ICustPointDao {

	@Override
	public void deleteByProjectId(String pjId) {
		String hql="DELETE FROM  "+getEntityName(CustPoint.class)+" WHERE projectId='"+pjId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}

	@Override
	public void deleteByRoomId(String roomId) {
		String hql="DELETE FROM  "+getEntityName(CustPoint.class)+" WHERE room.id='"+roomId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustPoint> listByProjectId(String pjId) {
		String hql="FROM  "+getEntityName(CustPoint.class)+" WHERE isDel='"+Po.N+"' AND projectId='"+pjId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustPoint> listByRoomId(String roomId) {
		String hql="FROM  "+getEntityName(CustPoint.class)+" WHERE isDel='"+Po.N+"' AND room.id='"+roomId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@Override
	public int getMaxSort(String pjId) {
		StringBuffer jpql = new StringBuffer("SELECT MAX(sort) FROM "+ getEntityName(CustPoint.class)+" WHERE isDel="+Po.N +" AND projectId='"+pjId+"' ");
		Object obj = query(jpql.toString()).getSingleResult();
		int maxSort = 0;
		if(null != obj){
			maxSort = (Integer)obj;
		}
		return maxSort;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustPoint findByName(String roomId, String name) {
		String hql="FROM  "+getEntityName(CustPoint.class)+" WHERE isDel='"+Po.N+"' AND room.id='"+roomId+"' order by sort asc";
		List<CustPoint> cpList=getEntityManager().createQuery(hql).getResultList();
		if(null!=cpList&&cpList.size()>0) {
			return cpList.get(0);
		}else {
			return null;
		}
	}
}
