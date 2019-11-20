package cn.demi.bus.simpjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.simpjt.vo.ProjectVo;


@Transactional
public interface IProjectTaskDjService extends IBaseService<ProjectVo> {

 
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,ProjectVo v) throws GlobalException;
	
	/**
	 * 流程终止
	 * @param id
	 * @param sort
	 * @return
	 * @throws GlobalException
	 */
	void update2Stop(ProjectVo v) throws GlobalException;
	
 
}
