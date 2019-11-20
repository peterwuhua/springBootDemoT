package cn.demi.base.system.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name="sys_message")
@Table(name="sys_message")
@Module(value="sys.message")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Message extends Po<Message>{
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","type","status","subject","content","sender","reciver"};
	/**
	 * 发送时间
	 */
	private String sendTime;	//发送时间
	/**
	 * 1未删除 0删除
	 */
	private int position = N;// 1未删除 0删除
	/**
	 * 类型
	 */
	private String type;// 类型
	/**
	 * status
	 */
	private String status;//
	/**
	 * 主题
	 */
	private String subject;//主题
	/**
	 * 内容
	 */
	private String content;//内容
	/**
	 * 发件人
	 */
	private Account sender;//发件人
	/**
	 * 收件人
	 */
	private String reciver;//收件人
	/**
	 * 收件人
	 */
	private String reciverIds;//收件人
	
	@Column(length=64)
	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	@Column(length=64)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(length=64)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length=64)
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	@ManyToOne
	@JoinColumn(name = "sender_id")
	public Account getSender() {
		return sender;
	}
	
	public void setSender(Account sender) {
		this.sender = sender;
	}
	@Lob
	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}
	
	@Lob
	public String getReciverIds() {
		return reciverIds;
	}
	public void setReciverIds(String reciverIds) {
		this.reciverIds = reciverIds;
	}
	@Lob
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

	
}
