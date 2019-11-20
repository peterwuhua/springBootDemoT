package cn.demi.init.std.po;

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
 * 评价标准与指标关系对象
 * @author QuJunLong
 *
 */
@Entity(name = "init_pstand_item")
@Table(name = "init_pstand_item")
@Module(value = "init.pstand_item")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PstandItem extends Po<PstandItem>{

	public static final String FLAG_FW = "数值范围";
	public static final String FLAG_SZ = "数值";
	public static final String FLAG_MS = "文字描述";
	
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","standId","code","standName","sampTypeId","sampTypeName","type","itemType","item","valStr"
			,"valStr2","otherType","value3","value","value2","maxValue"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
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
	 * 分类
	 */
	private String type;
	/**
	 * 指标类型
	 */
	private String itemType;
	/**
	 * 其他分类
	 */
	private String otherType;
	/**
	 * 限值类型
	 * 数值范围/数值/文字描述
	 */
	private String xzType;
	private String minFlag;//最小值符号
	private String minValue;//最小值
	private String maxFlag;//最大值符号
	private String maxValue;//最大值     最大超限倍数(职卫标准)
	private String flag;//符号
	private String value;//数值                     PCWTA(职卫标准)
	private String valStr;//数值描述
	//气 浓度
	private String flag2;//符号
	private String value2;//数值2     PC-STEL(职卫标准)
	private String valStr2;//数值2描述
	private String value3;//数值2     MAC(职卫标准)
	/**
	 * 项目
	 */
	private Item item;
	/**
	 * 备注
	 */
	private String remark;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
	@Column(length = 32)
	public String getStandId() {
		return standId;
	}
	public void setStandId(String standId) {
		this.standId = standId;
	}
	@Column(length = 100)
	public String getStandName() {
		return standName;
	}
	public void setStandName(String standName) {
		this.standName = standName;
	}
	@Column(length = 32)
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	@Column(length = 100)
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	@Column(length = 64)
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length = 64)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 64)
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	@Column(length = 64)
	public String getOtherType() {
		return otherType;
	}
	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}
	@Column(length = 64)
	public String getXzType() {
		return xzType;
	}
	public void setXzType(String xzType) {
		this.xzType = xzType;
	}
	@Column(length = 32)
	public String getMinFlag() {
		return minFlag;
	}
	public void setMinFlag(String minFlag) {
		this.minFlag = minFlag;
	}
	@Column(length = 32)
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	@Column(length = 32)
	public String getMaxFlag() {
		return maxFlag;
	}
	public void setMaxFlag(String maxFlag) {
		this.maxFlag = maxFlag;
	}
	@Column(length = 32)
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	@Column(length = 32)
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(length = 32)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Column(length = 128)
	public String getValStr() {
		return valStr;
	}
	public void setValStr(String valStr) {
		this.valStr = valStr;
	}
	@Column(length = 32)
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	@Column(length = 128)
	public String getValStr2() {
		return valStr2;
	}
	public void setValStr2(String valStr2) {
		this.valStr2 = valStr2;
	}
	@Column(length = 32)
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	@Column(length = 32)
	public String getFlag2() {
		return flag2;
	}
	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}
	
}
