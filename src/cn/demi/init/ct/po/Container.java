package cn.demi.init.ct.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 采样容器
 * @author QuJunLong
 */
@Entity(name = "init_container")
@Table(name = "init_container")
@Module(value = "init.container")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Container extends Po<Container>{
	private static final long serialVersionUID = 1L;
	 
	public String[] PROPERTY_TO_MAP = {"id","sort","code","name","unit","maxVal"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 容器编号
	 */
	private String code;
	/**
	 * 容器名称
	 */
	private String name;
	/**
	 * 容器单位
	 */
	private String unit;
	/**
	 *规格
	 */
	private String maxVal;
	@Column(length = 32)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length = 64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 32)
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(length = 32)
	public String getMaxVal() {
		return maxVal;
	}
	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}
//	public static void main(String[] args) {
//		CreateCodeUtils.autoCreateCode(Container.class, true, ActionType.JSP);
//	}
}
