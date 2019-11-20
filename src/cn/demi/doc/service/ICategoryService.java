package cn.demi.doc.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.doc.vo.CategoryVo;
/**
 * Create on : 2016年11月24日 下午4:46:39  <br>
 * Description : 文件夹接口 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Transactional
public interface ICategoryService extends IBaseService<CategoryVo> {
	/**
	 * Create on : Dave Yu 2017年1月6日 下午5:13:46 <br>
	 * Description : 查询文件名是否存在 <br>
	 * @param name
	 * @return boolean
	 * @throws GlobalException
	 */
	public boolean isExistName(String name,String id) throws GlobalException;
	
}
