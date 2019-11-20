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
 * 检测项目(指标)
 * @author QuJunLong
 *
 */
@Entity(name = "init_item")
@Table(name = "init_item")
@Module(value = "init.item")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Item extends Po<Item>{

	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","name","parent","unit","symbol","type","isNow","price","hours","sampTypeNames","remark","standNames","userNames","code"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private Item parent;
	//所有子项名称
	private String childNames;
	/**
	 * 项目名称
	 */
	private String name;
	//样品代码
	private String code;
	/**
	 * 检测结果单位
	 */
	private String unit;
	/**
	 * 类别
	 */
	private String type;
	/**
	 * 化学符号
	 */
	private String symbol;
	/**
	 * 工时
	 */
	private float hours;
	/**
	 * 参考价格
	 */
	private float price;
	/**
	 * 是否现场检测项目
	 */
	private String isNow;
	private float minVal;
	private float maxVal;
	/**
	 * 样品类型
	 */
	private String sampTypeIds;
	private String sampTypeNames;
	/**
	 * 方法标准
	 */
	private String standIds;
	private String standNames;
	
	/**
	 *默认检测人
	 */
	private String userIds;
	private String userNames;
	/**
	 * 采样容器
	 */
	private String rqIds;
	private String rqNames;
	private String saveHour;
	//仪器操作指导书
	private String fileId;
	private String fileName;
	private String filePath;
	/**
	 * 备注
	 */
	private String remark;
	
	@Column(length = 64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 32)
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(length = 64)
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 4)
	public float getHours() {
		return hours;
	}
	public void setHours(float hours) {
		this.hours = hours;
	}
	@Column(length = 11)
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Column(length = 1000)
	public String getSampTypeIds() {
		return sampTypeIds;
	}
	public void setSampTypeIds(String sampTypeIds) {
		this.sampTypeIds = sampTypeIds;
	}
	@Column(length = 1000)
	public String getSampTypeNames() {
		return sampTypeNames;
	}
	public void setSampTypeNames(String sampTypeNames) {
		this.sampTypeNames = sampTypeNames;
	}
	@Column(length = 1000)
	public String getStandIds() {
		return standIds;
	}
	public void setStandIds(String standIds) {
		this.standIds = standIds;
	}
	@Column(length = 1000)
	public String getStandNames() {
		return standNames;
	}
	public void setStandNames(String standNames) {
		this.standNames =standNames;
	}
	@Column(length = 64)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@ManyToOne
	@JoinColumn(name = "parent_id")
	public Item getParent() {
		return parent;
	}
	public void setParent(Item parent) {
		this.parent = parent;
	}
	@Column(length = 1000)
	public String getChildNames() {
		return childNames;
	}
	public void setChildNames(String childNames) {
		this.childNames = childNames;
	}
	@Column(length = 4)
	public String getIsNow() {
		return isNow;
	}
	public void setIsNow(String isNow) {
		this.isNow = isNow;
	}
	@Column(length = 16)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length = 32)
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	@Column(length = 64)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(length = 128)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length = 160)
	public String getRqIds() {
		return rqIds;
	}
	public void setRqIds(String rqIds) {
		this.rqIds = rqIds;
	}
	@Column(length = 160)
	public String getRqNames() {
		return rqNames;
	}
	public void setRqNames(String rqNames) {
		this.rqNames = rqNames;
	}
	@Column(length = 8)
	public String getSaveHour() {
		return saveHour;
	}
	public void setSaveHour(String saveHour) {
		this.saveHour = saveHour;
	}
	@Column(length = 160)
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	@Column(length = 160)
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public float getMinVal() {
		return minVal;
	}
	public void setMinVal(float minVal) {
		this.minVal = minVal;
	}
	public float getMaxVal() {
		return maxVal;
	}
	public void setMaxVal(float maxVal) {
		this.maxVal = maxVal;
	}
}
