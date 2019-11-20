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
import cn.demi.bus.simpjt.service.IProjectService;
import cn.demi.bus.simpjt.vo.ProjectVo;
import cn.demi.cus.customer.dao.IClientDao;
import cn.demi.cus.customer.dao.IContactsDao;
import cn.demi.cus.customer.dao.ICusClientDao;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Client;
import cn.demi.cus.customer.po.Contacts;
import cn.demi.cus.customer.po.CusClient;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.vo.CustomerVo;

/**
 * 项目 业务逻辑层
 * 
 * @author QuJunLong
 */
@Service("bus.simProjectService")
public class ProjectServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectService {

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

	@Override
	public ProjectVo find4New(ProjectVo v) throws GlobalException {
		v.setUserId(getCurrent().getAccountId());
		v.setUserName(getCurrent().getUserName());
		v.setRdate(DateUtils.getCurrDateStr());
		v.setXctk(Constants.S);
		v.setHtqd(Constants.S);
		v.setFabz(Constants.S);
		v.setFinishDate(DateUtils.getNextDate(v.getRdate(), 56));
		return v;
	}

	@Override
	public void delete(String... ids) throws GlobalException {
		List<Project> pList = projectDao.listByIds(ids);
		for (Project p : pList) {
			Progress pg = p.getProgress();
			Cust cust = p.getCust();
			p.setProgress(null);
			p.setCust(null);
			projectDao.delete(p);
			if (null != pg) {
				progressDao.delete(pg);
			}
			if (null != cust) {
				custDao.delete(cust);
			}
		}
	}

	@Override
	public ProjectVo findById(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = po2Vo(po);
		return vo;
	}

	@Override
	public void add(ProjectVo v) throws GlobalException {
		Project project = new Project();
		project = project.toPo(v, project);
		uptCustInfo(v, project);
		updateInfo(project, v);
		project.setUserId(getCurrent().getAccountId());
		project.setUserName(getCurrent().getUserName());
		project.setOrgId(getCurrent().getDepId());
		projectDao.add(project);
		v.setId(project.getId());
		v.setNo(project.getNo());
		// 添加流程实例
		Progress pg = progressDao.add4Project(project.getId(), EunmProject.PROJECT_LX.getStatus(), null, null, project.getUserId(), project.getUserName());
		project.setProgress(pg);
		project.setStatus(pg.getStatus());
		projectDao.update(project);
		commit(project, v);
	}

	@Override
	public void update(ProjectVo v) throws GlobalException {
		Project project = projectDao.findById(v.getId());
		updateInfo(project, v);
		uptCustInfo(v, project);
		project.setUserId(getCurrent().getAccountId());
		project.setUserName(getCurrent().getUserName());
		project.setOrgId(getCurrent().getDepId());
		projectDao.update(project);
		v.setNo(project.getNo());
		commit(project, v);
	}

	// 更新项目信息
	public void updateInfo(Project p, ProjectVo v) {
		p.setHtNo(v.getHtNo());
		p.setSampName(v.getSampName());
		if (StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(createCode(v.getRdate()));
		}
		if (StrUtils.isBlankOrNull(p.getOrgId())) {
			p.setOrgId(getCurrent().getOrgId());
			p.setOrgName(getCurrent().getOrgName());
		}
		p.setSampName(v.getSampName());
		p.setItemType(v.getItemType());
		p.setRdate(v.getRdate());
		p.setFinishDate(v.getFinishDate());
		p.setJj(v.getJj());
		p.setRemark(v.getRemark());
		if (StrUtils.isBlankOrNull(p.getIsBack())) {
			p.setIsBack(Constants.N);
		}
		p.setXctk(v.getXctk());
		p.setHtqd(v.getHtqd());
		p.setFabz(v.getFabz());
	};

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
			customer.setBuildDate(p.getRdate());
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
				customer.setBuildDate(p.getRdate());
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
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("userId like '" + getCurrent().getAccountId() + "'"));
		pageResult.addCondition(new QueryCondition("status like '" + EunmProject.PROJECT_LX.getStatus() + "'"));
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
		List<QueryCondition> QueryConditionList = new ArrayList<QueryCondition>();
		QueryConditionList.add(new QueryCondition(" no is not null"));
		return QueryConditionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Kp(GridVo gridVo, ProjectVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("st in('" + Constants.CONTRACT_STATUS_YQD + "','" + Constants.CONTRACT_STATUS_ZXZ + "')"));
		pageResult = projectDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Project>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public void update2Stop(ProjectVo v) throws GlobalException {
		List<Project> pList = projectDao.listByIds(v.getIds());
		for (Project project : pList) {
			Progress pg = project.getProgress();
			progressLogDao.add(project.getId(), project.getId(), EunmProject.PROJECT_LX.getStatus(), EunmProject.PROJECT_STOP.getStatus(),
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

	// 采样 提交
	public void commit(Project p, ProjectVo v) throws GlobalException {
		// 验证批次是否完结
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmProject.PASS_Y)) {
			p.setIsBack(Constants.N);
			Progress pg = p.getProgress();
			if (p.getHtqd().equals(Constants.S)) {
				pg = progressDao.update4Project(pg.getId(), EunmProject.PROJECT_PS.getStatus(), null, null, null, null);
			} else {
				Org org = orgDao.findByName("营销中心");
				if (null != org) {
					p.setOrgId(org.getId());
					p.setOrgName(org.getName());
				}
				pg = progressDao.update4Project(pg.getId(), EunmProject.PROJECT_DJ.getStatus(), p.getOrgId(), p.getOrgName(), null, null);
			}
			progressLogDao.add4Porject(p.getId(), p.getId(), EunmProject.PROJECT_LX.getStatus(), v.getIsCommit(), v.getRemark());
			p.setStatus(pg.getStatus());
			projectDao.update(p);
		}
	}

	/**
	 * //自送样 提交
	 * 
	 * @param v
	 * @param sch
	 */
	public void commitZSY(ProjectVo v, Project p) {
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmProject.PASS_Y)) {
			Progress schpg = progressDao.update4Project(p.getProgress().getId(), EunmProject.PROJECT_PS.getStatus(), null, null, null, null);
			p.setStatus(schpg.getStatus());
			projectDao.update(p);
			progressLogDao.add4Porject(p.getId(), p.getId(), EunmProject.PROJECT_LX.getStatus(), EunmProject.PASS_Y, v.getRemark());
		}
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
		hql.append(" AND log.status='" + EunmProject.PROJECT_LX.getStatus() + "' AND log.userId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' ");
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

	/**
	 * 生成项目编号
	 * 
	 * @param rdate
	 * @return
	 */
	public String createCode(String rdate) {
		String flag = "";
		rdate = rdate.replaceAll("-", "");
		flag += rdate;
		// flag += DateUtils.getYear() + DateUtils.getMonth() + DateUtils.getDay();
		String hql = "SELECT max(no) FROM " + projectDao.getEntityName(Project.class) + " WHERE isDel=" + Po.N + "  and no like '" + flag + "%'";
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

	public List<Map<?, Object>> poList2mapList1(List<Project> list) throws GlobalException {
		List<Map<?, Object>> tempList = new ArrayList<Map<?, Object>>();
		for (Project p : list) {
			tempList.add(p.toMapProperty(p, p.getPropertyToMap()));
		}
		return tempList;
	}

	@Override
	public List<ProjectVo> list(ProjectVo v) throws GlobalException {
		List<QueryCondition> queryConditionsList = new ArrayList<QueryCondition>();
		if (StrUtils.isNotBlankOrNull(v.getNo())) {
			queryConditionsList.add(new QueryCondition("no", QueryCondition.AK, v.getNo()));
		}
		if (null != v.getCustVo() && StrUtils.isNotBlankOrNull(v.getCustVo().getCustName())) {
			queryConditionsList.add(new QueryCondition("cust.custName", QueryCondition.AK, v.getCustVo().getCustName()));
		}
		List<OrderCondition> orderConditionsList = new ArrayList<OrderCondition>();
		orderConditionsList.add(new OrderCondition("rdate", OrderCondition.ORDER_DESC));
		List<Project> list = projectDao.list(queryConditionsList, orderConditionsList);
		return toVoList(list);
	}

	@Override
	public void updated(ProjectVo v) throws GlobalException {
		Project project = projectDao.findById(v.getId());
		// 更新客户信息
		CustVo custVo = v.getCustVo();
		Cust cust = project.getCust();
		cust = cust.toPo(custVo, cust);
		custDao.update(cust);
		project.setSampName(v.getSampName());
		project.setRdate(v.getRdate());
		project.setFinishDate(v.getFinishDate());
		project.setRemark(v.getRemark());
		project.setJj(v.getJj());
		projectDao.update(project);
		v.setNo(project.getNo());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProjectVo findByHtId(String htNo) throws GlobalException {

		ProjectVo vo = null;
		String sql = "select pj.no,cus.id,cus.cust_code,cus.name,cus.cust_address,cus.cust_tel,cus.cust_bank,cus.cust_account,pj.id pjId from  " + tablePrefix
				+ projectDao.getEntityName(Project.class) + " pj inner join  " + tablePrefix + custDao.getEntityName(Cust.class)
				+ " cust on pj.cust_id = cust.id inner join " + tablePrefix + customerDao.getEntityName(Customer.class)
				+ " cus on cust.customer_id = cus.id where pj.ht_no='" + htNo + "' and pj.is_del=0";
		List<Object[]> list = projectDao.queryBySql(sql);
		if (list != null) {
			for (Object[] obj : list) {
				vo = new ProjectVo();
				CustomerVo cusVo = new CustomerVo();
				CustVo custVo = new CustVo();
				vo.setNo(String.valueOf(obj[0]));
				cusVo.setId(String.valueOf(obj[1]));
				String custCode = String.valueOf(obj[2]);
				String name = String.valueOf(obj[3]);
				String address = String.valueOf(obj[4]);
				String tel = String.valueOf(obj[5]);
				String bank = String.valueOf(obj[6]);
				String account = String.valueOf(obj[7]);
				cusVo.setCustCode((custCode == null || custCode.equals("null")) ? "" : custCode);
				cusVo.setName((name == null || name.equals("null")) ? "" : name);
				cusVo.setCustAddress((address == null || address.equals("null")) ? "" : address);
				cusVo.setCustTel((tel == null || tel.equals("null")) ? "" : tel);
				cusVo.setCustBank((bank == null || bank.equals("null")) ? "" : bank);
				cusVo.setCustAccount((account == null || account.equals("null")) ? "" : account);
				vo.setId(String.valueOf(obj[8]));
				custVo.setCustomerVo(cusVo);
				vo.setCustVo(custVo);

			}

		}

		return vo;
	}

}
