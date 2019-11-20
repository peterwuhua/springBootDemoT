package cn.demi.office.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.office.vo.KqVo;

@Transactional
public interface IKqSpService extends IBaseService<KqVo> {
	
}
