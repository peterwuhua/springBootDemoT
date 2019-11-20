package cn.demi.bus.sample.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.bus.test.vo.TestItemVo;

public class SampCydVo extends Vo<SampCydVo> {
	
	private TaskVo taskVo;// 任务
	private String pointId;//点位   可能为空
	private String room;//车间/岗位
	private String pointName;//点位名称
	private String pointCode;//点位编号
	private String type;//采样单类型
	private String sampType;//样品大类
	private String sampName;//样品名称
	private String cyDate;//日期
	private int sampNum;//样品数量
	private String itemIds;//测试项目
	private String itemNames;
	private String itemType;//测试分类
	private String cyStandId;//采样标准
	private String cyStandName;// word 监测方法及来源
	private String cyAppId;//采样设备
	private String cyAppName;// word 采样器
	private String cyAppCode;//采样设备编号 
	private String cyId;//现场采样人
	private String cyName;
	private String jhId;//现场校核人
	private String jhName;
	private String fxId;//现场分析人
	private String fxName;
	private String ptUser;//客户陪同人
	private String xcDesc;//感观现状描述
	private String runType;//排放方式
	private String gnq;//功能区    word 功能区类
	
	private String tx;//天气
	private String fx;//风向
	private String fs;//风速
	private String qy;//气压
	private String qw;//气温
	private String sd;//湿度
	
	private String cySt;//采样单状态  0未填报 1填报中 2已填报
	private String remark;
	 
	private String deme1;// 修正参数
	private String deme2;// 距路肩距离
	private String deme3;// 校准值dB(A)
	private String deme4;// 激光辐射测量类型（照射量（J/cm2） ，辐照度（W/cm2））
	private String deme5;// 超高频测量类型（功率密度（mW/cm2），电场强度（V/m））
	private String deme6;// 流速m/s
	private String deme7;//
	private String deme8;//
	private String deme9;//
	private String deme10;//
	private String deme11;//
	private String deme12;//
	private String deme13;//
	private String deme14;//
	private String deme15;//
	private String deme16;//
	private String deme17;//
	private String deme18;//
	private String deme19;//
	private String deme20;//
	private String deme21;//
	private String deme22;//
	private String deme23;//
	private String deme24;//
	private String deme25;//
	
	private String filePath;//采样单文件
	private String fileName;//
	private String temp;//采样单模板文件
	
	private List<SamplingVo> sampList;
	private List<SamplingVo> zkList;
	private List<TestItemVo> itemList;//现场数据集合
	private List<TaskItemVo> timList;//现场数据集合
 
    private String limitLine;//采样方法(监测方法)规定范围
     /**
      * 废水（环境）
      */
    private String liusu; //流速
	/**
	 * 固定源（有组织大气）
	 */
	private String pqwd;//排气温度
    private String tgxs;//托管系数
    private String gdjy;//管道静压
    private String gddy;//管道动压
    private String hsl;//含湿量
    private String pqls;//排气流速
    private String bgll;//标干流量
    private String hjwd;//环境温度
    private String dqy;//大气压

    /**
     * x射线采样记录单（职卫）
     */
	private String jcchDay;//检测日期（xxxx年xx月xx日）
    
    
    
	public String getJcchDay() {
		return jcchDay;
	}
	public void setJcchDay(String jcchDay) {
		this.jcchDay = jcchDay;
	}
	public String getGdjy() {
		return gdjy;
	}
	public void setGdjy(String gdjy) {
		this.gdjy = gdjy;
	}
	public String getGddy() {
		return gddy;
	}
	public void setGddy(String gddy) {
		this.gddy = gddy;
	}
	public String getHsl() {
		return hsl;
	}
	public void setHsl(String hsl) {
		this.hsl = hsl;
	}
	public String getPqls() {
		return pqls;
	}
	public void setPqls(String pqls) {
		this.pqls = pqls;
	}
	public String getBgll() {
		return bgll;
	}
	public void setBgll(String bgll) {
		this.bgll = bgll;
	}
	public String getHjwd() {
		return hjwd;
	}
	public void setHjwd(String hjwd) {
		this.hjwd = hjwd;
	}
	public String getDqy() {
		return dqy;
	}
	public void setDqy(String dqy) {
		this.dqy = dqy;
	}
	public String getTgxs() {
		return tgxs;
	}
	public void setTgxs(String tgxs) {
		this.tgxs = tgxs;
	}
	public String getPqwd() {
		return pqwd;
	}
	public void setPqwd(String pqwd) {
		this.pqwd = pqwd;
	}
	public String getCyAppCode() {
		return cyAppCode;
	}
	public void setCyAppCode(String cyAppCode) {
		this.cyAppCode = cyAppCode;
	}
	public String getPointCode() {
		return pointCode;
	}
	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}
	public String getLiusu() {
		return liusu;
	}
	public void setLiusu(String liusu) {
		this.liusu = liusu;
	}
	public String getLimitLine() {
		return limitLine;
	}
	public void setLimitLine(String limitLine) {
		this.limitLine = limitLine;
	}
	public TaskVo getTaskVo() {
		return taskVo;
	}
	public void setTaskVo(TaskVo taskVo) {
		this.taskVo = taskVo;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public String getCyDate() {
		return cyDate;
	}
	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
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
	public String getCyStandId() {
		return cyStandId;
	}
	public void setCyStandId(String cyStandId) {
		this.cyStandId = cyStandId;
	}
	public String getCyStandName() {
		return cyStandName;
	}
	public void setCyStandName(String cyStandName) {
		this.cyStandName = cyStandName;
	}
	public String getCyAppId() {
		return cyAppId;
	}
	public void setCyAppId(String cyAppId) {
		this.cyAppId = cyAppId;
	}
	public String getCyAppName() {
		return cyAppName;
	}
	public void setCyAppName(String cyAppName) {
		this.cyAppName = cyAppName;
	}
	public String getCyId() {
		return cyId;
	}
	public void setCyId(String cyId) {
		this.cyId = cyId;
	}
	public String getCyName() {
		return cyName;
	}
	public void setCyName(String cyName) {
		this.cyName = cyName;
	}
	public String getJhId() {
		return jhId;
	}
	public void setJhId(String jhId) {
		this.jhId = jhId;
	}
	public String getJhName() {
		return jhName;
	}
	public void setJhName(String jhName) {
		this.jhName = jhName;
	}
	public String getFxId() {
		return fxId;
	}
	public void setFxId(String fxId) {
		this.fxId = fxId;
	}
	public String getFxName() {
		return fxName;
	}
	public void setFxName(String fxName) {
		this.fxName = fxName;
	}
	public String getPtUser() {
		return ptUser;
	}
	public void setPtUser(String ptUser) {
		this.ptUser = ptUser;
	}
	public String getXcDesc() {
		return xcDesc;
	}
	public void setXcDesc(String xcDesc) {
		this.xcDesc = xcDesc;
	}
	public String getRunType() {
		return runType;
	}
	public void setRunType(String runType) {
		this.runType = runType;
	}
	public String getGnq() {
		return gnq;
	}
	public void setGnq(String gnq) {
		this.gnq = gnq;
	}
	public String getTx() {
		return tx;
	}
	public void setTx(String tx) {
		this.tx = tx;
	}
	public String getFx() {
		return fx;
	}
	public void setFx(String fx) {
		this.fx = fx;
	}
	public String getFs() {
		return fs;
	}
	public void setFs(String fs) {
		this.fs = fs;
	}
	public String getQy() {
		return qy;
	}
	public void setQy(String qy) {
		this.qy = qy;
	}
	public String getQw() {
		return qw;
	}
	public void setQw(String qw) {
		this.qw = qw;
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	public String getCySt() {
		return cySt;
	}
	public void setCySt(String cySt) {
		this.cySt = cySt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeme1() {
		return deme1;
	}
	public void setDeme1(String deme1) {
		this.deme1 = deme1;
	}
	public String getDeme2() {
		return deme2;
	}
	public void setDeme2(String deme2) {
		this.deme2 = deme2;
	}
	public String getDeme3() {
		return deme3;
	}
	public void setDeme3(String deme3) {
		this.deme3 = deme3;
	}
	public String getDeme4() {
		return deme4;
	}
	public void setDeme4(String deme4) {
		this.deme4 = deme4;
	}
	public String getDeme5() {
		return deme5;
	}
	public void setDeme5(String deme5) {
		this.deme5 = deme5;
	}
	public String getDeme6() {
		return deme6;
	}
	public void setDeme6(String deme6) {
		this.deme6 = deme6;
	}
	public String getDeme7() {
		return deme7;
	}
	public void setDeme7(String deme7) {
		this.deme7 = deme7;
	}
	public String getDeme8() {
		return deme8;
	}
	public void setDeme8(String deme8) {
		this.deme8 = deme8;
	}
	public String getDeme9() {
		return deme9;
	}
	public void setDeme9(String deme9) {
		this.deme9 = deme9;
	}
	public String getDeme10() {
		return deme10;
	}
	public void setDeme10(String deme10) {
		this.deme10 = deme10;
	}
	public String getDeme11() {
		return deme11;
	}
	public void setDeme11(String deme11) {
		this.deme11 = deme11;
	}
	public String getDeme12() {
		return deme12;
	}
	public void setDeme12(String deme12) {
		this.deme12 = deme12;
	}
	public String getDeme13() {
		return deme13;
	}
	public void setDeme13(String deme13) {
		this.deme13 = deme13;
	}
	public String getDeme14() {
		return deme14;
	}
	public void setDeme14(String deme14) {
		this.deme14 = deme14;
	}
	public String getDeme15() {
		return deme15;
	}
	public void setDeme15(String deme15) {
		this.deme15 = deme15;
	}
	public String getDeme16() {
		return deme16;
	}
	public void setDeme16(String deme16) {
		this.deme16 = deme16;
	}
	public String getDeme17() {
		return deme17;
	}
	public void setDeme17(String deme17) {
		this.deme17 = deme17;
	}
	public String getDeme18() {
		return deme18;
	}
	public void setDeme18(String deme18) {
		this.deme18 = deme18;
	}
	public String getDeme19() {
		return deme19;
	}
	public void setDeme19(String deme19) {
		this.deme19 = deme19;
	}
	public String getDeme20() {
		return deme20;
	}
	public void setDeme20(String deme20) {
		this.deme20 = deme20;
	}
	public String getDeme21() {
		return deme21;
	}
	public void setDeme21(String deme21) {
		this.deme21 = deme21;
	}
	public String getDeme22() {
		return deme22;
	}
	public void setDeme22(String deme22) {
		this.deme22 = deme22;
	}
	public String getDeme23() {
		return deme23;
	}
	public void setDeme23(String deme23) {
		this.deme23 = deme23;
	}
	public String getDeme24() {
		return deme24;
	}
	public void setDeme24(String deme24) {
		this.deme24 = deme24;
	}
	public String getDeme25() {
		return deme25;
	}
	public void setDeme25(String deme25) {
		this.deme25 = deme25;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public List<SamplingVo> getSampList() {
		return sampList;
	}
	public void setSampList(List<SamplingVo> sampList) {
		this.sampList = sampList;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public List<SamplingVo> getZkList() {
		return zkList;
	}
	public void setZkList(List<SamplingVo> zkList) {
		this.zkList = zkList;
	}
	public List<TestItemVo> getItemList() {
		return itemList;
	}
	public void setItemList(List<TestItemVo> itemList) {
		this.itemList = itemList;
	}
	public List<TaskItemVo> getTimList() {
		return timList;
	}
	public void setTimList(List<TaskItemVo> timList) {
		this.timList = timList;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
}

