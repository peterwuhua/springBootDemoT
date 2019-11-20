package cn.demi.bus.pg.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.bus.pg.po.ProgressLog;

public interface IProgressLogDao extends IBaseDao<ProgressLog> {
	/**
	 * 增加进度日志
	 * 
	 * @param taskId 任务id
	 * @param busId  业务id
	 * @param status 业务类型
	 * @param audit  审批状态
	 * @param msg    审批备注
	 * @return
	 */
	public ProgressLog add(String taskId, String busId, String status, String audit, String msg);

	/**
	 * 
	 * @Title: add4Porject   
	 * @Description: 增加进度日志 
	 * @param: @param taskId
	 * @param: @param busId
	 * @param: @param status
	 * @param: @param audit
	 * @param: @param msg
	 * @param: @return      
	 * @return: ProgressLog      
	 * @throws
	 */
	public ProgressLog add4Porject(String taskId, String busId, String status, String audit, String msg);
	
	
	
	public ProgressLog add4Porject(String taskId, String busId, String status, String audit, String msg, String orgId,
			String orgName, String userId, String userName);
	
	
	/**
	 * App 增加进度
	 * 
	 * @param taskId  任务id
	 * @param busType 业务类型
	 * @param status  审批状态
	 * @param msg     审批备注
	 * @param org     操作部门
	 * @param user    操作人
	 * @return
	 */
	public ProgressLog addApp(String taskId, String busId, String status, String audit, String msg, ObjVo objVo);

	/**
	 * 增加进度日志(定时任务调用)
	 * 
	 * @param taskId 任务id
	 * @param busId  业务id
	 * @param status 业务类型
	 * @param audit  审批状态
	 * @param msg    审批备注
	 * @return
	 */
	public ProgressLog add(String taskId, String busId, String status, String audit, String msg, String orgId,
			String orgName, String userId, String userName);

	/**
	 * 根据任务id获取进度日志集合
	 * 
	 * @param taskId 任务id
	 * @return
	 */
	public List<ProgressLog> findByBusId(String taskId);

	/**
	 * 根据业务id获取某状态进度日志
	 * 
	 * @param taskId 任务id
	 * @return
	 */
	public ProgressLog findOne(String budId, String status);

	/**
	 * 获取进度日志集合
	 * 
	 * @param taskId 任务id
	 * @return
	 */
	public List<ProgressLog> findList(String taskId, String busId);

	void deleteByBusId(String busId);
}
