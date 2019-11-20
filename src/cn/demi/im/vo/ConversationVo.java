package cn.demi.im.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.AccountVo;
/**
 * Create on : 2016年12月15日 下午8:52:42  <br>
 * Description : 即时通讯会话Vo  <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public class ConversationVo extends Vo<ConversationVo> {
	/**
	 * 发送人
	 */
	private AccountVo senderVo;
	/**
	 * 接收人
	 */
	private AccountVo receiveVo;

	/**
	 * 群组
	 */
	private GroupVo groupVo;

	public AccountVo getSenderVo() {
		return senderVo;
	}

	public void setSenderVo(AccountVo senderVo) {
		this.senderVo = senderVo;
	}

	public AccountVo getReceiveVo() {
		return receiveVo;
	}

	public void setReceiveVo(AccountVo receiveVo) {
		this.receiveVo = receiveVo;
	}

	public GroupVo getGroupVo() {
		return groupVo;
	}

	public void setGroupVo(GroupVo groupVo) {
		this.groupVo = groupVo;
	}
	
	
}

