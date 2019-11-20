package cn.demi.bus.file.vo;

import cn.core.framework.common.vo.Vo;

public class ArchiveFileVo extends Vo<ArchiveFileVo> {
	/**
	 * 文档名称
	 */
	private String fileName;
	/**
	 * 文档路径
	 */
	private String filePath;
	/**
	 * 上传时间
	 */
	private String time;
	/**
	 * 文件类型
	 */
	private String type;
	/**
	 * 文档大小
	 */
	private String size;
	/**
	 * 原始长度
	 */
	private long originalSize;
	/**
	 * 文档分类
	 * 原始记录单
	 * 附件资料
	 * 报告文件
	 * 采样计划单
	 * 采样单
	 * 样品交接单
	 */
	private String sign;
	/**
	 * 文档说明
	 */
	private String describtion;
	/**
	 * 所属档案
	 */
	private ArchiveVo archiveVo;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public long getOriginalSize() {
		return originalSize;
	}
	public void setOriginalSize(long originalSize) {
		this.originalSize = originalSize;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	public ArchiveVo getArchiveVo() {
		return archiveVo;
	}
	public void setArchiveVo(ArchiveVo archiveVo) {
		this.archiveVo = archiveVo;
	}
}

