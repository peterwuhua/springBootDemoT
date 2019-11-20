package cn.demi.zk.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.constant.EunmZkTask;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 质控 任务信息
 * @author QuJunLong
 */
@Entity(name = "zk_task")
@Table(name = "zk_task")
@Module(value = "zk.task")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ZkTask extends Po<ZkTask>{
	private static final long serialVersionUID = 1L;
	
	public static final int STOP=-1;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","no","isDel","year","month","acceptName","progressLog","title","sampNum","finishDate","acceptDate","auditName","auditDate","isBack","objNames","itemNames"};
	public String[] IGNORE_PROPERTY_TO_PO = {"id","createTime","lastUpdTime","version","orgId","no","progress"};
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
	/**
	 * 任务编号
	 */
	private String no;
	/**
	 * 年
	 */
	private String year;
	/**
	 *月
	 */
	private String month;
	/**
	 * 检验性质
	 */
	private String title;
	
	/**
	 * 检测对象数量
	 */
	private String sampNum;
	       
	/**
	 *要求完成时间
	 */
	private String finishDate;
	/**
	 *考核对象
	 */
	private String objIds;
	private String objNames;
	/**
	 *实验室测试项目
	 */
	private String itemIds;
	/**
	 *实验室测试项目
	 */
	private String itemNames;
	/**
	 * 编制人
	 */
	private String acceptId;
	private String acceptName;
	/**
	 * 受理日期
	 */
	private String acceptDate;
	/**
	 * 审核人
	 */
	private String auditId;
	private String auditName;
	/**
	 * 审核日期
	 */
	private String auditDate;
	/**
	 * 编制人部门
	 */
	private String orgId;
	/**
	 * 其它说明
	 */
	private String remark;
    private int isBack;
	
    
    /**
     * 考核结果评价
     */
    private String result;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 状态
	 */
	private String statusName;
	/**
	 * 当前查询数据对于的日志记录
	 * map时用，不存入数据库
	 */
	private ZkProgressLog progressLog;
	
	@Column(length = 32)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	  
	@Column(length = 8)
	public String getSampNum() {
		return sampNum;
	}
	public void setSampNum(String sampNum) {
		this.sampNum = sampNum;
	}
	@Column(length = 20)
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Transient
	public ZkProgressLog getProgressLog() {
		return progressLog;
	}
	public void setProgressLog(ZkProgressLog progressLog) {
		this.progressLog = progressLog;
	}
	public String getAcceptDate() {
		return acceptDate;
	}
	@Column(length = 20)
	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}
	@Column(length = 32)
	public String getAcceptId() {
		return acceptId;
	}
	public void setAcceptId(String acceptId) {
		this.acceptId = acceptId;
	}
	@Column(length = 32)
	public String getAcceptName() {
		return acceptName;
	}
	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}
	@Column(length = 8)
	public int getIsBack() {
		return isBack;
	}
	public void setIsBack(int isBack) {
		this.isBack = isBack;
	}
	@Column(length =64)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(length = 320)
	public String getObjIds() {
		return objIds;
	}
	public void setObjIds(String objIds) {
		this.objIds = objIds;
	}
	@Column(length = 320)
	public String getObjNames() {
		return objNames;
	}
	public void setObjNames(String objNames) {
		this.objNames = objNames;
	}
	@Column(length = 32)
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	@Column(length = 32)
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	@Column(length = 20)
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	@Column(length = 4)
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	@Column(length = 2)
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@Column(length =128)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Column(length =32)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ZkTask.class, true, ActionType.JSP);
	}
	@Transient
	public String getStatusName() {
		if(null!=status) {
			statusName=EunmZkTask.getName(status);
		}
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	@Column(length =320)
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	@Column(length =320)
	public String getItemNames() {
		return itemNames;
	}
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	
	
}
