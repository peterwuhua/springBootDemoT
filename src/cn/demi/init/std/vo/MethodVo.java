package cn.demi.init.std.vo;

import cn.core.framework.common.vo.Vo;

public class MethodVo extends Vo<MethodVo> {
	/**
	 * 测试项目
	 */
	private String itemIds;
	private String itemNames;
	/**
	 * 标准ID
	 */
	private String standId;
	/**
	 * 标准编号
	 */
	private String code;
	/**
	 * 标准名称
	 */
	private String standName;
	/**
	 * 条款
	 */
	private String chapter;
	/**
	 * 方法名称
	 */
	private String name;
	/**
	 * 使用仪器
	 */
	private String appIds;
	/**
	 * 使用仪器
	 */
	private String appNames;
	/**
	 * 检出线
	 */
	private String limitLine;
	//预警值(上限)
	private String maxLine;
	//预警值(下限)
	private String minLine;
	//收费标准
	private float price;
	//工时标准
	private float hours;
    /**
     * 标准文档的电子记录路径
     */
    private String filePath;
    /**
     * 附件名称
     */
    private String fileName;
	private String tempIds;
	private String tempNames;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAppIds() {
		return appIds;
	}
	public void setAppIds(String appIds) {
		this.appIds = appIds;
	}
	public String getAppNames() {
		return appNames;
	}
	public void setAppNames(String appNames) {
		this.appNames = appNames;
	}
	public String getLimitLine() {
		return limitLine;
	}
	public void setLimitLine(String limitLine) {
		this.limitLine = limitLine;
	}
	public String getStandName() {
		return standName;
	}
	public void setStandName(String standName) {
		this.standName = standName;
	}
	public String getStandId() {
		return standId;
	}
	public void setStandId(String standId) {
		this.standId = standId;
	}
	public String getMaxLine() {
		return maxLine;
	}
	public void setMaxLine(String maxLine) {
		this.maxLine = maxLine;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getHours() {
		return hours;
	}
	public void setHours(float hours) {
		this.hours = hours;
	}
	public String getMinLine() {
		return minLine;
	}
	public void setMinLine(String minLine) {
		this.minLine = minLine;
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
	public String getTempIds() {
		return tempIds;
	}
	public void setTempIds(String tempIds) {
		this.tempIds = tempIds;
	}
	public String getTempNames() {
		return tempNames;
	}
	public void setTempNames(String tempNames) {
		this.tempNames = tempNames;
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
}

