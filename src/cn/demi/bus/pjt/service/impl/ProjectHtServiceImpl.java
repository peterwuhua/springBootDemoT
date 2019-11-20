package cn.demi.bus.pjt.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
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
import cn.core.framework.utils.NumberToCN;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.core.framework.utils.openIM.OpenIM;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.dao.IInvoiceDao;
import cn.demi.bus.pjt.dao.IProjectDao;
import cn.demi.bus.pjt.dao.IProjectFbDao;
import cn.demi.bus.pjt.dao.ISchemeDao;
import cn.demi.bus.pjt.dao.ISchemePointDao;
import cn.demi.bus.pjt.po.Invoice;
import cn.demi.bus.pjt.po.Project;
import cn.demi.bus.pjt.po.ProjectFb;
import cn.demi.bus.pjt.po.Scheme;
import cn.demi.bus.pjt.po.SchemePoint;
import cn.demi.bus.pjt.service.IProjectHtService;
import cn.demi.bus.pjt.vo.InvoiceVo;
import cn.demi.bus.pjt.vo.ProjectFbVo;
import cn.demi.bus.pjt.vo.ProjectHtBaseVo;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.bus.pjt.vo.SchemePointVo;
import cn.demi.bus.pjt.vo.SchemeVo;
import cn.demi.cus.contract.dao.IContractDao;
import cn.demi.cus.contract.po.Contract;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Customer;

@Service("bus.schemeHtService")
public class ProjectHtServiceImpl extends BaseServiceImpl<ProjectVo, Project> implements IProjectHtService {
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
	private IContractDao contractDao;
	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private IProjectFbDao projectFbDao;
	@Autowired
	private IInvoiceDao invoiceDao;
	
	@Override
	public ProjectVo find(String id) throws GlobalException {
		Project po = projectDao.findById(id);
		ProjectVo vo = new ProjectVo();
		vo = po2Vo(po);
		if(StrUtils.isBlankOrNull(po.getHtNo())) {
			po.setHtNo(createHtCode(vo));
		}
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
		if (StrUtils.isBlankOrNull(vo.getQdDate())) {
			vo.setQdDate(DateUtils.getCurrDateStr());
			vo.setQdId(getCurrent().getAccountId());
			vo.setQdName(getCurrent().getUserName());
		}
		if (null!=vo.getInvoiceVo()&&vo.getInvoiceVo().getPayRatio()<=0.0){
			vo.getInvoiceVo().setPayRatio(0.5);// 默认预付款50%
		}
		return vo;
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
		return vo;
	}

	@Override
	public ProjectVo find4Ht(String id) throws GlobalException {
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

		try {
			BigDecimal numberOfMoney = new BigDecimal(vo.getHtPrice());
			vo.setHtPrice_(NumberToCN.number2CNMontrayUnit(numberOfMoney));
		} catch (Exception e1) {
			vo.setHtPrice_("/");
		}
		try {
			vo.setSdate_(DateUtils.getChineseDate(DateUtils.parse(vo.getSdate(), DateUtils.YYYY_MM_DD)));
		} catch (ParseException e) {
			vo.setSdate_("");
		}

		BigDecimal yfkMin = new BigDecimal(vo.getInvoiceVo().getYfkMin());
		String yfkMax = NumberToCN.number2CNMontrayUnit(yfkMin); // 数字金额转成中文大写
		vo.getInvoiceVo().setYfkMax(yfkMax);
		BigDecimal sskMin = new BigDecimal(vo.getInvoiceVo().getSskMin());
		String sskMax = NumberToCN.number2CNMontrayUnit(sskMin);
		vo.getInvoiceVo().setSskMax(sskMax);
		BigDecimal htPrice = new BigDecimal(vo.getInvoiceVo().getHtPrice());
		String totalPriceMax = NumberToCN.number2CNMontrayUnit(htPrice);
		vo.getInvoiceVo().setTotalPriceMax(totalPriceMax); // 转成合同总金额大写
		double yf = vo.getInvoiceVo().getPayRatio() * 100;
		double left = (1 - vo.getInvoiceVo().getPayRatio()) * 100;
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String yfRatio = decimalFormat.format(yf);// 去掉结尾的.0
		String leftRatio = decimalFormat.format(left);
		vo.getInvoiceVo().setYfRatio(yfRatio);
		vo.getInvoiceVo().setLeftRatio(leftRatio);
		vo.getInvoiceVo().setPayRatio(vo.getInvoiceVo().getPayRatio());

		// 获取合同签订的开始日期和结束日期
		ProjectHtBaseVo pjtHtBaseVo = new ProjectHtBaseVo();

		// 合同签订日期
		String qdDate = vo.getQdDate();
		if (!StrUtils.isBlankOrNull(qdDate)) {
			if (qdDate.contains(" ")) {
				qdDate = qdDate.split(" ")[0];
			}
			qdDate = DateUtils.getChineseDate(qdDate);//将签订日期格式转成年月日格式
			pjtHtBaseVo.setQdDate(qdDate);
		} else {
			pjtHtBaseVo.setQdDate("");
		}

		vo.getInvoiceVo().setHtPriceZn(String.valueOf(vo.getInvoiceVo().getHtPrice()));// 合同价格字符
		vo.getInvoiceVo().setYfkMinZn(String.valueOf(vo.getInvoiceVo().getYfkMin()));
		vo.getInvoiceVo().setSskMinZn(String.valueOf(vo.getInvoiceVo().getSskMin()));
		// 获取开始日期
		String[] finishDates = vo.getFinishDate().split("-");
		pjtHtBaseVo.setEndyear(finishDates[0]);
		pjtHtBaseVo.setEndmonth(finishDates[1]);
		pjtHtBaseVo.setEndday(finishDates[2]);

		// 获取预计完成日期
		String[] rDates = vo.getRdate().split("-");
		pjtHtBaseVo.setBeginyear(rDates[0]);
		pjtHtBaseVo.setBeginmonth(rDates[1]);
		pjtHtBaseVo.setBeginday(rDates[2]);
		vo.setPjtHtBaseVo(pjtHtBaseVo);
		return vo;
	}

	@Override
	public void update(ProjectVo v) throws GlobalException {
		Project po = projectDao.findById(v.getId());
		po.setQdId(v.getQdId());
		po.setQdName(v.getQdName());
		po.setQdDate(v.getQdDate());
		po.setQdMsg(v.getQdMsg());
		if(StrUtils.isBlankOrNull(po.getHtTemp())) {
			po.setHtNo(createHtCode(v));
		}else if(!StrUtils.isBlankOrNull(v.getHtTemp())&&!po.getHtTemp().equals(v.getHtTemp())) {
			po.setHtNo(createHtCode(v));
		}else {
			po.setHtNo(v.getHtNo());
		}
		po.setHtTemp(v.getHtTemp());
		// 同步付款比例费用信息到费用表
		Invoice ipo = po.getInvoice();
		ipo.setYfkMin(v.getInvoiceVo().getYfkMin()); // 预付款数字小写
		ipo.setSskMin(v.getInvoiceVo().getSskMin()); // 剩余款数字小写
		ipo.setPayRatio(v.getInvoiceVo().getPayRatio()); // 预付款比例
		invoiceDao.update(ipo);// 更新费用信息
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			progressDao.update(po.getProgress().getId(), EunmTask.PROJECT_JX.getStatus(),null,null,null,null);
			po.setStatus(EunmTask.PROJECT_JX.getStatus());
			progressLogDao.add(po.getId(), po.getId(), EunmTask.PROJECT_QD.getStatus(), v.getIsCommit(), v.getQdMsg());
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
		pageResult.addCondition(new QueryCondition("status like '" + EunmTask.PROJECT_QD.getStatus() + "'"));
		//pageResult.addCondition(new QueryCondition("userId like '%" + getCurrent().getAccountId() + "%'"));
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
		hql.append(" AND log.status='" + EunmTask.PROJECT_QD.getStatus() + "' AND log.userId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' ");
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
	public void update2Ht(ProjectVo v) throws GlobalException {
		Project project = projectDao.findById(v.getId());
		project.setHtName(v.getHtName());
		project.setHtPath(v.getHtPath());
		project.setHtSt(Constants.CONTRACT_STATUS_WQD);
		projectDao.update(project);// 更新合同审核人信息
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
			contract.setNum(String.valueOf(project.getPc()));
			contract.setContractSum(String.valueOf(project.getInvoice().getTotalPrice()));
			contract.setPayPrice(String.valueOf(project.getHtPrice()));
			contract.setDiscount(String.valueOf(project.getInvoice().getDiscount()));
			contract.setPath(project.getHtPath());
			contract.setTrueName(project.getHtName());
			contract.setStatus(project.getHtSt());
			contractDao.add(contract);
			project.setSalerId(customer.getSalerId());
			project.setSalerName(customer.getSaler());
		} else {
			contract.setLeadId(getCurrent().getAccountId());
			contract.setLeadName(getCurrent().getUserName());
			contract.setName(project.getSampName());
			contract.setNum(String.valueOf(project.getPc()));
			contract.setContractSum(String.valueOf(project.getInvoice().getTotalPrice()));
			contract.setPayPrice(String.valueOf(project.getHtPrice()));
			contract.setDiscount(String.valueOf(project.getInvoice().getDiscount()));
			contract.setPath(project.getHtPath());
			contract.setTrueName(project.getHtName());
			contract.setStatus(project.getHtSt());
			contractDao.update(contract);
		}
		Progress pg = progressDao.findByBusId(contract.getId());
		if (pg == null) {
			progressDao.add(contract.getId(), EunmTask.PROJECT_HT.getStatus(), null, null, project.getSalerId(), project.getSalerName());
		}
		progressLogDao.add(project.getId(), project.getId(), EunmTask.PROJECT_QD.getStatus(), "", "生成或更新合同模板");
		// IM消息提醒
		if (!StrUtils.isBlankOrNull(project.getSalerId())) {
			OpenIM.pushMsg(getCurrent().getLoginName(), accountDao.findById(project.getSalerId()).getLoginName(), "合同文件已生成/更新，请及时查收！");
		}
	}

	@Override
	public void updateHtSt(String code) throws GlobalException {
		List<Project> projectList = projectDao.findByProperty("htNo", code);
		if(projectList!=null&&projectList.size()>0){
			Project po = projectList.get(0);
			po.setHtSt(Constants.CONTRACT_STATUS_YQD);
			projectDao.update(po);// 更新合同信息
		}else {
			throw new GlobalException("数据已过期！");
		}
	}
	/**
	 * 生成合同编号
	 */
	public String createHtCode(ProjectVo vo){
		String temp=vo.getHtTemp();
		if(StrUtils.isBlankOrNull(temp)) {
			temp="";
		}else if(temp.equals("HT-ZY-WS.ftl")) {//职业卫生检测技术服务合同
			temp="JSTB-ZW-JC-";
		}else if(temp.equals("HT-ZW-XZ.ftl")) {//职卫现状评价合同
			temp="JSTB-ZW-PJ-";
		}else if(temp.equals("HT-AQ-YJ.ftl")) {//安全应急预案
			temp="JSTB-AQ-YA-";
		}else if(temp.equals("HT-AQ-SJ.ftl")) {//安全三级标准化评审合同
			temp="JSTB-AQ-BZ-";
		}else if(temp.equals("HT-ZH-JS.ftl")) {//综合技术服务合同
			temp="JSTB-ZH-JC-";
		}else if(temp.equals("HT-HJ-JS.ftl")) {//环境技术服务合同 
			temp="JSTB-HJ-JC-";
		}else if(temp.equals("HT-WT-JC.ftl")) {//委托检测协议
			temp="JSTB-GGWS-";
		}
		String flag=temp+DateUtils.getYear();
		String hql="SELECT max(htNo) FROM "+projectDao.getEntityName(Project.class)+" WHERE isDel="+Po.N+" AND htNo like '"+flag+"%'";
		String ls=(String) projectDao.query(hql).getSingleResult();
		String no=flag;
		if(ls==null) {
			no+="001";
		}else {
			ls=ls.replace(flag, "");
			int sort;
			try {
				sort = Integer.valueOf(ls);
			} catch (NumberFormatException e) {
				sort=0;
			}
			sort++;
			if(sort<10) {
				no+="00"+sort;
			}else if(sort<100){
				no+="0"+sort;
			}else {
				no+=sort;
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
