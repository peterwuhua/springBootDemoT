package cn.demi.base.system.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name="sys_message_detail")
@Table(name="sys_message_detail")
@Module(value="sys.message.detail")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MessageDetail extends Po<MessageDetail> {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","readTime","type","message","reciver"};
	/**
	 * 是否已阅读
	 */
	private int readFlag = N;	//	是否已阅读
	/**
	 * -1 垃圾箱  0 收检箱 1 重要 
	 */
	private int position = N;	//	-1 垃圾箱  0 收检箱 1 重要 
	/**
	 * 阅读时间
	 */
	private String readTime;	//	阅读时间
	/**
	 * 阅读类型
	 */
	private String type;	//	阅读时间
	/**
	 * 邮件主体
	 */
	private Message message;	// 	邮件主体
	/**
	 * 接收人
	 */
	private Account reciver;		//	接收人
	
	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@ManyToOne
	@JoinColumn(name = "message_id")
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@ManyToOne
	@JoinColumn(name = "reciver_id")
	public Account getReciver() {
		return reciver;
	}

	public void setReciver(Account reciver) {
		this.reciver = reciver;
	}
	@Column(length=64)
	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
	@Column(length=64)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
}
