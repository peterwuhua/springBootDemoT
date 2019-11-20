package cn.demi.base.system.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.base.system.po.Permission;
/**
 * Create on : 2016年11月22日 下午3:04:04  <br>
 * Description : 权限  IPermissionDao<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public interface IPermissionDao extends IBaseDao<Permission> {
	/**
	 * Create on : Paddy Zhang 2016年12月30日 下午4:25:23 <br>
	 * Description :  通过权限编码查找权限<br>
	 * @param code 权限编码
	 * @return 
	 */
	public Permission findByCode(String code);

}

