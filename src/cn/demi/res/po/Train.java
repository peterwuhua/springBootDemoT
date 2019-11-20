package cn.demi.res.po;

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
 * 培训考核管理
 * @author QuJunLong
 *
 */
@Entity(name = "res_train")
@Table(name = "res_train")
@Module(value = "res.train")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Train  extends Po<Train>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PX="培训";
	public static final String KH="考核";
	
	public static final String ST_INIT="0";//计划状态
	public static final String ST_RUN="1";//计划执行
	public static final String ST_COMP="2";//计划结束
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","title","orgName","userName","type","post","startDate","endDate","address","status"};
	
	@Transient
	@Override
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
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
	 * 时间
	 */
	private String startDate;
	private String endDate;
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
	@Column(length = 64)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length = 32)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	@Column(length = 16)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 32)
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	@Column(length = 20)
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@Column(length = 20)
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Column(length = 128)
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
	@Lob
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Train.class, false, ActionType.JSP);
	}
}
