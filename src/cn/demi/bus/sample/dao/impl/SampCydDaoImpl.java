package cn.demi.bus.sample.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.sample.dao.ISampCydDao;
import cn.demi.bus.sample.po.SampCyd;

@Repository("bus.sampCydDao")
public class SampCydDaoImpl extends BaseDaoImpl<SampCyd> implements ISampCydDao {

	@Override
	public void deleteByTask(String taskId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(SampCyd.class)+" WHERE task.id ='"+taskId+"'").executeUpdate();
	}

	@Override
	public List<SampCyd> listByTaskId(String taskId) {
		String hql="FROM "+getEntityName(SampCyd.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"' ORDER BY sort asc";
		return super.list(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SampCyd find4Auto(String pointId, String cyd,String cyDate) {
		String hql="FROM "+getEntityName(SampCyd.class)+" WHERE isDel='"+Po.N+"' AND pointId ='"+pointId+"' AND cyDate='"+cyDate+"' ";
		if(StrUtils.isBlankOrNull(cyd)) {
			hql+=" AND (type is null or type='')";
		}else {
			hql+=" AND type='"+cyd+"' ";
		}
		hql+= " ORDER BY sort asc";
		List<SampCyd> cydList=getEntityManager().createQuery(hql).getResultList();
		if(null!=cydList&&cydList.size()>0) {
			return cydList.get(0);
		}else {
			return null;
		}
	}
	@Override
	public SampCyd find4Task(String taskId, String cyd,String cyDate) {
		String hql="FROM "+getEntityName(SampCyd.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"' AND cyDate='"+cyDate+"' ";
		if(StrUtils.isBlankOrNull(cyd)) {
			hql+=" AND (type is null or type='')";
		}else {
			hql+=" AND type='"+cyd+"' ";
		}
		hql+= " ORDER BY sort asc";
		List<SampCyd> cydList=super.list(hql);
		if(null!=cydList&&cydList.size()>0) {
			return cydList.get(0);
		}else {
			return null;
		}
	}
	@Override
	public int getMaxSort(String taskId) {
		StringBuffer jpql = new StringBuffer("SELECT MAX(sort) FROM "
				+ getEntityName(SampCyd.class)+" WHERE isDel="+Po.N+" AND task.id='"+taskId+"'");
		Object obj = query(jpql.toString()).getSingleResult();
		int maxSort = 0;
		if(null != obj){
			maxSort = (Integer)obj;
		}
		return maxSort;
	}
}
