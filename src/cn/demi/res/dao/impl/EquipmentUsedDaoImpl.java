package cn.demi.res.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.res.dao.IEquipmentUsedDao;
import cn.demi.res.po.EquipmentUsed;
/**
 * 仪器使用记录
 * @author QuJunLong
 *
 */
@Repository("res.equipmentUsedDao")
public class EquipmentUsedDaoImpl  extends BaseDaoImpl<EquipmentUsed> implements IEquipmentUsedDao{

	@Override
	public void deleteByBusId(String busId) {
		String hql="DELETE FROM  "+getEntityName(EquipmentUsed.class)+" WHERE busId='"+busId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}

}
