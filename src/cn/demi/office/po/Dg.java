package cn.demi.office.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.bus.pg.po.Progress;

/**
 * 到岗管理
 * 
 * @author QuJunLong
 *
 */
@Entity(name = "office_dg")
@Table(name = "office_dg")
@Module(value = "office.dg")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dg extends Po<Dg> {

	public static final String TP_DK = "打卡";
	public static final String TP_BK = "补卡";

	public static final String ST_0 = "已保存";
	public static final String ST_1 = "待审核";
	public static final String ST_2 = "已完成";

	private static final long serialVersionUID = -3028334739169063405L;
	private String type;// 打卡 补卡
	private String deptId;// 部门
	private String deptName;
	private String userId;// 打卡人
	private String userName;
	private String date;// 打卡/补卡日期
	private String curTime;// 打卡/补卡时间
	private String content;// 描述
	private String remark;// 备注
	private String latAndLng;// 经纬度
	private String status;// 状态 已保存 待审核 已完成
	private String checkOrNoCheck;//当前时间段是否打卡
	// 审核信息
	private String auditId;
	private String auditName;// 补卡的审核人
	private String auditDate;// 补卡的审核日期
	private String auditResult;// 补卡的审核状态
	private String auditMsg;// 补卡的审核备注
	private Progress progress;
	public String[] PROPERTY_TO_MAP = { "id", "sort", "deptName", "type", "userName", "date", "curTime", "content","latAndLng",
			"remark", "status", "auditName", "auditDate", "auditResult" };
	public String[] IGNORE_PROPERTY_TO_PO = { "id", "createTime", "lastUpdTime", "version", "deptId", "deptName",
			"userId", "userName", "type", "status" };

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}

	@Column(length = 32)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(length = 32)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(length = 32)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	@Column(length = 20)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(length = 256)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length = 128)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(length = 16)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Column(length = 20)
	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	@Column(length = 32)
	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	@Column(length = 64)
	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	@Column(length = 20)
	public String getCurTime() {
		return curTime;
	}

	public void setCurTime(String curTime) {
		this.curTime = curTime;
	}
	@Column(length = 128)
	public String getLatAndLng() {
		return latAndLng;
	}

	public void setLatAndLng(String latAndLng) {
		this.latAndLng = latAndLng;
	}
	@Column(length = 20)
	public String getCheckOrNoCheck() {
		return checkOrNoCheck;
	}

	public void setCheckOrNoCheck(String checkOrNoCheck) {
		this.checkOrNoCheck = checkOrNoCheck;
	}
	@ManyToOne
	@JoinColumn(name = "progress_id")
	public Progress getProgress() {
		return progress;
	}

	public void setProgress(Progress progress) {
		this.progress = progress;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Dg.class, false, ActionType.JSP);
	}
}
