package cn.demi.qlt.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.pg.vo.ProgressVo;
import cn.demi.qlt.constant.QltEunm;
import cn.demi.qlt.dao.INsRecordDao;
import cn.demi.qlt.po.NsRecord;
import cn.demi.qlt.service.IProgressService;

@Service("qlt.progressService")
public class ProgressServiceImpl extends BaseServiceImpl<ProgressVo,Progress> implements
 IProgressService {
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private INsRecordDao nsRecordDao;
 
	@SuppressWarnings("unchecked")
	@Override
	public ProgressVo findById(ProgressVo v) throws GlobalException {
		ProgressVo vo=new ProgressVo();
		if(v.getBusType().equals(QltEunm.TASK_STOP.getName())) {
			vo.setClasses("red-bg");
			vo.setBusId(v.getBusId());
			List<ProgressLog> pgLog=progressLogDao.findByProperties(new String[]{"busId","audit"},new String[]{v.getBusId(),QltEunm.TASK_STOP.getStatus()});
			if(null!=pgLog&&pgLog.size()>0) {
				ProgressLog pg=pgLog.get(0);
				vo.setStatus(pg.getStatus());
				vo.setOrgName(pg.getOrgName());
				vo.setUserName(pg.getUserName());
				vo.setDate(pg.getLogTime());
				vo.setBusType(pg.getBusType());
			}
		}else if(v.getBusType().equals(QltEunm.TASK_END.getName())) {
			vo.setClasses("navy-bg");
			vo.setBusId(v.getBusId());
			List<ProgressLog> pgLog=progressLogDao.findByProperties(new String[]{"busId","audit"},new String[]{v.getBusId(),QltEunm.TASK_END.getStatus()});
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
			vo.setClasses("yellow-bg");
			vo= po2Vo(po);
			if(null==vo.getUserName()) {
				String sql="select group_concat(u.name) from v_sys_user u join v_sys_account acc on u.id=acc.user_id " + 
						"join v_sys_account_role ar on ar.account_id=acc.id join v_sys_role_fun rf on rf.role_id=ar.role_id " + 
						"join v_sys_function fun on fun.id=rf.fun_id where u.is_del='"+Progress.N+"' and fun.wf_code='"+vo.getStatus()+"'";
				List<String> lst=progressDao.queryBySql(sql);
				if(lst!=null&&lst.size()>0) {
					vo.setUserName(lst.get(0));
				}
			}
		}
		setSub(vo.getStatus(), vo);
		LinkedList<ProgressVo> pgList=new LinkedList<ProgressVo>();
		List<ProgressLogVo> logList=new ArrayList<ProgressLogVo>();
		pgList.add(vo);
		if(!StrUtils.isBlankOrNull(vo.getBusId())&&!StrUtils.isBlankOrNull(vo.getStatus())) {
			createBeforeList(vo, pgList);//增加当前节点前操作
			createAfterList(vo, pgList);//初始当前节点后流程
		}
		//日志记录
		List<ProgressLog> plList=progressLogDao.findByBusId(v.getBusId());
		if(null!=plList) {
			for (ProgressLog pl : plList) {
				ProgressLogVo plVo=new ProgressLogVo();
				plVo=plVo.toVo(pl);
				if(plVo.getAudit().equals(QltEunm.PASS_Y)) {
					plVo.setAudit("提交/通过");
				}else if(plVo.getAudit().equals(QltEunm.PASS_N)) {
					plVo.setAudit("不通过");
				}else if(plVo.getAudit().equals(QltEunm.TASK_END.getStatus())) {
					plVo.setAudit(QltEunm.TASK_END.getName());
				}else if(plVo.getAudit().equals(QltEunm.TASK_STOP.getStatus())) {
					plVo.setAudit(QltEunm.TASK_STOP.getName());
				}
				logList.add(plVo);
			}
		}
		v.setProgressList(pgList);
		v.setLogList(logList);
		return v;
	}
	public void setSub(String status,ProgressVo v) {
		if(status.equals(QltEunm.QLT_20.getStatus())) {
			//子项 进度
			List<NsRecord> rdList=nsRecordDao.findByNsId(v.getBusId());
			List<ProgressVo> itPjList=new ArrayList<ProgressVo>();
			if(null!=rdList) {
				for (NsRecord it : rdList) {
					ProgressVo progressVo=new ProgressVo();
						progressVo.setBusType(it.getMonth()+"月");
					if(it.getStatus().equals(QltEunm.TASK_STOP.getStatus())) {
						List<ProgressLog> pgLog=progressLogDao.findByProperties(new String[]{"busId","audit"},new String[]{it.getId(),it.getStatus()});
						if(null!=pgLog&&pgLog.size()>0) {
							ProgressLog thisPgl=pgLog.get(0);
							ProgressVo subVo=new ProgressVo();
							subVo.setClasses("progress-bar-warning");
							subVo.setBusId(it.getId());
							subVo.setStatus(it.getStatus());
							subVo.setBusType(thisPgl.getBusType());
							LinkedList<ProgressVo> subList=new LinkedList<ProgressVo>();
							subList.add(subVo);
							createBeforeItemList(subVo, subList);
							createAfterItemList(subVo, subList);
							progressVo.setProgressList(subList);
						}
					}else if(it.getStatus().equals(QltEunm.QLT_M_40.getStatus())) {
						List<ProgressLog> pgLog=progressLogDao.findByProperties(new String[]{"busId","status"},new String[]{it.getId(),it.getStatus()});
						if(null!=pgLog&&pgLog.size()>0) {
							ProgressLog thisPgl=pgLog.get(0);
							ProgressVo subVo=new ProgressVo();
							subVo.setClasses("progress-bar-warning");
							subVo.setBusId(it.getId());
							subVo.setStatus(it.getStatus());
							subVo.setBusType(thisPgl.getBusType());
							LinkedList<ProgressVo> subList=new LinkedList<ProgressVo>();
							subList.add(subVo);
							createBeforeItemList(subVo, subList);
							progressVo.setProgressList(subList);
						}
					}else {
						if(null!=it.getPg()) {
							ProgressVo subVo=new ProgressVo();
							subVo.setClasses("progress-bar-warning");
							subVo.setBusId(it.getId());
							subVo.setStatus(it.getPg().getStatus());
							subVo.setBusType(it.getPg().getBusType());
							LinkedList<ProgressVo> subList=new LinkedList<ProgressVo>();
							subList.add(subVo);
							createBeforeItemList(subVo, subList);
							createAfterItemList(subVo, subList);
							progressVo.setProgressList(subList);
						}else {
							ProgressVo subVo=new ProgressVo();
							subVo.setClasses("progress-bar-gray");
							subVo.setBusId(it.getId());
							subVo.setStatus(QltEunm.QLT_M_00.getStatus());
							subVo.setBusType(QltEunm.QLT_M_00.getName());
							LinkedList<ProgressVo> subList=new LinkedList<ProgressVo>();
							subList.add(subVo);
							createAfterItemList(subVo, subList);
							progressVo.setProgressList(subList);
						}
					}
					itPjList.add(progressVo);
				}
			}
			v.setItList(itPjList);
		}
	}
	public void createBeforeList(ProgressVo v,LinkedList<ProgressVo> pglist){
		if(!v.getStatus().equals(QltEunm.QLT_00.getStatus())) {
			String before=QltEunm.getBefore(v.getStatus());
			if(null!=before) {
				String name=QltEunm.getName(before);
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
				setSub(vo.getStatus(), vo);
				pglist.addFirst(vo);
				createBeforeList(vo, pglist);
			}
		}
	}
	public void createAfterList(ProgressVo v,LinkedList<ProgressVo> pglist){
		if(!v.getStatus().equals(QltEunm.TASK_END.getStatus())) {
			String after=QltEunm.getAfter(v.getStatus());
			if(null!=after) {
				String name=QltEunm.getName(after);
				ProgressVo vo=new ProgressVo();
				vo.setBusType(name);
				vo.setBusId(v.getBusId());
				vo.setClasses("default-bg");
				vo.setStatus(after);
				setSub(vo.getStatus(), vo);
				pglist.addLast(vo);
				createAfterList(vo, pglist);
			}
		}
	}
	
	public void createBeforeItemList(ProgressVo v,LinkedList<ProgressVo> pglist){
		if(!v.getStatus().equals(QltEunm.QLT_M_00.getStatus())) {
			String before=QltEunm.getBefore(v.getStatus());
			if(null!=before) {
				String name=QltEunm.getName(before);
				ProgressVo vo=new ProgressVo();
				ProgressLog pl=progressLogDao.findOne(v.getBusId(),before);
				if(pl!=null) {
					vo.setOrgName(pl.getOrgName());
					vo.setUserName(pl.getUserName());
					vo.setDate(pl.getLogTime());
					vo.setMsg(pl.getMsg());
					vo.setClasses("progress-bar");
				}else {
					vo.setClasses("progress-bar-gray");
				}
				vo.setStatus(before);
				vo.setBusType(name);
				vo.setBusId(v.getBusId());
				pglist.addFirst(vo);
				createBeforeItemList(vo, pglist);
			}
		}
	}
	public void createAfterItemList(ProgressVo v,LinkedList<ProgressVo> pglist){
		if(!v.getStatus().equals(QltEunm.QLT_M_40.getStatus())) {
			String after=QltEunm.getAfter(v.getStatus());
			if(null!=after) {
				String name=QltEunm.getName(after);
				ProgressVo vo=new ProgressVo();
				vo.setBusType(name);
				vo.setBusId(v.getBusId());
				vo.setStatus(after);
				vo.setClasses("progress-bar-gray");
				pglist.addLast(vo);
				createAfterItemList(vo, pglist);
			}
		}
	}
}
