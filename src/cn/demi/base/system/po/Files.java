package cn.demi.base.system.po;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 附件管理
 * @author QuJunLong
 *
 */
@Entity(name = "sys_files")
@Table(name = "sys_files")
@Module(value = "sys.files")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Files extends Po<Files> {

	//业务id
	private String busId;
	//业务类型
	private String busType;
	//文件类型
	private String fileType;
	//文件名称
	private String fileName;
	//文件路径
	private String filePath;
	
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
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
	private static final long serialVersionUID = -5444576869469615071L;
	public String[] PROPERTY_TO_MAP= {"id","sort","busId","busType","fileType","fileName","filePath"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 代码生成
	 * @param args
	 */
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Files.class, false, ActionType.JSP);
	}
}
