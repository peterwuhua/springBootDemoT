package cn.demi.doc.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.doc.po.RoleDocument;

public interface IRoleDocumentDao extends IBaseDao<RoleDocument> {
	/**
	 * Create on : Dave Yu 2016年11月22日 上午9:31:00 <br>
	 * Description : 取已授权的角色Ids <br>
	 * @param roleId
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleDocument> listByRoleIds(String... roleId) throws GlobalException;
	
	/**
	 * Create on : Dave Yu 2016年11月22日 上午9:31:00 <br>
	 * Description : 取已授权的角色Ids <br>
	 * @param categoryIds
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleDocument> listByCategoryIds(String... categoryIds) throws GlobalException;
	
	/**
	 * Create on : Dave Yu 2016年11月23日 下午3:50:00 <br>
	 * Description : 返回所有文件夹id相同的文件和文件夹集合 <br>
	 * @param categoryIds
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleDocument> listRoleDocument(String categoryIds) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年11月22日 上午9:32:04 <br>
	 * Description : 取已授权的文件Ids <br>
	 * @param documentIds
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleDocument> listByDocumentIds(String... documentIds) throws GlobalException;
	
	/**
	 * Create on : Dave Yu 2016年11月22日 下午8:07:42 <br>
	 * Description :  具备当前文件的角色列表<br>
	 * @param documentIds
	 * @return
	 * @throws GlobalException
	 */
	public String getRoleIdsByFileId(String... documentIds) throws GlobalException;
	
	
	/**
	 * Create on : Dave Yu 2016年11月22日 下午8:07:42 <br>
	 * Description :  具备当前文件夹的角色列表<br>
	 * @param documentIds
	 * @return
	 * @throws GlobalException
	 */
	public String getRoleIdsByDirId(String... dirIds) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年11月23日 下午6:32:28 <br>
	 * Description :  <br>
	 * @param prePath
	 * @param path
	 * @throws GlobalException
	 */
	public void updateRoleDocumentPath(String prePath, String path) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年11月24日 下午4:44:17 <br>
	 * Description : 得到子路径下的集合 <br>
	 * @param path
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleDocument> listChildByPath(String path) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年11月29日 下午2:36:49 <br>
	 * Description : 根据文件id和角色id查询已授权数据 <br>
	 * @param id 文件id
	 * @param roleIds 角色id
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleDocument> listRoleDocument(String id,String roleIds) throws GlobalException;
	
	/**
	 * Create on : Dave Yu 2016年12月14日 上午9:27:53 <br>
	 * Description : 通过属性获取group by下的list <br>
	 * @param pageType
	 * @return List
	 * @throws GlobalException
	 */
	public List<String> selectByPageType(String pageType) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年12月28日 下午4:47:34 <br>
	 * Description : 通过文件夹Id查询授权文件夹信息 <br>
	 * @param id
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleDocument> listByPerDirId(String id) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年12月28日 下午5:00:57 <br>
	 * Description : 通过文件夹id和角色id查询授权文件信息 <br>
	 * @param pid
	 * @param roleId
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleDocument> listByRoleIdsAndPid(String pid,String roleId) throws GlobalException;
}

