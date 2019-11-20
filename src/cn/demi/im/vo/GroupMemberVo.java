package cn.demi.im.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.AccountVo;
/**
 * Create on : 2016年12月15日 下午8:53:11  <br>
 * Description : 即时通讯群组列表Vo <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public class GroupMemberVo extends Vo<GroupMemberVo> {
	 /**
	  * 群组
	  */
	private GroupVo groupVo;
	/**
	 * 群成员
	 */
	private AccountVo memberVo;
	public GroupVo getGroupVo() {
		return groupVo;
	}
	public void setGroupVo(GroupVo groupVo) {
		this.groupVo = groupVo;
	}
	public AccountVo getMemberVo() {
		return memberVo;
	}
	public void setMemberVo(AccountVo memberVo) {
		this.memberVo = memberVo;
	}
	
}

