package cn.demi.bus.pg.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.bus.pg.po.Progress;

public interface IProgressDao extends IBaseDao<Progress> {
	/**
	 * 增加进度
	 * @param taskId 任务id
	 * @param busType 业务类型
	 * @param status 审批状态
	 * @param msg   审批备注
	 * @param org   操作部门
	 * @param user  操作人
	 * @return
	 */
	public Progress add(String busId, String status,String orgId,String orgName, String userId,String userName);

	/**
	 * 
	 * @Title: add4Project   
	 * @Description: 增加进度   
	 * @param: @param busId
	 * @param: @param status
	 * @param: @param orgId
	 * @param: @param orgName
	 * @param: @param userId
	 * @param: @param userName
	 * @param: @return      
	 * @return: Progress      
	 * @throws
	 */
	public Progress add4Project(String busId, String status, String orgId, String orgName, String userId, String userName);
	
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
	public Progress addApp(String busId, String status,ObjVo objVo);
	/**
	 * 更新进度
	 * @param id
	 * @param status
	 * @param orgId
	 * @param orgName
	 * @param userId
	 * @param userName
	 * @return
	 */
	public Progress update(String id,String status,String orgId,String orgName, String userId,String userName);
	/**
	 * 更新进度
	 * @param id
	 * @param status
	 * @param userId
	 * @param userName
	 * @return
	 */
	public Progress update(String id,String status,String userId,String userName);
	
	
	/**
	 * 
	 * @Title: update4Project   
	 * @Description: 更新进度   
	 * @param: @param id
	 * @param: @param status
	 * @param: @param orgId
	 * @param: @param orgName
	 * @param: @param userId
	 * @param: @param userName
	 * @param: @return      
	 * @return: Progress      
	 * @throws
	 */
	public Progress update4Project(String id, String status, String orgId, String orgName, String userId, String userName);
	/**
	 * 
	 * @Title: update4Project   
	 * @Description: 更新进度   
	 * @param: @param id
	 * @param: @param status
	 * @param: @param userId
	 * @param: @param userName
	 * @param: @return      
	 * @return: Progress      
	 * @throws
	 */
	public Progress update4Project(String id,String status,String userId,String userName);
	
	/**
	 * 
	 * @Title: update4Project   
	 * @Description: 更新进度   
	 * @param: @param id 业务id
	 * @param: @param status 审批状态
	 * @param: @return      
	 * @return: Progress      
	 * @throws
	 */
	public Progress update4Project(String id, String status);
	/**
	 * 更新进度
	 * @param busId  业务id
	 * @param busType 业务类型
	 * @param status 审批状态
	 * @return
	 */
	public Progress update(String id,String status);
	/**
	 * 根据业务id获取进度
	 * @param taskId 任务id
	 * @return
	 */
	public Progress findByBusId(String busId);
	/**
	 * 根据业务id 删除进度实例
	 * @param taskId 任务id
	 * @return
	 */
	public void deleteByBusId(String busId);
	/**
	 * 检查任务下所有报告是否完成
	 * 质检
	 * @param sampId
	 * @return
	 * @throws GlobalException
	 */
	public boolean checkAllComp(String taskId,String comSt);
}

