package cn.demi.im.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.im.vo.ConversationVo;
/**
 * Create on : 2016年12月15日 下午6:57:29  <br>
 * Description : IConversationService 即时通讯会话service <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Transactional
public interface IConversationService extends IBaseService<ConversationVo> {
	/**
	 * Create on : Paddy Zhang 2016年12月15日 下午6:56:16 <br>
	 * Description :  获取即时会话的好友分组列表<br>
	 * @return
	 */
	public Map<String, Object> listFriend();
	/**
	 * Create on : Paddy Zhang 2016年12月15日 下午6:56:40 <br>
	 * Description :  添加即时会话的消息记录<br>
	 * @param mineId 发送人Id
	 * @param toId 收信人的Id
	 * @param vo
	 * @return
	 * @throws GlobalException
	 */
	public ConversationVo addConversation(String mineId, String toId, ConversationVo vo) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2017年3月10日 下午10:59:48 <br>
	 * Description : 即时通讯发送消息  <br>
	 * @param accountId  接收消息的账户的ID
	 * @param message 要发送的消息
	 * @throws GlobalException
	 */
	public void imSendMsg(String accountId, String message) throws GlobalException;
	
}
