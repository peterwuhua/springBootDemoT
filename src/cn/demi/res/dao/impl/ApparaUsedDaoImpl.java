package cn.demi.res.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.res.dao.IApparaUsedDao;
import cn.demi.res.po.ApparaUsed;
/**
 * 仪器使用记录
 * @author QuJunLong
 *
 */
@Repository("res.apparaUsedDao")
public class ApparaUsedDaoImpl  extends BaseDaoImpl<ApparaUsed> implements IApparaUsedDao{

	@Override
	public void deleteByBusId(String busId) {
		String hql="DELETE FROM  "+getEntityName(ApparaUsed.class)+" WHERE busId='"+busId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}

}
