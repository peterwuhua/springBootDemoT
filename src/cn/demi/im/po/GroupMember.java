package cn.demi.im.po;

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
import cn.demi.base.system.po.Account;
/**
 * Create on : 2016年12月15日 下午8:49:14  <br>
 * Description : 即时通讯群组列表Po <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Entity(name = "im_group_member")
@Table(name = "im_group_member")
@Module(value = "im.group.member")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupMember extends Po<GroupMember> {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","group","member"};
	 /**
	  * 群组
	  */
	private Group group;
	/**
	 * 群成员
	 */
	private Account member;
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	@ManyToOne
	@JoinColumn(name = "member_id")
	public Account getMember() {
		return member;
	}

	public void setMember(Account member) {
		this.member = member;
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
	public static void main(String[] args) {
		
		CreateCodeUtils.autoCreateCode(GroupMember.class, false, ActionType.JSP);
	}
}