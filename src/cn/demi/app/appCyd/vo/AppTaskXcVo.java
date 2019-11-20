package cn.demi.app.appCyd.vo;

public class AppTaskXcVo {

	private String id;
	// 任务编号
	private String no;
	// 受检单位
	private String custName;
	// 样品名称
	private String sampName;
	// 检测类型
	private String taskType;
	private String cyId;
	// 采样小组人员
	private String cyName;
	// 开始采样日期
	private String cyDate;
	// 采样结束日期
	private String cyEndDate;
	private String ItemIds;
	private String custAddress;
	private String sampType;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getSampName() {
		return sampName;
	}

	public void setSampName(String sampName) {
		this.sampName = sampName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getCyName() {
		return cyName;
	}

	public void setCyName(String cyName) {
		this.cyName = cyName;
	}

	public String getCyDate() {
		return cyDate;
	}

	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
	}

	public String getCyEndDate() {
		return cyEndDate;
	}

	public void setCyEndDate(String cyEndDate) {
		this.cyEndDate = cyEndDate;
	}

	public String getItemIds() {
		return ItemIds;
	}

	public void setItemIds(String itemIds) {
		ItemIds = itemIds;
	}

	public String getCyId() {
		return cyId;
	}

	public void setCyId(String cyId) {
		this.cyId = cyId;
	}

	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	public String getSampType() {
		return sampType;
	}

	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
}
