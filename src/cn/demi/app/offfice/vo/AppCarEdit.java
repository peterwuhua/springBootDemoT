package cn.demi.app.offfice.vo;

/**
 * app 车辆详情
 * 
 * @author user
 *
 */
public class AppCarEdit {
	private String id;
	private String code;// 编号
	private String name;// 名称
	private String rule;// 型号
	private String userId;// 保管人
	private String userName;
	private String status;// 状态
	private String remark;// 备注
	private int num;// 使用数
	/**
	 * 车辆照片
	 */
	private String fileName;
	private String filePath;
	private String buyDate;// 购买日期
	private String careDate;// 保养日期
	private String careCycle;// 保养周期
	private String oil;// 油
	private String version;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}
	public String getCareDate() {
		return careDate;
	}
	public void setCareDate(String careDate) {
		this.careDate = careDate;
	}
	public String getCareCycle() {
		return careCycle;
	}
	public void setCareCycle(String careCycle) {
		this.careCycle = careCycle;
	}
	public String getOil() {
		return oil;
	}
	public void setOil(String oil) {
		this.oil = oil;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

}
