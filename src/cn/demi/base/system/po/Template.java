package cn.demi.base.system.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
@Entity(name="sys_template")
@Table(name="sys_template")
@Module(value="sys.template")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Template extends Po<Template> {
	private static final long serialVersionUID = 1L;
	public static final String EXPORT = "export";
	public static final String IMPORT = "import";
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","type","name","code","user","versionNo","describtion","path","busType"};
	/**
	 * 标记模板类别（导入模板/导出模板）
	 */
	private String type;//标记模板类别（导入模板/导出模板）
	/**
	 * 模板名称
	 */
	private String name;//模板名称
	/**
	 * 模板编码
	 */
	private String code;//模板编码
	/**
	 * 负责人
	 */
	private String user;//负责人
	/**
	 * 版本号
	 */
	private String versionNo;//版本号
	/**
	 * 说明
	 */
	private String describtion;//说明
	/**
	 * 存储路径
	 */
	private String path;//存储路径
	/**
	 * 业务模块
	 */
	private String busType;//业务模块
	@Column(length=64)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length=64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=64)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@Column(length=64)
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	@Column(length=64)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length=64)
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Column(length=64)
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Template.class, false, ActionType.JSP);
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
}