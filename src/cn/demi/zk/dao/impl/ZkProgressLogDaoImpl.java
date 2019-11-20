package cn.demi.zk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.constant.EunmZkTask;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.current.Current;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.zk.dao.IZkProgressLogDao;
import cn.demi.zk.po.ZkProgressLog;

@Repository("zk.progressLogDao")
public class ZkProgressLogDaoImpl extends BaseDaoImpl<ZkProgressLog> implements IZkProgressLogDao {

	 
	@Override
	public ZkProgressLog add(String taskId, String status, String audit, String msg) {
		Current current=CurrentUtils.getCurrent();
		ZkProgressLog po =new ZkProgressLog();
		po.setTaskId(taskId);
		po.setBusType(EunmZkTask.getName(status));
		po.setStatus(status);
		po.setAudit(audit);
		po.setMsg(msg);
		po.setOrg(current.getDepId()+"-"+current.getDepName());
		po.setUser(current.getAccountId()+"-"+current.getUserName());
		po.setLogDate(DateUtils.getCurrDateStr());
		po.onAdd();
		if(po.getSort()==null) {
			po.setSort(getMaxSort()+1);
		}
		getEntityManager().persist(po);
		return po;
	}

	@Override
	public List<ZkProgressLog> findByTaskId(String taskId) {
		return super.findByProperty("taskId",taskId);
	}
	@Override
	public ZkProgressLog findOne(String budId, String status) {
		StringBuffer hql=new StringBuffer("FROM "+getEntityName(ZkProgressLog.class)+" WHERE isDel="+ZkProgressLog.N);
		hql.append(" AND taskId='"+budId+"'");
		hql.append(" AND status='"+status+"'");
		hql.append(" ORDER BY createTime desc");
		List<ZkProgressLog> lst=super.list(hql.toString());
		if(lst!=null&&lst.size()>0) {
			return lst.get(0);
		}else {
			return null;
		}
	}
}
