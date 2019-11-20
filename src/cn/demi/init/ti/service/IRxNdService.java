package cn.demi.init.ti.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.init.ti.vo.RxNdVo;

@Transactional
public interface IRxNdService extends IBaseService<RxNdVo> {
	
}
