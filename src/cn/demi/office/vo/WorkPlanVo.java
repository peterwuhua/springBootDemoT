package cn.demi.office.vo;

import cn.core.framework.common.vo.Vo;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.BeanUtils;
import cn.demi.office.po.WorkPlan;

public class WorkPlanVo extends Vo<WorkPlanVo> {
	//类型
	private String type;
	//标题
	private String title;
	//内容
	private String content;
	//用户
	private String userId;
	private String userName;
	//部门
	private String orgId;
	private String orgName;
	//紧急程度
	private String urgentLevel;
	private String startTime;
	private String endTime;
	private String dateStr;
	//选择日期
	private String selectdate;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
 
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSelectdate() {
		return selectdate;
	}
	public void setSelectdate(String selectdate) {
		this.selectdate = selectdate;
	}
	public String getUrgentLevel() {
		return urgentLevel;
	}
	public void setUrgentLevel(String urgentLevel) {
		this.urgentLevel = urgentLevel;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public WorkPlanVo toVo(WorkPlan plan) throws GlobalException {
		WorkPlanVo vo=instance();
		BeanUtils.copyProperties(plan, vo, new String[] {});
		if(null!=plan.getStartTime()) {
			String startTime[]=plan.getStartTime().split(" ");
			if(startTime.length==2) {
				vo.setStartDate(startTime[0]);
				vo.setStartTime(startTime[1]);
			}
		}
		if(null!=plan.getEndTime()) {
			String endTime[]=plan.getEndTime().split(" ");
			if(endTime.length==2) {
				vo.setEndDate(endTime[0]);
				vo.setEndTime(endTime[1]);
			}
		}
		return vo;
	}
}

