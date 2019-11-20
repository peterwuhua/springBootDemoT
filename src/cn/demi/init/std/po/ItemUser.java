package cn.demi.init.std.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
/**
 * 检测人
 * @author QuJunLong
 *
 */
@Entity(name = "init_item_user")
@Table(name = "init_item_user")
@Module(value = "init.itemUser")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemUser extends Po<ItemUser>{

	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","itemId","orgName","userName","userNames","methodNames"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	//项目Id
	private String itemId;
	//部门
	private String orgId;
	private String orgName;
	//默认检测人
	private String userId;
	private String userName;
	//检测人
	private String userIds;
	private String userNames;
	//检测方法
	private String methodIds;
	private String methodNames;
	@Column(length = 32)
	public String getItemId() {
		return itemId;
	}
	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	@Column(length = 32)
	public String getOrgName() {
		return orgName;
	}
	@Column(length = 32)
	public String getUserId() {
		return userId;
	}
	@Column(length = 32)
	public String getUserName() {
		return userName;
	}
	@Column(length = 1000)
	public String getUserIds() {
		return userIds;
	}
	@Column(length = 1000)
	public String getUserNames() {
		return userNames;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	@Column(length = 1000)
	public String getMethodIds() {
		return methodIds;
	}
	@Column(length = 1000)
	public String getMethodNames() {
		return methodNames;
	}
	public void setMethodIds(String methodIds) {
		this.methodIds = methodIds;
	}
	public void setMethodNames(String methodNames) {
		this.methodNames = methodNames;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ItemUser.class, false, ActionType.JSP);
	}
}
