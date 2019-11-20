package cn.demi.office.service;

import org.springframework.transaction.annotation.Transactional;

import cn.demi.office.vo.DgTjVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;

@Transactional
public interface IDgTjService extends IBaseService<DgTjVo> {
	
	void excutSchedule()throws GlobalException;
}
