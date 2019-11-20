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
 * @Description: 办公用品(主表)
 * @author: 吴华 
 * @date:   2019年4月9日 下午3:06:52       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Entity(name = "office_supplies")
@Table(name = "office_supplies")
@Module(value = "office.supplies")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OfficeSupplies extends Po<OfficeSupplies>{

	public static final String ST_0="已保存";//状态
	public static final String ST_1="已提交";//状态
	public static final String ST_2="主管已批";//状态
	public static final String ST_3="副总已批";//状态
	public static final String ST_4="总经理已批";//状态
	public static final String ST_5="总经办归档";//状态
	public static final String ST_6="已驳回";//状态
	public static final String ST_7="申请成功";//状态
	private static final long serialVersionUID = 1L;
	public String[] PROPERTY_TO_MAP= {"id","sort","ygbh","ygmc","deptname","sqDate","zj","djzt","content","ygId","status"};
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

	private String ygbh;//员工编号
	private String ygmc;//员工名称
	private String ygId;//
	private String deptname;//部门名称
	private String sqDate;//申请日期
	private String zj;//总价格
	private String djzt;//单据审核状态（申请/审批/驳回）
	private String content;//描述内容
	private String status;//状态
	
	@Column(length = 16)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Lob
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length = 32)
	public String getDjzt() {
		return djzt;
	}
	public void setDjzt(String djzt) {
		this.djzt = djzt;
	}
	@Column(length = 32)
	public String getYgbh() {
		return ygbh;
	}
	public void setYgbh(String ygbh) {
		this.ygbh = ygbh;
	}
	@Column(length = 128)
	public String getYgmc() {
		return ygmc;
	}
	public void setYgmc(String ygmc) {
		this.ygmc = ygmc;
	}
	@Column(length = 32)
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	@Column(length = 32)
	public String getSqDate() {
		return sqDate;
	}
	public void setSqDate(String sqDate) {
		this.sqDate = sqDate;
	}
	@Column(length = 32)
	public String getZj() {
		return zj;
	}
	public void setZj(String zj) {
		this.zj = zj;
	}
	@Column(length = 32)
	public String getYgId() {
		return ygId;
	}
	public void setYgId(String ygId) {
		this.ygId = ygId;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(OfficeSupplies.class, false, ActionType.JSP);
	}
	
	
	
	
	
}
