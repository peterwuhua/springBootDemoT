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
import cn.core.framework.utils.DateUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.office.dao.IDgDao;
import cn.demi.office.dao.IDgTjDao;
import cn.demi.office.po.Dg;
import cn.demi.office.service.IDgAuditService;
import cn.demi.office.vo.DgVo;

@Service("office.dgAuditService")
public class DgAuditServiceImpl extends BaseServiceImpl<DgVo,Dg> implements
		IDgAuditService {

	@Autowired
	private IDgDao dgDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IDgTjDao dgTjDao;
	@Override
	public DgVo findById(String id) throws GlobalException {
		return super.findById(id);
	}
	@Override
	public void update(DgVo v) throws GlobalException {
		Dg po=dgDao.findById(v.getId());
		po.setAuditDate(DateUtils.getCurrDateTimeStr());
		po.setAuditMsg(v.getAuditMsg());
		if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			po.setStatus(Dg.ST_2);
			po.setAuditResult("通过");
			//补卡通过审批后更新统计表
			dgTjDao.updDgTj(po.getUserId(), po.getDate());
			progressDao.update(po.getProgress().getId(), "finish", getCurrent().getUserId(), getCurrent().getUserName());
		}else if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_N)) {
			po.setAuditResult("不通过");
			po.setStatus(Dg.ST_0);
			progressDao.update(po.getId(), EunmTask.BK_SQ.getStatus(), getCurrent().getUserId(), getCurrent().getUserName());
		}
		dgDao.update(po);
		
		progressLogDao.add(po.getProgress().getId(), po.getId(), EunmTask.BK_SH.getStatus(), v.getIsCommit(), v.getAuditMsg(),getCurrent().getDepId(), getCurrent().getDepName(),getCurrent().getAccountId(),getCurrent().getUserName());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, DgVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status", QueryCondition.EQ, Dg.ST_1));
		pageResult.addCondition(new QueryCondition("auditId", QueryCondition.EQ, getCurrent().getAccountId()));
		pageResult = dgDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Dg>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, DgVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("auditDate is not null"));
		pageResult.addCondition(new QueryCondition("auditId", QueryCondition.EQ, getCurrent().getAccountId()));
		pageResult = dgDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Dg>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(DgVo v) throws GlobalException {
		return super.toQueryList(v);
	}
}
