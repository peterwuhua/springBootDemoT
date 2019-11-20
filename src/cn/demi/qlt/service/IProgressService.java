package cn.demi.qlt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pg.vo.ProgressVo;

@Transactional
public interface IProgressService extends IBaseService<ProgressVo> {
	
	public ProgressVo findById(ProgressVo v)throws GlobalException;
}
