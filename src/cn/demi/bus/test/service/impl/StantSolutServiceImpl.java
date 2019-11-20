package cn.demi.bus.test.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.test.dao.IStantSolutDao;
import cn.demi.bus.test.dao.IStantSolutMxDao;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.po.StantSolut;
import cn.demi.bus.test.po.StantSolutMx;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.service.IStantSolutService;
import cn.demi.bus.test.vo.StantSolutMxVo;
import cn.demi.bus.test.vo.StantSolutVo;

@Service("bus.stantSolutService")
public class StantSolutServiceImpl extends BaseServiceImpl<StantSolutVo, StantSolut> implements IStantSolutService {
	@Autowired
	private IStantSolutDao stantSolutDao;
	@Autowired
	private IStantSolutMxDao stantSolutMxDao;
	@Autowired
	private ITaskItemDao taskItemDao;

	public StantSolutVo find(StantSolutVo vo) throws GlobalException {
		List<StantSolut> poList = stantSolutDao.findByProperty("taskItemId", vo.getTaskItemId());
		if (poList != null && poList.size() > 0) {
			vo = po2Vo(poList.get(0));
			String hql = " from " + stantSolutMxDao.getEntityName(StantSolutMx.class) + " mx where mx.isDel=" + Po.N + " and mx.pId='" + vo.getId() + "'";
			List<StantSolutMx> mxList = stantSolutMxDao.list(hql);

			List<StantSolutMxVo> mxvoList = new ArrayList<>();
			for (StantSolutMx mx : mxList) {
				StantSolutMxVo mxvo = new StantSolutMxVo();
				StantSolutMxVo mvo = mxvo.toVo(mx, mxvo);
				mxvoList.add(mvo);
			}
			vo.setStantMxList(mxvoList);
		}
		return vo;
	}

	@Override
	public void save(StantSolutVo v) throws GlobalException {
		if (StrUtils.isBlankOrNull(v.getId())) {
			add(v);
		} else {
			update(v);
		}
	}

	@Override
	public void update(StantSolutVo v) throws GlobalException {
		StantSolut p = stantSolutDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		stantSolutDao.update(p);
		List<StantSolutMxVo> stantMxs = v.getStantMxList();
		for (StantSolutMxVo mxVo : stantMxs) {
			StantSolutMx mx = stantSolutMxDao.findById(mxVo.getId());
			mx = mx.toPo(mxVo, mx);
			stantSolutMxDao.update(mx);
		}
		TaskItem taskItem = taskItemDao.findById(v.getTaskItemId());
		taskItem.setPzSt("1");
		taskItemDao.update(taskItem);
	}

	@Override
	public void add(StantSolutVo v) throws GlobalException {
		StantSolut p = vo2Po(v);
		stantSolutDao.add(p);
		v.setId(p.getId());
		List<StantSolutMxVo> stantMxs = v.getStantMxList();
		for (StantSolutMxVo mxVo : stantMxs) {
			StantSolutMx mx = new StantSolutMx();
			mx.setDeme1(mxVo.getDeme1());
			mx.setDeme2(mxVo.getDeme2());
			mx.setV1(mxVo.getV1());
			mx.setV2(mxVo.getV2());
			mx.setV3(mxVo.getV3());
			mx.setV4(mxVo.getV4());
			mx.setV5(mxVo.getV5());
			mx.setAvg1(mxVo.getAvg1());
			mx.setAvg2(mxVo.getAvg2());
			mx.setJc1(mxVo.getJc1());
			mx.setJc2(mxVo.getJc2());
			mx.setJcall(mxVo.getJcall());
			mx.setpId(v.getId());
			stantSolutMxDao.add(mx);
		}
		TaskItem taskItem = taskItemDao.findById(v.getTaskItemId());
		taskItem.setPzSt("1");
		taskItemDao.update(taskItem);
	}

}
