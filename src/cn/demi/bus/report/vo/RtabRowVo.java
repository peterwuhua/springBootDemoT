package cn.demi.bus.report.vo;

import java.util.List;

/**
 * 监测结果 页面行对象
 * @author QuJunLong
 */
public class RtabRowVo {

	private String sort;
	private String itemName;//项目
	private String pointName;//点位
	private String appName;//仪器
	private String sampCode;//编号
	private String unit;
	private String date;
	private String limt;//限值
	private String v1;//值1
	private String v2;//值2
	private float avg;//平均值
	private String[] valueList;//值集合
	private List<RtabRowVo> rowList;
	private List<RtabDataVo> dataList;
	private List<RtabRoomVo> roomList;
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getV1() {
		return v1;
	}
	public void setV1(String v1) {
		this.v1 = v1;
	}
	public String getV2() {
		return v2;
	}
	public void setV2(String v2) {
		this.v2 = v2;
	}
	public float getAvg() {
		return avg;
	}
	public void setAvg(float avg) {
		this.avg = avg;
	}
	public String[] getValueList() {
		return valueList;
	}
	public void setValueList(String[] valueList) {
		this.valueList = valueList;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getSampCode() {
		return sampCode;
	}
	public void setSampCode(String sampCode) {
		this.sampCode = sampCode;
	}
	public List<RtabRowVo> getRowList() {
		return rowList;
	}
	public void setRowList(List<RtabRowVo> rowList) {
		this.rowList = rowList;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<RtabDataVo> getDataList() {
		return dataList;
	}
	public void setDataList(List<RtabDataVo> dataList) {
		this.dataList = dataList;
	}
	public String getLimt() {
		return limt;
	}
	public void setLimt(String limt) {
		this.limt = limt;
	}
	public List<RtabRoomVo> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<RtabRoomVo> roomList) {
		this.roomList = roomList;
	}
}
