package cn.demi.bus.file.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * Description :档案 文件 <br>
 * @version v 1.0.0 <br>
 */
@Entity(name = "bus_archive_file")
@Table(name = "bus_archive_file")
@Module(value = "bus.archiveFile")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ArchiveFile extends Po<ArchiveFile> {

	private static final long serialVersionUID = 1L;
	 
	public String[] PROPERTY_TO_MAP = { "id", "sort", "fileName","filePath", "time", "type", "size", "sign", "describtion","archive"};
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
	private Archive archive;
 
	
	@ManyToOne(targetEntity = Archive.class)
	@JoinColumn(name = "archive_id")
	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}
	@Column(length=128)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(length=512)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length=64)
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Column(length=64)
	public long getOriginalSize() {
		return originalSize;
	}

	public void setOriginalSize(long originalSize) {
		this.originalSize = originalSize;
	}

	@Column(length=64)
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Column(length=256)
	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	@Column(length=20)
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	@Column(length=64)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ArchiveFile.class, true, ActionType.JSP);
	}
}
