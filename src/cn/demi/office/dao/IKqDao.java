package cn.demi.office.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.office.po.Kq;

public interface IKqDao extends IBaseDao<Kq> {
	
	/**
	 *  考勤数据接口
	 * @param userId
	 * @param startTime 开始时间
	 * @param endTime 截止时间
	 * @param msg 描述内容
	 * @param type 请假/外勤
	 * @param status 状态
	 * @param auditId 审核人
	 *
	 *
	 */
	public void insert(String userId,String startTime,String endTime,String msg,String type,String status,String auditId);
	/**
	 * 指派类 外勤数据接口
	 * 直接完成状态
	 * @param userId 多个逗号隔开
	 * @param startTime 开始时间
	 * @param endTime 截止时间
	 * @param msg 描述内容
	 * @param busId 业务Id
	 */
	public void insert(String userId,String startTime,String endTime,String msg,String busId);
	/**
	 * 指派类 外勤数据接口
	 * 删除 
	 * @param busId 业务ID
	 */
	public void deleteByBusId(String busId);
	/**
	 * 获取用户某日的考勤状态
	 * 无则返回null
	 * @param userId
	 * @param date
	 * @return
	 */
	public String findByUserId(String userId,String date);
}

