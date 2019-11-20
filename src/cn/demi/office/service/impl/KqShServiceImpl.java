package cn.demi.office.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.office.dao.IKqDao;
import cn.demi.office.po.Kq;
import cn.demi.office.service.IKqShService;
import cn.demi.office.vo.KqVo;

@Service("office.kqShService")
public class KqShServiceImpl extends BaseServiceImpl<KqVo,Kq> implements
		IKqShService {
	@Autowired
	private IKqDao kqDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Override
	public KqVo findById(String id) throws GlobalException {
		return super.findById(id);
	}
	@Override
	public void update(KqVo v) throws GlobalException {
		Kq po=kqDao.findById(v.getId());
		po.setShDate(v.getShDate());
		po.setShId(v.getShId());
		po.setShName(v.getShName());
		po.setShMsg(v.getShMsg());
		Progress pg=progressDao.findByBusId(po.getId());
		if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			po.setShResult("通过");
			if(po.getHours()<=24) {
				//小于4小时 直接通过 到 销假
				po.setFstatus(Kq.ST_2);
				pg=progressDao.update(pg.getId(),EunmTask.QJ_XJ.getStatus(), null, null, po.getUserId(), po.getUserName());
			}else{
				//副总审核
				pg=progressDao.update(pg.getId(),EunmTask.QJ_SP.getStatus(), null, null, null, null);
			}
		}else if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_N)) {
			po.setShResult("不通过");
			po.setFstatus(Kq.ST_B);
			pg=progressDao.update(pg.getId(),EunmTask.QJ_SQ.getStatus(), null, null, po.getUserId(), po.getUserName());
		}
		po.setStatus(pg.getStatus());
		kqDao.update(po);
		progressLogDao.add(po.getId(), po.getId(), EunmTask.QJ_SH.getStatus(), v.getIsCommit(), v.getShMsg(),getCurrent().getDepId(), getCurrent().getDepName(),getCurrent().getAccountId(),getCurrent().getUserName());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, KqVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status", QueryCondition.EQ, EunmTask.QJ_SH.getStatus()));
		pageResult = kqDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Kq>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, KqVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("shDate is not null"));
		pageResult.addCondition(new QueryCondition("shId", QueryCondition.EQ, getCurrent().getAccountId()));
		pageResult = kqDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Kq>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(KqVo v) throws GlobalException {
		return super.toQueryList(v);
	}
}
