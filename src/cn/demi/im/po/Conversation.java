package cn.demi.im.po;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
import cn.demi.base.system.po.Account;
/**
 * Create on : 2016年12月15日 下午8:47:51  <br>
 * Description :  即时通讯会话Po<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Entity(name = "im_conversation")
@Table(name = "im_conversation")
@Module(value = "im.conversation")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Conversation extends Po<Conversation> {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","sender","receive","group"};
	/**
	 * 发送人
	 */
	private Account sender;
	/**
	 * 接收人
	 */
	private Account receive;

	/**
	 * 群组
	 */
	private Group group;
	
	@ManyToOne
	@JoinColumn(name = "sender_id")
	public Account getSender() {
		return sender;
	}
	public void setSender(Account sender) {
		this.sender = sender;
	}
	@ManyToOne
	@JoinColumn(name = "receive_id")
	public Account getReceive() {
		return receive;
	}
	public void setReceive(Account receive) {
		this.receive = receive;
	}
	@ManyToOne
	@JoinColumn(name = "group_id")
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
	@Override
	@Transient
	public void onAdd() {
		if(StrUtils.isBlankOrNull(this.getCreateUser())){
			this.setCreateUser(CurrentUtils.getCurrent().getKey());
		}
		if(StrUtils.isBlank(this.getLastUpdUser())){
			this.setLastUpdUser(CurrentUtils.getCurrent().getKey());
		}
		this.setCreateTime(System.currentTimeMillis());
		this.setLastUpdTime(System.currentTimeMillis());
	}
	
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Conversation.class, false, ActionType.JSP);
	}
	
	
}