package cn.demi.bus.pjt.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
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
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.dao.IInvoiceDao;
import cn.demi.bus.pjt.dao.IProjectDao;
import cn.demi.bus.pjt.dao.IProjectFbDao;
import cn.demi.bus.pjt.dao.ISchemeDao;
import cn.demi.bus.pjt.dao.ISchemePointDao;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.pjt.po.Invoice;
import cn.demi.bus.pjt.po.Project;
import cn.demi.bus.pjt.po.ProjectFb;
import cn.demi.bus.pjt.po.Scheme;
import cn.demi.bus.pjt.po.SchemePoint;
import cn.demi.bus.pjt.service.IProjectService;
import cn.demi.bus.pjt.vo.CustVo;
import cn.demi.bus.pjt.vo.InvoiceVo;
import cn.demi.bus.pjt.vo.ProjectFbVo;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.bus.pjt.vo.SchemePointVo;
import cn.demi.bus.pjt.vo.SchemeVo;
import cn.demi.bus.task.po.Task;
import cn.demi.cus.customer.dao.IClientDao;
import cn.demi.cus.customer.dao.IContactsDao;
import cn.demi.cus.customer.dao.ICusClientDao;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Client;
import cn.demi.cus.customer.po.Contacts;
import cn.demi.cus.customer.po.CusClient;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.vo.CustomerVo;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.res.dao.ISupplierDao;

/**
 * 项目 业务逻辑层
 * 
 * @author QuJunLong
 */
@Service("bus.projectService")
public class ProjectServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectService {

	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private ICustDao custDao;
	@Autowired
	private IInvoiceDao invoiceDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private ISchemeDao schemeDao;
	@Autowired
	private ISchemePointDao schemePointDao;
	@Autowired
	private IProjectFbDao projectFbDao;
	@Autowired
	private ISupplierDao supplierDao;
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
	private ISampTypeDao sampTypeDao;
	@Autowired
	private IImDao imDao;
	@Autowired
	private IOrgDao orgDao;

	@Override
	public ProjectVo find4New(ProjectVo v) throws GlobalException {
		v.setUserId(getCurrent().getAccountId());
		v.setUserName(getCurrent().getUserName());
		v.setSdate(DateUtils.getCurrDateStr());
		v.setRdate(DateUtils.getCurrDateStr());
		v.setFbzff(Constants.F);
		v.setXctk(Constants.F);
		v.setPj(Constants.S);
		v.setReportWay(Constants.RP_ZQ);
		v.setPc(1);
		v.setPcUnit(Project.PC_C);
		v.setReportNum(2);
		v.setCycle(15);
		v.setCycleUnit(Project.ZQ_DAY);
		v.setZsy(Constants.SAMP_XC);
		v.setFinishDate(DateUtils.getNextDate(v.getRdate(), v.getCycle()));
		return v;
	}

	@Override
	public void delete(String... ids) throws GlobalException {
		List<Project> pList = projectDao.listByIds(ids);
		for (Project p : pList) {
			Progress pg = p.getProgress();
			Cust cust = p.getCust();
			Invoice invoice = p.getInvoice();
			p.setProgress(null);
			p.setCust(null);
			p.setInvoice(null);
			projectDao.delete(p);
			List<Scheme> schList = schemeDao.listByPjId(p.getId());
			for (Scheme scheme : schList) {
				// 删除方案时 同步删除点位表和项目方法关系表
				List<SchemePoint> spList = schemePointDao.listBySchId(scheme.getId());
				if (null != spList) {
					for (SchemePoint sp : spList) {
						imDao.deleteByBusId(sp.getId());
						schemePointDao.delete(sp);
					}
				}
				schemeDao.delete(scheme);
			}
			projectFbDao.deleteByProjectId(p.getId());
			if (null != pg) {
				progressDao.delete(pg);
			}
			if (null != cust) {
				custDao.delete(cust);
			}
			if (null != invoice) {
				invoiceDao.delete(invoice);
			}
		}
	}

	@Override
	public ProjectVo findById(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = po2Vo(po);
		// 费用信息
		Invoice invoice = po.getInvoice();
		InvoiceVo invoiceVo = new InvoiceVo();
		if (null != invoice) {
			invoiceVo = invoiceVo.toVo(invoice);
		}
		vo.setInvoiceVo(invoiceVo);
		Scheme scheme = schemeDao.find4Zsy(po.getId());
		SchemeVo schemeVo = new SchemeVo();
		if (null != scheme) {
			schemeVo = schemeVo.toVo(scheme);
			vo.setSchemeVo(schemeVo);
			List<SchemePoint> pointList = schemePointDao.listBySchId(schemeVo.getId());
			if (null != pointList && pointList.size() > 0) {
				List<SchemePointVo> detailVoList = new ArrayList<SchemePointVo>();
				for (SchemePoint sp : pointList) {
					SchemePointVo spVo = new SchemePointVo();
					spVo = spVo.toVo(sp);
					spVo.setImId(imDao.findByBusId(sp.getId()));
					detailVoList.add(spVo);
				}
				vo.setPointList(detailVoList);
			}
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
		}
		return vo;
	}

	@Override
	public ProjectVo findNext(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = po2Vo(po);
		// 费用信息
		Invoice invoice = po.getInvoice();
		InvoiceVo invoiceVo = new InvoiceVo();
		if (null != invoice) {
			invoiceVo = invoiceVo.toVo(invoice);
		} else {
			invoiceVo.setDiscount(1);
		}
		vo.setInvoiceVo(invoiceVo);
		// 方案信息
		Scheme scheme = schemeDao.find4Zsy(po.getId());
		SchemeVo schemeVo = new SchemeVo();
		if (null != scheme) {
			schemeVo = schemeVo.toVo(scheme);
		}
		if (StrUtils.isBlankOrNull(schemeVo.getCyDate())) {
			schemeVo.setCyDate(DateUtils.getCurrDateTimeStr());
		}
		if (StrUtils.isBlankOrNull(schemeVo.getDealRequest())) {
			schemeVo.setDealRequest(Constants.SAMP_XH);
		}
		if (StrUtils.isBlankOrNull(schemeVo.getSaveRequest())) {
			schemeVo.setSaveRequest("常温");
		}
		vo.setSchemeVo(schemeVo);
		List<SchemePoint> pointList = schemePointDao.listBySchId(schemeVo.getId());
		if (null != pointList && pointList.size() > 0) {
			List<SchemePointVo> spVoList = new ArrayList<SchemePointVo>();
			for (SchemePoint sp : pointList) {
				SchemePointVo spVo = new SchemePointVo();
				spVo = spVo.toVo(sp);
				spVo.setImId(imDao.findByBusId(sp.getId()));
				spVoList.add(spVo);
			}
			vo.setPointList(spVoList);
		}
		// 分包信息
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
		return vo;
	}

	@Override
	public void add(ProjectVo v) throws GlobalException {
		Current current = CurrentUtils.getCurrent();
		Project project = new Project();
		project = project.toPo(v, project);
		uptCustInfo(v, project);
		updateInfo(project, v);
		projectDao.add(project);
		v.setId(project.getId());
		v.setNo(project.getNo());
		// 添加流程实例
		Progress pg = progressDao.add(project.getId(), EunmTask.PROJECT_LX.getStatus(), current.getOrgId(), current.getOrgName(), current.getAccountId(),
				current.getUserName());
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
		projectDao.update(project);
		v.setNo(project.getNo());
		commit(project, v);
	}

	// 更新项目信息
	public void updateInfo(Project p, ProjectVo v) {
		p.setTaskTotal(v.getPc());
		p.setTaskNum(0);
		if (v.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			p.setZsy(v.getZsy());
			p.setFbzff(v.getFbzff());
			p.setSampTypeId(v.getSampTypeId());
			p.setSampTypeName(v.getSampTypeName());
			p.setPj(v.getPj());
			p.setStandIds(v.getStandIds());
			p.setStandNames(v.getStandNames());
			p.setSampName(v.getSampTypeName());
		} else {
			p.setZsy(Constants.SAMP_XC);// 职卫 采样
			p.setFbzff(Constants.F);
			p.setPj(Constants.S);
			p.setSampTypeName("/");
			p.setSampName(v.getSampName());
		}
		if (p.getZsy().equals(Constants.SAMP_ZS)) {
			p.setXctk(Constants.F);
		} else {
			p.setXctk(v.getXctk());
		}
		if (StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(createCode(v.getTaskType(), v.getRdate()));
		}
		if (StrUtils.isBlankOrNull(p.getOrgId())) {
			p.setOrgId(getCurrent().getOrgId());
			p.setOrgName(getCurrent().getOrgName());
		}
		p.setHtSt(Constants.CONTRACT_STATUS_WSC);
		p.setPaySt(Constants.CHARGE_STATUS_WSF);
		p.setTaskType(v.getTaskType());
		p.setPc(v.getPc());
		p.setPcUnit(v.getPcUnit());
		p.setRdate(v.getRdate());
		p.setFinishDate(v.getFinishDate());
		p.setCycle(v.getCycle());
		p.setCycleUnit(v.getCycleUnit());
		p.setReportNum(v.getReportNum());
		p.setReportWay(v.getReportWay());
		p.setJj(v.getJj());
		p.setInfo(v.getInfo());
		p.setSdate(v.getSdate());
		p.setRemark(v.getRemark());
		if (StrUtils.isBlankOrNull(p.getFb())) {
			p.setFb(Constants.F);
		}
		if (StrUtils.isBlankOrNull(p.getIsBack())) {
			p.setIsBack(Constants.N);
		}
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
		pageResult.addCondition(new QueryCondition("status like '" + EunmTask.PROJECT_LX.getStatus() + "'"));
		pageResult.addCondition(new QueryCondition("userId like '" + getCurrent().getAccountId() + "'"));
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
	public GridVo gridData4Old(GridVo gridVo, ProjectVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
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
			progressLogDao.add(project.getId(), project.getId(), EunmTask.PROJECT_LX.getStatus(), EunmTask.TASK_STOP.getStatus(), "项目终止,原因：" + v.getRemark());
			project.setStatus(EunmTask.TASK_STOP.getStatus());
			project.setProgress(null);
			projectDao.update(project);
			// 清除流程实例，记录日志记录
			if (null != pg) {
				progressDao.delete(pg);
			}
		}
	}

	@Override
	public void updateNext(ProjectVo v) throws GlobalException {
		Project p = projectDao.findById(v.getId());
		// 更新费用相关信息
		Invoice invoice = p.getInvoice();
		if (invoice == null) {
			invoice = new Invoice();
		}
		invoice.setJjPrice(v.getInvoiceVo().getJjPrice());
		invoice.setDiscount(v.getInvoiceVo().getDiscount());
		invoice.setPrice(v.getInvoiceVo().getPrice());
		invoice.setYhPrice(v.getInvoiceVo().getYhPrice());
		invoice.setJtPrice(v.getInvoiceVo().getJtPrice());
		invoice.setBgPrice(v.getInvoiceVo().getBgPrice());
		invoice.setOtherPrice(v.getInvoiceVo().getOtherPrice());
		invoice.setTotalPrice(v.getInvoiceVo().getTotalPrice());
		invoice.setHtPrice(v.getInvoiceVo().getHtPrice());
		invoiceDao.saveOrUpdate(invoice);
		p.setInvoice(invoice);
		p.setHtPrice(invoice.getHtPrice());
		// 自送样更新方案信息
		List<Scheme> schemeList = schemeDao.listByPjId(p.getId());
		Scheme scheme = null;
		if (schemeList != null && schemeList.size() > 0) {
			scheme = schemeList.get(0);
		}
		if (scheme == null) {
			scheme = new Scheme();
			scheme.setProject(p);
			scheme.setSampName(p.getSampName());
		}
		scheme = scheme.toPo(v.getSchemeVo(), scheme);
		scheme.setCyEndDate(v.getSchemeVo().getCyDate());
		scheme.setStatus(Scheme.ST_WZX);
		scheme.setNum(1);
		scheme.setStartDate(p.getRdate());
		scheme.setEndDate(p.getFinishDate());
		scheme.setFxPrice(invoice.getPrice());
		schemeDao.saveOrUpdate(scheme);
		// 自送样点位信息
		uptSamp(p, v, scheme);
		// 自送样更新分包信息
		uptFb(p, v);
		schemeDao.update(scheme);
		projectDao.update(p);
		commitZSY(v, p);
	}

	// 更新样品信息
	public void uptSamp(Project p, ProjectVo v, Scheme scheme) {
		// 删除方案 同步删除点位表和项目方法关系表
		List<SchemePoint> spList = schemePointDao.listBySchId(scheme.getId());
		if (null != spList) {
			for (SchemePoint sp : spList) {
				imDao.deleteByBusId(sp.getId());
				schemePointDao.delete(sp);
			}
		}
		Set<String> sampTypeName = new HashSet<String>();
		Set<String> sampTypeId = new HashSet<String>();
		Set<String> itemNameSet = new HashSet<String>();
		int sampNum = 0;
		if (null != v.getPointList()) {
			for (SchemePointVo spVo : v.getPointList()) {
				if (spVo == null || StrUtils.isBlankOrNull(spVo.getSampTypeId())) {
					continue;
				}
				SchemePoint sp = new SchemePoint();
				if (null != spVo.getSort()) {
					sp.setSort(spVo.getSort());
				} else {
					sp.setSort(schemePointDao.findMaxSort(scheme.getId()) + 1);
				}
				sp.toPo(spVo, sp);
				sp.setScheme(scheme);
				sp.setProjectId(p.getId());
				sp.setPc(1);
				sp.setPcUnit(Project.PC_C);
				sp.setSampTypeName(sampTypeDao.findById(sp.getSampTypeId()).getName());
				schemePointDao.add(sp);
				// 更新项目方法关系
				imDao.uptIm(sp.getId(), spVo.getImId());
				// 获取实际添加样品类型集合
				if (!sampTypeId.contains(sp.getSampTypeId())) {
					sampTypeId.add(sp.getSampTypeId());
					sampTypeName.add(sp.getSampTypeName());
				}
				// 获取样品名称集合
				String names[] = sp.getItemName().split(",");
				for (String itemName : names) {
					if (StrUtils.isBlankOrNull(itemName)) {
						continue;
					}
					itemNameSet.add(itemName);
				}
				// 获取样品数量
				sampNum += sp.getPc();
			}
		}
		scheme.setSampTypeId(p.getSampTypeId());
		scheme.setSampTypeName(p.getSampTypeName());
		scheme.setSampNum(sampNum);
		scheme.setItemNames(String.join(",", itemNameSet));
		scheme.setPointNames(null);
	}

	// 更新项目方法关系信息
	// public void uptIm(String busId, String imStr) {
	// imDao.deleteByBusId(busId);
	// String imArr[] = imStr.split(",");
	// int n = 0;
	// for (String i : imArr) {
	// if (!StrUtils.isBlankOrNull(i) && i.contains("-")) {
	// String m[] = i.split("-");
	// if (m.length == 2 && m[0].trim().length() > 0) {
	// Im im = new Im();
	// im.setBusId(busId);
	// Item item = itemDao.findById(m[0].trim());
	// im.setItemId(item.getId());
	// im.setItemName(item.getName());
	// im.setPrice(item.getPrice());
	// if (!StrUtils.isBlankOrNull(m[1].trim())) {
	// Method method = methodDao.findById(m[1].trim());
	// im.setMethodId(method.getId());
	// im.setMethodName(method.getName());
	// }
	// im.setUserId(getCurrent().getAccountId());
	// im.setUserName(getCurrent().getUserName());
	// im.setDate(DateUtils.getCurrDateTimeStr());
	// im.setSort(n);
	// imDao.add(im);
	// n++;
	// }
	// }
	// }
	// }

	// 更新分包信息
	public void uptFb(Project p, ProjectVo v) {
		projectFbDao.deleteByProjectId(p.getId());
		int tnum = 0;
		List<String> itemId = new ArrayList<String>();
		List<String> itemName = new ArrayList<String>();
		float fbPrice = 0;
		if (null != v.getFbList()) {
			for (ProjectFbVo fbVo : v.getFbList()) {
				if (fbVo == null || fbVo.getFbVo() == null || StrUtils.isBlankOrNull(fbVo.getFbVo().getId())) {
					continue;
				}
				ProjectFb fb = new ProjectFb();
				fb = fb.toPo(fbVo, fb);
				fb.setProject(p);
				fb.setFb(supplierDao.findById(fbVo.getFbVo().getId()));
				projectFbDao.add(fb);
				tnum += fb.getNum();
				fbPrice += fb.getPrice();
				String ids[] = fb.getItemIds().split(",");
				String names[] = fb.getItemNames().split(",");
				int i = 0;
				for (String iid : ids) {
					if (!StrUtils.isBlankOrNull(iid) && !itemId.contains(iid)) {
						itemId.add(iid);
						itemName.add(names[i]);
					}
					i++;
				}
			}
		}
		p.setFb(v.getFb());
		p.setFbItemId(String.join(",", itemId));
		p.setFbItemName(String.join(",", itemName));
		p.setFbSampNum(tnum);
		p.setFbPrice(fbPrice);
	}

	// 采样 提交
	public void commit(Project p, ProjectVo v) throws GlobalException {
		// 验证批次是否完结
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			p.setIsBack(Constants.N);
			String status = "";

			if (p.getXctk().equals(Constants.S)) {// 踏勘，直接到任务指派
				status = EunmTask.PROJECT_ZP.getStatus();
				Progress pg = progressDao.update(p.getProgress().getId(), status, null, null, null, null);
				p.setStatus(pg.getStatus());
			} else if (p.getXctk().equals(Constants.F)) {// 不踏勘，直接到方案编制环节
				status = EunmTask.PROJECT_BZ.getStatus();
				Org org = null;
				if (p.getSampType().contains("环境")) {
					org = orgDao.findByName("环境");
				} else if (p.getSampType().contains("职业卫生")) {
					org = orgDao.findByName("职业卫生");
				}
				if (org != null) {
					p.setOrgId(org.getId());
					p.setOrgName(org.getName());
				}
				Progress pg = progressDao.update(p.getProgress().getId(), status, p.getOrgId(), null, null, null);
				p.setStatus(pg.getStatus());
			}

			projectDao.update(p);
			progressLogDao.add(p.getId(), p.getId(), EunmTask.PROJECT_LX.getStatus(), v.getIsCommit(), v.getRemark());
		}
	}

	/**
	 * //自送样 提交
	 * 
	 * @param v
	 * @param sch
	 */
	public void commitZSY(ProjectVo v, Project p) {
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			Progress schpg = progressDao.update(p.getProgress().getId(), EunmTask.PROJECT_PS.getStatus(), null, null, null, null);
			p.setStatus(schpg.getStatus());
			projectDao.update(p);
			progressLogDao.add(p.getId(), p.getId(), EunmTask.PROJECT_LX.getStatus(), EunmTask.PASS_Y, v.getRemark());
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
		hql.append(" AND log.status='" + EunmTask.PROJECT_LX.getStatus() + "' AND log.userId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' ");
		hql.append(" ORDER BY t." + pageResult.getOrderColumn() + " " + pageResult.getOrder() + "");
		Query query = schemeDao.query(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Project> pjList = schemeDao.query(hql.toString()).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
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
	 * @param taskType
	 * @param type
	 * @return
	 */
	public String createCode(String taskType, String rdate) {
		String flag = EnumBus.getCode(taskType);
		// flag += DateUtils.getYear() + DateUtils.getMonth() + DateUtils.getDay();
		rdate = rdate.replaceAll("-", "");
		flag += rdate;
		String hql = "SELECT max(no) FROM " + projectDao.getEntityName(Project.class) + " WHERE isDel=" + Po.N + " AND no like '" + flag + "%'";
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
		project.setTaskType(v.getTaskType());
		project.setSampName(v.getSampName());
		project.setRdate(v.getRdate());
		project.setFinishDate(v.getFinishDate());
		project.setRemark(v.getRemark());
		project.setReportNum(v.getReportNum());
		project.setReportWay(v.getReportWay());
		project.setJj(v.getJj());
		project.setInfo(v.getInfo());
		project.setSdate(v.getSdate());
		if (project.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			project.setFbzff(v.getFbzff());
			project.setPj(v.getPj());
			project.setStandIds(v.getStandIds());
			project.setStandNames(v.getStandNames());
		}
		if (project.getZsy().equals(Constants.SAMP_ZS)) {
			// 方案信息
			Scheme scheme = schemeDao.find4Zsy(project.getId());
			scheme.setDealRequest(v.getSchemeVo().getDealRequest());
			scheme.setSaveRequest(v.getSchemeVo().getSaveRequest());
			scheme.setCyUserName(v.getSchemeVo().getCyUserName());
			scheme.setCyDate(v.getSchemeVo().getCyDate());
			scheme.setRemark(v.getSchemeVo().getRemark());
			schemeDao.update(scheme);
			// 费用信息
			Invoice invoice = project.getInvoice();
			invoice.setJjPrice(v.getInvoiceVo().getJjPrice());
			invoice.setDiscount(v.getInvoiceVo().getDiscount());
			invoice.setPrice(v.getInvoiceVo().getPrice());
			invoice.setYhPrice(v.getInvoiceVo().getYhPrice());
			invoice.setJtPrice(v.getInvoiceVo().getJtPrice());
			invoice.setBgPrice(v.getInvoiceVo().getBgPrice());
			invoice.setOtherPrice(v.getInvoiceVo().getOtherPrice());
			invoice.setTotalPrice(v.getInvoiceVo().getTotalPrice());
			invoice.setHtPrice(v.getInvoiceVo().getHtPrice());
			invoiceDao.update(invoice);
			project.setInvoice(invoice);
			project.setHtPrice(invoice.getHtPrice());
			// 自送样点位信息
			List<SchemePointVo> spList = v.getPointList();
			if (null != spList) {
				for (SchemePointVo spVo : spList) {
					SchemePoint sp = schemePointDao.findById(spVo.getId());
					sp.toPo(spVo, sp);
					sp.setScheme(scheme);
					sp.setPc(1);
					sp.setPcUnit(Project.PC_C);
					sp.setSampTypeName(sampTypeDao.findById(sp.getSampTypeId()).getName());
					schemePointDao.update(sp);
					// 更新项目方法关系
					imDao.uptIm(sp.getId(), spVo.getImId());
				}
			}
			// 自送样更新分包信息
			uptFb(project, v);
		}
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

	@Override
	public void updatePaySt(String id) throws GlobalException {
		Project pjt = projectDao.findById(id);
		pjt.setPaySt(Constants.CHARGE_STATUS_YYF);
		projectDao.update(pjt);
	}

	@Override
	public String copy(ProjectVo v) throws GlobalException {
		v = findById(v.getId());
		v.setId(null);
		Project p = vo2Po(v);
		Cust cust = new Cust();
		cust = cust.toPo(v.getCustVo(), cust);
		cust.setId(null);
		Client client = clientDao.findById(v.getCustVo().getClientVo().getId());
		cust.setClient(client);
		Customer customer = customerDao.findById(v.getCustVo().getCustomerVo().getId());
		cust.setCustomer(customer);
		custDao.add(cust);
		p.setCust(cust);

		p.setRdate(DateUtils.getCurrDateStr());
		p.setFinishDate(DateUtils.getNextDate(p.getRdate(), 5));
		p.setUserId(getCurrent().getAccountId());
		p.setUserName(getCurrent().getUserName());
		p.setOrgId(getCurrent().getOrgId());
		p.setOrgName(getCurrent().getOrgName());
		p.setNo(checkCode(v.getTaskType()));
		p.setIsBack(Constants.N);
		p.setSampType(v.getSampType());
		p.setInvoice(null);
		projectDao.add(p);
		v.setId(p.getId());
		Progress progress = progressDao.add(p.getId(), EunmTask.PROJECT_LX.getStatus(), p.getOrgId(), p.getOrgName(), p.getUserId(), p.getUserName());
		p.setProgress(progress);
		p.setStatus(progress.getStatus());
		projectDao.update(p);
		return p.getId();
	}

	public String checkCode(String taskType) throws GlobalException {
		String prefix = "";
		prefix += DateUtils.getYear() + DateUtils.getMonth();
		String hql = "SELECT max(no) FROM " + projectDao.getEntityName(Task.class) + " WHERE isDel=" + Po.N + " AND no like '" + prefix + "%'";
		String ls = (String) projectDao.query(hql).getSingleResult();
		String no = prefix;
		if (ls == null) {
			no += "001";
		} else {
			ls = ls.replace(prefix, "");
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
}
