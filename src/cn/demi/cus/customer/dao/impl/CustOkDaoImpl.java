package cn.demi.cus.customer.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.cus.customer.dao.ICustOkDao;
import cn.demi.cus.customer.po.CustOk;

@Repository("cus.custOkDao")
public class CustOkDaoImpl extends BaseDaoImpl<CustOk> implements ICustOkDao {
}
