package cn.demi.init.std.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
/**
 * 采样标准
 * @author QuJunLong
 */
@Entity(name = "init_samp_source")
@Table(name = "init_samp_source")
@Module(value = "init.sampSource")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SampSource extends Po<SampSource>{
	private static final long serialVersionUID = 4163411790930603678L;

	public String[] PROPERTY_TO_MAP= {"id","sort","name","code","filePath","fileName"};
	
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
    
	@Column(length=32)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length=64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=256)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length=256)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(SampSource.class, true, ActionType.JSP);
	}
}
