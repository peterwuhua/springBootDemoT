package cn.demi.bus.pjt.service.impl;

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
import cn.core.framework.constant.EunmTask;
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
import cn.demi.bus.pjt.dao.IProjectDao;
import cn.demi.bus.pjt.dao.IProjectFbDao;
import cn.demi.bus.pjt.dao.ISchemeDao;
import cn.demi.bus.pjt.dao.ISchemePointDao;
import cn.demi.bus.pjt.po.Invoice;
import cn.demi.bus.pjt.po.Project;
import cn.demi.bus.pjt.po.ProjectFb;
import cn.demi.bus.pjt.po.Scheme;
import cn.demi.bus.pjt.po.SchemePoint;
import cn.demi.bus.pjt.service.IProjectPsService;
import cn.demi.bus.pjt.vo.InvoiceVo;
import cn.demi.bus.pjt.vo.ProjectFbVo;
import cn.demi.bus.pjt.vo.ProjectHtBaseVo;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.bus.pjt.vo.SchemePointVo;
import cn.demi.bus.pjt.vo.SchemeVo;

@Service("bus.projectVoPsService")
public class ProjectPsServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectPsService {

	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private ISchemeDao schemeDao;
	@Autowired
	private ISchemePointDao schemePointDao;
	@Autowired
	private IProjectFbDao projectFbDao;
	@Autowired
	private IOrgDao orgDao;

	@Override
	public ProjectVo findById(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		vo = po2Vo(po);
		// 费用信息
		Invoice invoice = po.getInvoice();
		InvoiceVo invoiceVo = new InvoiceVo();
		if (null != invoice) {
			invoiceVo = invoiceVo.toVo(invoice);
		}
		vo.setInvoiceVo(invoiceVo);
		List<Scheme> schList = schemeDao.listByPjId(po.getId());
		List<SchemeVo> schVoList = new ArrayList<SchemeVo>();
		for (Scheme scheme : schList) {
			SchemeVo schemeVo = new SchemeVo();
			schemeVo = schemeVo.toVo(scheme);
			List<SchemePoint> detailList = schemePointDao.listBySchId(scheme.getId());
			if (null != detailList) {
				List<SchemePointVo> detailVoList = new ArrayList<SchemePointVo>();
				for (SchemePoint detail : detailList) {
					SchemePointVo detailVo = new SchemePointVo();
					detailVo = detailVo.toVo(detail);
					detailVoList.add(detailVo);
				}
				schemeVo.setPointList(detailVoList);
			}
			schVoList.add(schemeVo);
		}
		vo.setSchemeList(schVoList);
		List<ProjectFb> fbList = projectFbDao.listByProjectId(po.getId());
		if (null != fbList) {
			List<ProjectFbVo> fbVoList = new ArrayList<>();
			for (ProjectFb fb : fbList) {
				ProjectFbVo fbVo = new ProjectFbVo();
				fbVo = fbVo.toVo(fb);
				fbVoList.add(fbVo);
			}
			vo.setFbList(fbVoList);
		}
		if (StrUtils.isBlankOrNull(vo.getPsDate())) {
			vo.setPsCt("检测项目及方法、人力资源是否满足、仪器设备是否满足、现场检测时间是否可行、提交报告时间是否可行、检测（评价）费用是否合理、客户特殊需求是否能够满足。");
			vo.setPsResult(
					"1、所检项目及检测方法在本实验室检测能力范围内；\r\n2、人力资源可以满足本次检测需要；\r\n3、仪器设备可以满足本次检测需要；\r\n4、现场检测时间可行；\r\n5、本实验室能够保证在规定时间内提交报告；\r\n6、检测费用较为合理；\r\n7、本次检测客户未提出特殊要求；\r\n8、本次检测为企业委托指定检测地点及检测项目。");
		}
		return vo;
	}

	@Override
	public void update(ProjectVo v) throws GlobalException {
		Project po = projectDao.findById(v.getId());
		po.setPsDate(v.getPsDate());
		po.setPsResult(v.getPsResult());
		po.setPsCt(v.getPsCt());
		// po.setPzId(v.getPzId());
		// po.setPzName(v.getPzName());
		po.setPsMsg(v.getPsMsg());
		Progress pg = null;
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			po.setSalerId(po.getCust().getCustomer().getSalerId());
			po.setSalerName(po.getCust().getCustomer().getSaler());
			pg = progressDao.update(po.getProgress().getId(), EunmTask.PROJECT_QD.getStatus(), null, null,null, null);
			po.setStatus(pg.getStatus());
		} else if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_N)) {
			pg = progressDao.update(po.getProgress().getId(), EunmTask.PROJECT_BZ.getStatus(), null, null, po.getFaUserId(), po.getFaUserName());
			po.setIsBack(Constants.Y);
			po.setStatus(pg.getStatus());
		}
		projectDao.update(po);
		progressLogDao.add(po.getId(), po.getId(), EunmTask.PROJECT_PS.getStatus(), v.getIsCommit(), v.getPsMsg());
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
		List<String> orgIds = orgDao.listChild(getCurrent().getOrgId());
		orgIds.add(getCurrent().getOrgId());
		StringBuffer hql = new StringBuffer();
		hql.append(" (");
		for (int i = 0; i < orgIds.size(); i++) {
			String orgId = orgIds.get(i);
			hql.append(" orgIds like '%" + orgId + "%' ");
			if (i < orgIds.size() - 1) {
				hql.append(" or ");
			}
		}
		hql.append(" )");
		pageResult.addCondition(new QueryCondition(hql.toString()));
		pageResult.addCondition(new QueryCondition(" status like '" + EunmTask.PROJECT_PS.getStatus() + "'"));
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
		hql.append(" AND log.status='" + EunmTask.PROJECT_PS.getStatus() + "' AND log.userId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' ");
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
				progressLogDao.add(project.getId(), project.getId(), pg.getStatus(), EunmTask.TASK_STOP.getStatus(), "项目终止,原因：" + v.getPsMsg());
				project.setStatus(EunmTask.TASK_STOP.getStatus());
				project.setProgress(null);
				projectDao.update(project);
				progressDao.delete(pg);
			}
		}
	}

	@Override
	public void update2PsFile(ProjectVo v) throws GlobalException {
		Project po = projectDao.findById(v.getId());
		po.setPsFileName(v.getPsFileName());
		po.setPsFilePath(v.getPsFilePath());
		projectDao.update(po);
	}

	@Override
	public ProjectVo find4File(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		vo = po2Vo(po);
		// 费用信息
		Invoice invoice = po.getInvoice();
		InvoiceVo invoiceVo = new InvoiceVo();
		if (null != invoice) {
			invoiceVo = invoiceVo.toVo(invoice);
		}
		vo.setInvoiceVo(invoiceVo);
		if (!StrUtils.isBlankOrNull(vo.getPsCt())) {
			vo.setPsCt(vo.getPsCt().replace("\r\n", "<w:p></w:p>"));
		}
		if (!StrUtils.isBlankOrNull(vo.getPsResult())) {
			vo.setPsResult(vo.getPsResult().replace("\r\n", "<w:p></w:p>"));
		}
		if (!StrUtils.isBlankOrNull(vo.getPsDate())) {
			vo.setPsDate(DateUtils.getChineseDate(vo.getPsDate()));
		}
		ProjectHtBaseVo pjtHtBaseVo = new ProjectHtBaseVo();
		vo.setPjtHtBaseVo(pjtHtBaseVo);
		return vo;
	}
}
