package cn.demi.bus.test.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.test.po.TestItem;

public interface ITestItemDao extends IBaseDao<TestItem> {
	/**
	 * 根据任务编号删除测试项目信息
	 * @param taskId 任务ID
	 */
	public void deleteByTaskId(String taskId);
	/**
	 * 根据任务删除测试项目信息
	 * @param taskId 任务ID
	 * @param type 项目类型
	 */
	public void deleteByTaskId(String taskId,String type);
	/**
	 * 根据项目任务删除其下的测试项目信息
	 * @param timId 项目任务Id
	 */
	public void deleteByTim(String timId);
	/**
	 * 获取测试项目信息
	 * @param pointId 点位
	 * @param cyDate 采样日期
	 * @param itemId 测试项目
	 * @return
	 */
	TestItem find(String pointId,String cyDate,String itemId);
	/**
	 * 根据任务编号获取项目集合
	 * @param taskId 任务Id
	 */
	public List<TestItem> listByTaskId(String taskId);
	/**
	 * 根据任务获取测试项目集合
	 * @param taskId 任务Id
	 * @param type 类型
	 */
	public List<TestItem> listByTaskId(String taskId,String type);
	
	/**
	 * 根据测试任务获取测试项目集合
	 * @param timId 测试任务ID
	 */
	public List<TestItem> listByTimId(String timId);
	/**
	 * 获取项目集合
	 * @param pid 父Id
	 */
	public List<TestItem> listByParent(String pid);
	/**
	 * <strong>Description :获取最大序号</strong> <br>
	 * @return int
	 */
	public int getMaxSort(String timId);
	/**
	 * 获取 项目任务下 最多结果数
	 * @param timId
	 * @return
	 */
	public int maxItemNum4Tim(String timId);
}

