package cn.demi.office.dao;

import cn.demi.office.po.DgTj;
import cn.core.framework.common.dao.IBaseDao;

public interface IDgTjDao extends IBaseDao<DgTj> {
	/**
	 * 当延迟打卡或补卡
	 * 同步某天的考勤统计 里的状态
	 * @param userId 用户Id
	 * @param date 
	 * @param date 
	 * @return
	 */
	void updDgTj(String userId,String date);
	/**
	 * 当延迟采样单、请假单时
	 * 同步某天的考勤统计 里的状态
	 * @param userId 用户Id
	 * @param date 
	 * @param date 
	 * @return
	 */
	void updDgTj(String userId,String startTime,String endTime,String  status);
	
	/**
	 * 定时任务执行 统计数据的插入
	 * 验证唯一性
	 * @param p
	 */
	void insertDgTj(DgTj p);
}

