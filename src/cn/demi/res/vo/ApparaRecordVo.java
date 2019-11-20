package cn.demi.res.vo;

import cn.core.framework.common.vo.Vo;


/** <strong>Create on : 2017年2月28日 下午1:33:35 </strong> <br>
 * <strong>Description :仪器设备附表信息 </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
public class ApparaRecordVo extends Vo<ApparaRecordVo>{
	
	private ApparaVo apparaVo;		//仪器
	private String unit;			//单位
	private String date;			//日期
	private String person;          //检定、校准、核查人
	private String nextDate;		//下次日期
	private String appliances;		//器具
	private String standardRange;	//标准量程
	private String uncertainty;		//不确定度
	private String basisFile;		//依据文件
	private String certificateNo;	//证书编号
	private String fileUrl;			//附件地址
	private String trueName;		//附件名称
	private String result;			//总结论
	private String remark;			//备注
	private String type;			//操作类型
	private String cycle;			//周期
	
	private String status;			//操作接入口
	
	public ApparaVo getApparaVo() {
		return apparaVo;
	}
	public void setApparaVo(ApparaVo apparaVo) {
		this.apparaVo = apparaVo;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getNextDate() {
		return nextDate;
	}
	public void setNextDate(String nextDate) {
		this.nextDate = nextDate;
	}
	public String getAppliances() {
		return appliances;
	}
	public void setAppliances(String appliances) {
		this.appliances = appliances;
	}
	public String getStandardRange() {
		return standardRange;
	}
	public void setStandardRange(String standardRange) {
		this.standardRange = standardRange;
	}
	public String getUncertainty() {
		return uncertainty;
	}
	public void setUncertainty(String uncertainty) {
		this.uncertainty = uncertainty;
	}
	public String getBasisFile() {
		return basisFile;
	}
	public void setBasisFile(String basisFile) {
		this.basisFile = basisFile;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	
}

