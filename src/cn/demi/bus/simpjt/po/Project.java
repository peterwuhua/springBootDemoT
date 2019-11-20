package cn.demi.bus.simpjt.po;

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
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pjt.po.Cust;

/**
 * 立项申请(简易)
 * 
 * 
 */
@Entity(name = "bus_sim_project")
@Table(name = "bus_sim_project")
@Module(value = "bus.simProject")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Project extends Po<Project> {

	private static final long serialVersionUID = 1L;

	public String[] PROPERTY_TO_MAP = { "id", "sort", "isDel","status", "progress", "no", "itemType", "sampName", "jj", "rdate", "finishDate", "htqd", "cust", "xctk",
			"orgId", "orgName", "userId", "userName", "remark", "psCt", "psResult", "psId", "psName", "psDate", "psMsg", "htNo", "totalPrice", "qdId", "qdName",
			"qdDate", "qdMsg", "taskUserId", "taskUserName", "date", "taskMsg", "tkUserId", "tkUserName", "zpUserId", "zpUserName", "zpDate", "zpMsg", "tkDate",
			"tkMsg", "faUserId", "faUserName", "faDate", "faMsg", "reptUserId", "reptUser", "makeUserId", "makeUser", "makeDate", "bzMsg", "auditUserId",
			"auditUser", "auditDate", "auditMsg","fileUserId","fileUser","fileDate","fileMsg","isBack" ,"fabz","fileGderId","fileGder","fileGdDate","fileGdMsg","position","categoryId","categoryName"};

	public String[] IGNORE_PROPERTY_TO_PO = { "id", "createTime", "lastUpdTime", "version", "orgId", "orgName", "no", "status" };

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

	// 项目立项信息 //
	private String no;// 项目编号
	private String itemType;// 项目类型
	private String sampName;// 项目名称
	private String jj;// 是否加急
	private String rdate;// 立项日期
	private String finishDate;// 拟完成日期
	private String htqd;// 合同签订 是/否
	private String xctk;// 现场踏勘 是/否
	private String userId;
	private String userName;// 项目负责人
	private String orgId;// 部门
	private String orgName;
	private String remark;// 备注
	private Cust cust;// 委托单位
	private String fabz;// 方案编制 是/否
	// 合同评审//
	private String psCt;// 评审内容
	private String psResult;// 评审结论
	private String psId;
	private String psName;// 评审人员
	private String psDate;// 评审日期
	private String psMsg;// 备注

	// 合同签订//
	private String htNo;// 合同编号
	private double totalPrice;// 合同总价
	private String qdId;
	private String qdName;// 签订人员
	private String qdDate;// 签订日期
	private String qdMsg;// 备注

	// 任务登记//
	private String taskUserId;
	private String taskUserName;// 登记人员
	private String date;// 登记日期
	private String taskMsg;// 备注

	// 派发任务//

	private String zpUserId;
	private String zpUserName;// 派发人员
	private String zpDate;// 派发日期
	private String zpMsg;// 备注

	// 现场踏勘//
	private String tkUserId;
	private String tkUserName;// 任务人员
	private String tkDate;// 踏勘日期
	private String tkMsg;// 备注
    
	
	// 方案编制//
	private String faUserId;
	private String faUserName;// 编制人员
	private String faDate;// 编制日期
	private String faMsg;// 备注

	// 报告编制//

	private String makeUserId;
	private String makeUser;// 编制人员
	private String makeDate;// 编制日期
	private String bzMsg;// 备注

	// 报告评审//
	private String auditUserId;
	private String auditUser;// 审核人员
	private String auditDate;// 审核日期
	private String auditMsg;// 审核意见

	//提交备案//
	private String fileUserId;
	private String fileUser;//备案人
	private String fileDate;//备案日期
	private String fileMsg;//备注
	
	//备案归档//
	private String fileGderId;
	private String fileGder;//归档人
	private String fileGdDate;//归档日期
	private String fileGdMsg;//备注
	
	private String position;//报告归档位置
	private String categoryId;//电子文档
	private String categoryName;//电子文档
	
	
	
	private String isBack;//退回状态
	private String status;//状态
	
	private Progress progress;
	
	
	
	
	@Column(length = 256)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	@Column(length = 32)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	@Column(length = 128)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(length = 8)
	public String getHtqd() {
		return htqd;
	}

	public void setHtqd(String htqd) {
		this.htqd = htqd;
	}


	@Column(length = 64)
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}


	

	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Cust getCust() {
		return cust;
	}

	public void setCust(Cust cust) {
		this.cust = cust;
	}



	@Column(length = 20)
	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	@Column(length = 20)
	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}



	@Column(length = 8)
	public String getXctk() {
		return xctk;
	}

	public void setXctk(String xctk) {
		this.xctk = xctk;
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

	

	@Column(length = 1000)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(length = 1000)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Lob
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
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(length = 8)
	public String getJj() {
		return jj;
	}

	public void setJj(String jj) {
		this.jj = jj;
	}


	@Column(length = 8)
	public String getFabz() {
		return fabz;
	}

	public void setFabz(String fabz) {
		this.fabz = fabz;
	}

	@Column(length = 320)
	public String getTkUserId() {
		return tkUserId;
	}

	public void setTkUserId(String tkUserId) {
		this.tkUserId = tkUserId;
	}

	@Column(length = 320)
	public String getTkUserName() {
		return tkUserName;
	}

	public void setTkUserName(String tkUserName) {
		this.tkUserName = tkUserName;
	}

	@Column(length = 20)
	public String getTkDate() {
		return tkDate;
	}

	public void setTkDate(String tkDate) {
		this.tkDate = tkDate;
	}


	@ManyToOne
	@JoinColumn(name = "progress_id")
	public Progress getProgress() {
		return progress;
	}

	public void setProgress(Progress progress) {
		this.progress = progress;
	}


	@Column(length = 32)
	public String getZpUserId() {
		return zpUserId;
	}

	public void setZpUserId(String zpUserId) {
		this.zpUserId = zpUserId;
	}

	@Column(length = 32)
	public String getZpUserName() {
		return zpUserName;
	}

	public void setZpUserName(String zpUserName) {
		this.zpUserName = zpUserName;
	}

	@Column(length = 20)
	public String getZpDate() {
		return zpDate;
	}

	public void setZpDate(String zpDate) {
		this.zpDate = zpDate;
	}

	@Column(length = 128)
	public String getZpMsg() {
		return zpMsg;
	}

	public void setZpMsg(String zpMsg) {
		this.zpMsg = zpMsg;
	}

	@Column(length = 128)
	public String getTkMsg() {
		return tkMsg;
	}

	public void setTkMsg(String tkMsg) {
		this.tkMsg = tkMsg;
	}

	@Column(length = 32)
	public String getFaUserId() {
		return faUserId;
	}

	public void setFaUserId(String faUserId) {
		this.faUserId = faUserId;
	}

	@Column(length = 16)
	public String getFaUserName() {
		return faUserName;
	}

	public void setFaUserName(String faUserName) {
		this.faUserName = faUserName;
	}

	@Column(length = 20)
	public String getFaDate() {
		return faDate;
	}

	public void setFaDate(String faDate) {
		this.faDate = faDate;
	}

	@Column(length = 128)
	public String getFaMsg() {
		return faMsg;
	}

	public void setFaMsg(String faMsg) {
		this.faMsg = faMsg;
	}

	@Column(length = 320)
	public String getPsId() {
		return psId;
	}

	public void setPsId(String psId) {
		this.psId = psId;
	}

	@Column(length = 320)
	public String getPsName() {
		return psName;
	}

	public void setPsName(String psName) {
		this.psName = psName;
	}

	@Column(length = 1000)
	public String getPsResult() {
		return psResult;
	}

	public void setPsResult(String psResult) {
		this.psResult = psResult;
	}

	@Column(length = 20)
	public String getPsDate() {
		return psDate;
	}

	public void setPsDate(String psDate) {
		this.psDate = psDate;
	}

	@Column(length = 128)
	public String getPsMsg() {
		return psMsg;
	}

	public void setPsMsg(String psMsg) {
		this.psMsg = psMsg;
	}

	@Column(length = 32)
	public String getQdId() {
		return qdId;
	}

	public void setQdId(String qdId) {
		this.qdId = qdId;
	}

	@Column(length = 32)
	public String getQdName() {
		return qdName;
	}

	public void setQdName(String qdName) {
		this.qdName = qdName;
	}

	@Column(length = 20)
	public String getQdDate() {
		return qdDate;
	}

	public void setQdDate(String qdDate) {
		this.qdDate = qdDate;
	}

	@Column(length = 128)
	public String getQdMsg() {
		return qdMsg;
	}

	public void setQdMsg(String qdMsg) {
		this.qdMsg = qdMsg;
	}

	@Column(length = 2)
	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	@Column(length = 32)
	public String getHtNo() {
		return htNo;
	}

	public void setHtNo(String htNo) {
		this.htNo = htNo;
	}

	@Column(length = 256)
	public String getPsCt() {
		return psCt;
	}

	public void setPsCt(String psCt) {
		this.psCt = psCt;
	}

	@Column(length = 256)
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@Column(length = 32)
	public String getTaskUserId() {
		return taskUserId;
	}

	public void setTaskUserId(String taskUserId) {
		this.taskUserId = taskUserId;
	}

	@Column(length = 32)
	public String getTaskUserName() {
		return taskUserName;
	}

	public void setTaskUserName(String taskUserName) {
		this.taskUserName = taskUserName;
	}


	@Column(length = 128)
	public String getTaskMsg() {
		return taskMsg;
	}

	public void setTaskMsg(String taskMsg) {
		this.taskMsg = taskMsg;
	}

	
	@Column(length = 256)
	public String getSampName() {
		return sampName;
	}

	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Column(length = 20)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	@Column(length = 32)
	public String getMakeUserId() {
		return makeUserId;
	}

	public void setMakeUserId(String makeUserId) {
		this.makeUserId = makeUserId;
	}
	@Column(length = 32)
	public String getMakeUser() {
		return makeUser;
	}

	public void setMakeUser(String makeUser) {
		this.makeUser = makeUser;
	}
	@Column(length = 20)
	public String getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}
	@Column(length = 128)
	public String getBzMsg() {
		return bzMsg;
	}

	public void setBzMsg(String bzMsg) {
		this.bzMsg = bzMsg;
	}
	@Column(length = 32)
	public String getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	@Column(length = 32)
	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	@Column(length = 20)
	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	@Column(length = 128)
	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}
	@Column(length = 32)
	public String getFileUserId() {
		return fileUserId;
	}

	public void setFileUserId(String fileUserId) {
		this.fileUserId = fileUserId;
	}
	@Column(length = 32)
	public String getFileUser() {
		return fileUser;
	}

	public void setFileUser(String fileUser) {
		this.fileUser = fileUser;
	}
	@Column(length = 20)
	public String getFileDate() {
		return fileDate;
	}

	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	@Column(length = 128)
	public String getFileMsg() {
		return fileMsg;
	}

	public void setFileMsg(String fileMsg) {
		this.fileMsg = fileMsg;
	}


	@Column(length = 32)
	public String getFileGderId() {
		return fileGderId;
	}

	public void setFileGderId(String fileGderId) {
		this.fileGderId = fileGderId;
	}
	@Column(length = 32)
	public String getFileGder() {
		return fileGder;
	}

	public void setFileGder(String fileGder) {
		this.fileGder = fileGder;
	}
	@Column(length = 20)
	public String getFileGdDate() {
		return fileGdDate;
	}

	public void setFileGdDate(String fileGdDate) {
		this.fileGdDate = fileGdDate;
	}
	@Column(length = 128)
	public String getFileGdMsg() {
		return fileGdMsg;
	}

	public void setFileGdMsg(String fileGdMsg) {
		this.fileGdMsg = fileGdMsg;
	}

	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Project.class, true, ActionType.JSP);
	}
}
