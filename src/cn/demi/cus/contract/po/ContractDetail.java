package cn.demi.cus.contract.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * Create on : 2016年11月15日 下午2:49:26  <br>
 * Description :合同项目实体对象   <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Entity(name = "cus_contract_detail")
@Table(name = "cus_contract_detail")
@Module(value = "cus.contract.detail")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContractDetail extends Po<ContractDetail>{

	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","code","name","contract","sampName","sampType","item","standard","batch","price"};
	/**
	 * 合同信息
	 */
	private Contract contract;
	/**
	 * 样品名称
	 */
	private String sampName;
	/**
	 * 样品种类
	 */
	private String sampType;
	/**
	 * 检测项目
	 */
	private String item;
	/**
	 * 检测标准
	 */
	private String standard;
	/**
	 * 批次
	 */
	private String batch;
	/**
	 * 价格
	 */
	private String price;
	


	@ManyToOne
	@JoinColumn(name = "contract_id")
	public Contract getContract() {
		return contract;
	}

	
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	@Column(length=32)
	public String getSampName() {
		return sampName;
	}


	public void setSampName(String sampName) {
		this.sampName = sampName;
	}

	@Column(length=32)
	public String getSampType() {
		return sampType;
	}


	public void setSampType(String sampType) {
		this.sampType = sampType;
	}

	@Lob
	public String getItem() {
		return item;
	}


	public void setItem(String item) {
		this.item = item;
	}

	@Column(length=32)
	public String getStandard() {
		return standard;
	}


	public void setStandard(String standard) {
		this.standard = standard;
	}

	@Column(length=32)
	public String getBatch() {
		return batch;
	}


	public void setBatch(String batch) {
		this.batch = batch;
	}

	@Column(length=32)
	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ContractDetail.class, false, ActionType.JSP);
	}

}
