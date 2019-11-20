package cn.demi.bus.simpjt.service.impl;

import java.util.ArrayList;
import java.util.Collections;
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
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.AccountRole;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.po.Role;
import cn.demi.base.system.po.User;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.vo.AuVo;
import cn.demi.bus.simpjt.dao.IProjectDao;
import cn.demi.bus.simpjt.po.Project;
import cn.demi.bus.simpjt.service.IProjectZpService;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Service("bus.simProjectZpService")
public class ProjectZpServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectZpService {

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
		ProjectVo vo = po2Vo(po);
		return vo;
	}

	@Override
	public void update(ProjectVo v) throws GlobalException {
		Project project = projectDao.findById(v.getId());
		// 更新指派信息
		project.setTkUserId(v.getTkUserId());
		project.setTkUserName(v.getTkUserName());
		project.setTkDate(v.getTkDate());
		project.setZpDate(DateUtils.getCurrDateTimeStr());
		project.setZpUserId(getCurrent().getAccountId());
		project.setZpUserName(getCurrent().getUserName());
		project.setZpMsg(v.getZpMsg());
		// 更新流程
		String xmStatus = "";
		if (project.getXctk().equals(Constants.S)) {
			xmStatus = EunmProject.PROJECT_TK.getStatus();
			project.setStatus(xmStatus);
			progressDao.update4Project(project.getProgress().getId(), xmStatus, project.getTkUserId(), project.getTkUserName());
		} 
/*		else if (project.getXctk().equals(Constants.F)) {
			
			Org org = null;
			if (project.getItemType().contains("环境咨询")) {
				org = orgDao.findByName("环境");
			} else if (project.getItemType().contains("安全") || project.getItemType().contains("职业卫生")) {
				org = orgDao.findByName("职业卫生");
			}
			if (null != org) {
				project.setOrgId(org.getId());
				project.setOrgName(org.getName());
			}
			if (project.getFabz().equals(Constants.S)) {
				Progress pg = progressDao.update4Project(project.getProgress().getId(), EunmProject.PROJECT_FA.getStatus(), project.getOrgId(), project.getOrgName(), null, null);
				project.setStatus(pg.getStatus());
			} else {
				
				Progress pg = progressDao.update4Project(project.getProgress().getId(), EunmProject.PROJECT_BZ.getStatus(), project.getOrgId(), project.getOrgName(), null, null);
				project.setStatus(pg.getStatus());
			}
			
		}*/

		projectDao.update(project);
		progressLogDao.add4Porject(project.getId(), project.getId(), EunmProject.PROJECT_ZP.getStatus(), EunmProject.PASS_Y, v.getZpMsg(),null,null,project.getZpUserId(),project.getZpUserName());
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
		pageResult.addCondition(new QueryCondition("orgId like '%" + getCurrent().getOrgId() + "%'"));
		pageResult.addCondition(new QueryCondition("status like '" + EunmProject.PROJECT_ZP.getStatus() + "'"));
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
		hql.append(" AND log.status='" + EunmProject.PROJECT_ZP.getStatus() + "' AND log.userId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' ");
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
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AuVo> listAu(String itemType) throws GlobalException {
		Org org=null;
		if (itemType.contains("环境咨询")||itemType.contains("环境")) {
			org=orgDao.findByName("环境");
		} else if (itemType.contains("安全") || itemType.contains("职业卫生")) {
			org=orgDao.findByName("职业卫生");
		}
		List<String>  orgIds=orgDao.listChild(org.getId());
		orgIds.add(org.getId());
		StringBuffer sql=new StringBuffer("SELECT at.id,u.name,u.no,u.mobile FROM "+tablePrefix+projectDao.getEntityName(Account.class)+" at ");
		sql.append(" JOIN "+tablePrefix+projectDao.getEntityName(User.class)+" u ON u.id=at.user_id");
		sql.append(" JOIN "+tablePrefix+projectDao.getEntityName(AccountRole.class)+" ar ON ar.account_id=at.id");
		sql.append(" JOIN "+tablePrefix+projectDao.getEntityName(Role.class)+" r ON r.id=ar.role_id");
		sql.append(" WHERE at.is_del='"+Po.N+"' ");
		sql.append(" AND at.org_id in('"+String.join("','", orgIds)+"')");
		sql.append(" AND r.code='"+Constants.ROLE_TK+"'");
		sql.append(" group by at.id ");
		sql.append(" order BY u.sort asc");
		List<Object[]> lt=projectDao.queryBySql(sql.toString());
		String sql2="select bp.tk_user_id from "+tablePrefix+projectDao.getEntityName(Project.class)+" bp where bp.is_del='0' AND bp.status ='"+EunmTask.PROJECT_TK.getStatus()+"' ";
		List<String> ptList=projectDao.queryBySql(sql2.toString());
		List<AuVo> auList=new ArrayList<>();
		for (Object[] obj : lt) {
			AuVo atVo=new AuVo();
			atVo.setId(String.valueOf(obj[0]));
			atVo.setName(String.valueOf(obj[1]));
			atVo.setNo(String.valueOf(obj[2]));
			atVo.setMobile(String.valueOf(obj[3]));
			if(null!=ptList) {
				int n=0;
				for (String tkIds : ptList) {
					if(tkIds.contains(atVo.getId())) {
					n++;	
					}
				}
				atVo.setNum(n);
			}
			auList.add(atVo);
		}
		Collections.sort(auList, new AuVo());
		return auList;
	}

}
