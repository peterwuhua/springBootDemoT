package cn.demi.bus.file.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.bus.pg.po.Progress;

/**
 * Description :档案 表 <br>
 * @version v 1.0.0 <br>
 */
@Entity(name = "bus_archive")
@Table(name = "bus_archive")
@Module(value = "bus.archive")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Archive extends Po<Archive> {

	public static final String STATUS_YBC="已保存";
	public static final String STATUS_YTJ="已提交";
	public static final String STATUS_SPTG="通过";
	public static final String STATUS_SPBTG="不通过";
	public static final String STATUS_WQR="未确认";
	public static final String STATUS_YQR="已确认";
	public static final String STATUS_WCY="未查阅";
	public static final String STATUS_YCY="已查阅";
	
	
	public static final String TY_BG="报告文件";
	public static final String TY_XY="合同协议";
	public static final String TY_PS="评审记录";
	public static final String TY_CY="采样单";
	public static final String TY_JH="采样计划";
	public static final String TY_JJ="样品交接单";
	public static final String TY_TK="踏勘文件";
	public static final String TY_YJ="原始记录单";
	public static final String TY_FJ="附件资料";
	
	/**
	 * 项目管理
	 */
	public static final String TY_ZL="资料文件";
	
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = { "id", "sort", "code","time", "title", "num", "describtion", "archiveType","userName","viewer","viewerId","viewCt","auditId","auditName","auditTime","auditCt","confirmId","confirm","confirmCt","status","progress","orgId","orgName","deptId","deptName","confirmTime","viewTime"};
	public String[] IGNORE_PROPERTY_TO_PO = {  "progress" };
	/**
	 * 任务编号
	 * 档案编号
	 */
	private String code;
	/**
	 * 档案标题
	 */
	private String title;
	/**
	 * 归档时间
	 */
	private String time;
	private String userId;//归档人
	private String userName;
	/**
	 * 档案数量
	 */
	private int num;
	/**
	 * 文档说明
	 */
	private String describtion;
	/**
	 * 文档所属类型
	 */
	private ArchiveType archiveType;
	/**
	 * 节点路径
	 */
	private String path;
	
	
	private String auditId;//审批人id
	private String auditName;//审批人
	private String auditTime;//审批时间
	private String auditCt;//审批意见
    private String confirmId;//确认人id	
    private String confirm;//确认人	
    private String confirmCt;//确认意见
    private String confirmTime;//确认时间
	private String viewer;//查阅人
	private String viewerId;//查阅人id
	private String viewCt;//查阅情况
	private String viewTime;//查阅时间
	private String status;//状态
	
	private Progress progress;
	
	
	
	private String orgId;
	private String orgName;//子公司
	private String deptId;
	private String deptName;//部门
	
	
	
	@Column(length=64)
	public String getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	@Column(length=64)
	public String getViewTime() {
		return viewTime;
	}

	public void setViewTime(String viewTime) {
		this.viewTime = viewTime;
	}

	@ManyToOne
	@JoinColumn(name = "progress_id")
	public Progress getProgress() {
		return progress;
	}

	public void setProgress(Progress progress) {
		this.progress = progress;
	}
	@Column(length=64)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length=128)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length=64)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(length=128)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(length=64)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(length=64)
	public String getConfirmId() {
		return confirmId;
	}

	public void setConfirmId(String confirmId) {
		this.confirmId = confirmId;
	}
	@Column(length=64)
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	@Column(length=128)
	public String getConfirmCt() {
		return confirmCt;
	}

	public void setConfirmCt(String confirmCt) {
		this.confirmCt = confirmCt;
	}
	@Column(length=128)
	public String getViewCt() {
		return viewCt;
	}

	public void setViewCt(String viewCt) {
		this.viewCt = viewCt;
	}

	@Column(length=64)
	public String getViewer() {
		return viewer;
	}

	public void setViewer(String viewer) {
		this.viewer = viewer;
	}
	@Column(length=64)
	public String getViewerId() {
		return viewerId;
	}

	public void setViewerId(String viewerId) {
		this.viewerId = viewerId;
	}
	@Column(length=64)
	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	@Column(length=64)
	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	@Column(length=64)
	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	@Column(length=128)
	public String getAuditCt() {
		return auditCt;
	}

	public void setAuditCt(String auditCt) {
		this.auditCt = auditCt;
	}

	@ManyToOne(targetEntity = ArchiveType.class)
	@JoinColumn(name = "archive_type_id")
	public ArchiveType getArchiveType() {
		return archiveType;
	}

	public void setArchiveType(ArchiveType archiveType) {
		this.archiveType = archiveType;
	}
	@Column(length=64)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Column(length=128)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(length=256)
	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	@Column(length=20)
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	@Column(length=512)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	@Column(length=32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length=32)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

}
