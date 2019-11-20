package cn.demi.base.system.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.po.Exportx;
/**
 * <strong>Create on : 2017年4月20日 下午7:43:05 </strong> <br>
 * <strong>Description : 自定义导出</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public interface IExportxDao extends IBaseDao<Exportx>{
	/**
	 * <strong>Create on : Carson Liu 2017年4月21日 上午8:23:30 </strong> <br>
	 * <strong>Description : 获取当前页面的自定义模板</strong> <br>
	 * @param busType
	 * @return
	 * @throws GlobalException
	 */
	public List<Exportx> listByBusType(String busType) throws GlobalException;
}
