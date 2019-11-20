package cn.demi.bus.task.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.task.po.TaskPoint;

public interface ITaskPointDao extends IBaseDao<TaskPoint> {
	
	/**
	 * 获取点位集合
	 * 根据任务Id
	 * @param taskId
	 * @return
	 */
	public List<TaskPoint> listByTaskId(String taskId);
	/**
	 * 删除点位信息
	 * @param taskId
	 */
	public void deleteByTaskId(String taskId);
	/**
	 * 获取该点位在该任务中的同类型顺序号
	 * @param taskId 任务Id
	 * @param ptId 点位Id
	 *  @param sampTypeId 样品id
	 * @return
	 */
	public int getSort4Code(String taskId,String ptId,String sampTypeId);
	
	/**
	 * <strong>Description :获取最大序号</strong> <br>
	 * @return int
	 */
	public int getMaxSort(String taskId);
}

