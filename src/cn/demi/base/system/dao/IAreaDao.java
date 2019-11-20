package cn.demi.base.system.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.po.Area;
/**
 * <strong>Create on : 2016年11月2日 下午2:46:52 </strong> <br>
 * <strong>Description : 城市编码库DAO </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IAreaDao extends IBaseDao<Area> {
	public Area findByCode(String code) throws GlobalException;

	/**
	 * Create on : Paddy Zhang 2016年12月26日 上午10:39:52 <br>
	 * Description : 修改path <br>
	 * @param oldPath 原path
	 * @param newPath 新path
	 * @throws GlobalException
	 */
	public void updatePath(String oldPath, String newPath) throws GlobalException;
}
