package cn.demi.bus.pjt.po;

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
 *  写实调查表
 * @author QuJunLong
 *
 */
@Entity(name = "bus_cust_work")
@Table(name = "bus_cust_work")
@Module(value = "bus.custWork")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustWork extends Po<CustWork>{

	private static final long serialVersionUID = 3154118724049666517L;
	public String[] PROPERTY_TO_MAP = { "id", "isDel","sort"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private Cust cust;	//客户信息
	private CustRoom room;//岗位
	private String projectId;//项目
	private CustPoint point;//采样点
	private String startTime;//工作开始时间
	private String endTime;//工作 结束时间
	private String workNum;//接触时间
	private String itemIds;//检测项目
	private String itemNames;
	private String works;//工作内容
	private String user;//姓名
	private String duty;//岗位
	private String age;//工龄
	private String remark;//备注
	
	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Cust getCust() {
		return cust;
	}
	public void setCust(Cust cust) {
		this.cust = cust;
	}
	@ManyToOne
	@JoinColumn(name = "room_id")
	public CustRoom getRoom() {
		return room;
	}
	public void setRoom(CustRoom room) {
		this.room = room;
	}
	@Column(length =32)
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@ManyToOne
	@JoinColumn(name = "point_id")
	public CustPoint getPoint() {
		return point;
	}
	public void setPoint(CustPoint point) {
		this.point = point;
	}
	@Column(length =20)
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(length =20)
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getWorkNum() {
		return workNum;
	}
	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}
	@Column(length =1000)
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	@Column(length =1000)
	public String getItemNames() {
		return itemNames;
	}
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length =64)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@Column(length =64)
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	@Column(length =128)
	public String getWorks() {
		return works;
	}
	public void setWorks(String works) {
		this.works = works;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(CustWork.class, true, ActionType.JSP);
	}
}
