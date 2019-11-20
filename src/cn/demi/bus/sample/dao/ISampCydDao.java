package cn.demi.bus.sample.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.sample.po.SampCyd;

public interface ISampCydDao extends IBaseDao<SampCyd> {
	
	
	/**
	 * 根据任务ID删除样品信息
	 * @param taskId
	 */
	public void deleteByTask(String taskId);
	/**
	 * 获取任务样品集合
	 * @param taskId
	 * @return
	 */
	List<SampCyd> listByTaskId(String taskId);
	
	/**
	 * 自动分配采样单
	 * 默认每个点位每个日期每个模板一个采样单
	 * 获取任务样品集合
	 * @param pointId 点位Id
	 * @param cyd 采样单模板
	 * @param cyDate 采样日期
	 * @return
	 */
	SampCyd find4Auto(String pointId,String cyd,String cyDate);
	/**
	 * 自动分配菜单时查询采样单
	 * 默认每个日期每个模板一个采样单
	 * 获取任务样品集合
	 * @param taskId 点位Id
	 * @param cyd 采样单模板
	 * @param cyDate 采样日期
	 * @return
	 */
	SampCyd find4Task(String taskId,String cyd,String cyDate);
	
	int getMaxSort(String taskId);
	
}

