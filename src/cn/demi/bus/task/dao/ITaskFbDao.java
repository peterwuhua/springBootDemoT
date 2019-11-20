package cn.demi.bus.task.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.task.po.TaskFb;

public interface ITaskFbDao extends IBaseDao<TaskFb> {
	
	/**
	 * 根据项目id删除分包详细信息
	 * @param pjId
	 */
	void deleteByTaskId(String pjId);
	/**
	 * 获取分包详情集合
	 * @param pjId
	 */
	List<TaskFb> listByTaskId(String pjId);
}

