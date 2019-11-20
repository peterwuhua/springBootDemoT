package cn.demi.init.std.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.FilesVo;

public class ItemVo extends Vo<ItemVo> {
	
	private ItemVo parentVo;
	/**
	 * 项目名称
	 */
	private String name;
	//样品代码
	private String code;
	//所有子项名称
	private String childNames;
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
	 * 是否现场检测项目
	 */
	private String isNow;
	/**
	 * 工时
	 */
	private float hours;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 参考价格
	 */
	private float price;
	private float minVal;
	private float maxVal;
	/**
	 * 样品类型
	 */
	private String sampTypeIds;
	private String sampTypeNames;
	private String sampType;//大类
	//仪器操作指导书
	private String fileId;
	private String fileName;
	private String filePath;
	/**
	 * 检测依据
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
	private String saveHour;//监测该项目的样品保存期限
	private List<ItemMethodVo> methodList;
	private List<MethodVo> mlist;
	//附件
	private List<FilesVo> fileList;
	private List<ItemVo> subList;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<ItemMethodVo> getMethodList() {
		return methodList;
	}
	public void setMethodList(List<ItemMethodVo> methodList) {
		this.methodList = methodList;
	}
	public float getHours() {
		return hours;
	}
	public void setHours(float hours) {
		this.hours = hours;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getSampTypeIds() {
		return sampTypeIds;
	}
	public void setSampTypeIds(String sampTypeIds) {
		this.sampTypeIds = sampTypeIds;
	}
	public String getSampTypeNames() {
		return sampTypeNames;
	}
	public void setSampTypeNames(String sampTypeNames) {
		this.sampTypeNames = sampTypeNames;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ItemVo getParentVo() {
		return parentVo;
	}
	public void setParentVo(ItemVo parentVo) {
		this.parentVo = parentVo;
	}
	public List<ItemVo> getSubList() {
		return subList;
	}
	public void setSubList(List<ItemVo> subList) {
		this.subList = subList;
	}
	public String getChildNames() {
		return childNames;
	}
	public void setChildNames(String childNames) {
		this.childNames = childNames;
	}
	public String getIsNow() {
		return isNow;
	}
	public void setIsNow(String isNow) {
		this.isNow = isNow;
	}
	public String getStandIds() {
		return standIds;
	}
	public void setStandIds(String standIds) {
		this.standIds = standIds;
	}
	public String getStandNames() {
		return standNames;
	}
	public void setStandNames(String standNames) {
		this.standNames = standNames;
	}
	public List<FilesVo> getFileList() {
		return fileList;
	}
	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getRqIds() {
		return rqIds;
	}
	public void setRqIds(String rqIds) {
		this.rqIds = rqIds;
	}
	public String getRqNames() {
		return rqNames;
	}
	public void setRqNames(String rqNames) {
		this.rqNames = rqNames;
	}
	public String getSaveHour() {
		return saveHour;
	}
	public void setSaveHour(String saveHour) {
		this.saveHour = saveHour;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public List<MethodVo> getMlist() {
		return mlist;
	}
	public void setMlist(List<MethodVo> mlist) {
		this.mlist = mlist;
	}
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
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

