package cn.demi.office.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.office.vo.CongressVo;

@Transactional
public interface ICongressViewService extends IBaseService<CongressVo> {
	
	public void updateStatus(CongressVo vo) throws GlobalException;
}
