package cn.demi.base.system.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.TreePo;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name = "sys_function")
@Module(value = "sys.function")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Function extends TreePo<Function> {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","parent","level","name","path","sort","code","wfCode","url","imageUrl","type","describtion"};
	/**
	 * 功能编码
	 */
	private String code; // 功能编码
	/**
	 * 流程编码
	 */
	private String wfCode; // 流程编码
	/**
	 * 功能路径
	 */
	private String url;// 功能路径
	/**
	 * imageUrl
	 */
	private String imageUrl;
	/**
	 * 功能类型
	 */
	private String type;// 功能类型
	/**
	 * 说明
	 */
	private String describtion;// 说明

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
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@Column(length=64)
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	@Column(length=64)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(length=64)
	public String getWfCode() {
		return wfCode;
	}

	public void setWfCode(String wfCode) {
		this.wfCode = wfCode;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
}