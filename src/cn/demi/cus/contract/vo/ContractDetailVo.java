package cn.demi.cus.contract.vo;

import cn.core.framework.common.vo.Vo;
/**
 * Create on : 2017年1月9日 下午6:29:44  <br>
 * Description : 合同项目详情Vo <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public class ContractDetailVo extends Vo<ContractDetailVo> {
	/**
	 * 合同信息
	 */
	private ContractVo contractVo;
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
	public ContractVo getContractVo() {
		return contractVo;
	}
	public void setContractVo(ContractVo contractVo) {
		this.contractVo = contractVo;
	}
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}

