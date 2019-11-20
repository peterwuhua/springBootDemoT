package cn.demi.base.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.dao.IExportxDao;
import cn.demi.base.system.po.Exportx;
import cn.demi.base.system.service.IExportxService;
import cn.demi.base.system.vo.ExportxVo;
/**
 * <strong>Create on : 2017年4月20日 下午7:44:32 </strong> <br>
 * <strong>Description : 自定义导出</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
@Service("sys.exportxService")
public class ExportxServiceImpl extends BaseServiceImpl<ExportxVo, Exportx> implements
		IExportxService {
	@Autowired IExportxDao exportxDao;
	@Override
	public List<ExportxVo> list(String busType) throws GlobalException {
		return toVoList(exportxDao.listByBusType(busType));
	}
}
