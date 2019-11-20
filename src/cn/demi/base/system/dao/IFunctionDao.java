package cn.demi.base.system.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.po.Function;
/**
 * <strong>Create on : 2016年11月2日 下午2:48:29 </strong> <br>
 * <strong>Description : 功能DAO </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IFunctionDao extends IBaseDao<Function> {
	/**
	 * Create on : Paddy Zhang 2016年12月26日 上午10:33:19 <br>
	 * Description : 修改path <br>
	 * @param oldPath 原path
	 * @param newPath 新path
	 * @throws GlobalException
	 */
	void updatePath(String oldPath, String newPath) throws GlobalException;

}
