package cn.demi.base.system.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.base.system.vo.CfgVo;

@Transactional
public interface ICfgService extends IBaseService<CfgVo> {
	
}
