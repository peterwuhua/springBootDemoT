package cn.demi.base.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.dao.IAreaDao;
import cn.demi.base.system.po.Area;
/**
 * <strong>Create on : 2016年11月2日 下午5:05:58 </strong> <br>
 * <strong>Description : AreaDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.areaDao")
public class AreaDaoImpl extends BaseDaoImpl<Area> implements IAreaDao {

	@Override
	public Area findByCode(String code) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ, Po.N));
		queryList.add(new QueryCondition("code", QueryCondition.EQ, code));
		return super.query0(queryList, null);
	}
	
	@Override
	public void updatePath(String oldPath, String newPath) throws GlobalException{
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ, Po.N));
		queryList.add(new QueryCondition("path", QueryCondition.RK,oldPath));
		List<Area> listArea = super.list(queryList);
		for (Area po : listArea) {
			po.setPath(po.getPath().replace(oldPath,newPath));
			super.update(po);
		}
	}
}
