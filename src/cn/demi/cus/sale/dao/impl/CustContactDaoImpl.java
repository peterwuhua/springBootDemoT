package cn.demi.cus.sale.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.cus.sale.dao.ICustContactDao;
import cn.demi.cus.sale.po.CustContact;

@Repository("cus.custContactDao")
public class CustContactDaoImpl extends BaseDaoImpl<CustContact> implements ICustContactDao {
}
