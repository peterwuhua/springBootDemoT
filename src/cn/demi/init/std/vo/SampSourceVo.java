package cn.demi.init.std.vo;

import cn.core.framework.common.vo.Vo;

public class SampSourceVo extends Vo<SampSourceVo> {
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
    /**
     * 标准文档的电子记录路径
     */
    private String filePath;
    /**
     * 附件名称
     */
    private String fileName;
    private String remark;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}

