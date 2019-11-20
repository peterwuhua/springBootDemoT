package cn.demi.qlt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.qlt.vo.ComplainVo;

@Transactional
public interface IComplainService extends IBaseService<ComplainVo> {
	
}
