package cn.demi.res.vo;

import cn.core.framework.common.vo.Vo;

public class TrainDetailVo extends Vo<TrainDetailVo> {
	
	private String trainId;
	/**
	 * 被培训人
	 */
	private String userId;
	private String userName;
	/**
	 * 培训结果
	 */
	private String result;
	/**
	 * 培训时间
	 */
	private String trainDate;
	/**
	 * 备注
	 */
	private String remark;
	public String getTrainId() {
		return trainId;
	}
	public void setTrainId(String trainId) {
		this.trainId = trainId;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTrainDate() {
		return trainDate;
	}
	public void setTrainDate(String trainDate) {
		this.trainDate = trainDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

