package cn.demi.doc.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.doc.vo.DocumentVo;
/**
 * Create on : 2016年11月24日 下午4:47:57  <br>
 * Description : 文件赋权接口 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Transactional
public interface IFileService extends IBaseService<DocumentVo> {
	/**
	 * Create on : Dave Yu 2016年11月21日 下午5:33:58 <br>
	 * Description : 按文件赋权方法  <br>
	 * @param RoleDocumentVo
	 * @throws GlobalException
	 */
	@Override
	public void save(DocumentVo v) throws GlobalException;
	
	
}
