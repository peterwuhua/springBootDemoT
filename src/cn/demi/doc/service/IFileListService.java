package cn.demi.doc.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.doc.vo.DocumentVo;
/**
 * Create on : 2016年11月24日 下午4:47:38  <br>
 * Description : 文件一览表接口 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Transactional
public interface IFileListService extends IBaseService<DocumentVo> {
	
	
}
