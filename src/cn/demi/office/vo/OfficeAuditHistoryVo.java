package cn.demi.office.vo;

import cn.core.framework.common.vo.Vo;

public class OfficeAuditHistoryVo extends Vo<OfficeAuditHistoryVo> {
	private String pId;// 文件传阅主表id
	private String auditId;// 审批人id
	private String auditName;// 审批人
	private String auditTime;// 审批时间
	private String auditCt;// 审批意见
	private String status;// 状态（审核状态）
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditCt() {
		return auditCt;
	}
	public void setAuditCt(String auditCt) {
		this.auditCt = auditCt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
