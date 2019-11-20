package cn.demi.init.car.vo;

import cn.core.framework.common.vo.Vo;

public class CarVo extends Vo<CarVo> {
	private String code;//编号
	private String name;//名称
	private String rule;//型号
	private String userId;//保管人
	private String userName;
	private String status;//状态
	private String remark;//备注
	private int num;//使用数
	 /**
     *车辆照片
     */
	private String fileName;
	private String filePath;
	private String buyDate;// 购买日期
	private String careDate;// 保养日期
	private String careCycle;// 保养周期
	private String oil;// 油
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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
	
}

