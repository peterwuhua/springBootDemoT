package cn.demi.bus.task.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.bus.pjt.vo.ImVo;
import cn.demi.bus.sample.vo.SampContainerVo;
import cn.demi.bus.sample.vo.SamplingVo;

public class TaskPointVo extends Vo<TaskPointVo> {
	
	private TaskVo taskVo;// 任务
	private String sampTypeId;//样品类别
	private String sampTypeName;
	private String type;//点位类型  水、气、声、土、生
	private String sampType;//样品大类
	private String sampName;//样品名称
	private String room;
	private String pointName;//点位名称
	private String pointCode;//点位代码
	private String cyType;//采样方式
	private int cyHours;//采样时长
	private int zq;//周期
	private int pc;//频次
	private String pcUnit;//频次单位
	private int sampNum;//样品数量
	private int zkNum;//质控数量
	private int pxNum;//平行样数量
	private String itemIds;//测试项目
	private String itemNames;
	//职业卫生字段
	private String workType;//接触方式
	private String workHours;//接触时间 h/d
	private String workPc;//接触频次
	private String workNum;//作业人数
	private String allItemId;//职业危害因素
	private String allItemName;
	private String fh;//职业病防护情况
	private String others;//个体防护用品及佩戴情况
//	private String ldqd;//体力劳动强度
	//检测项目和方法关系表id
	private String imId;
	private String status;//0未填报 1填报中 2已填报
	private String remark;
	 private String latAndLng;//经纬度
//	
//	private String runType;//排放方式
//	private String gnq;//功能区
//	private String startTime;//施测开始时间
//	private String endTime;//施测截止时间
//	private String cyStandId;//采样标准
//	private String cyStandName;
//	private String cyAppId;//采样设备
//	private String cyAppName;
//	private String fxId;//现场分析人
//	private String fxName;
//	private String cyId;//现场采样人
//	private String cyName;
//	private String jhId;//现场校核人
//	private String jhName;
//	private String xcDesc;//现场情况描述
//	private String lat;//纬度
//	private String lng;//经度
//	private String xc;//现场检测 是 否
//	private String xcSt;//若有现场检测单，现场检测单状态 0未填报 1填报中 2已填报
//	//水  气   声 
//	private String deme1;//排气筒名称   主要声源名称
//	private String deme2;//排气筒高度   车间工段名称
//	private String deme3;//测点内径       设备名称型号
//	private String deme4;//气温               功率
//	private String deme5;//气压               开
//	private String deme6;//截 面积            关
//	private String deme7;//流量
//	private String deme8;//
//	private String deme9;//
//	private String deme10;//
//	private String deme11;//
//	private String deme12;//
//	private String deme13;//
//	private String deme14;//
//	private String deme15;//废气温度
//	private String deme16;//废气流量
//	private String deme17;//
//	private String deme18;//
//	private String deme19;//
//	private String deme20;//
//	private String deme21;//
//	private String deme22;//
//	private String deme23;//
//	private String deme24;//
//	private String deme25;//
//	private String filePath;//采样单
//	private String fileName;//
//	private String temp;//采样单模板
	//采样单文件需要
	private String cyDate;//采样日期
	private String cyTime;//采样/监测时间
	private String cyDateCode;//采样日期编号
	private String cyUserCode;//采样人员编号

	
	private List<SampContainerVo>  containerList;//采样容器
	private List<SamplingVo>  sampList;//样品集合
	private List<SamplingVo>  zkList;//质控样品集合
	private List<ImVo> imList;//项目方法关系集合
    private String itemcctjs;//环境运输条件	
    private String itemRemarks;//备注	
  //  private List<ItemNwVo> itemNwList;//现场数据集合
    
	
//	public String getLdqd() {
//		return ldqd;
//	}
//	public void setLdqd(String ldqd) {
//		this.ldqd = ldqd;
//	}
	public String getItemcctjs() {
		return itemcctjs;
	}
	public void setItemcctjs(String itemcctjs) {
		this.itemcctjs = itemcctjs;
	}
	public String getItemRemarks() {
		return itemRemarks;
	}
	public void setItemRemarks(String itemRemarks) {
		this.itemRemarks = itemRemarks;
	}
	public TaskVo getTaskVo() {
		return taskVo;
	}
	public void setTaskVo(TaskVo taskVo) {
		this.taskVo = taskVo;
	}
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
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
	public int getZq() {
		return zq;
	}
	public void setZq(int zq) {
		this.zq = zq;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public String getPcUnit() {
		return pcUnit;
	}
	public void setPcUnit(String pcUnit) {
		this.pcUnit = pcUnit;
	}
	public int getSampNum() {
		return sampNum;
	}
	public void setSampNum(int sampNum) {
		this.sampNum = sampNum;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<SampContainerVo> getContainerList() {
		return containerList;
	}
	public void setContainerList(List<SampContainerVo> containerList) {
		this.containerList = containerList;
	}
	public List<SamplingVo> getSampList() {
		return sampList;
	}
	public void setSampList(List<SamplingVo> sampList) {
		this.sampList = sampList;
	}
//	public String getRunType() {
//		return runType;
//	}
//	public void setRunType(String runType) {
//		this.runType = runType;
//	}
//	public String getGnq() {
//		return gnq;
//	}
//	public void setGnq(String gnq) {
//		this.gnq = gnq;
//	}
//	public String getFxId() {
//		return fxId;
//	}
//	public void setFxId(String fxId) {
//		this.fxId = fxId;
//	}
//	public String getFxName() {
//		return fxName;
//	}
//	public void setFxName(String fxName) {
//		this.fxName = fxName;
//	}
//	public String getXcDesc() {
//		return xcDesc;
//	}
//	public void setXcDesc(String xcDesc) {
//		this.xcDesc = xcDesc;
//	}
//	public String getDeme1() {
//		return deme1;
//	}
//	public void setDeme1(String deme1) {
//		this.deme1 = deme1;
//	}
//	public String getDeme2() {
//		return deme2;
//	}
//	public void setDeme2(String deme2) {
//		this.deme2 = deme2;
//	}
//	public String getDeme3() {
//		return deme3;
//	}
//	public void setDeme3(String deme3) {
//		this.deme3 = deme3;
//	}
//	public String getDeme4() {
//		return deme4;
//	}
//	public void setDeme4(String deme4) {
//		this.deme4 = deme4;
//	}
//	public String getDeme5() {
//		return deme5;
//	}
//	public void setDeme5(String deme5) {
//		this.deme5 = deme5;
//	}
//	public String getDeme6() {
//		return deme6;
//	}
//	public void setDeme6(String deme6) {
//		this.deme6 = deme6;
//	}
//	public String getDeme7() {
//		return deme7;
//	}
//	public void setDeme7(String deme7) {
//		this.deme7 = deme7;
//	}
//	public String getDeme8() {
//		return deme8;
//	}
//	public void setDeme8(String deme8) {
//		this.deme8 = deme8;
//	}
//	public String getDeme9() {
//		return deme9;
//	}
//	public void setDeme9(String deme9) {
//		this.deme9 = deme9;
//	}
//	public String getDeme10() {
//		return deme10;
//	}
//	public void setDeme10(String deme10) {
//		this.deme10 = deme10;
//	}
//	public String getDeme11() {
//		return deme11;
//	}
//	public void setDeme11(String deme11) {
//		this.deme11 = deme11;
//	}
//	public String getDeme12() {
//		return deme12;
//	}
//	public void setDeme12(String deme12) {
//		this.deme12 = deme12;
//	}
//	public String getDeme13() {
//		return deme13;
//	}
//	public void setDeme13(String deme13) {
//		this.deme13 = deme13;
//	}
//	public String getDeme14() {
//		return deme14;
//	}
//	public void setDeme14(String deme14) {
//		this.deme14 = deme14;
//	}
//	public String getDeme15() {
//		return deme15;
//	}
//	public void setDeme15(String deme15) {
//		this.deme15 = deme15;
//	}
//	public String getDeme16() {
//		return deme16;
//	}
//	public void setDeme16(String deme16) {
//		this.deme16 = deme16;
//	}
//	public String getDeme17() {
//		return deme17;
//	}
//	public void setDeme17(String deme17) {
//		this.deme17 = deme17;
//	}
//	public String getDeme18() {
//		return deme18;
//	}
//	public void setDeme18(String deme18) {
//		this.deme18 = deme18;
//	}
//	public String getDeme19() {
//		return deme19;
//	}
//	public void setDeme19(String deme19) {
//		this.deme19 = deme19;
//	}
//	public String getDeme20() {
//		return deme20;
//	}
//	public void setDeme20(String deme20) {
//		this.deme20 = deme20;
//	}
//	public String getDeme21() {
//		return deme21;
//	}
//	public void setDeme21(String deme21) {
//		this.deme21 = deme21;
//	}
//	public String getCyStandId() {
//		return cyStandId;
//	}
//	public void setCyStandId(String cyStandId) {
//		this.cyStandId = cyStandId;
//	}
//	public String getCyStandName() {
//		return cyStandName;
//	}
//	public void setCyStandName(String cyStandName) {
//		this.cyStandName = cyStandName;
//	}
//	public String getCyAppId() {
//		return cyAppId;
//	}
//	public void setCyAppId(String cyAppId) {
//		this.cyAppId = cyAppId;
//	}
//	public String getCyAppName() {
//		return cyAppName;
//	}
//	public void setCyAppName(String cyAppName) {
//		this.cyAppName = cyAppName;
//	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
//	public String getStartTime() {
//		return startTime;
//	}
//	public void setStartTime(String startTime) {
//		this.startTime = startTime;
//	}
//	public String getEndTime() {
//		return endTime;
//	}
//	public void setEndTime(String endTime) {
//		this.endTime = endTime;
//	}
//	public String getLat() {
//		return lat;
//	}
//	public void setLat(String lat) {
//		this.lat = lat;
//	}
//	public String getLng() {
//		return lng;
//	}
//	public void setLng(String lng) {
//		this.lng = lng;
//	}
	public int getZkNum() {
		return zkNum;
	}
	public void setZkNum(int zkNum) {
		this.zkNum = zkNum;
	}
	public int getPxNum() {
		return pxNum;
	}
	public void setPxNum(int pxNum) {
		this.pxNum = pxNum;
	}
	public List<SamplingVo> getZkList() {
		return zkList;
	}
	public void setZkList(List<SamplingVo> zkList) {
		this.zkList = zkList;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
//	public String getFilePath() {
//		return filePath;
//	}
//	public void setFilePath(String filePath) {
//		this.filePath = filePath;
//	}
//	public String getFileName() {
//		return fileName;
//	}
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}
//	public String getTemp() {
//		return temp;
//	}
//	public void setTemp(String temp) {
//		this.temp = temp;
//	}
	public String getCyDate() {
		return cyDate;
	}
	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
	}
	public String getCyDateCode() {
		return cyDateCode;
	}
	public void setCyDateCode(String cyDateCode) {
		this.cyDateCode = cyDateCode;
	}
	public String getCyUserCode() {
		return cyUserCode;
	}
	public void setCyUserCode(String cyUserCode) {
		this.cyUserCode = cyUserCode;
	}
	public String getCyTime() {
		return cyTime;
	}
	public void setCyTime(String cyTime) {
		this.cyTime = cyTime;
	}
	public String getImId() {
		return imId;
	}
	public void setImId(String imId) {
		this.imId = imId;
	}
	public List<ImVo> getImList() {
		return imList;
	}
	public void setImList(List<ImVo> imList) {
		this.imList = imList;
	}
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
//	public String getCyId() {
//		return cyId;
//	}
//	public void setCyId(String cyId) {
//		this.cyId = cyId;
//	}
//	public String getCyName() {
//		return cyName;
//	}
//	public void setCyName(String cyName) {
//		this.cyName = cyName;
//	}
//	public String getJhId() {
//		return jhId;
//	}
//	public void setJhId(String jhId) {
//		this.jhId = jhId;
//	}
//	public String getJhName() {
//		return jhName;
//	}
//	public void setJhName(String jhName) {
//		this.jhName = jhName;
//	}
//	public String getDeme22() {
//		return deme22;
//	}
//	public void setDeme22(String deme22) {
//		this.deme22 = deme22;
//	}
//	public String getDeme23() {
//		return deme23;
//	}
//	public void setDeme23(String deme23) {
//		this.deme23 = deme23;
//	}
//	public String getDeme24() {
//		return deme24;
//	}
//	public void setDeme24(String deme24) {
//		this.deme24 = deme24;
//	}
//	public String getDeme25() {
//		return deme25;
//	}
//	public void setDeme25(String deme25) {
//		this.deme25 = deme25;
//	}
//	public String getXc() {
//		return xc;
//	}
//	public void setXc(String xc) {
//		this.xc = xc;
//	}
//	public String getXcSt() {
//		return xcSt;
//	}
//	public void setXcSt(String xcSt) {
//		this.xcSt = xcSt;
//	}
//	public List<ItemNwVo> getItemNwList() {
//		return itemNwList;
//	}
//	public void setItemNwList(List<ItemNwVo> itemNwList) {
//		this.itemNwList = itemNwList;
//	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getCyType() {
		return cyType;
	}
	public void setCyType(String cyType) {
		this.cyType = cyType;
	}
	public int getCyHours() {
		return cyHours;
	}
	public void setCyHours(int cyHours) {
		this.cyHours = cyHours;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getWorkHours() {
		return workHours;
	}
	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
	public String getWorkNum() {
		return workNum;
	}
	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}
	public String getAllItemId() {
		return allItemId;
	}
	public void setAllItemId(String allItemId) {
		this.allItemId = allItemId;
	}
	public String getAllItemName() {
		return allItemName;
	}
	public void setAllItemName(String allItemName) {
		this.allItemName = allItemName;
	}
	public String getFh() {
		return fh;
	}
	public void setFh(String fh) {
		this.fh = fh;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWorkPc() {
		return workPc;
	}
	public void setWorkPc(String workPc) {
		this.workPc = workPc;
	}
	public String getLatAndLng() {
		return latAndLng;
	}
	public void setLatAndLng(String latAndLng) {
		this.latAndLng = latAndLng;
	}
	
}

