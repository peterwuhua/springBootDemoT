package cn.demi.bus.simpjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Transactional
public interface IProjectReptService extends IBaseService<ProjectVo> {
	
	/**
	 * 编辑页面获取值
	 */
	public ProjectVo find(String id) throws GlobalException;
	
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,ProjectVo v) throws GlobalException;
	
	
}
