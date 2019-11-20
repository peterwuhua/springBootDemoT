package cn.demi.cus.customer.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.cus.customer.vo.CustOkVo;

@Transactional
public interface ICustOkService extends IBaseService<CustOkVo> {
	
}
