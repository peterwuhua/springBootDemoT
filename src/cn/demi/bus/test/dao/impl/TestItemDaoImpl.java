package cn.demi.bus.test.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.test.dao.ITestItemDao;
import cn.demi.bus.test.po.TestItem;

@Repository("bus.testItemDao")
public class TestItemDaoImpl extends BaseDaoImpl<TestItem> implements ITestItemDao {

	@Override
	public void deleteByTaskId(String taskId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(TestItem.class)+" WHERE task.id ='"+taskId+"'").executeUpdate();
	}

	@Override
	public void deleteByTaskId(String taskId, String type) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(TestItem.class)+" WHERE task.id ='"+taskId+"' AND type='"+type+"'").executeUpdate();
	}

	@Override
	public void deleteByTim(String timId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(TestItem.class)+" WHERE tim.id ='"+timId+"'").executeUpdate();
	}

	@Override
	public List<TestItem> listByTaskId(String taskId) {
		String hql="FROM "+getEntityName(TestItem.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"' ORDER BY cyDate,sort asc";
		return super.list(hql);
	}

	@Override
	public List<TestItem> listByTaskId(String taskId, String type) {
		String hql="FROM "+getEntityName(TestItem.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"' AND type='"+type+"' ORDER BY cyDate,sort asc";
		return super.list(hql);
	}

	@Override
	public List<TestItem> listByTimId(String timId) {
		String hql="FROM "+getEntityName(TestItem.class)+" WHERE isDel='"+Po.N+"' AND tim.id ='"+timId+"' ORDER BY cyDate,sort asc";
		return super.list(hql);
	}

	@Override
	public List<TestItem> listByParent(String pid) {
		String hql="FROM "+getEntityName(TestItem.class)+" WHERE isDel='"+Po.N+"' AND parent.id ='"+pid+"' ORDER BY sort asc";
		return super.list(hql);
	}

	@Override
	public TestItem find(String pointId, String cyDate, String itemId) {
		String hql="FROM "+getEntityName(TestItem.class)+" WHERE isDel='"+Po.N+"' AND point.id ='"+pointId+"' AND cyDate='"+cyDate+"' AND itemId='"+itemId+"'";
		List<TestItem> list=super.list(hql);
		if(null!=list&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public int getMaxSort(String timId) {
		StringBuffer jpql = new StringBuffer("SELECT MAX(sort) FROM "
				+ getEntityName(TestItem.class)+" WHERE isDel="+Po.N+" AND tim.id='"+timId+"'");
		Object obj = query(jpql.toString()).getSingleResult();
		int maxSort = 0;
		if(null != obj){
			maxSort = (Integer)obj;
		}
		return maxSort;
	}

	@Override
	public int maxItemNum4Tim(String timId) {
		String sql="SELECT max(c.ct) from ("
				+ "select it.id,count(rt.id) as ct FROM v_bus_test_result rt "
				+ "join v_bus_test_item it on rt.it_id=it.id AND it.is_del='"+Po.N
				+"' WHERE rt.is_del='"+Po.N+"' AND it.tim_id ='"+timId+"'  group by it.id) c ";
		Object n=super.queryBysql(sql).getSingleResult();
		if(null!=n) {
			return Integer.valueOf(n.toString());
		}else {
			return 0;
		}
	}
}
