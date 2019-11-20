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
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IFilesDao;
import cn.demi.base.system.po.Files;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.simpjt.dao.IProjectDao;
import cn.demi.bus.simpjt.po.Project;
import cn.demi.bus.simpjt.service.IProjectReptShService;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Service("bus.simProjectReptShService")
public class ProjectReptShServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectReptShService {
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IFilesDao filesDao;

	@Override
	public ProjectVo findById(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		vo = vo.toVo(po);
		// 附件
		List<FilesVo> fileList = new ArrayList<FilesVo>();
		String hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + vo.getId() + "' ";
		List<Files> fList = filesDao.list(hql);
		if (null != fList) {
			for (Files f : fList) {
				FilesVo fVo = new FilesVo();
				fVo = fVo.toVo(f);
				fileList.add(fVo);
			}
		}
		vo.setFileList(fileList);
		return vo;
	}

	@Override
	public void update(ProjectVo v) throws GlobalException {
		Project po = projectDao.findById(v.getId());
		po.setAuditDate(v.getAuditDate());
		po.setAuditUser(v.getAuditUser());
		po.setAuditUserId(v.getAuditUserId());
		po.setAuditMsg(v.getAuditMsg());
		// 插入任务进度日志
		Progress pg = po.getProgress();
		progressLogDao.add4Porject(po.getId(), po.getId(), EunmProject.PROJECT_BP.getStatus(), v.getIsCommit(), v.getAuditMsg()); 
		if (v.getIsCommit().equals(EunmProject.PASS_Y)) {
			pg = progressDao.update4Project(pg.getId(), EunmProject.PROJECT_BA.getStatus(),null, null, po.getMakeUserId(), po.getMakeUser());
		} else if (v.getIsCommit().equals(EunmProject.PASS_N)) {
			pg = progressDao.update4Project(pg.getId(), EunmProject.PROJECT_BZ.getStatus(), po.getMakeUserId(), po.getMakeUser());
			po.setIsBack(Constants.Y);
			
		}
		po.setStatus(pg.getStatus());
		projectDao.update(po);
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

		pageResult.addCondition(new QueryCondition("auditUserId like '%" + CurrentUtils.getCurrent().getAccountId() + "%'  "));
		pageResult.addCondition(new QueryCondition("status='" + EunmProject.PROJECT_BP.getStatus() + "' "));
		pageResult = projectDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Project>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<QueryCondition> toQueryList(ProjectVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		return queryList;
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
				+ projectDao.getEntityName(ProgressLog.class) + " log where log.busId=t.id and t.isDel !=" + Po.Y);
		if (QueryConditionList != null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='" + EunmProject.PROJECT_BP.getStatus() + "' AND log.userId like '" + CurrentUtils.getCurrent().getAccountId() + "' ");
		hql.append(" ORDER BY t." + pageResult.getOrderColumn() + " " + pageResult.getOrder() + "");
		Query query = projectDao.query(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Project> taskList = projectDao.query(hql.toString()).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		gridVo.setDatas(poList2mapList(taskList));
		pageResult.setPageBean(pager);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
