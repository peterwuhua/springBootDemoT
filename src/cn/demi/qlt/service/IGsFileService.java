package cn.demi.qlt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.qlt.vo.GsVo;

@Transactional
public interface IGsFileService extends IBaseService<GsVo> {
	
}
