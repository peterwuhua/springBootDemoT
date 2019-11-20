package cn.demi.cus.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.ContactsVo;
import cn.demi.cus.customer.vo.CustomerVo;

@Transactional
public interface ICustomerAuditService extends IBaseService<CustomerVo>{

 public	void add2mySaleContact(List<ContactsVo> contacts,String auth,CustomerVo v) throws GlobalException;
	
	
}
