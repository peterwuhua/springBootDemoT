package cn.demi.doc.service;

import java.util.List;

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
public interface IFileSelectService extends IBaseService<DocumentVo> {
	/**
	 * Create on : Dave Yu 2016年11月26日 上午11:23:01 <br>
	 * Description : 根据文件查询数据 <br>
	 * @param name
	 * @return List
	 * @throws GlobalException
	 */
	public List<DocumentVo> findByAll(String name) throws GlobalException;
}
