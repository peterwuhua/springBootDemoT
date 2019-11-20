package cn.demi.cus.contract.vo;

import cn.core.framework.common.vo.Vo;
/**
 * Create on : 2017年1月9日 下午6:29:58  <br>
 * Description : 合同执行情况Vo <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public class ContractExecuteVo extends Vo<ContractExecuteVo> {
	/**
	 * 合同
	 */
	private ContractVo contractVo;
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
	
	public ContractVo getContractVo() {
		return contractVo;
	}
	public void setContractVo(ContractVo contractVo) {
		this.contractVo = contractVo;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public String getEntrustName() {
		return entrustName;
	}
	public void setEntrustName(String entrustName) {
		this.entrustName = entrustName;
	}
	public String getSampCount() {
		return sampCount;
	}
	public void setSampCount(String sampCount) {
		this.sampCount = sampCount;
	}
	public String getSendSampDate() {
		return sendSampDate;
	}
	public void setSendSampDate(String sendSampDate) {
		this.sendSampDate = sendSampDate;
	}
	public String getSendSampPerson() {
		return sendSampPerson;
	}
	public void setSendSampPerson(String sendSampPerson) {
		this.sendSampPerson = sendSampPerson;
	}
	
}

