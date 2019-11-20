package cn.demi.im.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.im.po.MessageContent;
/**
 * Create on : 2016年12月13日 下午5:27:14  <br>
 * Description : im消息记录数据库操作层 <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public interface IMessageContentDao extends IBaseDao<MessageContent> {
	/**
	 * Create on : Paddy Zhang 2016年12月13日 下午5:26:51 <br>
	 * Description :  根据会话ID查询消息记录<br>
	 * @param mineId 发信人ID
	 * @param toId 收信人ID
	 * @return 消息记录集合
	 */
	public List<MessageContent> listByMineAndToId(String mineId,String toId);
	
	/**
	 * 获取发现消息 唯一
	 * @param mineId 发送人
	 * @param toId 发向人
	 * @param time 发送时间
	 * @return
	 */
	public MessageContent findByMineAndToId(String mineId,String toId,String ct,String time);
	/**
	 * 重新保存方法
	 * 用户定时任务保存数据
	 * @param po
	 */
	void addData(MessageContent po);
	
}

