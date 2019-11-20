package cn.demi.im.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.im.dao.IGroupMemberDao;
import cn.demi.im.po.GroupMember;
/**
 * Create on : 2016年12月13日 下午5:28:21  <br>
 * Description :  群组列表Dao<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Repository("im.group.memberDao")
public class GroupMemberDaoImpl extends BaseDaoImpl<GroupMember> implements IGroupMemberDao {
}
