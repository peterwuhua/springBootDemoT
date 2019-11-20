package cn.demi.bus.task.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.task.vo.TaskVo;

@Transactional
public interface ITaskSumService extends IBaseService<TaskVo> {	
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,TaskVo v) throws GlobalException;
	/**
	 * 项目终止
	 * @param ids
	 * @throws GlobalException
	 */
//	void update2Stop(TaskVo v) throws GlobalException;
	/**
	 * 项目退回
	 * @param ids
	 * @throws GlobalException
	 */
	void updateBack(TaskVo v) throws GlobalException;
	/**
	 * 项目重测
	 * @param ids
	 * @throws GlobalException
	 */
//	void updateCc(TaskVo v) throws GlobalException;
	 
}
