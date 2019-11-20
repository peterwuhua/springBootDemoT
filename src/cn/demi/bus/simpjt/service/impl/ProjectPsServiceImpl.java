package cn.demi.bus.simpjt.service.impl;

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
import cn.demi.bus.simpjt.service.IProjectPsService;
import cn.demi.bus.simpjt.vo.ProjectHtBaseVo;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Service("bus.simProjectVoPsService")
public class ProjectPsServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectPsService {

	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private IOrgDao orgDao;

	@Override
	public ProjectVo findById(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		vo = po2Vo(po);
		if (StrUtils.isBlankOrNull(vo.getPsDate())) {
			vo.setPsCt("检测项目及方法、人力资源是否满足、仪器设备是否满足、现场检测时间是否可行、提交报告时间是否可行、检测（评价）费用是否合理、客户特殊需求是否能够满足。");
			vo.setPsResult("1.检测项目及方法满足需求;\r\n2.人力资源满足;\r\n3.仪器设备满足;\r\n4.现场检测时间可行;\r\n5.提交报告时间可行;\r\n6.检测（评价）费用合理;\r\n7.客户特殊需求能够满足");
		}
		return vo;
	}

	@Override
	public void update(ProjectVo v) throws GlobalException {
		Project po = projectDao.findById(v.getId());
		po.setPsId(v.getPsId());
		po.setPsName(v.getPsName());
		po.setPsDate(v.getPsDate());
		po.setPsResult(v.getPsResult());
		po.setPsCt(v.getPsCt());
		po.setPsMsg(v.getPsMsg());
		Progress pg = null;
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmProject.PASS_Y)) {
			pg = progressDao.update4Project(po.getProgress().getId(), EunmProject.PROJECT_QD.getStatus(), null, null, po.getUserId(), po.getUserName());
		} else if (null != v.getIsCommit() && v.getIsCommit().equals(EunmProject.PASS_N)) {
			pg = progressDao.update4Project(po.getProgress().getId(), EunmProject.PROJECT_LX.getStatus(), null, null, po.getUserId(), po.getUserName());
			po.setIsBack(Constants.Y);
		}
		po.setStatus(pg.getStatus());
		projectDao.update(po);
		progressLogDao.add4Porject(po.getId(), po.getId(), EunmProject.PROJECT_PS.getStatus(), v.getIsCommit(), v.getPsMsg());
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ProjectVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status like '" + EunmProject.PROJECT_PS.getStatus() + "'"));
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
		hql.append(" AND log.status='" + EunmProject.PROJECT_PS.getStatus() + "' AND log.userId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' ");
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

	@Override
	public void update2Stop(ProjectVo v) throws GlobalException {
		List<Project> schList = projectDao.listByIds(v.getIds());
		for (Project project : schList) {
			Progress pg = project.getProgress();
			if (null != pg) {
				progressLogDao.add4Porject(project.getId(), project.getId(), pg.getStatus(), EunmProject.PROJECT_STOP.getStatus(), "项目终止,原因：" + v.getPsMsg());
				project.setStatus(EunmProject.PROJECT_STOP.getStatus());
				project.setProgress(null);
				projectDao.update(project);
				progressDao.delete(pg);
			}
		}
	}

	@Override
	public void update2PsFile(ProjectVo v) throws GlobalException {
		Project po = projectDao.findById(v.getId());
		projectDao.update(po);
	}

	@Override
	public ProjectVo find4File(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		vo = po2Vo(po);
		// 费用信息
		if (!StrUtils.isBlankOrNull(vo.getPsCt())) {
			vo.setPsCt(vo.getPsCt().replace("\r\n", "<w:p></w:p>"));
		}
		if (!StrUtils.isBlankOrNull(vo.getPsResult())) {
			vo.setPsResult(vo.getPsResult().replace("\r\n", "<w:p></w:p>"));
		}
		vo.setPsDate(DateUtils.getChineseDate(vo.getPsDate()));
		ProjectHtBaseVo pjtHtBaseVo = new ProjectHtBaseVo();
		vo.setPjtHtBaseVo(pjtHtBaseVo);
		return vo;
	}
}
