package cn.demi.bus.task.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.task.dao.ISampAppDao;
import cn.demi.bus.task.po.SampApp;

@Repository("bus.sampAppDao")
public class SampAppDaoImpl extends BaseDaoImpl<SampApp> implements ISampAppDao {

	@Override
	public void deleteByBusId(String busId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(SampApp.class)+" WHERE busId ='"+busId+"'").executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SampApp> findByBusId(String busId) {
		String hql="FROM  "+getEntityName(SampApp.class)+" WHERE isDel='"+Po.N+"' AND busId='"+busId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}
}
