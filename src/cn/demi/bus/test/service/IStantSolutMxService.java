package cn.demi.bus.test.service;

import org.springframework.transaction.annotation.Transactional;

import cn.demi.bus.test.vo.StantSolutMxVo;
import cn.core.framework.common.service.IBaseService;

@Transactional
public interface IStantSolutMxService extends IBaseService<StantSolutMxVo> {
	
}
