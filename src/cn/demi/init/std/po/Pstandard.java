package cn.demi.init.std.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
/**
 * 评价标准
 * @author QuJunLong
 */
@Entity(name = "init_pstandard")
@Table(name = "init_pstandard")
@Module(value = "init.pstandard")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pstandard extends Po<Pstandard>{
	
	public static final String ST_RUN = "现行";
	public static final String ST_STOP = "已作废";
	
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","name","enName","code","fbDate","ssDate","type","filePath","fileName","remark","sampTypeName","sampTypeId","status"};

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
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Column(length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 32)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Lob
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 100)
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	@Column(length = 20)
	public String getFbDate() {
		return fbDate;
	}
	public void setFbDate(String fbDate) {
		this.fbDate = fbDate;
	}
	@Column(length = 20)
	public String getSsDate() {
		return ssDate;
	}
	public void setSsDate(String ssDate) {
		this.ssDate = ssDate;
	}
	@Column(length = 20)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 128)
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
	@Column(length = 320)
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	@Column(length = 320)
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	@Column(length = 16)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 320)
	public String getStandIds() {
		return standIds;
	}
	public void setStandIds(String standIds) {
		this.standIds = standIds;
	}
	@Column(length = 640)
	public String getStandNames() {
		return standNames;
	}
	public void setStandNames(String standNames) {
		this.standNames = standNames;
	}
	@Column(length = 32)
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	@Column(length = 64)
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	
}
