package cn.demi.bus.pjt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.pjt.dao.ICustRoomDao;
import cn.demi.bus.pjt.po.CustRoom;

@Repository("bus.custRoomDao")
public class CustRoomDaoImpl extends BaseDaoImpl<CustRoom> implements ICustRoomDao {

	@Override
	public void deleteByProjectId(String pjId) {
		String hql="DELETE FROM  "+getEntityName(CustRoom.class)+" WHERE projectId='"+pjId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustRoom> listByProjectId(String pjId) {
		String hql="FROM  "+getEntityName(CustRoom.class)+" WHERE isDel='"+Po.N+"' AND projectId='"+pjId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@Override
	public int getMaxSort(String pjId) {
		StringBuffer jpql = new StringBuffer("SELECT MAX(sort) FROM "+ getEntityName(CustRoom.class)+" WHERE isDel="+Po.N +" AND projectId='"+pjId+"' ");
		Object obj = query(jpql.toString()).getSingleResult();
		int maxSort = 0;
		if(null != obj){
			maxSort = (Integer)obj;
		}
		return maxSort;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustRoom findByName(String pjId, String roomName) {
		String hql="FROM  "+getEntityName(CustRoom.class)+" WHERE isDel='"+Po.N+"' AND projectId='"+pjId+"' AND name like '"+roomName+"' order by sort asc";
		List<CustRoom> list=getEntityManager().createQuery(hql).getResultList();
		if(null!=list&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
}
