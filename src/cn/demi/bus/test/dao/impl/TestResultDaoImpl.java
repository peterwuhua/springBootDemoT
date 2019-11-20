package cn.demi.bus.test.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.test.dao.ITestResultDao;
import cn.demi.bus.test.po.TestResult;

@Repository("bus.testSampDao")
public class TestResultDaoImpl extends BaseDaoImpl<TestResult> implements ITestResultDao {
	
	@Override
	public void deleteByItId(String id) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(TestResult.class)+" WHERE it.id ='"+id+"'").executeUpdate();
	}

	@Override
	public void deleteBySamp(String sampId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(TestResult.class)+" WHERE samp.id ='"+sampId+"'").executeUpdate();
	}
	@Override
	public List<TestResult> listByItId(String itId) {
		String hql="FROM "+getEntityName(TestResult.class)+" WHERE isDel='"+TestResult.N+"' AND it.id ='"+itId+"' ORDER BY sort asc";
		return super.list(hql);
	}
	@Override
	public List<TestResult> listBySampId(String sampId) {
		String hql="FROM "+getEntityName(TestResult.class)+" WHERE isDel='"+TestResult.N+"' AND samp.id ='"+sampId+"' ORDER BY sort asc";
		return super.list(hql);
	}
	@Override
	public TestResult findBySampAndItem(String sampId, String itemId) {
		String hql="FROM "+getEntityName(TestResult.class)+" WHERE isDel='"+TestResult.N+"' AND samp.id ='"+sampId+"' AND it.itemId like '"+itemId+"'";
		List<TestResult> l= super.list(hql);
		if(null!=l&&l.size()>0) {
			return l.get(0);
		}else {
			return null;
		}
	}

	@Override
	public int getMaxSort(String itId) {
		StringBuffer jpql = new StringBuffer("SELECT MAX(sort) FROM "
				+ getEntityName(TestResult.class)+" WHERE isDel="+Po.N+" AND it.id='"+itId+"'");
		Object obj = query(jpql.toString()).getSingleResult();
		int maxSort = 0;
		if(null != obj){
			maxSort = (Integer)obj;
		}
		return maxSort;
	}
}
