package cn.demi.base.system.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.po.Org;
/**
 * <strong>Create on : 2016年11月2日 下午2:49:30 </strong> <br>
 * <strong>Description : 组织DAO </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IOrgDao extends IBaseDao<Org> {
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午5:44:03 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param code code
	 * @return Org
	 * @throws GlobalException
	 */
	public Org findByName(String name) throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午5:44:03 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param code code
	 * @return Org
	 * @throws GlobalException
	 */
	public Org findByCode(String code) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2016年12月26日 上午10:12:07 <br>
	 * Description :  修改path<br>
	 * @param oldPath
	 * @param newPath
	 * @throws GlobalException 
	 */
	public void updatePath(String oldPath, String newPath) throws GlobalException;
	/**
	 * 获取子集id集合
	 * @param pid
	 * @return
	 * @throws GlobalException
	 */
	public List<String> listChild(String pid) throws GlobalException;
	/**
	 * 获取一级部门
	 * @param code
	 * @return
	 * @throws GlobalException
	 */
	public Org findOrg(String orgId) throws GlobalException;
}
