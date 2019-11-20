package cn.demi.base.system.service;

import java.util.List;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.CodeVo;
/**
 * <strong>Create on : 2016年11月2日 下午3:20:01 </strong> <br>
 * <strong>Description : 公共代码service </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface ICodeService extends IBaseService<CodeVo> {
	public CodeVo findByCode(String code) throws GlobalException;
	/**
	 * 获取类型下的详细子集
	 * @param code 类型
	 * @return
	 * @throws GlobalException
	 */
	public List<String> listByCode(String code) throws GlobalException;
	/**
	 * Description : 根据code模糊查询，codeVoList  <br>
	 * @param code
	 * @return
	 * @throws GlobalException
	 */
	public List<CodeVo> listCodeVoByCode(String code) throws GlobalException;
}
