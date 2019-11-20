package cn.demi.bus.simpjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Transactional
public interface IProjectFaService extends IBaseService<ProjectVo> {
	
	/**
	 *组装方案信息
	 */
	public ProjectVo find(String id) throws GlobalException;
	
}
