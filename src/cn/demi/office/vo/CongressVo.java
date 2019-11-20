package cn.demi.office.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.po.User;
import cn.demi.bus.pg.vo.ProgressVo;
import cn.demi.init.car.po.CarUse;
/**
 * 
 * @ClassName:  CongressVo   
 * @Description:会议管理vo 
 * @author: 吴华 
 * @date:   2019年3月5日 下午5:59:14       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
public class CongressVo extends Vo<CongressVo> {
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
	private List<User> appender; //会议参与人员
	/**
	 * 会议物资
	 */
	private String carNames; //会议使用车辆名称
	private String carIds; 
	private List<CarUse> carUseList; //车辆使用信息列表
	private double ysfee; //会议预算费用
	private String qtRemark;//备注(其他)
	
	/**
	 * 会议审批
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
	private String fkContent; //反馈内容
	private String viewDate; //查阅日期
	
	
	private String fstatus; //会议申请状态
	
	private ProgressVo progressVo;//流程
	
	private String status;//状态
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ProgressVo getProgressVo() {
		return progressVo;
	}
	public void setProgressVo(ProgressVo progressVo) {
		this.progressVo = progressVo;
	}
	public String getViewr() {
		return viewr;
	}
	public void setViewr(String viewr) {
		this.viewr = viewr;
	}
	public String getViewId() {
		return viewId;
	}
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	public String getFkContent() {
		return fkContent;
	}
	public void setFkContent(String fkContent) {
		this.fkContent = fkContent;
	}
	public String getViewDate() {
		return viewDate;
	}
	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}
	public String getSumUserId() {
		return sumUserId;
	}
	public void setSumUserId(String sumUserId) {
		this.sumUserId = sumUserId;
	}
	public String getSumDate() {
		return sumDate;
	}
	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}
	public String getSumUserName() {
		return sumUserName;
	}
	public void setSumUserName(String sumUserName) {
		this.sumUserName = sumUserName;
	}
	public String getSumRemark() {
		return sumRemark;
	}
	public void setSumRemark(String sumRemark) {
		this.sumRemark = sumRemark;
	}
	public String getFstatus() {
		return fstatus;
	}
	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZjr() {
		return zjr;
	}
	public void setZjr(String zjr) {
		this.zjr = zjr;
	}
	public String getZjrId() {
		return zjrId;
	}
	public void setZjrId(String zjrId) {
		this.zjrId = zjrId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSqr() {
		return sqr;
	}
	public void setSqr(String sqr) {
		this.sqr = sqr;
	}
	public String getSqrId() {
		return sqrId;
	}
	public void setSqrId(String sqrId) {
		this.sqrId = sqrId;
	}
	public String getSupportDate() {
		return supportDate;
	}
	public void setSupportDate(String supportDate) {
		this.supportDate = supportDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public List<User> getAppender() {
		return appender;
	}
	public void setAppender(List<User> appender) {
		this.appender = appender;
	}
	public String getCarNames() {
		return carNames;
	}
	public void setCarNames(String carNames) {
		this.carNames = carNames;
	}
	public String getCarIds() {
		return carIds;
	}
	public void setCarIds(String carIds) {
		this.carIds = carIds;
	}
	public List<CarUse> getCarUseList() {
		return carUseList;
	}
	public void setCarUseList(List<CarUse> carUseList) {
		this.carUseList = carUseList;
	}
	public double getYsfee() {
		return ysfee;
	}
	public void setYsfee(double ysfee) {
		this.ysfee = ysfee;
	}
	public String getQtRemark() {
		return qtRemark;
	}
	public void setQtRemark(String qtRemark) {
		this.qtRemark = qtRemark;
	}
	
	
	
	
}

