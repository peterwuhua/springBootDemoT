package cn.demi.res.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.res.dao.IApparaOutDao;
import cn.demi.res.po.ApparaOut;

@Repository("res.apparaOutDao")
public class ApparaOutDaoImpl extends BaseDaoImpl<ApparaOut> implements IApparaOutDao {

	@Override
	public void deleteByBusId(String busId) {
		String hql="DELETE FROM  "+getEntityName(ApparaOut.class)+" WHERE busId='"+busId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}
}
