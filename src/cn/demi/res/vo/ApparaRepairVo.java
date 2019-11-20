package cn.demi.res.vo;

import cn.core.framework.common.vo.Vo;

public class ApparaRepairVo extends Vo<ApparaRepairVo> {
	private ApparaVo apparaVo;			//仪器
	private String unit;			//维修单位
	private String date;			//维修日期
	private String person;          //维修人
	private String content;			//维修内容
	private String fileUrl;			//附件地址
	private String trueName;		//附件名称
	private String result;			//维修结果
	private String remark;			//备注
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
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
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
	
}

