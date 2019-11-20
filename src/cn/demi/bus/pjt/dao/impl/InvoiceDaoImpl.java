package cn.demi.bus.pjt.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.bus.pjt.dao.IInvoiceDao;
import cn.demi.bus.pjt.po.Invoice;

@Repository("bus.invoiceDao")
public class InvoiceDaoImpl extends BaseDaoImpl<Invoice> implements IInvoiceDao {
}
