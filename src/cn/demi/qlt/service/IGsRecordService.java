package cn.demi.qlt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.qlt.vo.GsRecordVo;

@Transactional
public interface IGsRecordService extends IBaseService<GsRecordVo> {
	
}
