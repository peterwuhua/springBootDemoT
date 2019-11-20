package cn.demi.base.system.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.TreePo;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name = "sys_org")
@Table(name = "sys_org")
@Module(value = "sys.org")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Org extends TreePo<Org> {
	
	public static final String TYPE_JC = "检测室";
	public static final String TYPE_BG = "办公室";
	public static final String TYPE_XC = "现场室";
	
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","parent","level","name","path","sort","code","describtion","type"};
	/**
	 * 组织编码
	 */
	private String code;// 组织编码
	/**
	 * 组织编码
	 */
	private String type;//类别
	/**
	 * 说明
	 */
	private String describtion;// 说明
	
	private Long imId;

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
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Column(length=32)
	public Long getImId() {
		return imId;
	}

	public void setImId(Long imId) {
		this.imId = imId;
	}
	@Column(length=32)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}