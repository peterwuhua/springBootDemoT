package cn.demi.bus.pg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;

@Service("bus.progressLogService")
public class ProgressLogServiceImpl extends BaseServiceImpl<ProgressLogVo,ProgressLog> implements
		IProgressLogService {
	@Autowired
	private IProgressLogDao progressLogDao;
	@Override
	public List<ProgressLogVo> findList(String taskId, String busId) throws GlobalException {
		List<ProgressLog> plList=progressLogDao.findList(taskId, busId);
		List<ProgressLogVo> plVoList=null;
		if(null!=plList) {
			plVoList=new ArrayList<ProgressLogVo>();
			for (ProgressLog pl : plList) {
				ProgressLogVo plVo=new ProgressLogVo();
				plVo=plVo.toVo(pl);
				if(null!=plVo.getAudit()&&plVo.getAudit().equals(EunmTask.PASS_N)) {
					plVo.setAudit("退回");
				}else if(null!=plVo.getAudit()&&plVo.getAudit().equals(EunmTask.PASS_S)) {
					plVo.setAudit("保存");
				}if(null!=plVo.getAudit()&&plVo.getAudit().equals(EunmTask.PASS_Y)) {
					plVo.setAudit("提交");
				}
				plVoList.add(plVo);
			}
		}
		return plVoList;
	}
	@Override
	public ProgressLogVo find(String busId, String status) throws GlobalException {
		ProgressLog log=progressLogDao.findOne(busId, status);
		ProgressLogVo vo=new ProgressLogVo();
		if(null!=log) {
			vo=vo.toVo(log);
			String audit=vo.getAudit();
			if(log.getAudit().equals(EunmTask.PASS_Y)) {
				audit="提交/通过";
			}else if(log.getAudit().equals(EunmTask.PASS_N)) {
				audit="退回/不通过";
			}
			vo.setAudit(audit);
		}
		return vo;
	}
	@Override
	public ProgressLogVo findBack(String busId) throws GlobalException {
		String hql="FROM "+progressLogDao.getEntityName(ProgressLog.class)+" WHERE isDel='"+Po.N+"' AND busId='"+busId+"' AND audit='"+EunmTask.PASS_N+"' order by createTime desc";
		List<ProgressLog> list=progressLogDao.list(hql);
		if(null!=list&&list.size()>0) {
			return po2Vo(list.get(0));
		}else {
			return null;
		}
	}
}
