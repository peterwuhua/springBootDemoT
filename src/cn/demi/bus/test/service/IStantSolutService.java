package cn.demi.bus.test.service;

import org.springframework.transaction.annotation.Transactional;

import cn.demi.bus.test.vo.StantSolutVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;

@Transactional
public interface IStantSolutService extends IBaseService<StantSolutVo> {
	public StantSolutVo find(StantSolutVo vo) throws GlobalException;
}
