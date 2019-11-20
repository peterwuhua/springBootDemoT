package cn.demi.app.bus.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.app.bus.service.AppProjectService;
import cn.demi.app.bus.vo.AppProject;
import cn.demi.app.bus.vo.AppProjectEdit;
import cn.demi.app.bus.vo.AppProjectFb;
import cn.demi.app.bus.vo.AppProjectPoint;
import cn.demi.app.bus.vo.AppPstandard;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.base.system.dao.IUnitDao;
import cn.demi.base.system.po.Unit;
import cn.demi.bus.pjt.service.IProjectService;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
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
import cn.demi.bus.pjt.vo.ProjectFbVo;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.bus.pjt.vo.SchemePointVo;
import cn.demi.cus.customer.dao.IClientDao;
import cn.demi.cus.customer.dao.IContactsDao;
import cn.demi.cus.customer.dao.ICusClientDao;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Client;
import cn.demi.cus.customer.po.Contacts;
import cn.demi.cus.customer.po.CusClient;
import cn.demi.cus.customer.po.Customer;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.std.dao.IPstandardDao;
import cn.demi.init.std.po.Pstandard;
import cn.demi.res.dao.ISupplierDao;

@Service("app.projectService")
public class AppProjectServiceImpl implements AppProjectService {
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private IPstandardDao pstandardDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
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
	private IImDao imDao;
	@Autowired
	private IProjectService projectservice;

	@Override
	public List<AppProject> projectList(ObjVo v) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + projectDao.getEntityName(Project.class) + " WHERE isDel=" + Po.N);
		hql.append(" and status like '" + EunmTask.PROJECT_LX.getStatus() + "'");
		hql.append(" and userId like '" + v.getUserId() + "'");
		hql.append(" ORDER BY lastUpdTime desc");
		Query query = projectDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Project> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppProject> outList = new ArrayList<>();
		for (Project project : list) {
			AppProject appProject = new AppProject();
			appProject.setId(project.getId());
			appProject.setIsBack(project.getIsBack());
			appProject.setNo(project.getNo());
			appProject.setCustName(project.getCust().getCustName());
			appProject.setTaskType(project.getTaskType());
			appProject.setSampName(project.getSampName());
			appProject.setZsy(project.getZsy());
			appProject.setXctk(project.getXctk());
			appProject.setPj(project.getPj());
			appProject.setRdate(project.getRdate());
			appProject.setFinishDate(project.getFinishDate());
			appProject.setSampType(project.getSampType());
			appProject.setJj(project.getJj());
			outList.add(appProject);
		}
		return outList;
	}

	@Override
	public AppProjectEdit projectEdit(String id) throws GlobalException {

		ProjectVo po = projectservice.findById(id);
		AppProjectEdit vo = new AppProjectEdit();
		vo.setId(po.getId());
		vo.setSampType(po.getSampType());
		vo.setIsBack(po.getIsBack());
		vo.setCustName(po.getCustVo().getCustName());
		vo.setCustVoClientVoId(po.getCustVo().getClientVo().getId());
		vo.setCustAddress(po.getCustVo().getCustAddress());
		vo.setCustUser(po.getCustVo().getCustUser());
		vo.setCustTel(po.getCustVo().getCustTel());
		vo.setIndustry(po.getCustVo().getIndustry());
		vo.setAttribute(po.getCustVo().getAttribute());
		vo.setProduct(po.getCustVo().getProduct());
		vo.setWtName(po.getCustVo().getWtName());
		vo.setCustomerVoId(po.getCustVo().getCustomerVo().getId());
		vo.setWtAddress(po.getCustVo().getWtAddress());
		vo.setWtUser(po.getCustVo().getWtUser());
		vo.setWtTel(po.getCustVo().getTel());
		vo.setWtEmail(po.getCustVo().getWtEmail());
		vo.setWtZip(po.getCustVo().getWtZip());
		vo.setSampTypeName(po.getSampTypeName());
		vo.setSampTypeId(po.getSampTypeId());
		vo.setSampName(po.getSampName());
		vo.setPc(String.valueOf(po.getPc()));
		vo.setPcUnit(po.getPcUnit());
		vo.setRdate(po.getRdate());
		vo.setCycle(String.valueOf(po.getCycle()));
		vo.setCycleUnit(po.getCycleUnit());
		vo.setReportNum(String.valueOf(po.getReportNum()));
		vo.setReportWay(po.getReportWay());
		vo.setJj(po.getJj());
		vo.setZsy(po.getZsy());
		vo.setFbzff(po.getFbzff());
		vo.setXctk(po.getXctk());
		vo.setPj(po.getPj());
		vo.setStandNames(po.getStandNames());
		vo.setStandIds(po.getStandIds());
		vo.setInfo(po.getInfo());
		vo.setUserName(po.getUserName());
		vo.setUserId(po.getUserId());
		vo.setSdate(po.getSdate());
		vo.setRemark(po.getRemark());
		vo.setTaskType(po.getTaskType());
		vo.setFinishDate(po.getFinishDate());

		if (po.getFbList() != null) {
			List<AppProjectFb> appProjectFbList = new ArrayList<>();
			for (ProjectFbVo projectFbVo : po.getFbList()) {
				AppProjectFb appProjectFb = new AppProjectFb();
				appProjectFb.setFbVoName(projectFbVo.getFbVo().getName());
				appProjectFb.setFbVoId(projectFbVo.getFbVo().getId());
				appProjectFb.setFbUser(projectFbVo.getFbUser());
				appProjectFb.setFbMobile(projectFbVo.getFbMobile());
				appProjectFb.setItemNames(projectFbVo.getItemNames());
				appProjectFb.setItemIds(projectFbVo.getItemIds());
				appProjectFb.setNum(String.valueOf(projectFbVo.getNum()));
				appProjectFb.setPrice(String.valueOf(projectFbVo.getPrice()));
				appProjectFb.setFbPrice(String.valueOf(po.getFbPrice()));
				appProjectFbList.add(appProjectFb);
			}
			vo.setAppProjectFbList(appProjectFbList);
		}

		if (po.getPointList() != null) {
			List<AppProjectPoint> appProjectPointList = new ArrayList<>();
			for (SchemePointVo spVo : po.getPointList()) {
				AppProjectPoint e = new AppProjectPoint();
				e.setSampTypeId(spVo.getSampTypeId());
				e.setSampName(spVo.getSampName());
				e.setSampTypeName(spVo.getSampTypeName());
				e.setXz(spVo.getXz());
				e.setPacking(spVo.getPacking());
				e.setItemName(spVo.getItemName());
				e.setItemId(spVo.getItemId());
				e.setImId(spVo.getImId());
				e.setFxPrice(String.valueOf(spVo.getFxPrice()));
				e.setRemark(spVo.getRemark());
				e.setSort(String.valueOf(spVo.getSort()));
				appProjectPointList.add(e);
			}
			vo.setAppProjectPointList(appProjectPointList);
		}
		if (po.getSchemeVo() != null) {
			 
			vo.setSchemeVoDealRequest(po.getSchemeVo().getDealRequest());
			vo.setSchemeVoSaveRequest(po.getSchemeVo().getSaveRequest());
			vo.setSchemeVoCyUserName(po.getSchemeVo().getCyUserName());
			vo.setSchemeVoCyDate(po.getSchemeVo().getCyDate());
			vo.setSchemeVoRemark(po.getSchemeVo().getRemark());
		}

		vo.setInvoiceVoPrice(String.valueOf(po.getInvoiceVo().getPrice()));
		vo.setInvoiceVoDiscount(String.valueOf(po.getInvoiceVo().getDiscount()));
		vo.setInvoiceVoJtPrice(String.valueOf(po.getInvoiceVo().getJtPrice()));
		vo.setInvoiceVoBgPrice(String.valueOf(po.getInvoiceVo().getBgPrice()));
		vo.setInvoiceVoOtherPrice(String.valueOf(po.getInvoiceVo().getOtherPrice()));
		vo.setInvoiceVoYhPrice(String.valueOf(po.getInvoiceVo().getYhPrice()));
		vo.setInvoiceVoHtPrice(String.valueOf(po.getInvoiceVo().getHtPrice()));
		vo.setFb(po.getFb());

		return vo;
	}

	public List<AppProject> projectFaList(ObjVo v) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + projectDao.getEntityName(Project.class) + " WHERE isDel=" + Po.N);
		hql.append(" and status like '" + EunmTask.PROJECT_BZ.getStatus() + "'");

		hql.append(" ORDER BY lastUpdTime desc");
		Query query = projectDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Project> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppProject> outList = new ArrayList<>();
		for (Project project : list) {
			AppProject appProject = new AppProject();
			appProject.setId(project.getId());
			appProject.setIsBack(project.getIsBack());
			appProject.setNo(project.getNo());
			appProject.setCustName(project.getCust().getCustName());
			appProject.setTaskType(project.getTaskType());
			appProject.setSampName(project.getSampName());
			appProject.setZsy(project.getZsy());
			appProject.setXctk(project.getXctk());
			appProject.setPj(project.getPj());
			appProject.setRdate(project.getRdate());
			appProject.setFinishDate(project.getFinishDate());
			outList.add(appProject);
		}
		return outList;
	}

	@Override
	public List<AppPstandard> pstandardList(String sampTypeId, String name) throws GlobalException {
		String hql = "FROM " + pstandardDao.getEntityName(Pstandard.class) + " WHERE isDel=" + Po.N;
		hql += " and status = '现行' ";
		/*
		 * if (!StrUtils.isBlankOrNull(name)) { hql += " and name like  '%" + name +
		 * "%'"; hql += " or code like  '%" + name + "%'"; hql +=
		 * " or sampTypeName like  '%" + name + "%'"; }
		 */
		hql += " and  ";
		List<QueryCondition> returnList = new ArrayList<QueryCondition>();
		if (StrUtils.isNotBlankOrNull(sampTypeId)) {
			Set<String> idsSet = new HashSet<String>();
			String idArr[] = sampTypeId.replaceAll(" ", "").split(",");
			for (String idStr : idArr) {
				String ids = sampTypeDao.findAllIds(idStr);
				idsSet.addAll(Arrays.asList(ids.split(",")));
			}
			for (String id : idsSet) {
				if (id.trim().length() > 0) {
					hql += " sampTypeId like '%" + id + "%' or";
				}
			}
			if (hql.length() > 2) {
				hql = hql.substring(0, hql.length() - 2);
				returnList.add(new QueryCondition(hql));
			}
		}




		hql += " ORDER BY lastUpdTime desc";
		Query query = pstandardDao.query(hql.toString());
		List<Pstandard> list = query.getResultList();
		List<AppPstandard> outList = new ArrayList<>();
		for (Pstandard po : list) {
			AppPstandard vo = new AppPstandard();
			vo.setId(po.getId());
			vo.setCode(po.getCode());
			vo.setSampTypeName(po.getSampTypeName());
			vo.setName(po.getName());
			outList.add(vo);
		}
		return outList;
	}

	@Override
	public List<String> appTaskTypeList(String sampType) throws GlobalException {
		List<String> name = new ArrayList<>();
		for (EnumBus enuBus : EnumBus.getBusList(sampType)) {
			name.add(enuBus.getName());
		}
		return name;
	}

	@Override
	public Boolean projectAddOrUpDate(AppProjectEdit appProjectEdit, ObjVo objVo, String isCommit)
			throws GlobalException {
		// 判断是否有id
		if (StrUtils.isNotBlankOrNull(appProjectEdit.getId())) {
			// 有更新
			try {
				pjUpdate(appProjectEdit, isCommit, objVo);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			// 无保存
			try {
				pjAdd(appProjectEdit, objVo, isCommit);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

	}

	private void pjAdd(AppProjectEdit appProjectEdit, ObjVo objVo, String isCommit) throws GlobalException {
		Current current = CurrentUtils.getCurrent();
		Project project = new Project();

		Cust cust = new Cust();
		cust.setCustName(appProjectEdit.getCustName());
		cust.setCustAddress(appProjectEdit.getCustAddress());
		cust.setCustUser(appProjectEdit.getCustUser());
		cust.setCustTel(appProjectEdit.getCustTel());
		cust.setIndustry(appProjectEdit.getIndustry());
		cust.setAttribute(appProjectEdit.getAttribute());
		cust.setProduct(appProjectEdit.getProduct());
		cust.setWtName(appProjectEdit.getWtName());
		Customer customer =new Customer();
		customer.setId(appProjectEdit.getCustomerVoId());
		cust.setCustomer(customer);
		cust.setWtAddress(appProjectEdit.getWtAddress());
		cust.setWtUser(appProjectEdit.getWtUser());
		cust.setWtTel(appProjectEdit.getWtTel());
		cust.setWtEmail(appProjectEdit.getWtEmail());
		cust.setWtZip(appProjectEdit.getWtZip());
		project.setId(appProjectEdit.getId());
		project.setIsBack(appProjectEdit.getIsBack());
		project.setSampType(appProjectEdit.getSampType());
		project.setCust(cust);
		project.setSampTypeName(appProjectEdit.getSampTypeName());
		project.setSampTypeId(appProjectEdit.getSampTypeId());
		project.setSampName(appProjectEdit.getSampName());
		project.setPc(Integer.parseInt(appProjectEdit.getPc()));
		project.setPcUnit(appProjectEdit.getPcUnit());
		project.setRdate(appProjectEdit.getRdate());
		project.setCycle(Integer.parseInt(appProjectEdit.getCycle()));
		project.setCycleUnit(appProjectEdit.getCycleUnit());
		project.setReportNum(Integer.parseInt(appProjectEdit.getReportNum()));
		project.setReportWay(appProjectEdit.getReportWay());
		project.setJj(appProjectEdit.getJj());
		project.setZsy(appProjectEdit.getZsy());
		project.setFbzff(appProjectEdit.getFbzff());
		project.setXctk(appProjectEdit.getXctk());
		project.setPj(appProjectEdit.getPj());
		project.setStandNames(appProjectEdit.getStandNames());
		project.setStandIds(appProjectEdit.getStandIds());
		project.setInfo(appProjectEdit.getInfo());
		project.setUserName(objVo.getName());
		project.setUserId(objVo.getUserId());
		project.setSdate(appProjectEdit.getSdate());
		project.setRemark(appProjectEdit.getRemark());
		project.setNo(appProjectEdit.getNo());
		project.setTaskType(appProjectEdit.getTaskType());
		project.setFinishDate(appProjectEdit.getFinishDate());

		uptCustInfo(appProjectEdit, project);
		updateInfo(project, appProjectEdit, objVo);
		projectDao.add(project);
		appProjectEdit.setId(project.getId());
		appProjectEdit.setNo(project.getNo());
		// 添加流程实例
		Progress pg = progressDao.addApp(project.getId(), EunmTask.PROJECT_LX.getStatus(), objVo);
		project.setProgress(pg);
		project.setStatus(pg.getStatus());
		projectDao.update(project);
		commit(project, appProjectEdit, objVo, isCommit);

	}

	private void pjUpdate(AppProjectEdit appProjectEdit, String isCommit, ObjVo objVo) throws GlobalException {
		Project project = projectDao.findById(appProjectEdit.getId());
		uptCustInfo(appProjectEdit, project);
		updateInfo(project, appProjectEdit, objVo);
		projectDao.update(project);
		appProjectEdit.setNo(project.getNo());
		commit(project, appProjectEdit, objVo, isCommit);

	}

	// 检查 及更新 受检单位 委托单位信息
	public void uptCustInfo(AppProjectEdit v, Project p) throws GlobalException {

		Cust cust = p.getCust();
		if (cust == null)
			cust = new Cust();
		cust.setCustName(v.getCustName());
		cust.setCustAddress(v.getCustAddress());
		cust.setCustUser(v.getCustUser());
		cust.setCustTel(v.getCustTel());
		cust.setIndustry(v.getIndustry());
		cust.setAttribute(v.getAttribute());
		cust.setProduct(v.getProduct());
		cust.setWtName(v.getWtName());
		Customer customer1 = new Customer();
		customer1.setId(v.getCustomerVoId());
		cust.setCustomer(customer1);
		cust.setWtAddress(v.getWtAddress());
		cust.setWtUser(v.getWtUser());
		cust.setWtTel(v.getWtTel());
		cust.setWtEmail(v.getWtEmail());
		cust.setWtZip(v.getWtZip());
		// 受检单位
		Client client = null;
		if (!StrUtils.isBlankOrNull(v.getCustName())) {
			client = clientDao.findByName(v.getCustName());
		}
		// 受检单位自维护
		if (null == client && !StrUtils.isBlankOrNull(v.getCustName())) {
			client = new Client();
			client.setName(v.getCustName());
			client.setAddress(v.getCustAddress());
			client.setPerson(v.getCustUser());
			client.setPhone(v.getCustTel());

			client.setIndustry(v.getIndustry());
			client.setAttribute(v.getAttribute());
			client.setProduct(v.getProduct());
			clientDao.add(client);
		} else if (null != client) {
			// 同步受检单位信息

			if (!StrUtils.isBlankOrNull(v.getCustAddress())) {
				client.setAddress(v.getCustAddress());
			}
			if (!StrUtils.isBlankOrNull(v.getCustUser())) {
				client.setPerson(v.getCustUser());
			}
			if (!StrUtils.isBlankOrNull(v.getCustTel())) {
				client.setPhone(v.getCustTel());
			}
			clientDao.update(client);
		}
		cust.setClient(client);
		// 委托单位
		Customer customer = null;
		if (!StrUtils.isBlankOrNull(v.getWtName())) {
			customer = customerDao.findByName(v.getWtName());
		}
		// 委托单位自维护
		if (null == customer && !StrUtils.isBlankOrNull(v.getWtName())) {
			customer = new Customer();
			customer.setName(v.getWtName());
			customer.setAddress(v.getWtAddress());
			customer.setPerson(v.getWtUser());
			customer.setPhone(v.getWtTel());
			customer.setTelephone(v.getWtTel());
			customer.setEmail(v.getWtEmail());

			customer.setZip(v.getWtZip());

			customer.setBuildDate(p.getRdate());
			customer.setCode(customerDao.createCode());
			customerDao.add(customer);
		} else if (null == customer && !StrUtils.isBlankOrNull(v.getCustName())) {
			// 当委托单位为null时，委托单位=受检单位
			cust.setWtName(v.getCustName());
			cust.setWtAddress(v.getCustAddress());
			cust.setWtUser(v.getCustUser());
			cust.setWtTel(v.getCustTel());

			cust.setWtIndustry(v.getIndustry());
			cust.setWtAttribute(v.getAttribute());
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
			if (!StrUtils.isBlankOrNull(v.getWtZip())) {
				customer.setZip(v.getWtZip());
			}

			if (!StrUtils.isBlankOrNull(v.getWtAddress())) {
				customer.setAddress(v.getWtAddress());
			}
			if (!StrUtils.isBlankOrNull(v.getWtUser())) {
				customer.setPerson(v.getWtUser());
			}
			if (!StrUtils.isBlankOrNull(v.getWtTel())) {
				if (v.getWtTel().length() == 11 && !v.getWtTel().contains("-")) {
					customer.setPhone(v.getWtTel());
				} else {
					customer.setTelephone(v.getWtTel());
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

	// 更新项目信息
	public void updateInfo(Project p, AppProjectEdit v, ObjVo objVo) {
		p.setTaskTotal(Integer.parseInt(v.getPc()));
		p.setTaskNum(0);
		if (v.getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
			p.setZsy(Constants.SAMP_XC);// 职卫 采样
			p.setFbzff(Constants.F);
			p.setPj(Constants.S);
			p.setSampTypeName("/");
		} else {
			p.setZsy(v.getZsy());
			p.setFbzff(v.getFbzff());
			p.setSampTypeId(v.getSampTypeId());
			p.setSampTypeName(v.getSampTypeName());
			p.setPj(v.getPj());
			p.setStandIds(v.getStandIds());
			p.setStandNames(v.getStandNames());
		}
		if (p.getZsy().equals(Constants.SAMP_ZS)) {
			p.setXctk(Constants.F);
		} else {
			p.setXctk(v.getXctk());
		}
		if (StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(createCode(v.getTaskType()));
		}
		if (StrUtils.isBlankOrNull(p.getOrgId())) {
			p.setOrgId(objVo.getOrgId());
			p.setOrgName(objVo.getOrgName());
		}
		p.setHtSt(Constants.CONTRACT_STATUS_WSC);
		p.setPaySt(Constants.CHARGE_STATUS_WSF);
		p.setSampName(v.getSampName());
		p.setTaskType(v.getTaskType());
		p.setPc(Integer.parseInt(v.getPc()));
		p.setPcUnit(v.getPcUnit());
		p.setRdate(v.getRdate());
		p.setFinishDate(v.getFinishDate());
		p.setCycle(Integer.parseInt(v.getCycle()));
		p.setCycleUnit(v.getCycleUnit());
		p.setReportNum(Integer.parseInt(v.getReportNum()));
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

	/**
	 * 生成项目编号
	 * 
	 * @param taskType
	 * @param type
	 * @return
	 */
	public String createCode(String taskType) {
		String flag = EnumBus.getCode(taskType);
		flag += DateUtils.getYear() + DateUtils.getMonth() + DateUtils.getDay();
		String hql = "SELECT max(no) FROM " + projectDao.getEntityName(Project.class) + " WHERE isDel=" + Po.N
				+ " AND no like '" + flag + "%'";
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

	// 采样 提交
	public void commit(Project p, AppProjectEdit v, ObjVo objVo, String isCommit) throws GlobalException {
		// 验证批次是否完结
		if (null != isCommit && isCommit.equals(EunmTask.PASS_Y)) {
			p.setIsBack(Constants.N);
			String status = EunmTask.PROJECT_ZP.getStatus();
			if (p.getXctk().equals(Constants.F)) {// 不踏勘，直接到方案编制环节
				status = EunmTask.PROJECT_BZ.getStatus();
			}
			Progress pg = progressDao.update(p.getProgress().getId(), status, null, null);
			p.setStatus(pg.getStatus());
			projectDao.update(p);
			progressLogDao.addApp(p.getId(), p.getId(), EunmTask.PROJECT_LX.getStatus(), isCommit, v.getRemark(),
					objVo);
		}
	}

	@Override
	public Boolean updateNext(AppProjectEdit v, String isCommit, ObjVo objvo) throws GlobalException {

		try {

			Project p = projectDao.findById(v.getId());
			// 更新费用相关信息
			Invoice invoice = p.getInvoice();
			if (invoice == null) {
				invoice = new Invoice();
			}

			invoice.setDiscount(Float.parseFloat(v.getInvoiceVoDiscount()));
			invoice.setPrice(Float.parseFloat(v.getInvoiceVoPrice()));
			invoice.setYhPrice(Float.parseFloat(v.getInvoiceVoYhPrice()));
			invoice.setJtPrice(Float.parseFloat(v.getInvoiceVoJtPrice()));
			invoice.setBgPrice(Float.parseFloat(v.getInvoiceVoBgPrice()));
			invoice.setOtherPrice(Float.parseFloat(v.getInvoiceVoOtherPrice()));
			invoice.setHtPrice(Float.parseFloat(v.getInvoiceVoHtPrice()));
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
			scheme.setDealRequest(v.getSchemeVoDealRequest());
			scheme.setSaveRequest(v.getSchemeVoSaveRequest());
			scheme.setCyUserName(v.getSchemeVoCyUserName());
			scheme.setCyDate(v.getSchemeVoCyDate());
			scheme.setRemark(v.getSchemeVoRemark());

			scheme.setCyEndDate(v.getSchemeVoCyDate());
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
			commitZSY(v, p, isCommit, objvo);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	// 更新样品信息
	public void uptSamp(Project p, AppProjectEdit v, Scheme scheme) {
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
		if (null != v.getAppProjectPointList()) {
			for (AppProjectPoint spVo : v.getAppProjectPointList()) {
				if (spVo == null || StrUtils.isBlankOrNull(spVo.getSampTypeId())) {
					continue;
				}
				SchemePoint sp = new SchemePoint();
				if (null != spVo.getSort()) {
					sp.setSort(Integer.parseInt(spVo.getSort()));
				} else {
					sp.setSort(schemePointDao.findMaxSort(scheme.getId()) + 1);
				}
				sp.setSampTypeId(spVo.getSampTypeId());
				sp.setSampTypeName(spVo.getSampTypeName());
				sp.setSampName(spVo.getSampName());
				sp.setXz(spVo.getXz());
				sp.setPacking(spVo.getPacking());
				sp.setItemName(spVo.getItemName());
				sp.setItemId(spVo.getItemId());
				sp.setFxPrice(Float.parseFloat(spVo.getFxPrice()));
				sp.setRemark(spVo.getRemark());
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

	// 更新分包信息
	public void uptFb(Project p, AppProjectEdit v) {
		projectFbDao.deleteByProjectId(p.getId());
		int tnum = 0;
		List<String> itemId = new ArrayList<String>();
		List<String> itemName = new ArrayList<String>();
		float fbPrice = 0;
		if (null != v.getAppProjectFbList()) {
			for (AppProjectFb fbVo : v.getAppProjectFbList()) {
				if (fbVo == null || StrUtils.isBlankOrNull(fbVo.getFbVoId())) {
					continue;
				}
				ProjectFb fb = new ProjectFb();
				fb.getFb().setName(fbVo.getFbVoName());
				fb.getFb().setId(fbVo.getFbVoId());
				fb.setFbUser(fbVo.getFbUser());
				fb.setFbMobile(fbVo.getFbMobile());
				fb.setItemNames(fbVo.getItemNames());
				fb.setItemIds(fbVo.getItemIds());
				fb.setNum(Integer.parseInt(fbVo.getNum()));
				fb.setPrice(Float.parseFloat(fbVo.getPrice()));
				fb.setPrice(Float.parseFloat(fbVo.getFbPrice()));
				fb.setProject(p);
				fb.setFb(supplierDao.findById(fbVo.getFbVoId()));
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

	/**
	 * //自送样 提交
	 * 
	 * @param v
	 * @param sch
	 */
	public void commitZSY(AppProjectEdit v, Project p, String isCommit, ObjVo objVo) {
		if (null != isCommit && isCommit.equals(EunmTask.PASS_Y)) {
			Progress schpg = progressDao.update(p.getProgress().getId(), EunmTask.PROJECT_PS.getStatus(), null, null,
					null, null);
			p.setStatus(schpg.getStatus());
			projectDao.update(p);
			progressLogDao.addApp(p.getId(), p.getId(), EunmTask.PROJECT_LX.getStatus(), EunmTask.PASS_Y, v.getRemark(),
					objVo);
		}
	}

}
