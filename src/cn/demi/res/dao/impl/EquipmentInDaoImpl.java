package cn.demi.res.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.res.dao.IEquipmentInDao;
import cn.demi.res.po.EquipmentIn;

@Repository("res.equipmentInDao")
public class EquipmentInDaoImpl extends BaseDaoImpl<EquipmentIn> implements IEquipmentInDao {
}
