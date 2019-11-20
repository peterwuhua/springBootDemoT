package cn.demi.base.system.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.po.Code;
/**
 * <strong>Create on : 2016年11月2日 下午2:47:43 </strong> <br>
 * <strong>Description : 公共代码DAO </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface ICodeDao extends IBaseDao<Code>{
	public Code findByCode(String code) throws GlobalException;
	

}
