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
import cn.demi.office.service.IKqSpService;
import cn.demi.office.vo.KqVo;

@Service("office.kqSpService")
public class KqSpServiceImpl extends BaseServiceImpl<KqVo,Kq> implements
		IKqSpService {
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
		po.setSpDate(v.getSpDate());
		po.setSpId(v.getSpId());
		po.setSpName(v.getSpName());
		po.setSpMsg(v.getSpMsg());
		Progress pg=progressDao.findByBusId(po.getId());
		if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			po.setShResult("通过");
			po.setFstatus(Kq.ST_2);
			pg=progressDao.update(pg.getId(),EunmTask.QJ_XJ.getStatus(), null, null, po.getUserId(), po.getUserName());
		}else if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_N)) {
			po.setShResult("不通过");
			po.setFstatus(Kq.ST_B);
			pg=progressDao.update(pg.getId(),EunmTask.QJ_SQ.getStatus(), null, null, po.getUserId(), po.getUserName());
		}
		po.setStatus(pg.getStatus());
		kqDao.update(po);
		progressLogDao.add(po.getId(), po.getId(), EunmTask.QJ_SP.getStatus(), v.getIsCommit(), v.getSpMsg(),getCurrent().getDepId(), getCurrent().getDepName(),getCurrent().getAccountId(),getCurrent().getUserName());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, KqVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status", QueryCondition.EQ, EunmTask.QJ_SP.getStatus()));
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
		pageResult.addCondition(new QueryCondition("spDate is not null"));
		pageResult.addCondition(new QueryCondition("spId", QueryCondition.EQ, getCurrent().getAccountId()));
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
