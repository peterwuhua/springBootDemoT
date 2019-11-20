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
import cn.core.framework.utils.openIM.OpenIM;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Org;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.dao.IInvoiceDao;
import cn.demi.bus.pjt.po.Invoice;
import cn.demi.bus.simpjt.dao.IProjectDao;
import cn.demi.bus.simpjt.po.Project;
import cn.demi.bus.simpjt.service.IProjectHtService;
import cn.demi.bus.simpjt.vo.ProjectVo;
import cn.demi.cus.contract.dao.IContractDao;
import cn.demi.cus.contract.po.Contract;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Customer;

@Service("bus.simSchemeHtService")
public class ProjectHtServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectHtService {
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IContractDao contractDao;
	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private IInvoiceDao invoiceDao;
	@Autowired
	private IOrgDao orgDao;

	@Override
	public ProjectVo find(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		if (StrUtils.isBlankOrNull(po.getHtNo())) {
			po.setHtNo(createHtCode(vo));
		}
		vo = po2Vo(po);
		if (StrUtils.isBlankOrNull(vo.getQdDate())) {
			vo.setQdDate(DateUtils.getCurrDateStr());
			vo.setQdId(getCurrent().getAccountId());
			vo.setQdName(getCurrent().getUserName());
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
		po.setQdId(v.getQdId());
		po.setQdName(v.getQdName());
		po.setQdDate(v.getQdDate());
		po.setQdMsg(v.getQdMsg());
		if (StrUtils.isBlankOrNull(po.getHtNo())) {
			po.setHtNo(createHtCode(v));
		} else {
			po.setHtNo(v.getHtNo());
		}
		po.setTotalPrice(v.getTotalPrice());// 总价
		Progress pg = null;
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmProject.PASS_Y)) {
			// 更新合同信息
			update2Ht(po);
			Org org = orgDao.findByName("营销中心");
			if (null != org) {
				po.setOrgId(org.getId());
				po.setOrgName(org.getName());
			}
			pg = progressDao.update4Project(po.getProgress().getId(), EunmProject.PROJECT_DJ.getStatus(), po.getOrgId(), po.getOrgName(), null, null);
			po.setStatus(pg.getStatus());
			progressLogDao.add4Porject(po.getId(), po.getId(), EunmProject.PROJECT_QD.getStatus(), v.getIsCommit(), v.getQdMsg());
		}
		projectDao.update(po);
	}

	/**
	 * 生成编号
	 * 
	 * public String createCode(){ String
	 * flag=DateUtils.getYear()+DateUtils.getMonth()+DateUtils.getDay(); String
	 * hql="SELECT max(no) FROM "+projectDao.getEntityName(Project.class)+" WHERE
	 * isDel="+Po.N+" AND no like '"+flag+"%'"; String ls=(String)
	 * projectDao.query(hql).getSingleResult(); String no=flag; if(ls==null) {
	 * no+="01"; }else { ls=ls.replace(flag, ""); int sort; try { sort =
	 * Integer.valueOf(ls); } catch (NumberFormatException e) { sort=0; } sort++;
	 * if(sort<10) { no+="0"+sort; }else { no+=sort; } } return no; }
	 */
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
		pageResult.addCondition(new QueryCondition("status like '" + EunmProject.PROJECT_QD.getStatus() + "'"));
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
				+ projectDao.getEntityName(ProgressLog.class) + " log where log.busId=t.id and t.isDel !=" + Po.Y);
		if (QueryConditionList != null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='" + EunmProject.PROJECT_QD.getStatus() + "' AND log.userId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' ");
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
	public void update2Ht(Project project) throws GlobalException {
		// 同步合同信息到合同管理下
		Contract contract = contractDao.findByCode(project.getNo());
		if (contract == null) {
			contract = new Contract();
			Customer customer = customerDao.findById(project.getCust().getCustomer().getId());
			contract.setCustomer(customer);
			contract.setOrgId(project.getOrgId());
			contract.setOrgName(project.getOrgName());
			contract.setSaler(customer.getSaler());
			contract.setSalerId(customer.getSalerId());
			contract.setLeadId(getCurrent().getAccountId());
			contract.setLeadName(getCurrent().getUserName());
			contract.setCode(project.getHtNo());
			contract.setContacts(project.getCust().getCustUser());
			contract.setContactPhone(project.getCust().getCustTel());
			contract.setAddress(customer.getAddress());
			contract.setSdate(project.getRdate());
			contract.setEdime(project.getFinishDate());
			contract.setName(project.getSampName());
			contract.setContractSum(String.valueOf(project.getTotalPrice()));
			contract.setStatus(Constants.CONTRACT_STATUS_YQD);
			contractDao.add(contract);
		} else {
			contract.setLeadId(getCurrent().getAccountId());
			contract.setLeadName(getCurrent().getUserName());
			contract.setName(project.getSampName());
			contract.setContractSum(String.valueOf(project.getTotalPrice()));
			contract.setStatus(Constants.CONTRACT_STATUS_YQD);
			contractDao.update(contract);
		}

		if (!StrUtils.isBlankOrNull(project.getUserId())) {
			OpenIM.pushMsg(getCurrent().getLoginName(), accountDao.findById(project.getUserId()).getLoginName(), "合同文件已生成/更新，请及时查收！");
		}
	}

	@Override
	public void updateHtSt(String code) throws GlobalException {
		List<Project> projectList = projectDao.findByProperty("htNo", code);
		if (projectList != null && projectList.size() > 0) {
			Project po = projectList.get(0);
			// po.setHtSt(Constants.CONTRACT_STATUS_YQD);
			projectDao.update(po);// 更新合同信息
		} else {
			throw new GlobalException("数据已过期！");
		}
	}

	/**
	 * 生成合同编号
	 */
	public String createHtCode(ProjectVo vo) {
		String temp = "";
		String flag = temp + DateUtils.getYear();
		String hql = "SELECT max(htNo) FROM " + projectDao.getEntityName(Project.class) + " WHERE isDel=" + Po.N + " AND htNo like '" + flag + "%'";
		String ls = (String) projectDao.query(hql).getSingleResult();
		String no = flag;
		if (ls == null) {
			no += "001";
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
				no += "00" + sort;
			} else if (sort < 100) {
				no += "0" + sort;
			} else {
				no += sort;
			}
		}
		return no;
	}

	@Override
	public double findRatioByInvoiceId(String id) throws GlobalException {
		Invoice po = invoiceDao.findById(id);
		return po.getPayRatio();
	}

}
