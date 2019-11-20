package cn.demi.init.std.vo;

import cn.core.framework.common.vo.Vo;

public class PstandItemVo extends Vo<PstandItemVo> {
	
	/**
	 * 标准Id
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
	 * 标准分类
	 */
	private String sampType;
	/**
	 * 样品类型Id
	 */
	private String sampTypeId;
	/**
	 * 样品类型
	 */
	private String sampTypeName;
	/**
	 * 分类：常规,非常规
	 */
	private String type;
	/**
	 * 项目类型
	 */
	private String itemType;
	/**
	 * 项目Id
	 */
	private ItemVo itemVo;
	/**
	 * 其他分类
	 */
	private String otherType;
	/**
	 * 限值类型
	 * 数值范围/数值偏差/数值/文字描述
	 */
	private String xzType;
	private String minFlag;//最小值符号
	private String minValue;//最小值
	private String maxFlag;//最大值符号
	private String maxValue;//最大值        最大超限倍数(职卫标准)
	private String flag;//符号
	private String value;//数值                     PCWTA(职卫标准)
	private String valStr;//数值描述
	//气 浓度
	private String flag2;//符号
	private String value2;//数值2      PC-STEL(职卫标准)
	private String valStr2;//数值2描述
	
	private String value3;//数值2      MAC(职卫标准)
	/**
	 * 备注
	 */
	private String remark;
	//现场检测
	private String isNow;
	
	public String getStandId() {
		return standId;
	}
	public void setStandId(String standId) {
		this.standId = standId;
	}
	public String getStandName() {
		return standName;
	}
	public void setStandName(String standName) {
		this.standName = standName;
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
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsNow() {
		return isNow;
	}
	public void setIsNow(String isNow) {
		this.isNow = isNow;
	}
	public ItemVo getItemVo() {
		return itemVo;
	}
	public void setItemVo(ItemVo itemVo) {
		this.itemVo = itemVo;
	}
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	public String getOtherType() {
		return otherType;
	}
	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}
	public String getXzType() {
		return xzType;
	}
	public void setXzType(String xzType) {
		this.xzType = xzType;
	}
	public String getMinFlag() {
		return minFlag;
	}
	public void setMinFlag(String minFlag) {
		this.minFlag = minFlag;
	}
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxFlag() {
		return maxFlag;
	}
	public void setMaxFlag(String maxFlag) {
		this.maxFlag = maxFlag;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValStr() {
		return valStr;
	}
	public void setValStr(String valStr) {
		this.valStr = valStr;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getValStr2() {
		return valStr2;
	}
	public void setValStr2(String valStr2) {
		this.valStr2 = valStr2;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public String getFlag2() {
		return flag2;
	}
	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}
}

