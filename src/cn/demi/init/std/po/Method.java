package cn.demi.init.std.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 检测方法
 * @author QuJunLong
 *
 */
@Entity(name = "init_method")
@Table(name = "init_method")
@Module(value = "init.method")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Method extends Po<Method>{

	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","standId","itemNames","code","standName","chapter","name","appIds","appNames","limitLine","maxLine","minLine"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
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
	
	private String tempIds;
	private String tempNames;
    /**
     * 标准文档的电子记录路径
     */
    private String filePath;
    /**
     * 附件名称
     */
    private String fileName;
	@Column(length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 1000)
	public String getAppIds() {
		return appIds;
	}
	public void setAppIds(String appIds) {
		this.appIds = appIds;
	}
	@Column(length = 1000)
	public String getAppNames() {
		return appNames;
	}
	public void setAppNames(String appNames) {
		this.appNames = appNames;
	}
	@Column(length = 128)
	public String getLimitLine() {
		return limitLine;
	}
	public void setLimitLine(String limitLine) {
		this.limitLine = limitLine;
	}
	@Column(length = 64)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length = 32)
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	@Column(length = 64)
	public String getStandName() {
		return standName;
	}
	public void setStandName(String standName) {
		this.standName = standName;
	}
	@Column(length = 32)
	public String getStandId() {
		return standId;
	}
	public void setStandId(String standId) {
		this.standId = standId;
	}
	@Column(length = 64)
	public String getMaxLine() {
		return maxLine;
	}
	public void setMaxLine(String maxLine) {
		this.maxLine = maxLine;
	}
	@Column(length = 8)
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Column(length = 8)
	public float getHours() {
		return hours;
	}
	public void setHours(float hours) {
		this.hours = hours;
	}
	@Column(length = 64)
	public String getMinLine() {
		return minLine;
	}
	public void setMinLine(String minLine) {
		this.minLine = minLine;
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
	@Column(length = 1000)
	public String getTempIds() {
		return tempIds;
	}
	public void setTempIds(String tempIds) {
		this.tempIds = tempIds;
	}
	@Column(length = 1000)
	public String getTempNames() {
		return tempNames;
	}
	public void setTempNames(String tempNames) {
		this.tempNames = tempNames;
	}
	@Column(length = 256)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length = 128)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
