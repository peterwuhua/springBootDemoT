package cn.demi.base.system.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 系统消息
 * @author QuJunLong
 *
 */
@Entity(name="sys_msg")
@Table(name="sys_msg")
@Module(value="sys.msg")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysMsg extends Po<SysMsg>{
 
	public static final String RT_CUST="cust";
	public static final String RT_USER="user";
	public static final String RT_ROLE="role";
	public static final String RT_ORG="org";
	
	private static final long serialVersionUID = 1521375210466691837L;
	public String[] PROPERTY_TO_MAP= {"id","sort","remark","receiver","content","status","busType","title","sendName","busId","sendTime"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 业务ID
	 */
	private String busId;//业务ID
	/**
	 * 业务模块
	 */
	private String busType;//业务模块
	/**
	 *标题
	 */
	private String title;
	/**
	 * 主信息
	 */
	private String content;
	/**
	 * 收件人
	 * 客户Id
	 * 账户Id
	 * 角色Id
	 * 部门Id
	 */
	private String receiver;
	private String receiverId;
	/**
	 * 收件人类型
	 * 客户cust
	 * 账户user
	 * 角色role
	 * 部门org
	 */
	private String receiverType; 
	/**
	 * 状态
	 * 0未读  1已读
	 */
	private String status;//状态
	/**
	 * 发送时间
	 */
	private String sendTime;//发送时间
	/**
	 * 发送人
	 */
	private String sendId;
	private String sendName;
	/**
	 * 备注
	 */
	private String remark;//备注
	
	@Column(length=256)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length=640)
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	@Column(length=256)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length=8)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length=64)
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	@Column(length=32)
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	@Column(length=20)
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	@Column(length=64)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(length=320)
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	@Column(length=32)
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	@Column(length=32)
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	@Column(length=64)
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(SysMsg.class, true, ActionType.JSP);
	}
	 
	
	
}
