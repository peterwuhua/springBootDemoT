package cn.demi.office.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.office.vo.DtVo;

@Transactional
public interface IDtService extends IBaseService<DtVo> {
	
}
