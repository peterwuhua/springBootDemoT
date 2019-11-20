package cn.demi.bus.pg.dao.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.constant.EunmProject;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.ProgressLog;

@Repository("bus.progressLogDao")
public class ProgressLogDaoImpl extends BaseDaoImpl<ProgressLog> implements IProgressLogDao {

	@Override
	public ProgressLog add(String taskId, String busId, String status, String audit, String msg) {
		ProgressLog po =new ProgressLog();
		po.setTaskId(taskId);
		po.setBusId(busId);
		po.setBusType(EunmTask.getName(status));
		po.setStatus(status);
		po.setAudit(audit);
		po.setMsg(msg);
		po.setOrgId(getCurrent().getOrgId());
		po.setOrgName(getCurrent().getOrgName());
		po.setUserId(getCurrent().getAccountId());
		po.setUserName(getCurrent().getUserName());
		po.setLogTime(DateUtils.getCurrDateTimeStr());
		po.onAdd();
		if(po.getSort()==null) {
			po.setSort(getMaxSort()+1);
		}
		getEntityManager().persist(po);
		return po;
	}
	
	
	public ProgressLog add4Porject(String taskId, String busId, String status, String audit, String msg) {
		ProgressLog po =new ProgressLog();
		po.setTaskId(taskId);
		po.setBusId(busId);
		po.setBusType(EunmProject.getName(status));
		po.setStatus(status);
		po.setAudit(audit);
		po.setMsg(msg);
		po.setOrgId(getCurrent().getOrgId());
		po.setOrgName(getCurrent().getOrgName());
		po.setUserId(getCurrent().getAccountId());
		po.setUserName(getCurrent().getUserName());
		po.setLogTime(DateUtils.getCurrDateTimeStr());
		po.onAdd();
		if(po.getSort()==null) {
			po.setSort(getMaxSort()+1);
		}
		getEntityManager().persist(po);
		return po;
	}

	@Override
	public ProgressLog addApp(String taskId, String busId, String status, String audit, String msg, ObjVo objVo) {
		ProgressLog po =new ProgressLog();
		po.setTaskId(taskId);
		po.setBusId(busId);
		po.setBusType(EunmTask.getName(status));
		po.setStatus(status);
		po.setAudit(audit);
		po.setMsg(msg);
		po.setOrgId(objVo.getOrgId());
		po.setOrgName(objVo.getOrgName());
		po.setUserId(objVo.getUserId());
		po.setUserName(objVo.getName());
		po.setLogTime(DateUtils.getCurrDateTimeStr());
		po.onAdd();
		if(po.getSort()==null) {
			po.setSort(getMaxSort()+1);
		}
		getEntityManager().persist(po);
		return po;
	}

	@Override
	public List<ProgressLog> findByBusId(String busId) {
		StringBuffer hql=new StringBuffer("FROM "+getEntityName(ProgressLog.class)+" WHERE isDel="+ProgressLog.N);
		hql.append(" AND (taskId='"+busId+"' or busId='"+busId+"')");
		hql.append(" ORDER BY createTime asc");
		return super.list(hql.toString());
	}

	@Override
	public List<ProgressLog> findList(String taskId, String busId) {
		StringBuffer hql=new StringBuffer("FROM "+getEntityName(ProgressLog.class)+" WHERE isDel="+ProgressLog.N);
		if(!StrUtils.isBlankOrNull(taskId)) {
			hql.append(" AND taskId='"+taskId+"'");
		}
		if(!StrUtils.isBlankOrNull(busId)) {
			hql.append(" AND busId='"+busId+"'");
		}
		hql.append(" ORDER BY createTime asc");
		return super.list(hql.toString());
	}

	@Override
	public ProgressLog findOne(String budId, String status) {
		StringBuffer hql=new StringBuffer("FROM "+getEntityName(ProgressLog.class)+" WHERE isDel="+ProgressLog.N);
		hql.append(" AND (taskId='"+budId+"' or busId='"+budId+"')");
		hql.append(" AND status='"+status+"'");
		hql.append(" ORDER BY createTime desc");
		List<ProgressLog> lst=super.list(hql.toString());
		if(lst!=null&&lst.size()>0) {
			return lst.get(0);
		}else {
			return null;
		}
	}

	@Override
	public void deleteByBusId(String busId) {
		String hql="DELETE FROM  "+getEntityName(ProgressLog.class)+" WHERE busId='"+busId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}

	@Override
	public ProgressLog add(String taskId, String busId, String status, String audit, String msg, String orgId, String orgName, String userId, String userName) {
		ProgressLog po =new ProgressLog();
		po.setTaskId(taskId);
		po.setBusId(busId);
		po.setBusType(EunmTask.getName(status));
		po.setStatus(status);
		po.setAudit(audit);
		po.setMsg(msg);
		po.setOrgId(orgId);
		po.setOrgName(orgName);
		po.setUserId(userId);
		po.setUserName(userName);
		po.setLogTime(DateUtils.getCurrDateTimeStr());
		po.onAdd();
		if(po.getSort()==null) {
			po.setSort(getMaxSort()+1);
		}
		getEntityManager().persist(po);
		return po;
	}
	
	
	
	@Override
	public ProgressLog add4Porject(String taskId, String busId, String status, String audit, String msg, String orgId, String orgName, String userId, String userName) {
		ProgressLog po =new ProgressLog();
		po.setTaskId(taskId);
		po.setBusId(busId);
		po.setBusType(EunmProject.getName(status));
		po.setStatus(status);
		po.setAudit(audit);
		po.setMsg(msg);
		po.setOrgId(orgId);
		po.setOrgName(orgName);
		po.setUserId(userId);
		po.setUserName(userName);
		po.setLogTime(DateUtils.getCurrDateTimeStr());
		po.onAdd();
		if(po.getSort()==null) {
			po.setSort(getMaxSort()+1);
		}
		getEntityManager().persist(po);
		return po;
	}
	
}
