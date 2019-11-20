package cn.demi.qlt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.qlt.vo.AccidentVo;

@Transactional
public interface IAccidentService extends IBaseService<AccidentVo> {
	
	public void update4Audit(AccidentVo v) throws GlobalException;
	
	public GridVo gridData4Query(GridVo gridVo, AccidentVo v) throws GlobalException;
}
