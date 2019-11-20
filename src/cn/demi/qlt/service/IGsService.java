package cn.demi.qlt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.qlt.vo.GsVo;

@Transactional
public interface IGsService extends IBaseService<GsVo> {
	
	public GridVo gridData4Record(GridVo gridVo, GsVo v) throws GlobalException;
	
}
