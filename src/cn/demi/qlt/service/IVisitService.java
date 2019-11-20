package cn.demi.qlt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.qlt.vo.VisitVo;

@Transactional
public interface IVisitService extends IBaseService<VisitVo> {
	
}
