package cn.demi.im.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.im.vo.GroupVo;
/**
 * Create on : 2016年12月15日 下午8:52:13  <br>
 * Description :  即时通讯群组service<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Transactional
public interface IGroupService extends IBaseService<GroupVo> {
	
}
