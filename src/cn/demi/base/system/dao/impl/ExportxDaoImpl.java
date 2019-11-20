package cn.demi.base.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IExportxDao;
import cn.demi.base.system.po.Exportx;
/**
 * <strong>Create on : 2017年4月20日 下午7:43:27 </strong> <br>
 * <strong>Description : 自定义导出</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
@Repository("sys.exportxDao")
public class ExportxDaoImpl extends BaseDaoImpl<Exportx> implements IExportxDao {

	@Override
	public List<Exportx> listByBusType(String busType) throws GlobalException {
		if (StrUtils.isEmpty(busType))
			return null;
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("busType", QueryCondition.EQ,
				busType));
		return super.list(queryList, null);
	}
}
