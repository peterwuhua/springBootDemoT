package cn.demi.res.po;

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
/**
 * 仪器使用记录
 * @author QuJunLong
 */
@Entity(name = "res_equipment_used")
@Table(name = "res_equipment_used")
@Module(value="res.equipmentUsed")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EquipmentUsed extends Po<EquipmentUsed>{
	 
	private static final long serialVersionUID = 1L;
	public String[] PROPERTY_TO_MAP = {"id","sort","equipt","appStat","startTime","endTime","duration","orgName","userName","purpose"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
	private Equipment equipt;
	private String busId;
	/**
	 *仪器使用状态
	 */
	private String appStat;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 时长
	 */
	private String duration;
	/**
	 * 使用人部门
	 */
	private String orgId;
	private String orgName;
	/**
	 * 使用人
	 */
	private String userId;
	private String userName;
	//用途
	private String purpose;
	//备注
	private String remark;
	
	@ManyToOne
	@JoinColumn(name = "equipt_id")
	public Equipment getEquipt() {
		return equipt;
	}
	public void setEquipt(Equipment equipt) {
		this.equipt = equipt;
	}
	@Column(length = 32)
	public String getAppStat() {
		return appStat;
	}
	public void setAppStat(String appStat) {
		this.appStat = appStat;
	}
	@Column(length = 20)
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(length = 20)
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(length = 32)
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length = 32)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length = 320)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length = 320)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 128)
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	
}
