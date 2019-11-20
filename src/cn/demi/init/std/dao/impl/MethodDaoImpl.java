package cn.demi.init.std.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.init.std.dao.IMethodDao;
import cn.demi.init.std.po.Method;

@Repository("init.methodDao")
public class MethodDaoImpl extends BaseDaoImpl<Method> implements IMethodDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Method> listByStandId(String standId) {
		String hql = "FROM "+getEntityName(Method.class)+" WHERE standId='"+standId+"'";
		List<Method> imList=getEntityManager().createQuery(hql).getResultList();
		return imList;
	}

	@Override
	public void deleteByStandId(String standId) {
		String hql = "DELETE FROM "+getEntityName(Method.class)+" WHERE standId='"+standId+"'";
		getEntityManager().createQuery(hql).executeUpdate();
	}

	@Override
	public void update4Del(String standId) {
		String hql = "UPDATE "+getEntityName(Method.class)+" SET isDel='"+Po.Y+"' WHERE standId='"+standId+"'";
		getEntityManager().createQuery(hql).executeUpdate();
	}
}
