package cn.demi.im.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
import cn.core.framework.utils.current.CurrentUtils;
/**
 * Create on : 2016年12月15日 下午8:49:52  <br>
 * Description : 即时通讯消息记录Po <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Entity(name = "im_message_content")
@Table(name = "im_message_content")
@Module(value = "im.message.content")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MessageContent extends Po<MessageContent> {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","username","status","sign","avatar"};
	
	public static final String GROUP = "group";
	public static final String FRIEND = "friend";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	
	private String system; // true //系统消息
	private String type;//聊天窗口来源 friend  朋友  group 群聊
	private String content; //消息内容
	private Conversation conversation;//会话对象
	private String senderTime;//服务端动态时间戳  发送时间
	private String receiveTime;//服务端动态时间戳  成功接收时间
	private String status;// true对方在线消息发送成功     false表示对方不在线消息未发送成功
	
	
	@Column(length=64)
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	@Column(length=64)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Lob
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@ManyToOne
	@JoinColumn(name = "conversation_id")
	public Conversation getConversation() {
		return conversation;
	}
	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}
	@Column(length=64)
	public String getSenderTime() {
		return senderTime;
	}
	public void setSenderTime(String senderTime) {
		this.senderTime = senderTime;
	}
	@Column(length=32)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length=32)
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public void onAdd() {
		if(StrUtils.isBlankOrNull(this.getCreateUser())){
			this.setCreateUser(CurrentUtils.getCurrent().getKey());
		}
		if(StrUtils.isBlank(this.getLastUpdUser())){
			this.setLastUpdUser(CurrentUtils.getCurrent().getKey());
		}
		this.setCreateTime(System.currentTimeMillis());
		this.setLastUpdTime(System.currentTimeMillis());
	}
	
	@Override
	public void onUpdate() {
		if(StrUtils.isBlankOrNull(this.getLastUpdUser())){
			this.setCreateUser(CurrentUtils.getCurrent().getKey());
		}
		this.setLastUpdTime(System.currentTimeMillis());
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		// TODO Auto-generated method stub
		return new String[]{"id","sort","createTime","lastUpdTime","version","conversation"};
	}
	public static void main(String[] args) {
		
		CreateCodeUtils.autoCreateCode(MessageContent.class, false, ActionType.JSP);
	}
}