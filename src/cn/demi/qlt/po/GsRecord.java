package cn.demi.qlt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 内审记录
 * @author QuJunLong
 *
 */
@Entity(name = "qlt_gs_record")
@Table(name = "qlt_gs_record")
@Module(value = "qlt.gsRecord")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GsRecord extends Po<GsRecord>{

	private static final long serialVersionUID = -7844988463000815126L;
	public String[] PROPERTY_TO_MAP= {"id","sort","gs","userName","userName","date","content","problem","result","measures","checks"};
	private Gs gs;//计划
	
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
	
	@ManyToOne
	@JoinColumn(name = "gs_id")
	public Gs getGs() {
		return gs;
	}
	public void setGs(Gs gs) {
		this.gs = gs;
	}
	@Column(length=320)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length=320)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=20)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Column(length=256)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length=256)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length=128)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length=64)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(length=320)
	public String getYzId() {
		return yzId;
	}
	public void setYzId(String yzId) {
		this.yzId = yzId;
	}
	@Column(length=320)
	public String getYzName() {
		return yzName;
	}
	public void setYzName(String yzName) {
		this.yzName = yzName;
	}
	@Column(length=20)
	public String getYzDate() {
		return yzDate;
	}
	public void setYzDate(String yzDate) {
		this.yzDate = yzDate;
	}
	@Column(length=128)
	public String getMeasures() {
		return measures;
	}
	public void setMeasures(String measures) {
		this.measures = measures;
	}
	@Column(length=128)
	public String getChecks() {
		return checks;
	}
	public void setChecks(String checks) {
		this.checks = checks;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(GsRecord.class, true, ActionType.JSP);
	}
}
