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
import cn.demi.res.po.Supplier;
/**
 * 方案 分包单位
 * @author QuJunLong
 *
 */
@Entity(name = "bus_project_fb")
@Table(name = "bus_project_fb")
@Module(value = "bus.projectFb")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectFb extends Po<ProjectFb>{
 
	private static final long serialVersionUID = -2612430647741674348L;
	public String[] PROPERTY_TO_MAP = {"id","sort","isDel","project","fb","fbUser","fbMobile","num","itemNames","price"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private Project project;
	private Supplier fb;//分包商
	private String fbUser;//联系人
	private String fbMobile;//联系电话
	private int num;//分包数量
	private String itemIds;//分包项目
	private String itemNames;//分包项目
	private float price;//分包费用
	private String remark;//备注
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@ManyToOne
	@JoinColumn(name = "fb_id")
	public Supplier getFb() {
		return fb;
	}
	public void setFb(Supplier fb) {
		this.fb = fb;
	}
	@Column(length = 32)
	public String getFbUser() {
		return fbUser;
	}
	public void setFbUser(String fbUser) {
		this.fbUser = fbUser;
	}
	@Column(length = 32)
	public String getFbMobile() {
		return fbMobile;
	}
	public void setFbMobile(String fbMobile) {
		this.fbMobile = fbMobile;
	}
	@Column(length = 16)
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Column(length = 320)
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	@Column(length = 320)
	public String getItemNames() {
		return itemNames;
	}
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	@Column(length = 16)
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ProjectFb.class, true, ActionType.JSP);
	}
}
