package cn.demi.office.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

/**
 * 
 * @ClassName:  OfficeSuppliesVo   
 * @Description:  
 * @author: 吴华 
 * @date:   2019年4月10日 下午4:27:19       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
public class OfficeSuppliesVo extends Vo<OfficeSuppliesVo> {
	private String ygbh;//员工编号
	private String ygmc;//员工名称
	private String deptname;//部门名称
	private String sqDate;//申请日期
	private String zj;//总价格
	private String djzt;//单据状态
	private String content;//描述内容
	private String ygId;
	private String status;//状态
	private List<OfficeSuppliesDetailVo> ypList;//办公用品集合

	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getYgId() {
		return ygId;
	}
	public void setYgId(String ygId) {
		this.ygId = ygId;
	}
	public List<OfficeSuppliesDetailVo> getYpList() {
		return ypList;
	}
	public void setYpList(List<OfficeSuppliesDetailVo> ypList) {
		this.ypList = ypList;
	}
	public String getYgbh() {
		return ygbh;
	}
	public void setYgbh(String ygbh) {
		this.ygbh = ygbh;
	}
	public String getYgmc() {
		return ygmc;
	}
	public void setYgmc(String ygmc) {
		this.ygmc = ygmc;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getSqDate() {
		return sqDate;
	}
	public void setSqDate(String sqDate) {
		this.sqDate = sqDate;
	}
	public String getZj() {
		return zj;
	}
	public void setZj(String zj) {
		this.zj = zj;
	}
	public String getDjzt() {
		return djzt;
	}
	public void setDjzt(String djzt) {
		this.djzt = djzt;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
	
}

