package cn.demi.office.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.task.po.Task;
import cn.demi.office.dao.ICmDao;
import cn.demi.office.po.Cm;
import cn.demi.office.service.ICmAuditService;
import cn.demi.office.vo.CmVo;

@Service("office.cmAuditService")
public class CmAuditServiceImpl extends BaseServiceImpl<CmVo, Cm> implements ICmAuditService {
	@Autowired
	private ICmDao cmDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;

	@Override
	public CmVo findById(String id) throws GlobalException {
		Cm po = cmDao.findById(id);
		return po2Vo(po);
	}

	@Override
	public void update(CmVo v) throws GlobalException {
		Cm p = cmDao.findById(v.getId());
		p.setAuditId(v.getAuditId());
		p.setAuditName(v.getAuditName());
		p.setAuditCt(v.getAuditCt());
		p.setAuditTime(v.getAuditTime());
		p.setPrice(v.getPrice());
		p.setHours(v.getHours());
		p.setContent(v.getContent());
		Progress pg = progressDao.findByBusId(p.getId());
		String loguId = p.getUserId();
		String loguName = p.getUserName();
		if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			p.setAuditResult("通过");
			if (!StrUtils.isBlankOrNull(v.getAuditId())) {
				p.setFstatus(Cm.ST_1);
				progressDao.update(pg.getId(), EunmTask.GS_SH.getStatus(), p.getUserId(), p.getUserName());
				loguId = p.getAuditId();
				loguName = p.getAuditName();
			} else {
				p.setFstatus(Cm.ST_2);
				progressDao.update(pg.getId(), EunmTask.GS_CS.getStatus(), p.getViewIds(), p.getViewNames());
			}
			p.setStatus(pg.getStatus());
		} else if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_N)) {
			p.setAuditResult("不通过");
			p.setFstatus(Cm.ST_0);
			progressDao.update(pg.getId(), EunmTask.GS_SQ.getStatus(), p.getUserId(), p.getUserName());
			p.setStatus(pg.getStatus());
		}
		progressLogDao.add(p.getId(), p.getId(), EunmTask.GS_SH.getStatus(), v.getIsCommit(), p.getRemark(), p.getOrgId(), p.getOrgName(), loguId, loguName);
		cmDao.update(p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CmVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("fstatus", QueryCondition.EQ, Cm.ST_1));
		pageResult.addCondition(new QueryCondition("status", QueryCondition.EQ, EunmTask.GS_SH.getStatus()));
		pageResult.addCondition(new QueryCondition("auditId", QueryCondition.AK, getCurrent().getAccountId()));
		pageResult = cmDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Cm>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, CmVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql = new StringBuffer("SELECT distinct t FROM " + cmDao.getEntityName(Cm.class) + " t," + cmDao.getEntityName(ProgressLog.class)
				+ " log where log.busId=t.id and t.isDel =" + Po.N);
		if (QueryConditionList != null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='" + EunmTask.GS_SH.getStatus() + "' AND log.userId like '" + CurrentUtils.getCurrent().getAccountId() + "%' ");
		hql.append(" ORDER BY t." + pageResult.getOrderColumn() + " " + pageResult.getOrder() + "");
		pageResult = cmDao.getPageResult(hql.toString(), pageResult);
		gridVo.setDatas(poList2mapList((List<Cm>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
