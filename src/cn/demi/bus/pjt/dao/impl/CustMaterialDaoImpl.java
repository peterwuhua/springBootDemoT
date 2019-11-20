package cn.demi.bus.pjt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.pjt.dao.ICustMaterialDao;
import cn.demi.bus.pjt.po.CustMaterial;

@Repository("bus.custMaterialDao")
public class CustMaterialDaoImpl extends BaseDaoImpl<CustMaterial> implements ICustMaterialDao {

	@Override
	public void deleteByProjectId(String pjId) {
		String hql="DELETE FROM  "+getEntityName(CustMaterial.class)+" WHERE projectId='"+pjId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}
	@Override
	public void deleteByRoomId(String roomId) {
		String hql="DELETE FROM  "+getEntityName(CustMaterial.class)+" WHERE room.id='"+roomId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustMaterial> listByProjectId(String pjId) {
		String hql="FROM  "+getEntityName(CustMaterial.class)+" WHERE isDel='"+Po.N+"' AND projectId='"+pjId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustMaterial> listByRoomId(String roomId) {
		String hql="FROM  "+getEntityName(CustMaterial.class)+" WHERE isDel='"+Po.N+"' AND room.id='"+roomId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@Override
	public int getMaxSort(String pjId) {
		StringBuffer jpql = new StringBuffer("SELECT MAX(sort) FROM "+ getEntityName(CustMaterial.class)+" WHERE isDel="+Po.N +" AND projectId='"+pjId+"' ");
		Object obj = query(jpql.toString()).getSingleResult();
		int maxSort = 0;
		if(null != obj){
			maxSort = (Integer)obj;
		}
		return maxSort;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustMaterial> listTypeByPjtId(String pjId) {
		String hql="FROM  "+getEntityName(CustMaterial.class)+" WHERE isDel='"+Po.N+"' AND projectId='"+pjId+"' group by type order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustMaterial> listBypjtIdAndType(String pjId, String type) {
		String hql="FROM  "+getEntityName(CustMaterial.class)+" WHERE isDel='"+Po.N+"' AND projectId='"+pjId+"' and type='"+type+"'  order by sort asc";
		return  getEntityManager().createQuery(hql).getResultList();
	}
}
