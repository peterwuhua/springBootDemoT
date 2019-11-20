package cn.demi.bus.pjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.vo.ProjectVo;

@Transactional
public interface IProjectFaService extends IBaseService<ProjectVo> {
	/**
	 * 终止项目
	 * @param v
	 * @throws GlobalException
	 */
	public void update2Stop(ProjectVo v) throws GlobalException;
	
	/**
	 *组装方案信息
	 */
	public ProjectVo find(String id) throws GlobalException;
	
	/**
	 * 自动生成监测点信息
	 * 根据踏勘信息
	 * @param v
	 * @throws GlobalException
	 */
	public void addPoint(String id) throws GlobalException;
}
