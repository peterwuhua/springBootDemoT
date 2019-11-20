package cn.demi.init.std.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class MstandardVo extends Vo<MstandardVo> {
	/**
	 * 名称
	 */
	private String name;
    /**
     * 英文名称
     */
    private String enName;
	/**
	 * 编号
	 */
	private String code;
	
	/**
	 *发布日期 
	 */
	private String fbDate;
	/**
	 * 实施日期
	 */
	private String ssDate;
	/**
	 * 标准类型
	 */
	private String type;
	/**
	 * 标准分类
	 */
	private String sampTypeId;
	private String sampTypeName;
    /**
     * 标准文档的电子记录路径
     */
    private String filePath;
    /**
     * 附件名称
     */
    private String fileName;
	/**
	 * 备注
	 */
	private String remark;
    
    private String isSyncMethod;
	
    private String methodNames;
    
	private List<MethodVo> methodList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFbDate() {
		return fbDate;
	}
	public void setFbDate(String fbDate) {
		this.fbDate = fbDate;
	}
	public String getSsDate() {
		return ssDate;
	}
	public void setSsDate(String ssDate) {
		this.ssDate = ssDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<MethodVo> getMethodList() {
		return methodList;
	}
	public void setMethodList(List<MethodVo> methodList) {
		this.methodList = methodList;
	}
 
	public String getIsSyncMethod() {
		return isSyncMethod;
	}
	public void setIsSyncMethod(String isSyncMethod) {
		this.isSyncMethod = isSyncMethod;
	}
	public String getMethodNames() {
		return methodNames;
	}
	public void setMethodNames(String methodNames) {
		this.methodNames = methodNames;
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
	
}

