package cn.demi.bus.test.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.bus.pjt.vo.CustVo;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.task.vo.TaskVo;

public class TestItemVo extends Vo<TestItemVo> {
	/**
	 * 任务
	 */
	private TaskVo taskVo; 

	private CustVo custVo;//客户信息
	/**
	 * 点位
	 */
	private TaskPointVo pointVo;
	/**
	 * 子任务
	 */
	private TaskItemVo timVo;
	/**
	 * 采样日期
	 */
	private String cyDate;
	 /**
     * 父项目
     */
	private TestItemVo parentVo;
	/**
	 * 检测项目
	 */
	private String itemId;
	private String itemName;
	private int level;//级别
	/**
	 * 项目类型
	 * 0实验室项目
	 * 1现场项目
	 * 2重测项目
	 */
	private String type;
	/**
	 * 项目类型
	 * 0普通
	 * 1现场平行样
	 * 2室内平行样
	 * 3全程序空白样
	 * 4加标回收样
	 */
	private String zkType;
	private String value;//值  均值
	private String value2;//值         均值(多结果结构)
	private String sl;//速率 均值
	/**
	 * PC-TWA
	 * 时间加权平均容器浓度
	 * 以时间加权规定的8小时，40小时平均容器浓度
	 */
	private String twa;
	/**
	 * PC-TWA
	 * 在PC-TWA前提下，容许短时间（15min）接触浓度
	 */
	private String stel;
	/**
	 * 最高容许浓度
	 */
	private String mac;
	/**
	 * 超限倍数
	 */
	private String lmt;
	/**
	 * 单项判定
	 * 合格 不合格
	 */
	private String result;
    private String isBack;
    private String status;
    
    private TestResultVo trVo;//测试结果对象
    private List<TestResultVo> trList;
    private String[] trArry;
    //报告文件字段
    private String fcType;//粉尘种类  光谱范围  部位
  //  private String pl;// 波形分类,频率
    private String workHours;//接触时间 h/d
    
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

	public TaskItemVo getTimVo() {
		return timVo;
	}

	public void setTimVo(TaskItemVo timVo) {
		this.timVo = timVo;
	}

	public TestItemVo getParentVo() {
		return parentVo;
	}

	public void setParentVo(TestItemVo parentVo) {
		this.parentVo = parentVo;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getTwa() {
		return twa;
	}

	public void setTwa(String twa) {
		this.twa = twa;
	}

	public String getStel() {
		return stel;
	}

	public void setStel(String stel) {
		this.stel = stel;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getLmt() {
		return lmt;
	}

	public void setLmt(String lmt) {
		this.lmt = lmt;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public TaskPointVo getPointVo() {
		return pointVo;
	}

	public void setPointVo(TaskPointVo pointVo) {
		this.pointVo = pointVo;
	}

	public TestResultVo getTrVo() {
		return trVo;
	}

	public void setTrVo(TestResultVo trVo) {
		this.trVo = trVo;
	}

	public List<TestResultVo> getTrList() {
		return trList;
	}
	public void setTrList(List<TestResultVo> trList) {
		this.trList = trList;
	}
	public String getCyDate() {
		return cyDate;
	}
	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
	}
	public String getZkType() {
		return zkType;
	}
	public void setZkType(String zkType) {
		this.zkType = zkType;
	}
	public String[] getTrArry() {
		return trArry;
	}
	public void setTrArry(String[] trArry) {
		this.trArry = trArry;
	}

	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getFcType() {
		return fcType;
	}

	public void setFcType(String fcType) {
		this.fcType = fcType;
	}

	public String getWorkHours() {
		return workHours;
	}

	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
}

