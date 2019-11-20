package cn.demi.base.system.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.PermissionVo;

@Transactional
public interface IPermissionService extends IBaseService<PermissionVo> {
	/**
	 * Create on : Paddy Zhang 2016年12月30日 下午4:21:45 <br>
	 * Description :  通过code查找权限<br>
	 * @param string
	 * @return
	 * @throws GlobalException 
	 */
	public PermissionVo findByCode(String string) throws GlobalException;
}
