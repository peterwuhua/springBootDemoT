package cn.demi.qlt.vo;

import cn.core.framework.common.vo.Vo;

public class GsRecordVo extends Vo<GsRecordVo> {
	private GsVo gsVo;//计划
	private String userId;//记录人
	private String userName;//记录人
	private String date;//记录日期
	private String content;//评审内容
	private String problem;//评审中发现的问题
	private String result;//结论
	private String measures;//实施纠正/改进的措施
	private String checks;//对纠正/改进措施的检查
	private String remark;
	private String yzId; //验证人
	private String yzName; 
	private String yzDate;//验证日期
	private String fileName;//报告名称及路径
	private String filePath;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMeasures() {
		return measures;
	}
	public void setMeasures(String measures) {
		this.measures = measures;
	}
	public String getChecks() {
		return checks;
	}
	public void setChecks(String checks) {
		this.checks = checks;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getYzId() {
		return yzId;
	}
	public void setYzId(String yzId) {
		this.yzId = yzId;
	}
	public String getYzName() {
		return yzName;
	}
	public void setYzName(String yzName) {
		this.yzName = yzName;
	}
	public String getYzDate() {
		return yzDate;
	}
	public void setYzDate(String yzDate) {
		this.yzDate = yzDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public GsVo getGsVo() {
		return gsVo;
	}
	public void setGsVo(GsVo gsVo) {
		this.gsVo = gsVo;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
}

