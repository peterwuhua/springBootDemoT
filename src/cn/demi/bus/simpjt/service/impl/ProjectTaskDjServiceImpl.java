package cn.demi.bus.simpjt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
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
import cn.demi.base.system.dao.IUnitDao;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.po.Unit;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.dao.ICustDao;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.pjt.vo.CustVo;
import cn.demi.bus.simpjt.dao.IProjectDao;
import cn.demi.bus.simpjt.po.Project;
import cn.demi.bus.simpjt.service.IProjectTaskDjService;
import cn.demi.bus.simpjt.vo.ProjectVo;
import cn.demi.cus.customer.dao.IClientDao;
import cn.demi.cus.customer.dao.IContactsDao;
import cn.demi.cus.customer.dao.ICusClientDao;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Client;
import cn.demi.cus.customer.po.Contacts;
import cn.demi.cus.customer.po.CusClient;
import cn.demi.cus.customer.po.Customer;

/**
 * 项目 业务逻辑层
 * 
 * @author QuJunLong
 */
@Service("bus.simProjectTaskDjService")
public class ProjectTaskDjServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectTaskDjService {

	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private ICustDao custDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IClientDao clientDao;
	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private ICusClientDao cusClientDao;
	@Autowired
	private IContactsDao contactsDao;
	@Autowired
	private IUnitDao unitDao;
	@Autowired
	private IOrgDao orgDao;

	/**
	 * 获取任务详细信息 点位信息
	 */
	@Override
	public ProjectVo findById(String id) throws GlobalException {
		Project p = projectDao.findById(id);
		String cid = p.getCust().getId();
		Cust cus = custDao.findById(cid);
		p.setCust(cus);

		ProjectVo vo = po2Vo(p);
		if (StrUtils.isBlankOrNull(vo.getDate())) {
			vo.setDate(DateUtils.getCurrDateTimeStr());
			vo.setTaskUserId(getCurrent().getAccountId());
			vo.setTaskUserName(getCurrent().getUserName());
		}
		return vo;
	}

	@Override
	public void delete(String... ids) throws GlobalException {
		for (String id : ids) {
			if (!StrUtils.isBlankOrNull(id)) {
				Project p = projectDao.findById(id);
				Progress pg = p.getProgress();
				projectDao.delete(p);
				progressDao.delete(pg);
			}
		}
	}

	@Override
	public void update(ProjectVo v) throws GlobalException {
		Project p = projectDao.findById(v.getId());
		if (StrUtils.isBlankOrNull(p.getDate())) {
			p.setDate(v.getDate());
			p.setTaskUserId(v.getTaskUserId());
			p.setTaskUserName(v.getTaskUserName());
		}
		p.setTaskMsg(v.getTaskMsg());
		uptCustInfo(v, p);
		commit(v, p);
		projectDao.update(p);
	}

	// 检查 及更新 受检单位 委托单位信息
	public void uptCustInfo(ProjectVo v, Project p) throws GlobalException {
		CustVo custVo = v.getCustVo();
		Cust cust = p.getCust();
		if (cust == null)
			cust = new Cust();
		cust = cust.toPo(custVo, cust);
		// 受检单位
		Client client = null;
		if (null != custVo && !StrUtils.isBlankOrNull(custVo.getCustName())) {
			client = clientDao.findByName(custVo.getCustName());
		}
		// 受检单位自维护
		if (null == client && !StrUtils.isBlankOrNull(custVo.getCustName())) {
			client = new Client();
			client.setName(custVo.getCustName());
			client.setAddress(custVo.getCustAddress());
			client.setPerson(custVo.getCustUser());
			client.setPhone(custVo.getCustTel());
			client.setEmail(custVo.getCustEmail());
			client.setFax(custVo.getCustFax());
			client.setZip(custVo.getCustZip());
			client.setIndustry(custVo.getIndustry());
			client.setAttribute(custVo.getAttribute());
			client.setProduct(custVo.getProduct());
			clientDao.add(client);
		} else if (null != client) {
			// 同步受检单位信息
			if (!StrUtils.isBlankOrNull(custVo.getCustZip())) {
				client.setZip(custVo.getCustZip());
			}
			if (!StrUtils.isBlankOrNull(custVo.getCustFax())) {
				client.setFax(custVo.getCustFax());
			}
			if (!StrUtils.isBlankOrNull(custVo.getCustAddress())) {
				client.setAddress(custVo.getCustAddress());
			}
			if (!StrUtils.isBlankOrNull(custVo.getCustUser())) {
				client.setPerson(custVo.getCustUser());
			}
			if (!StrUtils.isBlankOrNull(custVo.getCustTel())) {
				client.setPhone(custVo.getCustTel());
			}
			clientDao.update(client);
		}
		cust.setClient(client);
		// 委托单位
		Customer customer = null;
		if (null != custVo && !StrUtils.isBlankOrNull(custVo.getWtName())) {
			customer = customerDao.findByName(custVo.getWtName());
		}
		// 委托单位自维护
		if (null == customer && !StrUtils.isBlankOrNull(custVo.getWtName())) {
			customer = new Customer();
			customer.setName(custVo.getWtName());
			customer.setAddress(custVo.getWtAddress());
			customer.setPerson(custVo.getWtUser());
			customer.setPhone(custVo.getWtTel());
			customer.setTelephone(custVo.getWtTel());
			customer.setEmail(custVo.getWtEmail());
			customer.setFax(custVo.getWtFax());
			customer.setZip(custVo.getWtZip());
			customer.setIndustry(custVo.getWtIndustry());
			customer.setCusType(custVo.getWtAttribute());
			customer.setBuildDate(p.getDate());
			customer.setCode(customerDao.createCode());
			customerDao.add(customer);
		} else if (null == customer && !StrUtils.isBlankOrNull(custVo.getCustName())) {
			// 当委托单位为null时，委托单位=受检单位
			cust.setWtName(custVo.getCustName());
			cust.setWtAddress(custVo.getCustAddress());
			cust.setWtUser(custVo.getCustUser());
			cust.setWtTel(custVo.getCustTel());
			cust.setWtEmail(custVo.getCustEmail());
			cust.setWtFax(custVo.getCustFax());
			cust.setWtZip(custVo.getCustZip());
			cust.setWtIndustry(custVo.getIndustry());
			cust.setWtAttribute(custVo.getAttribute());
			customer = customerDao.findByName(cust.getWtName());
			if (customer == null) {
				customer = new Customer();
				customer.setName(cust.getWtName());
				customer.setAddress(cust.getWtAddress());
				customer.setPerson(cust.getWtUser());
				customer.setPhone(cust.getWtTel());
				customer.setTelephone(cust.getWtTel());
				customer.setEmail(cust.getWtEmail());
				customer.setFax(cust.getWtFax());
				customer.setZip(cust.getWtZip());
				customer.setIndustry(cust.getWtIndustry());
				customer.setCusType(cust.getWtAttribute());
				customer.setBuildDate(p.getDate());
				customer.setCode(customerDao.createCode());
				customerDao.add(customer);
			} else {
				// 同步受检单位信息
				if (!StrUtils.isBlankOrNull(cust.getWtZip())) {
					customer.setZip(cust.getWtZip());
				}
				if (!StrUtils.isBlankOrNull(cust.getWtFax())) {
					customer.setFax(cust.getWtFax());
				}
				if (!StrUtils.isBlankOrNull(cust.getWtAddress())) {
					customer.setAddress(cust.getWtAddress());
				}
				if (!StrUtils.isBlankOrNull(cust.getWtUser())) {
					customer.setPerson(cust.getWtUser());
				}
				if (!StrUtils.isBlankOrNull(cust.getWtTel())) {
					if (cust.getWtTel().length() == 11 && !cust.getWtTel().contains("-")) {
						customer.setPhone(cust.getWtTel());
					} else {
						customer.setTelephone(cust.getWtTel());
					}
				}
				customerDao.update(customer);
			}
		} else if (null != customer) {
			// 同步受检单位信息
			if (!StrUtils.isBlankOrNull(custVo.getWtZip())) {
				customer.setZip(custVo.getWtZip());
			}
			if (!StrUtils.isBlankOrNull(custVo.getWtFax())) {
				customer.setFax(custVo.getWtFax());
			}
			if (!StrUtils.isBlankOrNull(custVo.getWtAddress())) {
				customer.setAddress(custVo.getWtAddress());
			}
			if (!StrUtils.isBlankOrNull(custVo.getWtUser())) {
				customer.setPerson(custVo.getWtUser());
			}
			if (!StrUtils.isBlankOrNull(custVo.getWtTel())) {
				if (custVo.getWtTel().length() == 11 && !custVo.getWtTel().contains("-")) {
					customer.setPhone(custVo.getWtTel());
				} else {
					customer.setTelephone(custVo.getWtTel());
				}
			}
			customerDao.update(customer);
		}
		if (customer != null) {
			cust.setCustomer(customer);
			// 委托单位联系人自维护
			Contacts ct = contactsDao.findBycusId(customer.getId(), customer.getPerson());
			if (null == ct) {
				ct = new Contacts();
				ct.setName(customer.getPerson());
				ct.setPhone(customer.getPhone());
				ct.setCustomer(customer);
				contactsDao.add(ct);
			}
			// 建立受检单位和委托单位关系
			CusClient cc = cusClientDao.findByClientIdAndCustId(client.getId(), customer.getId());
			if (cc == null) {
				cc = new CusClient();
				cc.setClient(client);
				cc.setCustomer(customer);
				cusClientDao.add(cc);
			}
		}

		// 插入检测单位信息
		if (StrUtils.isBlankOrNull(cust.getUnit())) {
			Unit unit = unitDao.findById("0");
			if (null != unit) {
				cust.setUnit(unit.getName());
				cust.setAddress(unit.getAddress());
				cust.setUrl(unit.getWebsite());
				cust.setUser(unit.getLinkMan());
				cust.setTel(unit.getTelephone());
				cust.setZip(unit.getPost());
			}
		}
		custDao.saveOrUpdate(cust);
		p.setCust(cust);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ProjectVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("status", OrderCondition.ORDER_ASC);
			pageResult.addOrder("lastUpdTime", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("orgId like '%" + getCurrent().getOrgId() + "%'"));
		pageResult.addCondition(new QueryCondition("status='" + EunmProject.PROJECT_DJ.getStatus() + "'"));
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
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql = new StringBuffer("SELECT distinct t FROM " + projectDao.getEntityName(Project.class) + " t,"
				+ projectDao.getEntityName(ProgressLog.class) + " log where log.busId=t.id and t.isDel =" + Po.N);
		if (QueryConditionList != null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='" + EunmProject.PROJECT_DJ.getStatus() + "' AND log.userId like '" + CurrentUtils.getCurrent().getAccountId() + "' ");
		hql.append(" ORDER BY t." + pageResult.getOrderColumn() + " " + pageResult.getOrder() + "");
		pageResult = projectDao.getPageResult(hql.toString(), pageResult);
		gridVo.setDatas(poList2mapList((List<Project>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<Map<?, Object>> poList2mapList(List<Project> list) throws GlobalException {
		List<Map<?, Object>> tempList = new ArrayList<Map<?, Object>>();
		for (Project p : list) {
			Map<String, Object> map = po2map(p);
			tempList.add(map);
		}
		return tempList;
	}

	/**
	 * 普通任务 提交
	 * 
	 * @param v
	 * @param p
	 * @throws GlobalException
	 */
	public void commit(ProjectVo v, Project p) throws GlobalException {
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmProject.PASS_Y)) {
			Progress pg = p.getProgress();

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

			if (p.getXctk().equals(Constants.S)) {
				pg = progressDao.update4Project(pg.getId(), EunmProject.PROJECT_ZP.getStatus(), p.getOrgId(), p.getOrgName(), null, null);
			} else if (p.getXctk().equals(Constants.F)) {
				if (p.getFabz().equals(Constants.S)) {
					pg = progressDao.update4Project(p.getProgress().getId(), EunmProject.PROJECT_FA.getStatus(), p.getOrgId(), p.getOrgName(), null, null);
				} else {
					pg = progressDao.update4Project(p.getProgress().getId(), EunmProject.PROJECT_BZ.getStatus(), p.getOrgId(), p.getOrgName(), null, null);
				}
			}
			progressLogDao.add4Porject(p.getId(), p.getId(), EunmProject.PROJECT_DJ.getStatus(), v.getIsCommit(), v.getTaskMsg(), p.getOrgId(), p.getOrgName(),
					getCurrent().getAccountId(), getCurrent().getUserName());
			// 更新项目状态
			p.setStatus(pg.getStatus());
			p.setIsBack(Constants.N);
			p.setProgress(pg);
		}
	}

	@Override
	public void update2Stop(ProjectVo v) throws GlobalException {
		List<Project> pList = projectDao.listByIds(v.getIds());
		for (Project project : pList) {
			Progress pg = project.getProgress();
			progressLogDao.add(project.getId(), project.getId(), EunmProject.PROJECT_DJ.getStatus(), EunmProject.PROJECT_STOP.getStatus(),
					"项目终止,原因：" + v.getRemark());
			project.setStatus(EunmProject.PROJECT_STOP.getStatus());
			project.setProgress(null);
			projectDao.update(project);
			// 清除流程实例，记录日志记录
			if (null != pg) {
				progressDao.delete(pg);
			}
		}
	}

}
