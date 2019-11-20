package cn.demi.init.st.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.TreePo;
import cn.core.framework.utils.code.annotation.Module;
/**
 * 样品类型
 * @author QuJunLong
 *
 */
@Entity(name = "init_samp_type")
@Table(name = "init_samp_type")
@Module(value = "init.samp_type")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SampType  extends TreePo<SampType> {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","parent","level","path","sort","typeNo","name","packCodeType","type","isUsed","remark"};
 
	/**
	 * 分类编号
	 */
	private String typeNo;
 
	/**
	 * 水容器开头字母
	 */
	private String packCodeType;
	/**
	 * 样品种类字母
	 */
	private String type;
	/**
	 * 是否启用
	 */
	private String isUsed;
	/**
	 * 备注
	 */
	private String remark;
 
	
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

	@Column(length = 32)
	public String getTypeNo() {
		return typeNo;
	}


	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}

	@Column(length = 32)
	public String getPackCodeType() {
		return packCodeType;
	}


	public void setPackCodeType(String packCodeType) {
		this.packCodeType = packCodeType;
	}

	@Column(length = 32)
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	@Column(length = 8)
	public String getIsUsed() {
		return isUsed;
	}


	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	@Lob
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
}
