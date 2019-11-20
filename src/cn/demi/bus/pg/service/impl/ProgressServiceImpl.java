package cn.demi.bus.pg.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.EunmProject;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pg.service.IProgressService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.pg.vo.ProgressVo;

@Service("bus.progressService")
public class ProgressServiceImpl extends BaseServiceImpl<ProgressVo,Progress> implements
		IProgressService {
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IOrgDao orgDao;
	@SuppressWarnings("unchecked")
	@Override
	public ProgressVo findById(ProgressVo v) throws GlobalException {
		ProgressVo vo=new ProgressVo();
		if(v.getBusType().equals(EunmTask.TASK_STOP.getName())||v.getBusType().equals(EunmTask.TASK_STOP.getStatus())) {
			vo.setClasses("red-bg");
			vo.setBusId(v.getBusId());
			List<ProgressLog> pgLog=progressLogDao.findByProperties(new String[]{"busId","audit"},new String[]{v.getBusId(),EunmTask.TASK_STOP.getStatus()});
			if(null!=pgLog&&pgLog.size()>0) {
				ProgressLog pg=pgLog.get(0);
				vo.setStatus(pg.getStatus());
				vo.setOrgName(pg.getOrgName());
				vo.setUserName(pg.getUserName());
				vo.setDate(pg.getLogTime());
				vo.setBusType(pg.getBusType());
			}
		}else if(v.getBusType().equals(EunmTask.TASK_END.getName())||v.getBusType().equals(EunmTask.TASK_END.getStatus())) {
			vo.setClasses("navy-bg");
			vo.setBusId(v.getBusId());
			List<ProgressLog> pgLog=progressLogDao.findByProperties(new String[]{"busId","audit"},new String[]{v.getBusId(),EunmTask.TASK_END.getStatus()});
			if(null!=pgLog&&pgLog.size()>0) {
				ProgressLog pg=pgLog.get(0);
				vo.setStatus(pg.getStatus());
				vo.setOrgName(pg.getOrgName());
				vo.setUserName(pg.getUserName());
				vo.setDate(pg.getLogTime());
				vo.setBusType(pg.getBusType());
			}
		}else{
			Progress po=progressDao.findByBusId(v.getBusId());
			vo= po2Vo(po);
			vo.setClasses("yellow-bg");
			if(null==vo.getUserName()) {
				String sql="select group_concat(distinct u.name) from v_sys_user u join v_sys_account acc on u.id=acc.user_id " + 
						"join v_sys_account_role ar on ar.account_id=acc.id join v_sys_role_fun rf on rf.role_id=ar.role_id " + 
						"join v_sys_function fun on fun.id=rf.fun_id where u.is_del='"+Progress.N+"' and fun.wf_code='"+vo.getStatus()+"'";
				if(!StrUtils.isBlankOrNull(po.getOrgId())) {
					List<String> orgList=orgDao.listChild(po.getOrgId());
					orgList.add(po.getOrgId());
					sql+=" and acc.org_id in('"+String.join("','", orgList)+"')";
				}
				List<String> lst=progressDao.queryBySql(sql);
				if(lst!=null&&lst.size()>0) {
					vo.setUserName(lst.get(0));
				}
			}
		}
		LinkedList<ProgressVo> pgList=new LinkedList<ProgressVo>();
		List<ProgressLogVo> logList=new ArrayList<ProgressLogVo>();
		pgList.add(vo);
		if(!StrUtils.isBlankOrNull(vo.getBusId())&&!StrUtils.isBlankOrNull(vo.getStatus())) {
			createBeforeList(vo, pgList);//增加当前节点前操作
			createAfterList(vo, pgList);//初始当前节点后流程
		}
		v.setProgressList(pgList);
		//日志记录
		List<ProgressLog> plList=progressLogDao.findByBusId(v.getBusId());
		if(null!=plList) {
			for (ProgressLog pl : plList) {
				ProgressLogVo plVo=new ProgressLogVo();
				plVo=plVo.toVo(pl);
				if(null==plVo.getAudit()) {
					
				}else if(plVo.getAudit().equals(EunmTask.PASS_Y)) {
					plVo.setAudit("提交/通过");
				}else if(plVo.getAudit().equals(EunmTask.PASS_N)) {
					plVo.setAudit("不通过");
				}else if(plVo.getAudit().equals(EunmTask.TASK_END.getStatus())) {
					plVo.setAudit(EunmTask.TASK_END.getName());
				}else if(plVo.getAudit().equals(EunmTask.TASK_STOP.getStatus())) {
					plVo.setAudit(EunmTask.TASK_STOP.getName());
				}
				logList.add(plVo);
			}
		}
		v.setLogList(logList);
		return v;
	}
	 
	public void createBeforeList(ProgressVo v,LinkedList<ProgressVo> pglist){
		if(!v.getStatus().equals(EunmTask.TASK_DJ.getStatus())) {
			String before=EunmTask.getBefore(v.getStatus());
			if(null!=before) {
				String name=EunmTask.getName(before);
				ProgressVo vo=new ProgressVo();
				ProgressLog pl=progressLogDao.findOne(v.getBusId(),before);
				if(pl!=null) {
					vo.setOrgId(pl.getOrgId());
					vo.setOrgName(pl.getOrgName());
					vo.setUserId(pl.getUserId());
					vo.setUserName(pl.getUserName());
					vo.setDate(pl.getLogTime());
					vo.setClasses("navy-bg");
				}else {
					vo.setClasses("default-bg");
				}
				vo.setStatus(before);
				vo.setBusType(name);
				vo.setBusId(v.getBusId());
				pglist.addFirst(vo);
				createBeforeList(vo, pglist);
			}
		}
	}
	public void createAfterList(ProgressVo v,LinkedList<ProgressVo> pglist){
		if(!v.getStatus().equals(EunmTask.TASK_END.getStatus())) {
			String after=EunmTask.getAfter(v.getStatus());
			if(null!=after) {
				String name=EunmTask.getName(after);
				ProgressVo vo=new ProgressVo();
				vo.setBusType(name);
				vo.setBusId(v.getBusId());
				if(after.equals(EunmTask.TASK_END.getStatus())&&v.getClasses().equals("navy-bg")) {
					vo.setClasses("navy-bg");
				}else {
					vo.setClasses("default-bg");
				}
				vo.setStatus(after);
				pglist.addLast(vo);
				createAfterList(vo, pglist);
			}
		}
	}

	
	
	
	public void createBeforeList4Sim(ProgressVo v,LinkedList<ProgressVo> pglist){
		if(!v.getStatus().equals(EunmProject.PROJECT_LX.getStatus())) {
			String before=EunmProject.getBefore(v.getStatus());
			if(null!=before) {
				String name=EunmProject.getName(before);
				ProgressVo vo=new ProgressVo();
				ProgressLog pl=progressLogDao.findOne(v.getBusId(),before);
				if(pl!=null) {
					vo.setOrgId(pl.getOrgId());
					vo.setOrgName(pl.getOrgName());
					vo.setUserId(pl.getUserId());
					vo.setUserName(pl.getUserName());
					vo.setDate(pl.getLogTime());
					vo.setClasses("navy-bg");
				}else {
					vo.setClasses("default-bg");
				}
				vo.setStatus(before);
				vo.setBusType(name);
				vo.setBusId(v.getBusId());
				pglist.addFirst(vo);
				createBeforeList4Sim(vo, pglist);
			}
		}
	}
	public void createAfterList4Sim(ProgressVo v,LinkedList<ProgressVo> pglist){
		if(!v.getStatus().equals(EunmProject.PROJECT_END.getStatus())) {
			String after=EunmProject.getAfter(v.getStatus());
			if(null!=after) {
				String name=EunmProject.getName(after);
				ProgressVo vo=new ProgressVo();
				vo.setBusType(name);
				vo.setBusId(v.getBusId());
				if(after.equals(EunmProject.PROJECT_END.getStatus())&&v.getClasses().equals("navy-bg")) {
					vo.setClasses("navy-bg");
				}else {
					vo.setClasses("default-bg");
				}
				vo.setStatus(after);
				pglist.addLast(vo);
				createAfterList4Sim(vo, pglist);
			}
		}
	}
	
	
	
	@Override
	public ProgressVo findById4Simple(ProgressVo v) throws GlobalException {
		ProgressVo vo=new ProgressVo();
		if(v.getBusType().equals(EunmProject.PROJECT_STOP.getName())||v.getBusType().equals(EunmProject.PROJECT_STOP.getStatus())) {
			vo.setClasses("red-bg");
			vo.setBusId(v.getBusId());
			List<ProgressLog> pgLog=progressLogDao.findByProperties(new String[]{"busId","audit"},new String[]{v.getBusId(),EunmProject.PROJECT_STOP.getStatus()});
			if(null!=pgLog&&pgLog.size()>0) {
				ProgressLog pg=pgLog.get(0);
				vo.setStatus(pg.getStatus());
				vo.setOrgName(pg.getOrgName());
				vo.setUserName(pg.getUserName());
				vo.setDate(pg.getLogTime());
				vo.setBusType(pg.getBusType());
			}
		}else if(v.getBusType().equals(EunmProject.PROJECT_END.getName())||v.getBusType().equals(EunmProject.PROJECT_END.getStatus())) {
			vo.setClasses("navy-bg");
			vo.setBusId(v.getBusId());
			List<ProgressLog> pgLog=progressLogDao.findByProperties(new String[]{"busId","audit"},new String[]{v.getBusId(),EunmProject.PROJECT_END.getStatus()});
			if(null!=pgLog&&pgLog.size()>0) {
				ProgressLog pg=pgLog.get(0);
				vo.setStatus(pg.getStatus());
				vo.setOrgName(pg.getOrgName());
				vo.setUserName(pg.getUserName());
				vo.setDate(pg.getLogTime());
				vo.setBusType(pg.getBusType());
			}
		}else{
			Progress po=progressDao.findByBusId(v.getBusId());
			vo= po2Vo(po);
			vo.setClasses("yellow-bg");
			if(null==vo.getUserName()) {
				String sql="select group_concat(distinct u.name) from v_sys_user u join v_sys_account acc on u.id=acc.user_id " + 
						"join v_sys_account_role ar on ar.account_id=acc.id join v_sys_role_fun rf on rf.role_id=ar.role_id " + 
						"join v_sys_function fun on fun.id=rf.fun_id where u.is_del='"+Progress.N+"' and fun.wf_code='"+vo.getStatus()+"'";
				if(!StrUtils.isBlankOrNull(po.getOrgId())) {
					List<String> orgList=orgDao.listChild(po.getOrgId());
					orgList.add(po.getOrgId());
					sql+=" and acc.org_id in('"+String.join("','", orgList)+"')";
				}
				List<String> lst=progressDao.queryBySql(sql);
				if(lst!=null&&lst.size()>0) {
					vo.setUserName(lst.get(0));
				}
			}
		}
		LinkedList<ProgressVo> pgList=new LinkedList<ProgressVo>();
		List<ProgressLogVo> logList=new ArrayList<ProgressLogVo>();
		pgList.add(vo);
		if(!StrUtils.isBlankOrNull(vo.getBusId())&&!StrUtils.isBlankOrNull(vo.getStatus())) {
			createBeforeList4Sim(vo, pgList);//增加当前节点前操作
			createAfterList4Sim(vo, pgList);//初始当前节点后流程
		}
		v.setProgressList(pgList);
		//日志记录
		List<ProgressLog> plList=progressLogDao.findByBusId(v.getBusId());
		if(null!=plList) {
			for (ProgressLog pl : plList) {
				ProgressLogVo plVo=new ProgressLogVo();
				plVo=plVo.toVo(pl);
				if(null==plVo.getAudit()) {
					
				}else if(plVo.getAudit().equals(EunmProject.PASS_Y)) {
					plVo.setAudit("提交/通过");
				}else if(plVo.getAudit().equals(EunmProject.PASS_N)) {
					plVo.setAudit("不通过");
				}else if(plVo.getAudit().equals(EunmProject.PROJECT_END.getStatus())) {
					plVo.setAudit(EunmProject.PROJECT_END.getName());
				}else if(plVo.getAudit().equals(EunmProject.PROJECT_STOP.getStatus())) {
					plVo.setAudit(EunmProject.PROJECT_STOP.getName());
				}
				logList.add(plVo);
			}
		}
		v.setLogList(logList);
		return v;
	}
}
