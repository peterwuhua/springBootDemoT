package cn.demi.office.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.office.dao.IOfficeSuppliesDao;
import cn.demi.office.po.OfficeSupplies;

@Repository("office.suppliesDao")
public class OfficeSuppliesDaoImpl extends BaseDaoImpl<OfficeSupplies> implements IOfficeSuppliesDao {
}
