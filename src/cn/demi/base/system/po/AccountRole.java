package cn.demi.base.system.po;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name = "sys_account_role")
@Table(name = "sys_account_role")
@Module(value = "sys.account_role")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountRole extends Po<AccountRole> {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","account","role","sort"};
	/**
	 * account
	 */
	private Account account;
	/**
	 * role
	 */
	private Role role;

	@ManyToOne
	@JoinColumn(name = "account_id")
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@ManyToOne
	@JoinColumn(name = "role_id")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
}
