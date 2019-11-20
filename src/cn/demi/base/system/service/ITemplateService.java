package cn.demi.base.system.service;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.TemplateVo;
/**
 * <strong>Create on : 2016年11月2日 下午3:28:03 </strong> <br>
 * <strong>Description : 模板service </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface ITemplateService extends IBaseService<TemplateVo> {
	/**
	 * <strong>Create on : Carson Liu 2016年11月17日 下午3:01:47 </strong> <br>
	 * <strong>Description : 仅仅修改模板文件</strong> <br>
	 * @param id 记录ID
	 * @throws GlobalException
	 */
	public void updateFile(String id) throws GlobalException;
}
