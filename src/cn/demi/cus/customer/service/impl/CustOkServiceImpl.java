package cn.demi.cus.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.dao.ICustOkDao;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.CustOk;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.service.ICustOkService;
import cn.demi.cus.customer.vo.CustOkVo;

@Service("cus.custOkService")
public class CustOkServiceImpl extends BaseServiceImpl<CustOkVo,CustOk> implements
		ICustOkService {
	
	@Autowired 
	private ICustomerDao customerDao;
	@Autowired 
	private ICustOkDao custOkDao;
	@Override
	public void add(CustOkVo v) throws GlobalException {
		CustOk p = vo2Po(v);
		Customer cust=customerDao.findById(v.getCustomerVo().getId());
		p.setCustomer(cust);
		p.setOrgId(getCurrent().getOrgId());
		p.setOrgName(getCurrent().getOrgName());
		p.setUserId(getCurrent().getAccountId());
		p.setUserName(getCurrent().getUserName());
		custOkDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public void update(CustOkVo v) throws GlobalException {
		CustOk p = custOkDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		Customer cust=customerDao.findById(v.getCustomerVo().getId());
		p.setCustomer(cust);
		custOkDao.update(p);
	}
	
}
