package cn.demi.base.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.dao.IFunctionDao;
import cn.demi.base.system.po.Function;
/**
 * <strong>Create on : 2016年11月2日 下午5:06:29 </strong> <br>
 * <strong>Description : FunctionDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.functionDao")
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements
		IFunctionDao {
	
	@Override
	public void updatePath(String oldPath, String newPath) throws GlobalException{
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ, Po.N));
		queryList.add(new QueryCondition("path", QueryCondition.RK,oldPath));
		List<Function> listFunction = super.list(queryList);
		for (Function po : listFunction) {
			po.setPath(po.getPath().replace(oldPath,newPath));
			super.update(po);
		}
	}
}
