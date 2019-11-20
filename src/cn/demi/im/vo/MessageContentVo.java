package cn.demi.im.vo;

import cn.core.framework.common.vo.Vo;
/**
 * Create on : 2016年12月15日 下午8:54:12  <br>
 * Description :  即时通讯消息记录Vo<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public class MessageContentVo extends Vo<MessageContentVo> {
	
	private String system; // true //系统消息
	private String type;//聊天窗口来源 friend  朋友  group 群聊
	private String content; //消息内容
	private String mine;//true 表示我发送的消息 false表示对方发送的消息
	private ConversationVo conversationVo;//会话对象
	private String senderTime;//服务端动态时间戳
	private String mineId;
	private String toId;
	private String status;// true对方在线消息发送成功     false表示对方不在线消息未发送成功
	
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMine() {
		return mine;
	}
	public void setMine(String mine) {
		this.mine = mine;
	}
	public ConversationVo getConversationVo() {
		return conversationVo;
	}
	public void setConversationVo(ConversationVo conversationVo) {
		this.conversationVo = conversationVo;
	}
	public String getSenderTime() {
		return senderTime;
	}
	public void setSenderTime(String senderTime) {
		this.senderTime = senderTime;
	}
	public String getMineId() {
		return mineId;
	}
	public void setMineId(String mineId) {
		this.mineId = mineId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}

