package cn.demi.cus.fee.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.cus.fee.vo.FeeVo;

@Transactional
public interface IFeeService extends IBaseService<FeeVo> {
	
}
