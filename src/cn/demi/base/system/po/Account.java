package cn.demi.base.system.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
import cn.core.framework.utils.current.CurrentUtils;

@Entity(name = "sys_account")
@Table(name = "sys_account")
@Module(value = "sys.account")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Account extends Po<Account> {
	private static final long serialVersionUID = 1L;
	public static final String onlien = "online";
	public static final String hide = "offline";
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","user","org","loginName","signature","dep","sort","isUse","ip"};
	/**
	 * 用户信息Id
	 */
	private User user;
	/**
	 * 账户所属部门
	 */
	private Org org;
	/**
	 * 登陆名称
	 */
	private String loginName;
	/**
	 * 登陆的密码
	 */
	private String password;
	/**
	 * 签章图片存放路径
	 */
	private String signature;
	/**
	 * 系统配置 部门
	 */
	private String dep;
	/**
	 * 在线状态
	 */
	private String status=hide;
	/**
	 *即时通讯个性签名
	 */
	private String signText;
	/**
	 *账户是否启用
	 */
	private String isUse=String.valueOf(Y);
	/**
	 * 角色Id
	 */
	private String roleIds;
	/**
	 * ip
	 */
	private String ip;
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	@ManyToOne
	@JoinColumn(name = "org_id")
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}
	
	@Column(length=64)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length=64)
	public String getSignText() {
		return signText;
	}

	public void setSignText(String signText) {
		this.signText = signText;
	}
	
	@Column(length = 8,nullable=false)
	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	
	@Lob
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	@Column(length=64)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	@Transient
	public void onUpdate() {
		if(StrUtils.isBlankOrNull(this.getLastUpdUser())){
			this.setLastUpdUser(CurrentUtils.getCurrent().getKey());
		}
		this.setLastUpdTime(System.currentTimeMillis());
	}

	@Transient
	public String getKey() {
		return user.getName() + "【" + loginName + "】";
	}
	
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Account.class, false, ActionType.JSP);
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
}
