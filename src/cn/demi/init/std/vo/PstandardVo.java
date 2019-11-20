package cn.demi.init.std.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class PstandardVo extends Vo<PstandardVo> {
	
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
	 * 标准分类
	 */
	private String sampTypeId;
	private String sampTypeName;
	/**
	 * 标准类型
	 */
	private String type;
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
	//状态
	private String status; 
	//代替标准
	private String standIds;
	private String standNames;
	//替代该标准的标准
	private String pid;
	private String pname;
 
	private List<PstandItemVo> itemList;

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

	public List<PstandItemVo> getItemList() {
		return itemList;
	}

	public void setItemList(List<PstandItemVo> itemList) {
		this.itemList = itemList;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
}

