package cn.demi.bus.pg.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pg.vo.ProgressVo;

@Transactional
public interface IProgressService extends IBaseService<ProgressVo> {
	
	public ProgressVo findById(ProgressVo v)throws GlobalException;
	
	/**
	 * 
	 * @Title: findById4Simple   
	 * @Description: 项目管理（简易）  
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: ProgressVo      
	 * @throws
	 */
	public ProgressVo findById4Simple(ProgressVo v)throws GlobalException;
}
