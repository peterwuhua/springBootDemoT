package cn.demi.base.system.service;

import java.util.List;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.ExportxVo;
/**
 * <strong>Create on : 2017年4月20日 下午7:44:09 </strong> <br>
 * <strong>Description : 自定义导出</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public interface IExportxService extends IBaseService<ExportxVo> {
	/**
	 * <strong>Create on : Carson Liu 2017年4月21日 上午8:23:30 </strong> <br>
	 * <strong>Description : 获取当前页面的自定义模板</strong> <br>
	 * @param busType
	 * @return
	 * @throws GlobalException
	 */
	public List<ExportxVo> list(String busType) throws GlobalException;
}
