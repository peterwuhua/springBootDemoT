package cn.demi.doc.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.doc.vo.RoleDocumentVo;
/**
 * Create on : 2016年11月24日 下午4:48:08  <br>
 * Description : 按文件赋权接口 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Transactional
public interface IRoleDocumentService extends IBaseService<RoleDocumentVo> {

	/**
	 * Create on : Dave Yu 2016年11月21日 下午10:33:49 <br>
	 * Description : 根据复选框ids查授权库对象 <br>
	 * @param categoryIds
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleDocumentVo> listByCategoryIds(String... categoryIds) throws GlobalException;
	
	/**
	 * Create on : Dave Yu 2016年11月21日 下午10:33:49 <br>
	 * Description : 根据复选框ids查授权库对象 <br>
	 * @param documentIds
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleDocumentVo> listByDocumentIds(String... documentIds) throws GlobalException;
	
	
	/**
	 * Create on : Dave Yu 2016年11月21日 下午10:33:49 <br>
	 * Description : 根据复选框ids查授权库对象 <br>
	 * @param roleIds
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleDocumentVo> listByRoleIds(String... roleIds) throws GlobalException;
	
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
	 * Create on : Dave Yu 2016年12月14日 上午9:26:18 <br>
	 * Description : 通过属性获取group by下的list <br>
	 * @param pageType
	 * @return List
	 * @throws GlobalException
	 */
	public List<String> selectByPageType(String pageType) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年12月28日 下午4:45:38 <br>
	 * Description : 通过文件夹Id查询授权文件夹信息 <br>
	 * @param id
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleDocumentVo> listByPerDirId(String id) throws GlobalException;
	
}
