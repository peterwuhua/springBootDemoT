package cn.demi.base.system.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name = "sys_role")
@Module(value = "sys.role")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends Po<Role> {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","name","describtion","code","sort","funNames"};
	/**
	 * 角色名称
	 */
	private String name;// 角色名称
	/**
	 * 说明
	 */
	private String describtion;// 说明
	/**
	 * 角色编码
	 */
	private String code;// 角色编码
	
	private String funNames;
	
	@Column(length=1000)
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	@Column(length=64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length=64)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Column(length=1000)
	public String getFunNames() {
		return funNames;
	}

	public void setFunNames(String funNames) {
		this.funNames = funNames;
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
}