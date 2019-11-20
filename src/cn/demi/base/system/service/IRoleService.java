package cn.demi.base.system.service;

import java.util.List;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.RoleVo;

/**
 * <strong>Create on : 2016年11月2日 下午3:27:06 </strong> <br>
 * <strong>Description : 角色service </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IRoleService extends IBaseService<RoleVo> {
	/**
	 * Create on : Paddy Zhang 2017年5月22日 下午2:38:05 <br>
	 * Description : 非超级管理员不能给赋权超级管理员和管理员角色  <br>
	 * @return
	 * @throws GlobalException
	 */
	public List<RoleVo> list4Login() throws GlobalException;


	
}
