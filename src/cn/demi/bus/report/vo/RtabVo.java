package cn.demi.bus.report.vo;

import java.util.List;

import cn.demi.bus.test.vo.TaskItemVo;

/**
 * 监测结果 tab页面对象
 * @author QuJunLong
 *
 */
public class RtabVo {
	
	private String type;//水，气（气、无组织气），声
	private String sampName;//样品名称
	private String pointName;//点位名称
	private String itemName;//项目名称
	private String unit;//单位
	private String date;//监测日期/采样日期
	private String remark;//备注：方法标准等其他信息
	private String others;//pH浸提剂
	private int page;//页码
	private int colWth;//列表宽度
	private int cols;//列表宽度
	private List<RtabVo> tabList;
	
	private String[] headList;//标题集合
	private String[] dateList;//时间集合
	private String[] dtList;//其他集合
	private String[] ceList;//层次集合
	private String[] addList;//采样地点集合
	private List<RtabRowVo> rowList;//监测结果集合
	private List<TaskItemVo> timList;
//	private List<String[]> arrList;//有组织气  监测公共部分  无组织气表头部分   质控监测结果内容
//	private List<RtabDataVo> qdList;//有组织气  监测结果集合
//	
//	private List<RtabDataVo> qwList;//无组织组织气  监测结果集合 气象参数
//	private List<String> headList;// 无组织气表头部分  声 表头
//	
//	private RtabRowVo szjVo;//声 昼间 监测结果集合
//	private RtabRowVo syjVo;//声 夜间 监测结果集合
	
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public List<RtabRowVo> getRowList() {
		return rowList;
	}
	public void setRowList(List<RtabRowVo> rowList) {
		this.rowList = rowList;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
//	public List<RtabDataVo> getQdList() {
//		return qdList;
//	}
//	public void setQdList(List<RtabDataVo> qdList) {
//		this.qdList = qdList;
//	}
//	public List<RtabDataVo> getQwList() {
//		return qwList;
//	}
//	public void setQwList(List<RtabDataVo> qwList) {
//		this.qwList = qwList;
//	}
//	public List<String> getHeadList() {
//		return headList;
//	}
//	public void setHeadList(List<String> headList) {
//		this.headList = headList;
//	}
//	public RtabRowVo getSzjVo() {
//		return szjVo;
//	}
//	public void setSzjVo(RtabRowVo szjVo) {
//		this.szjVo = szjVo;
//	}
//	public RtabRowVo getSyjVo() {
//		return syjVo;
//	}
//	public void setSyjVo(RtabRowVo syjVo) {
//		this.syjVo = syjVo;
//	}
//	public List<String[]> getArrList() {
//		return arrList;
//	}
//	public void setArrList(List<String[]> arrList) {
//		this.arrList = arrList;
//	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<RtabVo> getTabList() {
		return tabList;
	}
	public void setTabList(List<RtabVo> tabList) {
		this.tabList = tabList;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getColWth() {
		return colWth;
	}
	public void setColWth(int colWth) {
		this.colWth = colWth;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	public String[] getHeadList() {
		return headList;
	}
	public void setHeadList(String[] headList) {
		this.headList = headList;
	}
	public String[] getDateList() {
		return dateList;
	}
	public void setDateList(String[] dateList) {
		this.dateList = dateList;
	}
	public String[] getDtList() {
		return dtList;
	}
	public void setDtList(String[] dtList) {
		this.dtList = dtList;
	}
	public String[] getCeList() {
		return ceList;
	}
	public void setCeList(String[] ceList) {
		this.ceList = ceList;
	}
	public String[] getAddList() {
		return addList;
	}
	public void setAddList(String[] addList) {
		this.addList = addList;
	}
	public List<TaskItemVo> getTimList() {
		return timList;
	}
	public void setTimList(List<TaskItemVo> timList) {
		this.timList = timList;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
}
