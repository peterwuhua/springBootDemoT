package cn.demi.im.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.demi.im.po.Group;
import cn.demi.im.service.IGroupService;
import cn.demi.im.vo.GroupVo;
/**
 * Create on : 2016年12月15日 下午8:51:03  <br>
 * Description :  即时通讯群组serviceImpl<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Service("im.groupService")
public class GroupServiceImpl extends BaseServiceImpl<GroupVo,Group> implements
		IGroupService {
}
