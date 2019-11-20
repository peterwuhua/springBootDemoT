package cn.demi.qlt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.qlt.vo.AbilityVo;

@Transactional
public interface IAbilityService extends IBaseService<AbilityVo> {
	
//	public void update4Audit(AbilityVo v) throws GlobalException;
	
	public void update4Record(AbilityVo v) throws GlobalException;
	
	public GridVo gridData4Query(GridVo gridVo, AbilityVo v) throws GlobalException;
}
