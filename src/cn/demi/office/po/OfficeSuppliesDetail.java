package cn.demi.office.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 
 * @ClassName:  OfficeSupplies   
 * @Description: 办公用品(子表)
 * @author: 吴华 
 * @date:   2019年4月9日 下午3:06:52       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Entity(name = "office_supplies_detail")
@Table(name = "office_supplies_detail")
@Module(value = "office.supplies_detail")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OfficeSuppliesDetail extends Po<OfficeSuppliesDetail>{

	
	private static final long serialVersionUID = 1L;
	public String[] PROPERTY_TO_MAP= {"id","sort","bgname","sl","dw","remark","pid"};
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","lastUpdTime","version"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}

	private String bgname;//办公用品名称
	private Integer sl;//数量
	private String dw;//单位名称
	private String remark;//备注
	private String pid;//办公用品主表id
	
	
	@Column(length = 32)
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	@Column(length = 64)
	public String getBgname() {
		return bgname;
	}
	public void setBgname(String bgname) {
		this.bgname = bgname;
	}
	@Column(length = 32)
	public Integer getSl() {
		return sl;
	}
	public void setSl(Integer sl) {
		this.sl = sl;
	}
	@Column(length = 32)
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(OfficeSuppliesDetail.class, false, ActionType.JSP);
	}
	
	
}
