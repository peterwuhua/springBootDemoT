package cn.demi.bus.task.vo;

import java.util.List;

public class CyPlanVo {
 
	private String no;
	private String custName;
	private String user;
	private String color;
	private int num;
	private List<CyObjVo> tabList; 
	private int colSpan;//合并列
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getColSpan() {
		return colSpan;
	}
	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public List<CyObjVo> getTabList() {
		return tabList;
	}
	public void setTabList(List<CyObjVo> tabList) {
		this.tabList = tabList;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
}