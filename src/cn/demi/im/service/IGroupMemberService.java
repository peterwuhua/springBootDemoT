package cn.demi.im.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.im.vo.GroupMemberVo;
/**
 * Create on : 2016年12月15日 下午8:51:37  <br>
 * Description : 即时通讯群组列表service <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Transactional
public interface IGroupMemberService extends IBaseService<GroupMemberVo> {
	
	/**
	 * 获取组成员
	 * @param v
	 * @return
	 */
	public Map<String, Object> listMember(GroupMemberVo v) throws GlobalException;
}
