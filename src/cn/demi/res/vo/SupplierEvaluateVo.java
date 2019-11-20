package cn.demi.res.vo;

import cn.core.framework.common.vo.Vo;

public class SupplierEvaluateVo extends Vo<SupplierEvaluateVo> {
	
	
	private SupplierVo supplierVo;//供应商
	private String tradeName;//商品名称
	private String supTime;//评价时间
	private String evaluateTime;//评价时间
	private String evaluatePerson;//评价人
	private String price;//价额
	private String deliveryTime;//交货期
	private String productQuality;//产品质量
	private String serviceQuality;//服务质量
	private String content;//评价内容
	private String filePath;//附件地址
	private String fileName;//附件名称
 
	public SupplierVo getSupplierVo() {
		return supplierVo;
	}
	public void setSupplierVo(SupplierVo supplierVo) {
		this.supplierVo = supplierVo;
	}
	public String getEvaluateTime() {
		return evaluateTime;
	}
	public void setEvaluateTime(String evaluateTime) {
		this.evaluateTime = evaluateTime;
	}
	public String getSupTime() {
		return supTime;
	}
	public void setSupTime(String supTime) {
		this.supTime = supTime;
	}
	public String getEvaluatePerson() {
		return evaluatePerson;
	}
	public void setEvaluatePerson(String evaluatePerson) {
		this.evaluatePerson = evaluatePerson;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getProductQuality() {
		return productQuality;
	}
	public void setProductQuality(String productQuality) {
		this.productQuality = productQuality;
	}
	public String getServiceQuality() {
		return serviceQuality;
	}
	public void setServiceQuality(String serviceQuality) {
		this.serviceQuality = serviceQuality;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
}
