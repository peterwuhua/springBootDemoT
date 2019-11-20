package cn.demi.bus.test.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.test.po.TaskItem;

public interface ITaskItemDao extends IBaseDao<TaskItem> {
	/**
	 * 根据任务删除项任务目信息
	 * @param taskId 任务ID
	 */
	public void deleteByTaskId(String taskId);
	/**
	 * 根据任务删除项目任务信息
	 * @param taskId 任务ID
	 * @param type 项目类型
	 */
	public void deleteByTaskId(String taskId,String type);
	 
	/**
	 * 根据任务获取项目任务集合
	 * @param taskId 任务Id
	 */
	public List<TaskItem> listByTaskId(String taskId);
	/**
	 * 根据任务获取项目任务集合
	 * @param taskId 任务Id
	 * @param type 类型
	 */
	public List<TaskItem> listByTaskId(String taskId,String type);
	/**
	 * 根据任务和项目获取项目任务信息
	 * @param taskId 任务Id
	 * @param itemId 项目Id
	 */
	public TaskItem find(String taskId,String itemId);
	/**
	 * <strong>Description :获取最大序号</strong> <br>
	 * @return int
	 */
	public int getMaxSort(String taskId);
	 
}

