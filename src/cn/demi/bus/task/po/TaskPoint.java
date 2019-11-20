package cn.demi.bus.task.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 采样点位
 *
 * @author QuJunLong
 */
@Entity(name = "bus_task_point")
@Table(name = "bus_task_point")
@Module(value = "bus.taskPoint")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskPoint extends Po<TaskPoint> {

  public static final String ST_0 = "0";//未采集
  public static final String ST_1 = "1";//采集中
  public static final String ST_2 = "2";//已采集

  private static final long serialVersionUID = -2907613253531353034L;
  public String[] PROPERTY_TO_MAP = {"id", "sort", "task", "sampName", "pointName"
      , "pointCode", "pc", "pcUnit", "sampNum", "itemNames", "remark"};

  @Override
  @Transient
  public String[] getPropertyToMap() {
    return PROPERTY_TO_MAP;
  }

  private Task task;// 任务
  private String sampTypeId;//样品类别
  private String sampTypeName;
  private String type;//点位类型  水、气、声、土、生
  private String sampType;//样品大类
  private String sampName;//样品名称
  private String room;//车间
  private String pointName;//点位名称
  private String pointCode;//点位代码

  private String cyType;//采样方式
  private int cyHours;//采样时长
  private int zq;//周期
  private int pc;//频次
  private String pcUnit;//频次单位
  private int sampNum;//样品数量
  private int zkNum;//空白样数量
  private int pxNum;//平行样数量
  private String itemIds;//测试项目
  private String itemNames;

  //职业卫生字段
  private String workType;//接触方式
  private String workPc;//接触频次
  private String workHours;//接触时间 h/d
  private String workNum;//作业人数
  private String allItemId;//职业危害因素
  private String allItemName;
  private String fh;//职业病防护情况
  private String others;//个体防护用品及佩戴情况
//	private String ldqd;//体力劳动强度

  //	private String startTime;//施测开始时间
//	private String endTime;//施测截止时间
//	private String cyStandId;//采样标准
//	private String cyStandName;
//	private String cyAppId;//采样设备
//	private String cyAppName;
//	private String cyId;//现场采样人
//	private String cyName;
//	private String jhId;//现场校核人
//	private String jhName;
//	private String fxId;//现场分析人
//	private String fxName;
//	private String xcDesc;//感观现状描述
//	private String runType;//排放方式
//	private String gnq;//功能区
//	private String status;//0未填报 1填报中 2已填报
  private String remark;
  private String latAndLng;//经纬度
//	private String lat;//纬度
//	private String lng;//经度
//	private String xc;//现场检测 是 否
//	private String xcSt;//若有现场检测单，现场检测单状态 0未填报 1填报中 2已填报
  //气
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
//	private String deme15;//
//	private String deme16;//
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

  //	@Column(length = 64)
//	public String getLdqd() {
//		return ldqd;
//	}
//	public void setLdqd(String ldqd) {
//		this.ldqd = ldqd;
//	}
  @ManyToOne
  @JoinColumn(name = "task_id")
  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  @Column(length = 32)
  public String getSampType() {
    return sampType;
  }

  public void setSampType(String sampType) {
    this.sampType = sampType;
  }

  @Column(length = 64)
  public String getSampName() {
    return sampName;
  }

  public void setSampName(String sampName) {
    this.sampName = sampName;
  }

  @Column(length = 64)
  public String getPointName() {
    return pointName;
  }

  public void setPointName(String pointName) {
    this.pointName = pointName;
  }

  @Column(length = 32)
  public String getPointCode() {
    return pointCode;
  }

  public void setPointCode(String pointCode) {
    this.pointCode = pointCode;
  }

  @Column(length = 4)
  public int getZq() {
    return zq;
  }

  public void setZq(int zq) {
    this.zq = zq;
  }

  @Column(length = 4)
  public int getPc() {
    return pc;
  }

  public void setPc(int pc) {
    this.pc = pc;
  }

  @Column(length = 32)
  public String getPcUnit() {
    return pcUnit;
  }

  public void setPcUnit(String pcUnit) {
    this.pcUnit = pcUnit;
  }

  @Column(length = 4)
  public int getSampNum() {
    return sampNum;
  }

  public void setSampNum(int sampNum) {
    this.sampNum = sampNum;
  }

  @Column(length = 1000)
  public String getItemIds() {
    return itemIds;
  }

  public void setItemIds(String itemIds) {
    this.itemIds = itemIds;
  }

  @Column(length = 1000)
  public String getItemNames() {
    return itemNames;
  }

  public void setItemNames(String itemNames) {
    this.itemNames = itemNames;
  }

  @Lob
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  //	@Column(length = 16)
//	public String getRunType() {
//		return runType;
//	}
//	public void setRunType(String runType) {
//		this.runType = runType;
//	}
//	@Column(length = 16)
//	public String getGnq() {
//		return gnq;
//	}
//	public void setGnq(String gnq) {
//		this.gnq = gnq;
//	}
//	@Column(length = 32)
//	public String getFxId() {
//		return fxId;
//	}
//	public void setFxId(String fxId) {
//		this.fxId = fxId;
//	}
//	@Column(length = 32)
//	public String getFxName() {
//		return fxName;
//	}
//	public void setFxName(String fxName) {
//		this.fxName = fxName;
//	}
//	@Column(length = 128)
//	public String getXcDesc() {
//		return xcDesc;
//	}
//	public void setXcDesc(String xcDesc) {
//		this.xcDesc = xcDesc;
//	}
//	@Column(length = 32)
//	public String getDeme1() {
//		return deme1;
//	}
//	public void setDeme1(String deme1) {
//		this.deme1 = deme1;
//	}
//	@Column(length = 32)
//	public String getDeme2() {
//		return deme2;
//	}
//	public void setDeme2(String deme2) {
//		this.deme2 = deme2;
//	}
//	@Column(length = 32)
//	public String getDeme3() {
//		return deme3;
//	}
//	public void setDeme3(String deme3) {
//		this.deme3 = deme3;
//	}
//	@Column(length = 32)
//	public String getDeme4() {
//		return deme4;
//	}
//	public void setDeme4(String deme4) {
//		this.deme4 = deme4;
//	}
//	@Column(length = 32)
//	public String getDeme5() {
//		return deme5;
//	}
//	public void setDeme5(String deme5) {
//		this.deme5 = deme5;
//	}
//	@Column(length = 32)
//	public String getDeme6() {
//		return deme6;
//	}
//	public void setDeme6(String deme6) {
//		this.deme6 = deme6;
//	}
//	@Column(length = 32)
//	public String getDeme7() {
//		return deme7;
//	}
//	public void setDeme7(String deme7) {
//		this.deme7 = deme7;
//	}
//	@Column(length = 32)
//	public String getDeme8() {
//		return deme8;
//	}
//	public void setDeme8(String deme8) {
//		this.deme8 = deme8;
//	}
//	@Column(length = 32)
//	public String getDeme9() {
//		return deme9;
//	}
//	public void setDeme9(String deme9) {
//		this.deme9 = deme9;
//	}
//	@Column(length = 32)
//	public String getDeme10() {
//		return deme10;
//	}
//	public void setDeme10(String deme10) {
//		this.deme10 = deme10;
//	}
//	@Column(length = 32)
//	public String getDeme11() {
//		return deme11;
//	}
//	public void setDeme11(String deme11) {
//		this.deme11 = deme11;
//	}
//	@Column(length = 32)
//	public String getDeme12() {
//		return deme12;
//	}
//	public void setDeme12(String deme12) {
//		this.deme12 = deme12;
//	}
//	@Column(length = 32)
//	public String getDeme13() {
//		return deme13;
//	}
//	public void setDeme13(String deme13) {
//		this.deme13 = deme13;
//	}
//	@Column(length = 32)
//	public String getDeme14() {
//		return deme14;
//	}
//	public void setDeme14(String deme14) {
//		this.deme14 = deme14;
//	}
//	@Column(length = 32)
//	public String getDeme15() {
//		return deme15;
//	}
//	public void setDeme15(String deme15) {
//		this.deme15 = deme15;
//	}
//	@Column(length = 32)
//	public String getDeme16() {
//		return deme16;
//	}
//	public void setDeme16(String deme16) {
//		this.deme16 = deme16;
//	}
//	@Column(length = 32)
//	public String getDeme17() {
//		return deme17;
//	}
//	public void setDeme17(String deme17) {
//		this.deme17 = deme17;
//	}
//	@Column(length = 32)
//	public String getDeme18() {
//		return deme18;
//	}
//	public void setDeme18(String deme18) {
//		this.deme18 = deme18;
//	}
//	@Column(length = 32)
//	public String getDeme19() {
//		return deme19;
//	}
//	public void setDeme19(String deme19) {
//		this.deme19 = deme19;
//	}
//	@Column(length = 32)
//	public String getDeme20() {
//		return deme20;
//	}
//	public void setDeme20(String deme20) {
//		this.deme20 = deme20;
//	}
//	@Column(length = 32)
//	public String getDeme21() {
//		return deme21;
//	}
//	public void setDeme21(String deme21) {
//		this.deme21 = deme21;
//	}
//	@Column(length = 160)
//	public String getCyStandId() {
//		return cyStandId;
//	}
//	public void setCyStandId(String cyStandId) {
//		this.cyStandId = cyStandId;
//	}
//	@Column(length = 320)
//	public String getCyStandName() {
//		return cyStandName;
//	}
//	public void setCyStandName(String cyStandName) {
//		this.cyStandName = cyStandName;
//	}
//	@Column(length = 160)
//	public String getCyAppId() {
//		return cyAppId;
//	}
//	public void setCyAppId(String cyAppId) {
//		this.cyAppId = cyAppId;
//	}
//	@Column(length = 320)
//	public String getCyAppName() {
//		return cyAppName;
//	}
//	public void setCyAppName(String cyAppName) {
//		this.cyAppName = cyAppName;
//	}
//	@Column(length = 4)
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
//	@Column(length =20)
//	public String getStartTime() {
//		return startTime;
//	}
//	public void setStartTime(String startTime) {
//		this.startTime = startTime;
//	}
//	@Column(length =20)
//	public String getEndTime() {
//		return endTime;
//	}
//	public void setEndTime(String endTime) {
//		this.endTime = endTime;
//	}
//	@Column(length = 32)
//	public String getLat() {
//		return lat;
//	}
//	public void setLat(String lat) {
//		this.lat = lat;
//	}
//	@Column(length = 32)
//	public String getLng() {
//		return lng;
//	}
//	public void setLng(String lng) {
//		this.lng = lng;
//	}
  @Column(length = 2)
  public int getZkNum() {
    return zkNum;
  }

  public void setZkNum(int zkNum) {
    this.zkNum = zkNum;
  }

  @Column(length = 2)
  public int getPxNum() {
    return pxNum;
  }

  public void setPxNum(int pxNum) {
    this.pxNum = pxNum;
  }

  @Column(length = 320)
  public String getSampTypeId() {
    return sampTypeId;
  }

  public void setSampTypeId(String sampTypeId) {
    this.sampTypeId = sampTypeId;
  }

  @Column(length = 320)
  public String getSampTypeName() {
    return sampTypeName;
  }

  public void setSampTypeName(String sampTypeName) {
    this.sampTypeName = sampTypeName;
  }

  @Column(length = 4)
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  //	@Column(length = 128)
//	public String getFilePath() {
//		return filePath;
//	}
//	public void setFilePath(String filePath) {
//		this.filePath = filePath;
//	}
//	@Column(length = 64)
//	public String getFileName() {
//		return fileName;
//	}
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}
//	@Column(length = 64)
//	public String getTemp() {
//		return temp;
//	}
//	public void setTemp(String temp) {
//		this.temp = temp;
//	}
//	@Column(length = 32)
//	public String getCyId() {
//		return cyId;
//	}
//	public void setCyId(String cyId) {
//		this.cyId = cyId;
//	}
//	@Column(length = 32)
//	public String getCyName() {
//		return cyName;
//	}
//	public void setCyName(String cyName) {
//		this.cyName = cyName;
//	}
//	@Column(length = 32)
//	public String getJhId() {
//		return jhId;
//	}
//	public void setJhId(String jhId) {
//		this.jhId = jhId;
//	}
//	@Column(length = 32)
//	public String getJhName() {
//		return jhName;
//	}
//	public void setJhName(String jhName) {
//		this.jhName = jhName;
//	}
//	@Column(length = 32)
//	public String getDeme22() {
//		return deme22;
//	}
//	public void setDeme22(String deme22) {
//		this.deme22 = deme22;
//	}
//	@Column(length = 32)
//	public String getDeme23() {
//		return deme23;
//	}
//	public void setDeme23(String deme23) {
//		this.deme23 = deme23;
//	}
//	@Column(length = 32)
//	public String getDeme24() {
//		return deme24;
//	}
//	public void setDeme24(String deme24) {
//		this.deme24 = deme24;
//	}
//	@Column(length = 32)
//	public String getDeme25() {
//		return deme25;
//	}
//	public void setDeme25(String deme25) {
//		this.deme25 = deme25;
//	}
//	@Column(length = 4)
//	public String getXc() {
//		return xc;
//	}
//	public void setXc(String xc) {
//		this.xc = xc;
//	}
//	@Column(length = 4)
//	public String getXcSt() {
//		return xcSt;
//	}
//	public void setXcSt(String xcSt) {
//		this.xcSt = xcSt;
//	}
  @Column(length = 128)
  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  @Column(length = 64)
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

  @Column(length = 32)
  public String getWorkType() {
    return workType;
  }

  public void setWorkType(String workType) {
    this.workType = workType;
  }

  @Column(length = 32)
  public String getWorkHours() {
    return workHours;
  }

  public void setWorkHours(String workHours) {
    this.workHours = workHours;
  }

  @Column(length = 16)
  public String getWorkNum() {
    return workNum;
  }

  public void setWorkNum(String workNum) {
    this.workNum = workNum;
  }

  @Column(length = 1000)
  public String getAllItemId() {
    return allItemId;
  }

  public void setAllItemId(String allItemId) {
    this.allItemId = allItemId;
  }

  @Column(length = 1000)
  public String getAllItemName() {
    return allItemName;
  }

  public void setAllItemName(String allItemName) {
    this.allItemName = allItemName;
  }

  @Column(length = 1000)
  public String getFh() {
    return fh;
  }

  public void setFh(String fh) {
    this.fh = fh;
  }

  @Column(length = 1000)
  public String getOthers() {
    return others;
  }

  public void setOthers(String others) {
    this.others = others;
  }

  @Column(length = 32)
  public String getWorkPc() {
    return workPc;
  }

  public void setWorkPc(String workPc) {
    this.workPc = workPc;
  }
  @Column(length = 256)
  public String getLatAndLng() {
    return latAndLng;
  }

  public void setLatAndLng(String latAndLng) {
    this.latAndLng = latAndLng;
  }

//	public static void main(String[] args) {
//		CreateCodeUtils.autoCreateCode(TaskPoint.class, true, ActionType.JSP);
//	}
}
