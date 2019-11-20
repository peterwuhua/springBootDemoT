package cn.demi.base.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.demi.base.system.dao.ICfgDao;
import cn.demi.base.system.po.Cfg;

@Repository("sys.cfgDao")
public class CfgDaoImpl extends BaseDaoImpl<Cfg> implements ICfgDao {

	@Override
	public Cfg find(String userId, String type) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ, Po.N));
		queryList.add(new QueryCondition("userId", QueryCondition.EQ,userId));
		queryList.add(new QueryCondition("type", QueryCondition.EQ,type));
		List<Cfg> list = super.list(queryList);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public Cfg find(String type) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ, Po.N));
		queryList.add(new QueryCondition("type", QueryCondition.EQ,type));
		List<Cfg> list = super.list(queryList);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
}
