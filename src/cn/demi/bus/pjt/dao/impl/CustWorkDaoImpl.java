package cn.demi.bus.pjt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.pjt.dao.ICustWorkDao;
import cn.demi.bus.pjt.po.CustWork;

@Repository("bus.custWorkDao")
public class CustWorkDaoImpl extends BaseDaoImpl<CustWork> implements ICustWorkDao {

	@Override
	public void deleteByProjectId(String pjId) {
		String hql="DELETE FROM  "+getEntityName(CustWork.class)+" WHERE projectId='"+pjId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}
	@Override
	public void deleteByRoomId(String roomId) {
		String hql="DELETE FROM  "+getEntityName(CustWork.class)+" WHERE room.id='"+roomId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
		
	}
	@Override
	public void deleteByPointId(String pointId) {
		String hql="DELETE FROM  "+getEntityName(CustWork.class)+" WHERE point.id='"+pointId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustWork> listByProjectId(String pjId) {
		String hql="FROM  "+getEntityName(CustWork.class)+" WHERE isDel='"+Po.N+"' AND projectId='"+pjId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public List<CustWork> listByRoomId(String roomId) {
		String hql="FROM  "+getEntityName(CustWork.class)+" WHERE isDel='"+Po.N+"' AND room.id='"+roomId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustWork> listByPointId(String pointId) {
		String hql="FROM  "+getEntityName(CustWork.class)+" WHERE isDel='"+Po.N+"' AND point.id='"+pointId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@Override
	public int getMaxSort(String pjId) {
		StringBuffer jpql = new StringBuffer("SELECT MAX(sort) FROM "+ getEntityName(CustWork.class)+" WHERE isDel="+Po.N +" AND projectId='"+pjId+"' ");
		Object obj = query(jpql.toString()).getSingleResult();
		int maxSort = 0;
		if(null != obj){
			maxSort = (Integer)obj;
		}
		return maxSort;
	}
}
