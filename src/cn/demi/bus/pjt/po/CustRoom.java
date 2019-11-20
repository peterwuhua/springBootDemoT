package cn.demi.bus.pjt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 岗位 车间
 * @author QuJunLong
 *
 */
@Entity(name = "bus_cust_room")
@Table(name = "bus_cust_room")
@Module(value = "bus.custRoom")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustRoom extends Po<CustRoom> {
	
	private static final long serialVersionUID = -7926706962638263761L;
	public String[] PROPERTY_TO_MAP = { "id", "isDel","sort"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private Cust cust;//客户信息
	private String projectId;//项目
	private String name;//岗位名称
	private int num;//岗位总人数
	private String workType;//工作制度
	private String works;//工作内容
	private String workWay;//工作方式
	
	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Cust getCust() {
		return cust;
	}
	public void setCust(Cust cust) {
		this.cust = cust;
	}
	@Column(length =32)
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Column(length =64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Column(length =32)
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	@Column(length =256)
	public String getWorks() {
		return works;
	}
	public void setWorks(String works) {
		this.works = works;
	}
	@Column(length =64)
	public String getWorkWay() {
		return workWay;
	}
	public void setWorkWay(String workWay) {
		this.workWay = workWay;
	}
	
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(CustRoom.class, true, ActionType.JSP);
	}
	
}
