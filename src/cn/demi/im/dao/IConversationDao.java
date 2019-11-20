package cn.demi.im.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.im.po.Conversation;
/**
 * Create on : 2016年12月13日 下午5:30:59  <br>
 * Description : 会话 IConversationDao<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public interface IConversationDao extends IBaseDao<Conversation> {
	/**
	 * Create on : Paddy Zhang 2016年12月13日 下午5:31:22 <br>
	 * Description : 根据发信人Id与收信人Id查询会话对象 <br>
	 * @param mineId 发送人ID
	 * @param toId 收信人ID
	 * @return Conversation
	 */
	public Conversation findByMineAndToId(String mineId, String toId);
	
	void addData(Conversation po);
	
}

