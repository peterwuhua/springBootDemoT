package cn.demi.bus.simpjt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import cn.core.framework.utils.openIM.OpenIM;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Org;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.dao.ICustDao;
import cn.demi.bus.pjt.dao.ICustPointDao;
import cn.demi.bus.pjt.dao.ICustRoomDao;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.simpjt.dao.IProjectDao;
import cn.demi.bus.simpjt.po.Project;
import cn.demi.bus.simpjt.service.IProjectTkService;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Service("bus.simSchemeTkService")
public class ProjectTkServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectTkService {
	@Autowired
	private ICustDao custDao;
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private ICustRoomDao custRoomDao;
	@Autowired
	private ICustPointDao custPointDao;
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private IImDao imDao;


	@Override
	public ProjectVo find(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = po2Vo(po);
		return vo;
	}
  
	@Override
	public ProjectVo findById(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = po2Vo(po);
		return vo;
	}

	@Override
	public void update4Survey(ProjectVo v) throws GlobalException {
		Project p = projectDao.findById(v.getId());
		p.setTkDate(v.getTkDate());
		p.setTkMsg(v.getTkMsg());
		p.setTkUserId(v.getTkUserId());
		p.setTkUserName(v.getTkUserName());
		Cust cust = p.getCust();
		if (null != v.getCustVo()) {
			cust.setTkJdr(v.getCustVo().getTkJdr());
			custDao.update(cust);
		}
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmProject.PASS_Y)) {
			// 更新流程
			Org org = null;
			if (p.getItemType().contains("环境咨询")) {
				org = orgDao.findByName("环境");
			} else if (p.getItemType().contains("安全") || p.getItemType().contains("职业卫生")) {
				org = orgDao.findByName("职业卫生");
			}
			if (null != org) {
				p.setOrgId(org.getId());
				p.setOrgName(org.getName());
			}
			if (p.getFabz().equals(Constants.S)) {
				Progress pg = progressDao.update4Project(p.getProgress().getId(), EunmProject.PROJECT_FA.getStatus(), p.getOrgId(), p.getOrgName(), null, null);
				p.setStatus(pg.getStatus());
			} else {
				Progress pg = progressDao.update4Project(p.getProgress().getId(), EunmProject.PROJECT_BZ.getStatus(), p.getOrgId(), p.getOrgName(), null, null);
				p.setStatus(pg.getStatus());
			}
			progressLogDao.add4Porject(p.getId(), p.getId(), EunmProject.PROJECT_TK.getStatus(), EunmProject.PASS_Y, v.getTkMsg());
		}
		projectDao.update(p);
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

		pageResult.addCondition(new QueryCondition("tkUserId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' "));
		pageResult.addCondition(new QueryCondition("status like '" + EunmProject.PROJECT_TK.getStatus() + "'"));
		pageResult = projectDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Project>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
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
		hql.append(" AND log.status='" + EunmProject.PROJECT_TK.getStatus() + "' AND log.userId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' ");
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<?, Object>> poList2mapList(List<Project> list) throws GlobalException {
		List tempList = new ArrayList();
		for (Project p : list) {
			Map<String, Object> map = po2map(p);
			if (null != map.get("tkDate")) {
				String tdate = String.valueOf(map.get("tkDate"));
				long n = DateUtils.getIntevalDays(DateUtils.getCurrDateStr(), tdate);
				if (n < 0) {
					map.put("color", "red");
				} else if (n < 3) {
					map.put("color", "blue");
				}
			}
			tempList.add(map);
		}
		return tempList;
	}

	@Override
	public void excutSchedule() throws GlobalException {
		String hql = "FROM " + projectDao.getEntityName(Project.class) + " WHERE isDel='" + Po.N + "' AND status='" + EunmProject.PROJECT_TK.getName() + "'";
		List<Project> schemeList = projectDao.list(hql);
		if (null != schemeList) {
			for (Project project : schemeList) {
				try {
					long d = DateUtils.getIntevalDays(DateUtils.getCurrDateStr(), project.getTkDate());
					if (d == 0) {// 当天
						String msg = "【系统提示】项目 编号为" + project.getNo() + "的任务今天到期，请及时处理！";
						List<Account> atList = accountDao.listByIds(project.getTkUserId());
						if (null != atList) {
							for (Account at : atList) {
								OpenIM.pushMsg(Constants.SUADMIN_LOGIN, at.getLoginName(), msg);
							}
						}
					} else if (d < 0) {
						String msg = "【系统提示】项目 编号为" + project.getNo() + "的任务已超期" + (-d) + "天，请及时处理！";
						List<Account> atList = accountDao.listByIds(project.getTkUserId());
						if (null != atList) {
							for (Account at : atList) {
								OpenIM.pushMsg(Constants.SUADMIN_LOGIN, at.getLoginName(), msg);
							}
						}
						OpenIM.pushMsg(Constants.SUADMIN_LOGIN, accountDao.findById(project.getZpUserId()).getLoginName(), msg);
					}
				} catch (Exception e) {
					log.error("定时任务：踏勘任务【" + project.getNo() + "】定时检测异常！", e);
				}
			}
		}
	}

}
