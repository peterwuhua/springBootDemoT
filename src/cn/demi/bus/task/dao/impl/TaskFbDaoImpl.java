package cn.demi.bus.task.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.task.dao.ITaskFbDao;
import cn.demi.bus.task.po.TaskFb;
 

@Repository("taskFbDao")
public class TaskFbDaoImpl extends BaseDaoImpl<TaskFb> implements ITaskFbDao {
	
	@Override
	public void deleteByTaskId(String pjId) {
		String hql="DELETE FROM  "+getEntityName(TaskFb.class)+" WHERE task.id='"+pjId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskFb> listByTaskId(String pjId) {
		String hql="FROM  "+getEntityName(TaskFb.class)+" WHERE isDel='"+Po.N+"' AND task.id='"+pjId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}
}
