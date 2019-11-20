package cn.demi.office.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;


@Entity(name = "office_audit_history")
@Table(name = "office_audit_history")
@Module(value = "office.auditHistory")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OfficeAuditHistory extends Po<OfficeAuditHistory>{
	
	private static final long serialVersionUID = 1L;
	public static final String ST_0="未审核";//状态
	public static final String ST_1="通过";//状态
	public static final String ST_2="不通过";//状态
	
	
	public String[] PROPERTY_TO_MAP= {"id","sort","pId","auditName","auditId","status","auditTime","auditCt"};
	public String[] IGNORE_PROPERTY_TO_PO= {"id"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}
	
	private String pId;//文件传阅主表id
	
	private String auditId;//审批人id
	private String auditName;//审批人
	private String auditTime;//审批时间
	private String auditCt;//审批意见
	
	private String status;//状态（审核状态）
	
	@Column(length = 32)
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	@Column(length = 32)
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	@Column(length = 32)
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	@Column(length = 16)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 20)
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	@Column(length = 128)
	public String getAuditCt() {
		return auditCt;
	}
	public void setAuditCt(String auditCt) {
		this.auditCt = auditCt;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(OfficeAuditHistory.class, false, ActionType.JSP);
	}
	
	
	
}
