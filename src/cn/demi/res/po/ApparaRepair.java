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

/**
 * 仪器维修
 * @author QuJunLong
 *
 */
@Entity(name = "res_appara_repair")
@Table(name = "res_appara_repair")
@Module(value="res.apparaRepair")
public class ApparaRepair extends Po<ApparaRepair>{
	
	private static final long serialVersionUID = 8716550570174407901L;
	public String[] PROPERTY_TO_MAP= {"id","appara","unit","date","person","content","fileUrl","trueName","result","remark"};
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","lastUpdTime","version","appara"};
	
	@Transient
	@Override
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}
	private Appara appara;			//仪器
	private String unit;			//维修单位
	private String date;			//维修日期
	private String person;          //维修人
	private String content;			//维修内容
	private String fileUrl;			//附件地址
	private String trueName;		//附件名称
	private String result;			//维修结果
	private String remark;			//备注

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
	@Column(length = 64)
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	@Column(length = 256)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length = 256)
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	@Column(length = 64)
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	@Column(length = 64)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ApparaRepair.class, false, ActionType.JSP);
	}
}
