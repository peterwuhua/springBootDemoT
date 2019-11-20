package cn.demi.cus.contract.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
 * Description :合同执行情况实体对象  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Entity(name = "cus_contract_execute")
@Table(name = "cus_contract_execute")
@Module(value = "cus.contract.execute")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContractExecute extends Po<ContractExecute>{

	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","code","contract","taskNo","sampName","entrustName","sampCount","sendSampDate","sendSampPerson"};
	/**
	 * 合同
	 */
	private Contract contract;
	/**
	 * 任务编号
	 */
	private String taskNo;
	/**
	 * 样品名称
	 */
	private String sampName;
	/**
	 * 委托单位
	 */
	private String entrustName;
	/**
	 * 样品数量
	 */
	private String sampCount;
	/**
	 * 送样日期
	 */
	private String sendSampDate;
	/**
	 * 送样人
	 */
	private String sendSampPerson;

	
	@ManyToOne
	@JoinColumn(name = "contract_id")
	public Contract getContract() {
		return contract;
	}
	
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	@Column(length=32)
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	@Column(length=32)
	public String getSampName() {
		return sampName;
	}

	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Column(length=32)
	public String getEntrustName() {
		return entrustName;
	}

	public void setEntrustName(String entrustName) {
		this.entrustName = entrustName;
	}
	@Column(length=32)
	public String getSampCount() {
		return sampCount;
	}

	public void setSampCount(String sampCount) {
		this.sampCount = sampCount;
	}
	@Column(length=32)
	public String getSendSampDate() {
		return sendSampDate;
	}

	public void setSendSampDate(String sendSampDate) {
		this.sendSampDate = sendSampDate;
	}
	@Column(length=32)
	public String getSendSampPerson() {
		return sendSampPerson;
	}

	public void setSendSampPerson(String sendSampPerson) {
		this.sendSampPerson = sendSampPerson;
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ContractExecute.class, false, ActionType.JSP);
	}

}
