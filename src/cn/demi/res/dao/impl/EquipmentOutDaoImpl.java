package cn.demi.res.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.res.dao.IEquipmentOutDao;
import cn.demi.res.po.EquipmentOut;

@Repository("res.equipmentOutDao")
public class EquipmentOutDaoImpl extends BaseDaoImpl<EquipmentOut> implements IEquipmentOutDao {

	@Override
	public void deleteByBusId(String busId) {
		String hql="DELETE FROM  "+getEntityName(EquipmentOut.class)+" WHERE busId='"+busId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}
}
