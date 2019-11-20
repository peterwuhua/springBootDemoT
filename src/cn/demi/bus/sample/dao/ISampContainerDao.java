package cn.demi.bus.sample.dao;
import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.sample.po.SampContainer;
/**
 * 采样容器数据持久层接口
 * @author QuJunLong
 *
 */
public interface ISampContainerDao extends IBaseDao<SampContainer> {
	/**
	 * 根据任务编号删除所有容器信息
	 * @param no 任务编号
	 */
	public void deleteByNo(String no);
	/**
	 * 根据样品编号删除所有容器信息
	 * @param sampId 样品Id
	 */
	public void deleteBySamp(String sampId);
	/**
	 * 根据任务删除所有容器信息
	 * @param taskId 任务Id
	 */
	public void deleteByTask(String taskId);
	/**
	 * 根据任务删除所有容器信息
	 * @param pointId 点位Id
	 */
	public void deleteByPoint(String pointId);
	/**
	 * 获取点位的容器
	 * @param pointId
	 * @return
	 */
	List<SampContainer> listByPointId(String pointId);
	/**
	 * 获取样品的容器
	 * @param taskId
	 * @return
	 */
	List<SampContainer> listByTaskId(String taskId);
	/**
	 * 获取样品的容器
	 * @param sampId
	 * @return
	 */
	List<SampContainer> listBySampId(String sampId);
}

