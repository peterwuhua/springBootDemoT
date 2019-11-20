package cn.demi.bus.pjt.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Org;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.dao.ICustPointDao;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.dao.IInvoiceDao;
import cn.demi.bus.pjt.dao.IProjectDao;
import cn.demi.bus.pjt.dao.IProjectFbDao;
import cn.demi.bus.pjt.dao.ISchemeDao;
import cn.demi.bus.pjt.dao.ISchemePointDao;
import cn.demi.bus.pjt.po.CustPoint;
import cn.demi.bus.pjt.po.Invoice;
import cn.demi.bus.pjt.po.Project;
import cn.demi.bus.pjt.po.ProjectFb;
import cn.demi.bus.pjt.po.Scheme;
import cn.demi.bus.pjt.po.SchemePoint;
import cn.demi.bus.pjt.service.IProjectFaService;
import cn.demi.bus.pjt.vo.InvoiceVo;
import cn.demi.bus.pjt.vo.ProjectFbVo;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.bus.pjt.vo.SchemePointVo;
import cn.demi.bus.pjt.vo.SchemeVo;
import cn.demi.cus.customer.dao.IClientPointDao;
import cn.demi.cus.customer.po.Client;
import cn.demi.cus.customer.po.ClientPoint;
import cn.demi.init.sp.vo.PcUnit;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.res.dao.ISupplierDao;

/**
 * 方案 业务逻辑层
 * 
 * @author QuJunLong
 */
@Service("bus.schemeFaService")
public class ProjectFaServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectFaService {
	@Autowired
	private IProjectDao projectDao;
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
	private IInvoiceDao invoiceDao;
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Autowired
	private IClientPointDao clientPointDao;
	@Autowired
	private IImDao imDao;
	@Autowired
	private ICustPointDao custPointDao;
	@Autowired
	private IItemDao itemDao;

	// @Autowired
	// private IMethodDao methodDao;
	@Override
	public ProjectVo find(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		vo = po2Vo(po);
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
		vo.setSchemeList(getSchList(vo, po));
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

	// 装配 方案详情
	public List<SchemeVo> getSchList(ProjectVo vo, Project po) throws GlobalException {
		List<SchemeVo> schVoList = new ArrayList<SchemeVo>();
		int pc = vo.getPc();
		List<Scheme> schList = schemeDao.listByPjId(vo.getId());
		String startDate = vo.getRdate();
		if (schList.size() == 0) {
			for (int n = 0; n < pc; n++) {
				String endDate = getNextDate(startDate, vo.getCycle(), vo.getCycleUnit());
				endDate = DateUtils.getNextDate(endDate, -1);
				Scheme sch = new Scheme();
				sch.setProject(po);
				sch.setNum(n + 1);
				sch.setStartDate(startDate);
				sch.setEndDate(endDate);
				sch.setSampName(vo.getSampName());
				sch.setCyDay(1);
				schemeDao.add(sch);
				SchemeVo schVo = new SchemeVo();
				schVo = schVo.toVo(sch);
				schVoList.add(schVo);
				startDate = DateUtils.getNextDate(endDate, 1);
			}
		} else if (schList.size() < pc) {
			for (Scheme sch : schList) {
				SchemeVo schVo = new SchemeVo();
				schVo = schVo.toVo(sch);
				schVo.setPointList(getPointList(sch.getId()));
				if (schVo.getCyDay() < 1) {
					schVo.setCyDay(1);
				}
				schVoList.add(schVo);
				startDate = DateUtils.getNextDate(schVo.getEndDate(), 1);
			}
			for (int n = schList.size(); n < pc; n++) {
				String endDate = getNextDate(startDate, vo.getCycle(), vo.getCycleUnit());
				endDate = DateUtils.getNextDate(endDate, -1);
				Scheme sch = new Scheme();
				sch.setProject(po);
				sch.setNum(n + 1);
				sch.setStartDate(startDate);
				sch.setEndDate(endDate);
				sch.setSampName(vo.getSampName());
				sch.setCyDay(1);
				schemeDao.add(sch);
				SchemeVo schVo = new SchemeVo();
				schVo = schVo.toVo(sch);
				schVoList.add(schVo);
				startDate = DateUtils.getNextDate(endDate, 1);
			}
		} else {
			int n = 0;
			for (Scheme scheme : schList) {
				if (n < pc) {
					SchemeVo schVo = new SchemeVo();
					schVo = schVo.toVo(scheme);
					schVo.setPointList(getPointList(scheme.getId()));
					schVoList.add(schVo);
				} else {
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
				n++;
			}
		}
		return schVoList;
	}

	// 装配 方案详情 查看
	public List<SchemeVo> getSchList4Show(ProjectVo vo) throws GlobalException {
		List<SchemeVo> schVoList = new ArrayList<SchemeVo>();
		List<Scheme> schList = schemeDao.listByPjId(vo.getId());
		for (Scheme scheme : schList) {
			SchemeVo schVo = new SchemeVo();
			schVo = schVo.toVo(scheme);
			schVo.setPointList(getPointList(scheme.getId()));
			schVoList.add(schVo);
		}
		return schVoList;
	}

	public List<SchemePointVo> getPointList(String schemeId) throws GlobalException {
		List<SchemePointVo> spVoList = new ArrayList<>();
		List<SchemePoint> spList = schemePointDao.listBySchId(schemeId);
		int sort = 1;
		for (SchemePoint sp : spList) {
			SchemePointVo spVo = new SchemePointVo();
			spVo = spVo.toVo(sp);
			spVo.setImId(imDao.findByBusId(sp.getId()));
			spVo.setSort(sort);
			spVoList.add(spVo);
			sort++;
		}
		return spVoList;
	}

	// 获取下个周期开始日期
	public String getNextDate(String startDate, int cycle, String cycleUnit) {
		String nextDate = null;
		if (cycleUnit.equals(Project.ZQ_DAY)) {// 周期为 ‘日’
			nextDate = DateUtils.getNextDate(startDate, cycle);
		} else if (cycleUnit.equals(Project.ZQ_MONTH)) {// 周期为 ‘月’ 单位为 次/月
			nextDate = DateUtils.getNextDate4M(startDate, cycle);
		} else if (cycleUnit.equals(Project.ZQ_QUARTER)) {// 周期为 ‘季’ 单位为 次/季
			nextDate = DateUtils.getNextDate4M(startDate, cycle * 3);
		} else if (cycleUnit.equals(Project.ZQ_YEAR)) {// 周期为 ‘年’ 单位为 次/年
			nextDate = DateUtils.getNextDate4M(startDate, cycle * 12);
		}
		return nextDate;
	}

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
		// 方案信息
		vo.setSchemeList(getSchList4Show(vo));
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
	public void update(ProjectVo v) throws GlobalException {
		Project po = projectDao.findById(v.getId());
		po.setFaUserId(getCurrent().getAccountId());
		po.setFaUserName(getCurrent().getUserName());
		po.setFaDate(DateUtils.getCurrDateTimeStr());
		po.setFaMsg(v.getFaMsg());
		po.setPc(v.getPc());
		po.setCycle(v.getCycle());
		po.setCycleUnit(v.getCycleUnit());
		po.setJsps(Constants.S);
		//评审部门
		po.setOrgId1(v.getOrgId1());
		po.setOrgName1(v.getOrgName1());
		po.setOrgId2(v.getOrgId2());
		po.setOrgName2(v.getOrgName2());
		po.setOrgId3(v.getOrgId3());
		po.setOrgName3(v.getOrgName3());
		po.setOrgId4(v.getOrgId4());
		po.setOrgName4(v.getOrgName4());
		po.setOrgId5(v.getOrgId5());
		po.setOrgName5(v.getOrgName5());
		po.setOrgIds(v.getOrgIds());
		po.setOrgNames(v.getOrgNames());
		// 更新费用相关信息
		Invoice invoice = po.getInvoice();
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
		po.setInvoice(invoice);
		po.setHtPrice(v.getHtPrice());
		// 更新分包信息
		uptFb(po, v);
		// 更新方案信息
		List<SchemeVo> schList = v.getSchemeList();
		for (SchemeVo schemeVo : schList) {
			if (schemeVo == null)
				continue;
			Scheme scheme = schemeDao.findById(schemeVo.getId());
			if (scheme == null) {
				scheme = new Scheme();
			}
			scheme = scheme.toPo(schemeVo, scheme);
			scheme.setProject(po);
			scheme.setSampName(po.getSampName());
			scheme.setStatus(Scheme.ST_WZX);
			scheme.setNum(schemeVo.getNum());
			scheme.setCyDay(schemeVo.getCyDay());
			schemeDao.saveOrUpdate(scheme);
			// 更新方案点位信息
			uptPoint(po, scheme, schemeVo, v.getIsCommit());
			schemeDao.update(scheme);
		}
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			String orgIds = v.getOrgIds().substring(0, v.getOrgIds().length()-1);
			po.setOrgIds(orgIds);
			String orgNames = v.getOrgNames().substring(0, v.getOrgNames().length()-1);
			po.setOrgNames(orgNames);
			Progress pg = progressDao.update(po.getProgress().getId(), EunmTask.PROJECT_PS.getStatus(), po.getOrgIds(), po.getOrgNames(), null, null);
			progressLogDao.add(po.getId(), po.getId(), EunmTask.PROJECT_BZ.getStatus(), v.getIsCommit(), v.getFaMsg());
			po.setStatus(pg.getStatus());
			po.setIsBack(Constants.N);
		}
		projectDao.update(po);
		v.setNo(po.getNo());
	}

	// 更新点位信息
	public void uptPoint(Project po, Scheme scheme, SchemeVo schemeVo, String isCommit) throws GlobalException {
		List<SchemePointVo> spList = schemeVo.getPointList();
		List<String> sampTypeId = new ArrayList<String>();
		List<String> sampTypeName = new ArrayList<String>();
		Set<String> itemNameSet = new HashSet<String>();
		Set<String> pointNameSet = new HashSet<String>();
		int sampNum = 0;
		if (null != spList) {
			for (SchemePointVo spVo : spList) {
				if (spVo == null)
					continue;
				SchemePoint sp = schemePointDao.findById(spVo.getId());
				if (null == sp) {
					sp = new SchemePoint();
				}
				sp.setScheme(scheme);
				sp.setProjectId(po.getId());
				if (!StrUtils.isBlankOrNull(spVo.getSampTypeId())) {
					SampType st = sampTypeDao.findById(spVo.getSampTypeId());
					sp.setSampTypeId(st.getId());
					sp.setSampTypeName(st.getName());
					sp.setPointType(st.getType());
				}
				if (po.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
					sp.setSampName(sp.getSampTypeName());
				} else {
					sp.setSampName(po.getSampName());
				}
				sp.setPc(spVo.getPc());
				sp.setItemId(spVo.getItemId());
				sp.setItemName(spVo.getItemName());
				sp.setFxPrice(spVo.getFxPrice());
				sp.setPointName(spVo.getPointName());
				sp.setPointCode(spVo.getPointCode());
				sp.setSort(spVo.getSort());
				sp.setRoom(spVo.getRoom());
				sp.setCyType(spVo.getCyType());
				sp.setCyHours(spVo.getCyHours());
				schemePointDao.saveOrUpdate(sp);
				// 更新项目方法关系
				imDao.uptIm(sp.getId(), spVo.getImId());
				// 同步点位
				if (!StrUtils.isBlankOrNull(sp.getPointName()) && null != isCommit && isCommit.equals(EunmTask.PASS_Y)) {
					synchPoint(po.getCust().getClient(), sp, spVo.getImId());
					// 汇总方案的检测点位
					pointNameSet.add(sp.getPointName());
				}
				// 汇总方案的样品类别
				if (!sampTypeId.contains(sp.getSampTypeId())) {
					sampTypeId.add(sp.getSampTypeId());
					sampTypeName.add(sp.getSampTypeName());
				}
				// 汇总方案的检测项目
				String names[] = sp.getItemName().split(",");
				for (String itemName : names) {
					if (StrUtils.isBlankOrNull(itemName)) {
						continue;
					}
					itemNameSet.add(itemName);
				}
				sampNum += sp.getPc();
			}
		}
		scheme.setSampTypeId(String.join(",", sampTypeId));
		scheme.setSampTypeName(String.join(",", sampTypeName));
		if (po.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			scheme.setSampName(scheme.getSampTypeName());
		} else {
			scheme.setSampName(po.getSampTypeName());
		}
		scheme.setSampNum(sampNum);
		scheme.setItemNames(String.join(",", itemNameSet));
		scheme.setPointNames(String.join(",", pointNameSet));
	}

	// 更新项目方法关系信息
	// public void uptIm(String busId,String imStr) {
	// imDao.deleteByBusId(busId);
	// String imArr[]=imStr.split(",");
	// int n=0;
	// for (String i : imArr) {
	// if(!StrUtils.isBlankOrNull(i)&&i.contains("-")) {
	// String m[]=i.split("-");
	// if(m.length==2&&m[0].trim().length()>0) {
	// Im im=new Im();
	// im.setBusId(busId);
	// Item item=itemDao.findById(m[0].trim());
	// im.setItemId(item.getId());
	// im.setItemName(item.getName());
	// im.setPrice(item.getPrice());
	// if(!StrUtils.isBlankOrNull(m[1].trim())) {
	// Method method=methodDao.findById(m[1].trim());
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
	public void uptFb(Project po, ProjectVo vo) {
		projectFbDao.deleteByProjectId(po.getId());
		int tnum = 0;
		List<String> fbItemId = new ArrayList<String>();// 分包项目集合
		List<String> fbItemName = new ArrayList<String>();
		if (null != vo.getFbList()) {
			for (ProjectFbVo fbVo : vo.getFbList()) {
				if (fbVo == null || fbVo.getFbVo() == null || StrUtils.isBlankOrNull(fbVo.getFbVo().getId())) {
					continue;
				}
				ProjectFb fb = new ProjectFb();
				fb = fb.toPo(fbVo, fb);
				fb.setProject(po);
				fb.setFb(supplierDao.findById(fbVo.getFbVo().getId()));
				projectFbDao.add(fb);
				tnum += fb.getNum();
				String ids[] = fb.getItemIds().split(",");
				String names[] = fb.getItemNames().split(",");
				int i = 0;
				for (String iid : ids) {
					if (!StrUtils.isBlankOrNull(iid) && !fbItemId.contains(iid)) {
						fbItemId.add(iid);
						fbItemName.add(names[i]);
					}
					i++;
				}
			}
		}
		po.setFb(vo.getFb());
		po.setFbPrice(vo.getFbPrice());
		po.setFbItemId(String.join(",", fbItemId));
		po.setFbItemName(String.join(",", fbItemName));
		po.setFbSampNum(tnum);
	}

	// 同步受检单位的检测点位
	public void synchPoint(Client client, SchemePoint sp, String imStr) throws GlobalException {
		ClientPoint cp = clientPointDao.findBycusId(client.getId(), sp.getSampTypeId(), sp.getScheme().getProject().getSampType(), sp.getPointName());
		if (cp == null) {
			cp = new ClientPoint();
			cp.setClient(client);
			cp.setSampType(sp.getScheme().getProject().getSampType());
			cp.setSampTypeId(sp.getSampTypeId());
			cp.setSampTypeName(sp.getSampTypeName());
			cp.setType(sp.getPointType());
			cp.setSampName(sp.getSampName());
			cp.setRoom(sp.getRoom());
			cp.setName(sp.getPointName());
			cp.setCode(sp.getPointCode());
			cp.setItemId(sp.getItemId());
			cp.setItemName(sp.getItemName());
			cp.setImId(imStr);
			clientPointDao.add(cp);
		} else {
			cp.setImId(imStr);
			cp.setItemId(sp.getItemId());
			cp.setItemName(sp.getItemName());
			cp.setCode(sp.getPointCode());
		}
	}

	/**
	 * 生成合同编号
	 */
	public String createHtCode() {
		String flag = DateUtils.getYear() + DateUtils.getMonth() + DateUtils.getDay();
		String hql = "SELECT max(htNo) FROM " + projectDao.getEntityName(Project.class) + " WHERE isDel=" + Po.N + " AND htNo like '" + flag + "%'";
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

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ProjectVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.addOrder("startDate", OrderCondition.ORDER_ASC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		String orgId = getCurrent().getOrgId();
		List<String> orgIds = null;
		if (getCurrent().getOrgName().contains("环境")) {
			orgIds = orgDao.listChild(orgId);
			orgIds.add(orgId);
		} else if (getCurrent().getOrgName().contains("职业卫生")) {
			orgIds = orgDao.listChild(orgId);
			orgIds.add(orgId);
		}
		if(orgIds!=null) {
			pageResult.addCondition(new QueryCondition(" orgId in ('" + String.join("','", orgIds) + "')"));	
		}
		
		pageResult.addCondition(new QueryCondition(" status like '" + EunmTask.PROJECT_BZ.getStatus() + "'"));
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
		hql.append(" AND log.status='" + EunmTask.PROJECT_BZ.getStatus() + "' AND log.userId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' ");
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
	public void addPoint(String id) throws GlobalException {
		schemePointDao.deleteByProjectId(id);
		List<Scheme> scList = schemeDao.listByPjId(id);
		List<CustPoint> cpList = custPointDao.listByProjectId(id);
		for (Scheme scheme : scList) {
			if (null != cpList) {
				int sort = 1;
				for (CustPoint cp : cpList) {
					SchemePoint sp = new SchemePoint();
					sp.setProjectId(id);
					sp.setScheme(scheme);
					sp.setPc(3);
					sp.setPcUnit(PcUnit.CT.getName());
					sp.setRoom(cp.getRoom().getName());
					sp.setPointName(cp.getName());
					sp.setItemId(cp.getItemIds());
					sp.setItemName(cp.getItemNames());
					sp.setCyType(Constants.CY_DD);
					sp.setCyHours(15);
					if (!StrUtils.isBlankOrNull(cp.getItemIds())) {
						sp.setFxPrice(itemDao.getSumPrice(cp.getItemIds()));
					}
					sp.setSort(sort);
					schemePointDao.add(sp);
					sort++;
					// IM信息
					imDao.uptIm(sp.getId(), imDao.findByBusId(cp.getId()));
				}
			}
		}
	}
}
