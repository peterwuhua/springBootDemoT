package cn.demi.res.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class TrainVo extends Vo<TrainVo> {
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 部门
	 */
	private String orgId;
	private String orgName;
	/**
	 * 计划人
	 */
	private String userId;
	private String userName;
	/**
	 * 培训/考核
	 */
	private String type;
	/**
	 *岗位
	 */
	private String post;
	/**
	 * 地点
	 */
	private String address;
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 被培训人
	 */
	private String trainIds;
	/**
	 * 被培训人
	 */
	private String trainNames;
	/**
	 * 状态
	 */
	private String status;	
	
	private String remark;//备注

	private List<TrainDetailVo> detailList;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTrainIds() {
		return trainIds;
	}

	public void setTrainIds(String trainIds) {
		this.trainIds = trainIds;
	}

	public String getTrainNames() {
		return trainNames;
	}

	public void setTrainNames(String trainNames) {
		this.trainNames = trainNames;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<TrainDetailVo> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<TrainDetailVo> detailList) {
		this.detailList = detailList;
	}
	
}

