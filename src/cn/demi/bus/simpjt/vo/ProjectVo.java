package cn.demi.bus.simpjt.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.file.vo.ArchiveFileVo;
import cn.demi.bus.pg.vo.ProgressVo;
import cn.demi.bus.pjt.vo.CustVo;

public class ProjectVo extends Vo<ProjectVo> {
	
	private ProjectHtBaseVo pjtHtBaseVo; //合同基础vo
	
	//客户信息
	private CustVo custVo;
	
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
		private List<ArchiveFileVo> archList;
		
		private String isBack;//退回状态
		private String status;//状态
		
		private ProgressVo progressVo;

		private List<FilesVo> fileList;
		
		public List<ArchiveFileVo> getArchList() {
			return archList;
		}

		public void setArchList(List<ArchiveFileVo> archList) {
			this.archList = archList;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public String getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public String getFabz() {
			return fabz;
		}

		public void setFabz(String fabz) {
			this.fabz = fabz;
		}

		public List<FilesVo> getFileList() {
			return fileList;
		}

		public void setFileList(List<FilesVo> fileList) {
			this.fileList = fileList;
		}

		public ProjectHtBaseVo getPjtHtBaseVo() {
			return pjtHtBaseVo;
		}

		public void setPjtHtBaseVo(ProjectHtBaseVo pjtHtBaseVo) {
			this.pjtHtBaseVo = pjtHtBaseVo;
		}

		public CustVo getCustVo() {
			return custVo;
		}

		public void setCustVo(CustVo custVo) {
			this.custVo = custVo;
		}

		public String getNo() {
			return no;
		}

		public void setNo(String no) {
			this.no = no;
		}

		public String getItemType() {
			return itemType;
		}

		public void setItemType(String itemType) {
			this.itemType = itemType;
		}

		public String getSampName() {
			return sampName;
		}

		public void setSampName(String sampName) {
			this.sampName = sampName;
		}

		public String getJj() {
			return jj;
		}

		public void setJj(String jj) {
			this.jj = jj;
		}

		public String getRdate() {
			return rdate;
		}

		public void setRdate(String rdate) {
			this.rdate = rdate;
		}

		public String getFinishDate() {
			return finishDate;
		}

		public void setFinishDate(String finishDate) {
			this.finishDate = finishDate;
		}

		public String getHtqd() {
			return htqd;
		}

		public void setHtqd(String htqd) {
			this.htqd = htqd;
		}

		public String getXctk() {
			return xctk;
		}

		public void setXctk(String xctk) {
			this.xctk = xctk;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getOrgId() {
			return orgId;
		}

		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getPsCt() {
			return psCt;
		}

		public void setPsCt(String psCt) {
			this.psCt = psCt;
		}

		public String getPsResult() {
			return psResult;
		}

		public void setPsResult(String psResult) {
			this.psResult = psResult;
		}

		public String getPsId() {
			return psId;
		}

		public void setPsId(String psId) {
			this.psId = psId;
		}

		public String getPsName() {
			return psName;
		}

		public void setPsName(String psName) {
			this.psName = psName;
		}

		public String getPsDate() {
			return psDate;
		}

		public void setPsDate(String psDate) {
			this.psDate = psDate;
		}

		public String getPsMsg() {
			return psMsg;
		}

		public void setPsMsg(String psMsg) {
			this.psMsg = psMsg;
		}

		public String getHtNo() {
			return htNo;
		}

		public void setHtNo(String htNo) {
			this.htNo = htNo;
		}

		public double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(double totalPrice) {
			this.totalPrice = totalPrice;
		}

		public String getQdId() {
			return qdId;
		}

		public void setQdId(String qdId) {
			this.qdId = qdId;
		}

		public String getQdName() {
			return qdName;
		}

		public void setQdName(String qdName) {
			this.qdName = qdName;
		}

		public String getQdDate() {
			return qdDate;
		}

		public void setQdDate(String qdDate) {
			this.qdDate = qdDate;
		}

		public String getQdMsg() {
			return qdMsg;
		}

		public void setQdMsg(String qdMsg) {
			this.qdMsg = qdMsg;
		}

		public String getTaskUserId() {
			return taskUserId;
		}

		public void setTaskUserId(String taskUserId) {
			this.taskUserId = taskUserId;
		}

		public String getTaskUserName() {
			return taskUserName;
		}

		public void setTaskUserName(String taskUserName) {
			this.taskUserName = taskUserName;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getTaskMsg() {
			return taskMsg;
		}

		public void setTaskMsg(String taskMsg) {
			this.taskMsg = taskMsg;
		}

		public String getZpUserId() {
			return zpUserId;
		}

		public void setZpUserId(String zpUserId) {
			this.zpUserId = zpUserId;
		}

		public String getZpUserName() {
			return zpUserName;
		}

		public void setZpUserName(String zpUserName) {
			this.zpUserName = zpUserName;
		}

		public String getZpDate() {
			return zpDate;
		}

		public void setZpDate(String zpDate) {
			this.zpDate = zpDate;
		}

		public String getZpMsg() {
			return zpMsg;
		}

		public void setZpMsg(String zpMsg) {
			this.zpMsg = zpMsg;
		}

		public String getTkUserId() {
			return tkUserId;
		}

		public void setTkUserId(String tkUserId) {
			this.tkUserId = tkUserId;
		}

		public String getTkUserName() {
			return tkUserName;
		}

		public void setTkUserName(String tkUserName) {
			this.tkUserName = tkUserName;
		}

		public String getTkDate() {
			return tkDate;
		}

		public void setTkDate(String tkDate) {
			this.tkDate = tkDate;
		}

		public String getTkMsg() {
			return tkMsg;
		}

		public void setTkMsg(String tkMsg) {
			this.tkMsg = tkMsg;
		}

		public String getFaUserId() {
			return faUserId;
		}

		public void setFaUserId(String faUserId) {
			this.faUserId = faUserId;
		}

		public String getFaUserName() {
			return faUserName;
		}

		public void setFaUserName(String faUserName) {
			this.faUserName = faUserName;
		}

		public String getFaDate() {
			return faDate;
		}

		public void setFaDate(String faDate) {
			this.faDate = faDate;
		}

		public String getFaMsg() {
			return faMsg;
		}

		public void setFaMsg(String faMsg) {
			this.faMsg = faMsg;
		}

		public String getMakeUserId() {
			return makeUserId;
		}

		public void setMakeUserId(String makeUserId) {
			this.makeUserId = makeUserId;
		}

		public String getMakeUser() {
			return makeUser;
		}

		public void setMakeUser(String makeUser) {
			this.makeUser = makeUser;
		}

		public String getMakeDate() {
			return makeDate;
		}

		public void setMakeDate(String makeDate) {
			this.makeDate = makeDate;
		}

		public String getBzMsg() {
			return bzMsg;
		}

		public void setBzMsg(String bzMsg) {
			this.bzMsg = bzMsg;
		}

		public String getAuditUserId() {
			return auditUserId;
		}

		public void setAuditUserId(String auditUserId) {
			this.auditUserId = auditUserId;
		}

		public String getAuditUser() {
			return auditUser;
		}

		public void setAuditUser(String auditUser) {
			this.auditUser = auditUser;
		}

		public String getAuditDate() {
			return auditDate;
		}

		public void setAuditDate(String auditDate) {
			this.auditDate = auditDate;
		}

		public String getAuditMsg() {
			return auditMsg;
		}

		public void setAuditMsg(String auditMsg) {
			this.auditMsg = auditMsg;
		}

		public String getFileUserId() {
			return fileUserId;
		}

		public void setFileUserId(String fileUserId) {
			this.fileUserId = fileUserId;
		}

		public String getFileUser() {
			return fileUser;
		}

		public void setFileUser(String fileUser) {
			this.fileUser = fileUser;
		}

		public String getFileDate() {
			return fileDate;
		}

		public void setFileDate(String fileDate) {
			this.fileDate = fileDate;
		}

		public String getFileMsg() {
			return fileMsg;
		}

		public void setFileMsg(String fileMsg) {
			this.fileMsg = fileMsg;
		}

		public String getIsBack() {
			return isBack;
		}

		public void setIsBack(String isBack) {
			this.isBack = isBack;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public ProgressVo getProgressVo() {
			return progressVo;
		}

		public void setProgressVo(ProgressVo progressVo) {
			this.progressVo = progressVo;
		}

		public String getFileGderId() {
			return fileGderId;
		}

		public void setFileGderId(String fileGderId) {
			this.fileGderId = fileGderId;
		}

		public String getFileGder() {
			return fileGder;
		}

		public void setFileGder(String fileGder) {
			this.fileGder = fileGder;
		}

		public String getFileGdDate() {
			return fileGdDate;
		}

		public void setFileGdDate(String fileGdDate) {
			this.fileGdDate = fileGdDate;
		}

		public String getFileGdMsg() {
			return fileGdMsg;
		}

		public void setFileGdMsg(String fileGdMsg) {
			this.fileGdMsg = fileGdMsg;
		}
	
	
	
}

