package cn.demi.zk.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.zk.po.ZkProgressLog;

public interface IZkProgressLogDao extends IBaseDao<ZkProgressLog> {
	/**
	 * 增加进度日志
	 * @param taskId 任务id
	 * @param busType 业务类型
	 * @param status 审批状态
	 * @param msg   审批备注
	 * @return
	 */
	public ZkProgressLog add(String taskId,String status,String audit,String msg);
	/**
	 * 根据任务id获取进度日志集合
	 * @param taskId 任务id
	 * @return
	 */
	public List<ZkProgressLog> findByTaskId(String taskId);
	/**
	 * 根据业务id获取某状态进度日志
	 * @param taskId 任务id
	 * @return
	 */
	public ZkProgressLog findOne(String budId,String status);
}

