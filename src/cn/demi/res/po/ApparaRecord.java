package cn.demi.res.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/** <strong>Create on : 2017年2月28日 下午1:28:15 </strong> <br>
 * <strong>Description : 仪器设备附表</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
@Entity(name = "res_appara_record")
@Table(name = "res_appara_record")
@Module(value="res.appara.record")
public class ApparaRecord extends Po<ApparaRecord> {
	private static final long serialVersionUID = 1L;
	
	private Appara appara;			//仪器
	private String unit;			//单位
	private String date;			//日期
	private String person;          //检定、核查、校准人
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
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","appara","unit","date","person","nextDate","appliances","standardRange","uncertainty","basisFile","certificateNo",
			"fileUrl","trueName","result","remark","type"};
	
	@ManyToOne
	@JoinColumn(name = "appara_id")
	public Appara getAppara() {
		return appara;
	}

	public void setAppara(Appara appara) {
		this.appara = appara;
	}
	@Column(length = 64)
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(length = 20)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Column(length = 20)
	public String getNextDate() {
		return nextDate;
	}
	public void setNextDate(String nextDate) {
		this.nextDate = nextDate;
	}
	@Column(length = 64)
	public String getAppliances() {
		return appliances;
	}
	public void setAppliances(String appliances) {
		this.appliances = appliances;
	}
	@Column(length = 64)
	public String getStandardRange() {
		return standardRange;
	}
	public void setStandardRange(String standardRange) {
		this.standardRange = standardRange;
	}
	@Column(length = 64)
	public String getUncertainty() {
		return uncertainty;
	}
	public void setUncertainty(String uncertainty) {
		this.uncertainty = uncertainty;
	}
	@Column(length = 128)
	public String getBasisFile() {
		return basisFile;
	}
	public void setBasisFile(String basisFile) {
		this.basisFile = basisFile;
	}
	@Column(length = 64)
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	@Column(length = 256)
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	@Column(length = 64)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Column(length = 64)
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 64)
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ApparaRecord.class, false, ActionType.JSP);
	}
	
	@Transient
	@Override
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
}
