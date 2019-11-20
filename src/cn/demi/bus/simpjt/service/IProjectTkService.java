package cn.demi.bus.simpjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Transactional
public interface IProjectTkService extends IBaseService<ProjectVo> {
	/**
	 * 已办记录
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException;
	/**
	 * 获取踏勘信息
	 * 职卫 公共卫生
	 * @param projectId 项目Id
	 */
	ProjectVo find(String projectId) throws GlobalException;
	
	/**
	 * 更新踏勘信息
	 * @param v
	 * @throws GlobalException
	 */
	void update4Survey(ProjectVo v) throws GlobalException;
	/**
	 * 定时任务 
	 * 自动自动检测踏勘任务进度
	 * 1天内向踏勘人 推送消息
	 * 超期向踏勘人和指派人 推送消息
	 * @param v
	 * @return
	 */
	public void excutSchedule()throws GlobalException;
	
	
	
	
	
}
