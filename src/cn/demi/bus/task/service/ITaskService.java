package cn.demi.bus.task.service;


import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.task.vo.TaskVo;

@Transactional
public interface ITaskService extends IBaseService<TaskVo> {
	
	void update4File(TaskVo v) throws GlobalException;
	/**
	 * 登记测试
	 * 新增
	 * @return
	 * @throws GlobalException
	 */
	public TaskVo find4Dj(TaskVo v) throws GlobalException;
	/**
	 * 获取样品任务单
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public TaskVo find4Rwd(String id) throws GlobalException;
 
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,TaskVo v) throws GlobalException;
	/**
	 * 获取历史记录
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Old(GridVo gridVo,TaskVo v) throws GlobalException;
	/**
	 * 获取历史记录
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Lx(GridVo gridVo,TaskVo v) throws GlobalException;
	/**
	 * 外部人员登记
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Other(GridVo gridVo,TaskVo v) throws GlobalException;
	/**
	 * 外部人员登记
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Select(GridVo gridVo,TaskVo v) throws GlobalException;
	
	/**
	 * 
	 * @Title: gridData4MySelect   
	 * @Description: 我的客户任务进度跟踪 
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	public GridVo gridData4MySelect(GridVo gridVo,TaskVo v) throws GlobalException;
	/**
	 * 更新 项目的方法
	 * 已办
	 * @param v
	 * @throws GlobalException
	 */
	public void update4Yb(TaskVo v) throws GlobalException;
	/**
	 * 外部登记保存
	 * @param id
	 * @param sort
	 * @return
	 * @throws GlobalException
	 */
	void save4Other(TaskVo v) throws GlobalException;
	/**
	 * 流程终止
	 * @param id
	 * @param sort
	 * @return
	 * @throws GlobalException
	 */
	void update2Stop(TaskVo v) throws GlobalException;
	/**
	 * 更新任务进度
	 * @param taskIds 任务ids
	 * @param timIds 项目任务ids
	 * @return
	 * @throws GlobalException
	 */
	void update2St(String taskIds,String timIds) throws GlobalException;
}
