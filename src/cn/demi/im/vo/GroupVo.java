package cn.demi.im.vo;

import cn.core.framework.common.vo.Vo;
/**
 * Create on : 2016年12月15日 下午8:53:41  <br>
 * Description :  即时通讯群组Vo<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public class GroupVo extends Vo<GroupVo> {
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
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	
}

