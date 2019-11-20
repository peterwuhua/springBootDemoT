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
 * 仪器出库管理
 * @author QuJunLong
 *
 */
@Entity(name = "res_appara_out")
@Table(name = "res_appara_out")
@Module(value = "res.apparaOut")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApparaOut extends Po<ApparaOut>{
	
	public static final String ST_0="待出库";//仪器出库状态
	public static final String ST_1="已出库";//仪器出库状态
	
	private static final long serialVersionUID = 321034992986711061L;
	public String[] PROPERTY_TO_MAP= {"id","sort","appara","order","useName","useTime","mobile","appStatus","status","cyNames"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	//仪器信息
	private Appara appara;
	private String busId;
	//采样小组
	private String cyIds;
	private String cyNames;
	//登记人
	private String userId;
	private String userName;
	//出库人部门
	private String orgId;
	private String orgName;
	//出库人
	private String useId;
	private String useName;
	//出库时间
	private String useTime;
	//预计入库时间
	private String backTime;
	//出库人手机
	private String mobile;
	//仪器状态
	private String appStatus;
	//仪器状态
	private String status;
	//备注
	private String remark;
	@ManyToOne
	@JoinColumn(name = "appara_id")
	public Appara getAppara() {
		return appara;
	}
	public void setAppara(Appara appara) {
		this.appara = appara;
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
	@Column(length = 20)
	public String getBackTime() {
		return backTime;
	}
	public void setBackTime(String backTime) {
		this.backTime = backTime;
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
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ApparaOut.class, false, ActionType.JSP);
	}
	
}
