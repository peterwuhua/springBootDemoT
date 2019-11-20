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
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 *  仪器入库管理
 * @author QuJunLong
 *
 */
@Entity(name = "res_equipment_in")
@Table(name = "res_equipment_in")
@Module(value = "res.equipmentIn")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EquipmentIn extends Po<EquipmentIn>{

	public static final String ST_0="待入库";//仪器入库状态
	public static final String ST_1="已入库";//仪器入库状态
	
	private static final long serialVersionUID = -8443556628628099744L;
	public String[] PROPERTY_TO_MAP= {"id","sort","equipt","out","useName","useTime","mobile","appStatus","status","duration"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	//仪器信息
	private Equipment equipt;
	//出库信息
	private EquipmentOut out;
	
	private String busId;
	//采样小组
	private String cyIds;
	private String cyNames;
	//登记人
	private String userId;
	private String userName;
	//入库人部门
	private String orgId;
	private String orgName;
	//入库人
	private String useId;
	private String useName;
	//入库时间
	private String useTime;
	 
	private String duration;//时长
	//入库人手机
	private String mobile;
	//仪器状态
	private String appStatus;
	//状态
	private String status;
	//备注
	private String remark;
	
	@ManyToOne
	@JoinColumn(name = "equipt_id")
	public Equipment getEquipt(){
		return equipt;
	}
	public void setEquipt(Equipment equipt) {
		this.equipt = equipt;
	}
	@ManyToOne
	@JoinColumn(name = "out_id")
	public EquipmentOut getOut() {
		return out;
	}
	public void setOut(EquipmentOut out) {
		this.out = out;
	}
	@Column(length = 32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length = 32)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 320)
	public String getUseId() {
		return useId;
	}
	public void setUseId(String useId) {
		this.useId = useId;
	}
	@Column(length = 320)
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	@Column(length = 20)
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	@Column(length = 16)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(length = 10)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 10)
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length = 64)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length = 32)
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	@Column(length = 320)
	public String getCyIds() {
		return cyIds;
	}
	public void setCyIds(String cyIds) {
		this.cyIds = cyIds;
	}
	@Column(length = 320)
	public String getCyNames() {
		return cyNames;
	}
	public void setCyNames(String cyNames) {
		this.cyNames = cyNames;
	}
	@Column(length = 64)
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(EquipmentIn.class, false, ActionType.JSP);
	}
}
