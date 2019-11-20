package cn.demi.bus.pjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.vo.CustWorkVo;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.bus.pjt.vo.SurveyVo;

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
	 * 环境
	 * @param projectId 项目Id
	 */
	SurveyVo findByProjectId(String projectId) throws GlobalException;
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
	void update4Survey(SurveyVo v) throws GlobalException;
	/**
	 * 踏勘任务转移
	 * @param v
	 * @throws GlobalException
	 */
	void update4Zy(SurveyVo v) throws GlobalException;
	/**
	 * 定时任务 
	 * 自动自动检测踏勘任务进度
	 * 1天内向踏勘人 推送消息
	 * 超期向踏勘人和指派人 推送消息
	 * @param v
	 * @return
	 */
	public void excutSchedule()throws GlobalException;
	
	/**
	 * 
	 * @Title: findById4Word   
	 * @Description: 物料单、点位表 
	 * @param: @param id
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: ProjectVo      
	 * @throws
	 */
	public ProjectVo findById4Word(String id,String type) throws GlobalException;
	
	/**
	 * 
	 * @Title: update4Word   
	 * @Description: 更新踏勘文件路径  
	 * @param: @param v
	 * @param: @throws GlobalException      
	 * @return: void      
	 * @throws
	 */
	public void update4Word(ProjectVo v) throws GlobalException;
	
	/**
	 * 
	 * @Title: findById4DCB   
	 * @Description: 现场调查表信息查询  
	 * @param: @param id
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: CustWorkVo      
	 * @throws
	 */
	public CustWorkVo findById4DCB(String id) throws GlobalException;
	
	
}
