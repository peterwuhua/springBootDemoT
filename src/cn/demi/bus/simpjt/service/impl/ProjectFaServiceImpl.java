package cn.demi.bus.simpjt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmProject;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Org;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.simpjt.dao.IProjectDao;
import cn.demi.bus.simpjt.po.Project;
import cn.demi.bus.simpjt.service.IProjectFaService;
import cn.demi.bus.simpjt.vo.ProjectVo;

/**
 * 方案 业务逻辑层
 * 
 * @author QuJunLong
 */
@Service("bus.simSchemeFaService")
public class ProjectFaServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectFaService {
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IOrgDao orgDao;

	@Override
	public ProjectVo find(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		vo = po2Vo(po);
		if (StrUtils.isBlankOrNull(vo.getFaDate())) {
			vo.setFaDate(DateUtils.getCurrDateTimeStr());
			vo.setFaUserId(getCurrent().getAccountId());
			vo.setFaUserName(getCurrent().getUserName());
		}
		return vo;
	}



	@Override
	public ProjectVo findById(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		vo = po2Vo(po);
		return vo;
	}

	@Override
	public void update(ProjectVo v) throws GlobalException {
		Project po = projectDao.findById(v.getId());
		po.setFaUserId(getCurrent().getAccountId());
		po.setFaUserName(getCurrent().getUserName());
		po.setFaDate(DateUtils.getCurrDateTimeStr());
		po.setFaMsg(v.getFaMsg());
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmProject.PASS_Y)) {
			Org org = null;
			if (po.getItemType().contains("环境咨询")) {
				org = orgDao.findByName("环境");
			} else if (po.getItemType().contains("安全") || po.getItemType().contains("职业卫生")) {
				org = orgDao.findByName("职业卫生");
			}
			if (null != org) {
				po.setOrgId(org.getId());
				po.setOrgName(org.getName());
			}
			Progress pg = progressDao.update4Project(po.getProgress().getId(), EunmProject.PROJECT_BZ.getStatus(), po.getOrgId(), po.getOrgName(), null, null);
			progressLogDao.add4Porject(po.getId(), po.getId(), EunmProject.PROJECT_FA.getStatus(), v.getIsCommit(), v.getFaMsg(), null, null,
					getCurrent().getAccountId(), getCurrent().getUserName());
			po.setStatus(pg.getStatus());
			po.setIsBack(Constants.N);
		}
		projectDao.update(po);
		v.setNo(po.getNo());
	}

	/**
	 * 生成合同编号
	 */
	public String createHtCode() {
		String flag = DateUtils.getYear() + DateUtils.getMonth() + DateUtils.getDay();
		String hql = "SELECT max(htNo) FROM " + projectDao.getEntityName(Project.class) + " WHERE isDel=" + Po.N + " AND htNo like '" + flag + "%'";
		String ls = (String) projectDao.query(hql).getSingleResult();
		String no = flag;
		if (ls == null) {
			no += "01";
		} else {
			ls = ls.replace(flag, "");
			int sort;
			try {
				sort = Integer.valueOf(ls);
			} catch (NumberFormatException e) {
				sort = 0;
			}
			sort++;
			if (sort < 10) {
				no += "0" + sort;
			} else {
				no += sort;
			}
		}
		return no;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ProjectVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.addOrder("startDate", OrderCondition.ORDER_ASC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}

		pageResult.addCondition(new QueryCondition("orgId like '%" + getCurrent().getOrgId() + "%'"));
		pageResult.addCondition(new QueryCondition("status like '" + EunmProject.PROJECT_FA.getStatus() + "'"));
		pageResult = projectDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Project>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql = new StringBuffer("SELECT distinct t FROM " + projectDao.getEntityName(Project.class) + " t,"
				+ progressLogDao.getEntityName(ProgressLog.class) + " log where log.busId=t.id and t.isDel !=" + Po.Y);
		if (QueryConditionList != null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='" + EunmProject.PROJECT_FA.getStatus() + "' AND log.userId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' ");
		hql.append(" ORDER BY t." + pageResult.getOrderColumn() + " " + pageResult.getOrder() + "");
		Query query = projectDao.query(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Project> pjList = projectDao.query(hql.toString()).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		gridVo.setDatas(poList2mapList(pjList));
		pageResult.setPageBean(pager);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

}
