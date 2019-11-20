package cn.demi.bus.task.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.po.TaskPoint;

@Repository("bus.taskPointDao")
public class TaskPointDaoImpl extends BaseDaoImpl<TaskPoint> implements ITaskPointDao {

	@Override
	public List<TaskPoint> listByTaskId(String taskId) {
		String hql="FROM "+getEntityName(TaskPoint.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"' ORDER BY sort asc";
		return super.list(hql);
	}

	@Override
	public void deleteByTaskId(String taskId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(TaskPoint.class)+" WHERE task.id ='"+taskId+"'").executeUpdate();
	}

	@Override
	public int getSort4Code(String taskId, String ptId,String sampTypeId) {
		String hql="FROM "+getEntityName(TaskPoint.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"' ORDER BY sort asc";
		List<TaskPoint> tpList=super.list(hql);
		int n=1;
		for (TaskPoint tp : tpList) {
			if(tp.getId().equals(ptId)) {
				break;
			}else if(null!=tp.getSampTypeId()&&null!=sampTypeId&&tp.getSampTypeId().equals(sampTypeId)){
				n++;
			}
		}
		return n;
	}

	@Override
	public int getMaxSort(String taskId) {
		StringBuffer jpql = new StringBuffer("SELECT MAX(sort) FROM "
				+ getEntityName(TaskPoint.class)+" WHERE isDel="+Po.N+" AND task.id='"+taskId+"'");
		Object obj = query(jpql.toString()).getSingleResult();
		int maxSort = 0;
		if(null != obj){
			maxSort = (Integer)obj;
		}
		return maxSort;
	}
}
