package cn.demi.zk.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.EunmZkTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.demi.zk.dao.IZkProgressLogDao;
import cn.demi.zk.dao.IZkTaskDao;
import cn.demi.zk.po.ZkProgressLog;
import cn.demi.zk.po.ZkTask;
import cn.demi.zk.service.IZkProgressLogService;
import cn.demi.zk.vo.ZkProgressLogVo;

@Service("zk.progressLogService")
public class ZkProgressLogServiceImpl extends BaseServiceImpl<ZkProgressLogVo,ZkProgressLog> implements
		IZkProgressLogService {
	@Autowired
	private IZkProgressLogDao zkProgressLogDao;
	@Autowired
	private IZkTaskDao taskDao;
	@Override
	public List<ZkProgressLogVo> findList(String taskId) throws GlobalException {
		List<ZkProgressLog> plList=zkProgressLogDao.findByProperty("taskId", taskId);
		List<ZkProgressLogVo> plVoList=null;
		if(null!=plList) {
			plVoList=new ArrayList<ZkProgressLogVo>();
			for (ZkProgressLog pl : plList) {
				ZkProgressLogVo plVo=new ZkProgressLogVo();
				plVo=plVo.toVo(pl);
				if(null!=pl.getUser()) {
					String user[]=pl.getUser().split("-");
					plVo.setUserId(user[0]);
					plVo.setUserName(user[1]);
				}
				if(null!=pl.getOrg()) {
					String org[]=pl.getOrg().split("-");
					plVo.setOrgId(org[0]);
					plVo.setOrgName(org[1]);
				}
				plVoList.add(plVo);
			}
		}
		return plVoList;
	}
	@Override
	public ZkProgressLogVo findById(ZkProgressLogVo v) throws GlobalException {
		v.setLogList(findList(v.getTaskId()));
		LinkedList<ZkProgressLogVo> pList=new LinkedList<ZkProgressLogVo>();
		ZkTask task=taskDao.findById(v.getTaskId());
		ZkProgressLogVo thisProgress=new ZkProgressLogVo();
		thisProgress.setStatus(task.getStatus());
		thisProgress.setBusType(EunmZkTask.getName(task.getStatus()));
		thisProgress.setLogDate(DateUtils.parseToDateStr(task.getLastUpdTime()));
		thisProgress.setClasses("yellow-bg");
		thisProgress.setTaskId(v.getTaskId());
		pList.add(thisProgress);
		v.setProgressList(pList);
		createBeforeList(thisProgress, pList);
		createAfterList(thisProgress, pList);
		return v;
	}
	public void createBeforeList(ZkProgressLogVo v,LinkedList<ZkProgressLogVo> pglist){
		if(!v.getStatus().equals(EunmZkTask.TASK_00.getStatus())) {
			String before=EunmZkTask.getBefore(v.getStatus());
			if(null!=before) {
				String name=EunmZkTask.getName(before);
				ZkProgressLogVo vo=new ZkProgressLogVo();
				ZkProgressLog pl=zkProgressLogDao.findOne(v.getTaskId(),before);
				if(pl!=null) {
					vo.setOrgName(pl.getOrg());
					vo.setUserName(pl.getUser());
					if(null!=pl.getUser()) {
						String user[]=pl.getUser().split("-");
						vo.setUserId(user[0]);
						vo.setUserName(user[1]);
					}
					if(null!=pl.getOrg()) {
						String org[]=pl.getOrg().split("-");
						vo.setOrgId(org[0]);
						vo.setOrgName(org[1]);
					}
					vo.setLogDate(pl.getLogDate());
					vo.setMsg(pl.getMsg());
				}
				vo.setStatus(before);
				vo.setBusType(name);
				vo.setTaskId(v.getTaskId());
				vo.setClasses("navy-bg");
				pglist.addFirst(vo);
				createBeforeList(vo, pglist);
			}
		}
	}
	public void createAfterList(ZkProgressLogVo v,LinkedList<ZkProgressLogVo> pglist){
		if(!v.getStatus().equals(EunmZkTask.TASK_100.getStatus())) {
			String after=EunmZkTask.getAfter(v.getStatus());
			if(null!=after) {
				String name=EunmZkTask.getName(after);
				ZkProgressLogVo vo=new ZkProgressLogVo();
				vo.setBusType(name);
				vo.setTaskId(v.getTaskId());
				vo.setClasses("default-bg");
				vo.setStatus(after);
				pglist.addLast(vo);
				createAfterList(vo, pglist);
			}
		}
	}
}
