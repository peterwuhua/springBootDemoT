package cn.demi.init.car.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.init.car.vo.CarUseVo;

@Transactional
public interface ICarAuditService extends IBaseService<CarUseVo> {
	
}
