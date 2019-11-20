package cn.demi.bus.sample.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.bus.pjt.vo.CustVo;
import cn.demi.bus.task.vo.TaskVo;

public class SampUsedVo extends Vo<SampUsedVo> {
	private TaskVo taskVo;// 任务
	private CustVo custVo;//客户信息
	private SamplingVo samplingVo;//样品
	private String sampTypeId;//样品名称
	private String sampTypeName;//样品名称
	private String sampId;//样品名称
	private String sampName;//样品名称
	private String sampCode;
	private String pointName;//点位名称
	private String pointCode;//点位代码
	private String num;//数量、  采样体积(气)  距声源距离(声)
	private String itemIds;//测试项目
	private String itemNames; 
	 
	private String useId;//领样人
	private String useName;
	private String useDate;//领样日期
	private String content;//内容
	//部门
	private String orgId;
	private String orgName;
	private String deptId;
	private String deptName;
	
	private String remark;//备注
	private String type;//out 出库 in 入库
    private String status;//样品状态：已出库、待归还、已归还
    private String gh;//是 否
    
	public TaskVo getTaskVo() {
		return taskVo;
	}
	public void setTaskVo(TaskVo taskVo) {
		this.taskVo = taskVo;
	}
	public CustVo getCustVo() {
		return custVo;
	}
	public void setCustVo(CustVo custVo) {
		this.custVo = custVo;
	}
	public SamplingVo getSamplingVo() {
		return samplingVo;
	}
	public void setSamplingVo(SamplingVo samplingVo) {
		this.samplingVo = samplingVo;
	}
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	public String getSampId() {
		return sampId;
	}
	public void setSampId(String sampId) {
		this.sampId = sampId;
	}
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public String getSampCode() {
		return sampCode;
	}
	public void setSampCode(String sampCode) {
		this.sampCode = sampCode;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getPointCode() {
		return pointCode;
	}
	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
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
	public String getUseId() {
		return useId;
	}
	public void setUseId(String useId) {
		this.useId = useId;
	}
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
}

