package cn.demi.cus.bill.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.cus.bill.dao.IBillDao;
import cn.demi.cus.bill.po.Bill;

@Repository("cus.billDao")
public class BillDaoImpl extends BaseDaoImpl<Bill> implements IBillDao {
}
