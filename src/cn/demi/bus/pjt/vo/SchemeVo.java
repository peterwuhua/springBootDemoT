package cn.demi.bus.pjt.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.FilesVo;

public class SchemeVo extends Vo<SchemeVo> {
	
	private ProjectVo projectVo; 
	//样品信息
	private String sampTypeId;//样品类型名称
	private String sampTypeName;
	private String sampName;//样品名称
	private int sampNum;//样品数量
	private String dealRequest;//样品处理要求
	private String saveRequest;//样品保存条件
	private String cyDate;//采/送样日期
	private String cyEndDate;//采送样结束日期
	private String cyUserName;//送样人员
	private String itemNames;//检测项目
	private String pointNames;//检测点位
	private float fxPrice;//检测费用=每个点位（项目费用总额*频次）的合计
	private float cyPrice;//采样费用
	private String status;//当前状态：未执行、执行中、 已执行
	private String remark;
	private int num;//方案序号
	
	private int cyDay;//采样天数
	
	
	private List<SchemePointVo> pointList;
	private List<FilesVo> fileList;
	
	
	
	

	public int getCyDay() {
		return cyDay;
	}
	public void setCyDay(int cyDay) {
		this.cyDay = cyDay;
	}
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public float getFxPrice() {
		return fxPrice;
	}
	public void setFxPrice(float fxPrice) {
		this.fxPrice = fxPrice;
	}
	public float getCyPrice() {
		return cyPrice;
	}
	public void setCyPrice(float cyPrice) {
		this.cyPrice = cyPrice;
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
	public List<FilesVo> getFileList() {
		return fileList;
	}
	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}
	public List<SchemePointVo> getPointList() {
		return pointList;
	}
	public void setPointList(List<SchemePointVo> pointList) {
		this.pointList = pointList;
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
	public ProjectVo getProjectVo() {
		return projectVo;
	}
	public void setProjectVo(ProjectVo projectVo) {
		this.projectVo = projectVo;
	}
	public String getCyUserName() {
		return cyUserName;
	}
	public void setCyUserName(String cyUserName) {
		this.cyUserName = cyUserName;
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
	public String getDealRequest() {
		return dealRequest;
	}
	public void setDealRequest(String dealRequest) {
		this.dealRequest = dealRequest;
	}
	public String getSaveRequest() {
		return saveRequest;
	}
	public void setSaveRequest(String saveRequest) {
		this.saveRequest = saveRequest;
	}
	public int getSampNum() {
		return sampNum;
	}
	public void setSampNum(int sampNum) {
		this.sampNum = sampNum;
	}
	public String getItemNames() {
		return itemNames;
	}
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	public String getPointNames() {
		return pointNames;
	}
	public void setPointNames(String pointNames) {
		this.pointNames = pointNames;
	}
}

