package cn.demi.bus.simpjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.simpjt.vo.ProjectVo;


@Transactional
public interface IProjectService extends IBaseService<ProjectVo> {
	 /**
	  * 获取合同信息
	  * 开票申请
	  * @param gridVo
	  * @param v
	  * @return
	  * @throws GlobalException
	  */
	GridVo gridData4Kp(GridVo gridVo, ProjectVo v) throws GlobalException;
	/**
	 * 新增 编辑页面赋值
	 */
	public ProjectVo find4New(ProjectVo v) throws GlobalException;
	/**
	 * 终止项目
	 * @param v
	 * @throws GlobalException
	 */
	void update2Stop(ProjectVo v) throws GlobalException;
	
	/**
	 * 已办更新 项目信息
	 * @param v
	 * @throws GlobalException
	 */
	void updated(ProjectVo v) throws GlobalException;

	
	public ProjectVo  findByHtId(String htId)throws GlobalException;
	
 
}
