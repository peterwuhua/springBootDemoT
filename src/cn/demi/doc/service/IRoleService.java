package cn.demi.doc.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.doc.vo.RoleDocumentVo;
/**
 * Create on : 2016年11月24日 下午4:48:26  <br>
 * Description : 按角色赋权接口 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Transactional
public interface IRoleService extends IBaseService<RoleDocumentVo> {

	/**
	 * Create on : Dave Yu 2016年11月21日 下午5:33:58 <br>
	 * Description : 按角色赋权方法  <br>
	 * @param RoleDocumentVo
	 * @throws GlobalException
	 */
	@Override
	public void save(RoleDocumentVo v) throws GlobalException;
	
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
	
}
