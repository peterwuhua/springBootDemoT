package cn.demi.doc.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.doc.vo.CategoryVo;
import cn.demi.doc.vo.RoleDocumentVo;
/**
 * Create on : 2016年11月24日 下午4:47:04  <br>
 * Description : 文件夹赋权接口 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Transactional
public interface IDirService extends IBaseService<CategoryVo> {
	
	/**
	 * Create on : Dave Yu 2016年11月21日 下午5:33:58 <br>
	 * Description : 按角色赋权方法  <br>
	 * @param RoleDocumentVo
	 * @throws GlobalException
	 */
	public void save(RoleDocumentVo v) throws GlobalException;
}
