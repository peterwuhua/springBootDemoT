package cn.demi.base.system.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name="sys_sms")
@Table(name="sys_sms")
@Module(value="sys.sms")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sms extends Po<Sms>{
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","remark","receiver","content","status","busType","busInfo","result","busId","sendTime"};
	/**
	 * 备注
	 */
	private String remark;//备注
	/**
	 * 收件人
	 */
	private String receiver; //收件人
	/**
	 * 主信息
	 */
	private String content;//主信息
	/**
	 * 状态
	 */
	private String status;//状态
	/**
	 * 业务模块
	 */
	private String busType;//业务模块
	/**
	 * 业务模块
	 */
	private String busInfo;//业务模块
	/**
	 * 业务ID
	 */
	private String busId;//业务ID
	/**
	 * 状态
	 */
	private String result;//状态
	/**
	 * 发送时间
	 */
	private String sendTime;//发送时间
	@Column(length=64)
	public String getBusInfo() {
		return busInfo;
	}
	public void setBusInfo(String busInfo) {
		this.busInfo = busInfo;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length=64)
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length=64)
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
	@Column(length=64)
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	@Column(length=64)
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	@Column(length=64)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
}
