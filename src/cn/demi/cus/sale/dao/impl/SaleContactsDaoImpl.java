package cn.demi.cus.sale.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.cus.sale.dao.ISaleContactsDao;
import cn.demi.cus.sale.po.SaleContacts;

@Repository("cus.saleContactDao")
public class SaleContactsDaoImpl extends BaseDaoImpl<SaleContacts> implements ISaleContactsDao {
}
