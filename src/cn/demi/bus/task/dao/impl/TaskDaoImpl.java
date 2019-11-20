package cn.demi.bus.task.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.po.Po;
import cn.core.framework.constant.EunmTask;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.test.po.TaskItem;

@Repository("bus.taskDao")
public class TaskDaoImpl extends BaseDaoImpl<Task> implements ITaskDao {

	@Override
	public PageResult queryBySql(String sql, PageResult pageResult) {
		Query query = getEntityManager().createNativeQuery(sql);
		int totalRows=query.getResultList().size();
		query.setFirstResult(pageResult.getPageBean().getStartRow());
		query.setMaxResults(pageResult.getPageBean().getPageSize());
		pageResult.setResultList(query.getResultList());
		pageResult.getPageBean().setTotalRows(totalRows);
		return pageResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateSt(String id) {
		Task t=super.findById(id);
		String hql = "SELECT status FROM "+getEntityName(TaskItem.class)+" where isDel='"+Po.N+"' AND task.id='"+id+"'";
		List<Object> list=getEntityManager().createQuery(hql).getResultList();
		if(list!=null) {
			int n=0;
			for (Object o : list) {
				if(null!=o&&o.toString().equals(EunmTask.ITEM_HZ.getStatus())) {
					n++;
				}
			}
			t.setTimNum(String.valueOf(n));
			t.setTimTal(list.size()+"");
		}else {
			t.setTimNum("0");
			t.setTimTal("0");
		}
		super.update(t);
	}
	
}
