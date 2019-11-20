package cn.demi.bus.pjt.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.bus.pjt.dao.ICustDao;
import cn.demi.bus.pjt.po.Cust;

@Repository("bus.custDao")
public class CustDaoImpl extends BaseDaoImpl<Cust> implements ICustDao {
}
