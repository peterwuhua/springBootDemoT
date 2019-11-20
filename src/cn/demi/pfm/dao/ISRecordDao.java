package cn.demi.pfm.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.pfm.po.SRecord;

public interface ISRecordDao extends IBaseDao<SRecord> {
	/**
	 * 业务模块调用接口
	 * 检查是否超时，超时自送插入记录
	 * @param no 任务编号
	 * @param msg 备注
	 * @param startTime 开始工作时间
	 * @param code 业务代码
	 */
	public void insert(String no,String startTime,String code);
	/**
	 * 业务模块调用接口
	 * 直接插入考核记录
	 * @param msg 说明
	 * @param code 业务代码
	 */
	public void insert(String msg,String code);
}
