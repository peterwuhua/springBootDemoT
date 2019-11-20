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
 * @ClassName:  Congress   
 * @Description:会议管理
 * @author: 吴华 
 * @date:   2019年3月5日 下午5:26:55       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Entity(name = "office_congress")
@Table(name = "office_congress")
@Module(value = "office.congress")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Congress extends Po<Congress>{

	public static final String ST_BC="已保存";
	public static final String ST_SH="审核中";
	public static final String ST_TG="已通过";
	public static final String ST_JJ="已拒绝";
	private static final long serialVersionUID = 1L;
	
	public String[] PROPERTY_TO_MAP= {"id","sort","name","zjr","zjrId","address","beginTime","endTime","sqr","sqrId","supportDate","content","users","userIds","carNames","carIds","ysfee","qtRemark","sumDate","sumUserName","sumRemark","fstatus","viewr","viewId","viewDate","sumUserId","progress","status"};
	public String[] IGNORE_PROPERTY_TO_PO = {"id","createTime","lastUpdTime","version","status"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}
	
	private String name; //会议名称
	private String zjr; //主讲人
	private String zjrId; //主讲人id
	private String address; //会议地点
	private String beginTime; //开始时间（会议开始时间）
	private String endTime; //结束时间
	private String sqr; //申请人
	private String sqrId; //申请人id
	private String supportDate; //申请日期
	private String content;//会议内容
	private String users; //会议参与人员名称
	private String userIds; //会议参与人员id
//	private List<User> appender; //会议参与人员
	/**
	 * 会议物资
	 */
	private String carNames; //会议使用车辆名称
	private String carIds; 
//	private List<CarUse> carUseList; //车辆使用信息列表
	private double ysfee; //会议预算费用
	private String qtRemark;//备注(其他)
	
	
	/**
	 * 会议审核
	 */
	private String sumDate; //审批日期
	private String sumUserName;//审批人
	private String sumUserId;//审批人id
	private String sumRemark; //审批意见
	
	/**
	 * 会议查阅
	 */
	private String viewr; //查阅人
	private String viewId; //查阅人id
	private String viewDate; //查阅日期
	
	private String fstatus; //会议申请状态
	
	private String status;//状态
	
	
	
	@Column(length=16)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length=64)
	public String getSumUserId() {
		return sumUserId;
	}
	public void setSumUserId(String sumUserId) {
		this.sumUserId = sumUserId;
	}
	@Column(length=1000)
	public String getViewr() {
		return viewr;
	}
	public void setViewr(String viewr) {
		this.viewr = viewr;
	}
	@Column(length=1000)
	public String getViewId() {
		return viewId;
	}
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	@Column(length=64)
	public String getViewDate() {
		return viewDate;
	}
	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}
	@Column(length=1000)
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	@Column(length=1000)
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	@Column(length=1000)
	public String getCarNames() {
		return carNames;
	}
	public void setCarNames(String carNames) {
		this.carNames = carNames;
	}
	@Column(length=1000)
	public String getCarIds() {
		return carIds;
	}
	public void setCarIds(String carIds) {
		this.carIds = carIds;
	}
	@Column(length=128)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=32)
	public String getZjr() {
		return zjr;
	}
	public void setZjr(String zjr) {
		this.zjr = zjr;
	}
	 @Column(length=32)
	public String getZjrId() {
		return zjrId;
	}
	public void setZjrId(String zjrId) {
		this.zjrId = zjrId;
	}
	  @Column(length=512)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	 @Column(length=32)
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	  @Column(length=32)
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	  @Column(length=32)
	public String getSqr() {
		return sqr;
	}
	public void setSqr(String sqr) {
		this.sqr = sqr;
	}
	  @Column(length=32)
	public String getSqrId() {
		return sqrId;
	}
	public void setSqrId(String sqrId) {
		this.sqrId = sqrId;
	}
	  @Column(length=32)
	public String getSupportDate() {
		return supportDate;
	}
	public void setSupportDate(String supportDate) {
		this.supportDate = supportDate;
	}
	  @Column(length=128)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getYsfee() {
		return ysfee;
	}
	public void setYsfee(double ysfee) {
		this.ysfee = ysfee;
	}
	@Lob
	public String getQtRemark() {
		return qtRemark;
	}
	public void setQtRemark(String qtRemark) {
		this.qtRemark = qtRemark;
	}
	
	
	
	@Column(length=64)
	public String getSumDate() {
		return sumDate;
	}
	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}
	@Column(length=64)
	public String getSumUserName() {
		return sumUserName;
	}
	public void setSumUserName(String sumUserName) {
		this.sumUserName = sumUserName;
	}
	@Lob
	public String getSumRemark() {
		return sumRemark;
	}
	public void setSumRemark(String sumRemark) {
		this.sumRemark = sumRemark;
	}
	@Column(length=64)
	public String getFstatus() {
		return fstatus;
	}
	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Congress.class, true, ActionType.JSP);
	}
	
	
}
