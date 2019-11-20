package cn.demi.base.system.dao;

import cn.demi.base.system.po.SysMsg;
import cn.core.framework.common.dao.IBaseDao;

public interface ISysMsgDao extends IBaseDao<SysMsg> {
	/**
	 * 系统消息发送接口
	 * @param busId 业务id
	 * @param busType 业务类型
	 * @param title 消息标题
	 * @param content 消息内容
	 * @param reciveId 接收人
	 * @param reciveName 接收人
	 * @param type  接收人类型：客户，内部账户，角色，部门
	 * @param remark 备注
	 */
	public void sendMsg(String busId,String busType,String title,String content,String reciveId,String reciveName,String type);
}

