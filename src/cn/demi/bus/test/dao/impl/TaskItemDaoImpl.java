package cn.demi.bus.test.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.po.TaskItem;

@Repository("bus.taskItemDao")
public class TaskItemDaoImpl extends BaseDaoImpl<TaskItem> implements ITaskItemDao {

	@Override
	public void deleteByTaskId(String taskId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(TaskItem.class)+" WHERE task.id ='"+taskId+"'").executeUpdate();
	}

	@Override
	public void deleteByTaskId(String taskId, String type) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(TaskItem.class)+" WHERE task.id ='"+taskId+"' AND type='"+type+"'").executeUpdate();
	}

	@Override
	public List<TaskItem> listByTaskId(String taskId) {
		String hql="FROM "+getEntityName(TaskItem.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"' ORDER BY sort asc";
		return super.list(hql);
	}

	@Override
	public List<TaskItem> listByTaskId(String taskId, String type) {
		String hql="FROM "+getEntityName(TaskItem.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"' AND type='"+type+"' ORDER BY sort asc";
		return super.list(hql);
	}

	@Override
	public TaskItem find(String taskId, String itemId) {
		String hql="FROM "+getEntityName(TaskItem.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"' AND itemId='"+itemId+"' ";
		List<TaskItem> list=super.list(hql);
		if(null!=list&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public int getMaxSort(String taskId) {
		StringBuffer jpql = new StringBuffer("SELECT MAX(sort) FROM "
				+ getEntityName(TaskItem.class)+" WHERE isDel="+Po.N+" AND task.id='"+taskId+"'");
		Object obj = query(jpql.toString()).getSingleResult();
		int maxSort = 0;
		if(null != obj){
			maxSort = (Integer)obj;
		}
		return maxSort;
	}
}
