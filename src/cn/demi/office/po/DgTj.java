package cn.demi.office.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 到岗管理
 * @author QuJunLong
 *
 */
@Entity(name = "office_dg_tj")
@Table(name = "office_dg_tj")
@Module(value = "office.dgTj")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DgTj extends Po<DgTj>{
 
	private static final long serialVersionUID = 1335453991628117128L;
	
	public static final String ST_0 = "缺勤";
	public static final String ST_1 = "在岗";
	public static final String ST_2 = "采样";
	public static final String ST_3 = "请假";
	  
	private String deptId;//部门
	private String deptName;
	private String userId;//打卡人
	private String userName;
	private String userCode;
	private String date;//打卡日期 
	private String stime;//开始时间
	private String etime;//结束时间   
	private String status;
	public String[] PROPERTY_TO_MAP= {"id","sort","userCode","deptName","userName","date","stime","etime","status"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	 
	@Column(length = 160)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(length = 160)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(length = 32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length = 32)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 20)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Column(length = 16)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 32)
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	@Column(length = 20)
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	@Column(length = 20)
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}

	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(DgTj.class, false, ActionType.JSP);
	}
}
