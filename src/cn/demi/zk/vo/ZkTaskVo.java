package cn.demi.zk.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.core.framework.constant.EunmZkTask;

public class ZkTaskVo extends Vo<ZkTaskVo> {
	/**
	 * 任务编号
	 */
	private String no;
	/**
	 * 年
	 */
	private String year;
	/**
	 *月
	 */
	private String month;
	/**
	 * 检验性质
	 */
	private String title;
	
	/**
	 * 检测对象数量
	 */
	private String sampNum;
	       
	/**
	 *要求完成时间
	 */
	private String finishDate;
	/**
	 *考核对象
	 */
	private String objIds;
	private String objNames;
	  
	/**
	 * 编制人
	 */
	private String acceptId;
	private String acceptName;
	/**
	 * 受理日期
	 */
	private String acceptDate;
	/**
	 * 审核人
	 */
	private String auditId;
	private String auditName;
	/**
	 *实验室测试项目
	 */
	private String itemIds;
	/**
	 *实验室测试项目
	 */
	private String itemNames;
	/**
	 * 审核日期
	 */
	private String auditDate;
	/**
	 * 编制人部门
	 */
	private String orgId;
	/**
	 * 其它说明
	 */
	private String remark;
    private int isBack;
	
    
    /**
     * 考核结果评价
     */
    private String result;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 状态
	 */
	private String statusName;
	/**
	 * 当前查询数据对于的日志记录
	 * map时用，不存入数据库
	 */
	private ZkProgressLogVo progressLogVo;
	/**
	 * 普通样集合
	 */
	private List<ZkSamplingVo> sampList;
	
	private List<ZkItemTestVo> itemList;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSampNum() {
		return sampNum;
	}

	public void setSampNum(String sampNum) {
		this.sampNum = sampNum;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public String getObjIds() {
		return objIds;
	}

	public void setObjIds(String objIds) {
		this.objIds = objIds;
	}

	public String getObjNames() {
		return objNames;
	}

	public void setObjNames(String objNames) {
		this.objNames = objNames;
	}

	public String getAcceptId() {
		return acceptId;
	}

	public void setAcceptId(String acceptId) {
		this.acceptId = acceptId;
	}

	public String getAcceptName() {
		return acceptName;
	}

	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}

	public String getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
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

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsBack() {
		return isBack;
	}

	public void setIsBack(int isBack) {
		this.isBack = isBack;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ZkProgressLogVo getProgressLogVo() {
		return progressLogVo;
	}

	public void setProgressLogVo(ZkProgressLogVo progressLogVo) {
		this.progressLogVo = progressLogVo;
	}

	public List<ZkSamplingVo> getSampList() {
		return sampList;
	}

	public void setSampList(List<ZkSamplingVo> sampList) {
		this.sampList = sampList;
	}

	public List<ZkItemTestVo> getItemList() {
		return itemList;
	}

	public void setItemList(List<ZkItemTestVo> itemList) {
		this.itemList = itemList;
	}

	public String getStatusName() {
		if(null!=status) {
			statusName=EunmZkTask.getName(status);
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

	public String getItemNames() {
		return itemNames;
	}

	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	
}

