package cn.demi.bus.test.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.test.vo.TaskItemVo;

@Transactional
public interface ITaskItemService extends IBaseService<TaskItemVo> {

	/**
	 * 编辑页面 数据源
	 */
	public TaskItemVo find(String id) throws GlobalException;
	/**
	 *原始记录文件    数据源
	 */
	public TaskItemVo find4Temp(String id) throws GlobalException;
	/**
	 * 任务转移
	 * @param v
	 * @throws GlobalException
	 */
	public void update4Zy(TaskItemVo v) throws GlobalException;
	/**
	 *更新原始记录文件
	 * @param v
	 * @throws GlobalException
	 */
	public void updateFile(TaskItemVo v) throws GlobalException;
	/**
	 *更新限值文件
	 * @param v
	 * @throws GlobalException
	 */
	public void update4Limited(TaskItemVo v) throws GlobalException;
}
