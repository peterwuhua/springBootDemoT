package cn.demi.im.po;

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
 * Create on : 2016年12月15日 下午8:48:47  <br>
 * Description : 即时通讯群组Po <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Entity(name = "im_group")
@Table(name = "im_group")
@Module(value = "im.group")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Group extends Po<Group> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","groupname","avatar","online"};
	/**
	 * 分组名称
	 */
	private String groupname;//群组名称
	/**
	 * 群组头像
	 */
	private String avatar;//群组,存储头像存放路径
	/**
	 * 群列表显示顺序
	 */
	private String online;
	
	
	
	

	@Column(length=64)
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	@Column(length=64)
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Column(length=64)
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
	public static void main(String[] args) {
		
		CreateCodeUtils.autoCreateCode(Group.class, false, ActionType.JSP);
	}
}