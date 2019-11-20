package cn.demi.cus.contract.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IUserService;
import cn.demi.bus.pjt.service.IProjectHtService;
import cn.demi.cus.contract.dao.IContractDao;
import cn.demi.cus.contract.po.Contract;
import cn.demi.cus.contract.service.IContractService;
import cn.demi.cus.contract.vo.ContractVo;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.dao.IVisitDao;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.po.Visit;
import cn.demi.cus.customer.vo.CustomerVo;

/**
 * Create on : 2016年11月15日 下午2:56:17 <br>
 * Description : ContractServiceImpl <br>
 * 
 * @version v 1.0.0 <br>
 * @author Danlee Li<br>
 */
@Service("cus.contractService")
public class ContractServiceImpl extends BaseServiceImpl<ContractVo, Contract> implements IContractService {
	@Autowired
	private IContractDao contractDao;
	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private IVisitDao visitDao;
	@Autowired
	private IUserService userService;
	@Autowired
	private IProjectHtService projectHtService;

	@Override
	public void add(ContractVo v) throws GlobalException {
		Contract p = vo2Po(v);
		if (null != v.getCustomerVo() && null != v.getCustomerVo().getId()) {
			p.setCustomer(customerDao.findById(v.getCustomerVo().getId()));
		}
		p.setStatus(Constants.CONTRACT_STATUS_WQD);
		p.setOrgId(getCurrent().getOrgId());
		p.setOrgName(getCurrent().getOrgName());
		contractDao.add(p);
		v.setId(p.getId());
	}

	@Override
	public void update(ContractVo v) throws GlobalException {
		Contract p = contractDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		// if(null!=v.getCustomerVo()&&null!=v.getCustomerVo().getId()) {
		// p.setCustomer(customerDao.findById(v.getCustomerVo().getId()));
		// }
		if (null == p.getStatus() || p.getStatus().equals(Constants.CONTRACT_STATUS_WQD)) {
			p.setStatus(Constants.CONTRACT_STATUS_YQD);
		}
		contractDao.update(p);
		// 更新项目合同状态-已回签
		projectHtService.updateHtSt(p.getCode());
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ContractVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("orgId like '" + getCurrent().getOrgId() + "'"));
		pageResult.addCondition(new QueryCondition("salerId like '" + getCurrent().getAccountId() + "'"));
		pageResult = contractDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Contract>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Tab(GridVo gridVo, ContractVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());

		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		pageResult.addConditionList(toQueryList(v));

		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);

		pageResult = contractDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Contract>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<QueryCondition> toQueryList(ContractVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if (null != v.getCustomerVo() && !StrUtils.isBlankOrNull(v.getCustomerVo().getId()))
			queryList.add(new QueryCondition("customer.id", QueryCondition.EQ, v.getCustomerVo().getId()));
		if (!StrUtils.isBlankOrNull(v.getCode())) {
			queryList.add(new QueryCondition("code", QueryCondition.AK, v.getCode()));
		}
		// queryList.add(new QueryCondition("status!='" + Constants.CONTRACT_STATUS_YWJ
		// + "' OR status IS NULL"));
		return queryList;
	}

	@Override
	public void data2Vo(String[] values, ContractVo v, String param) throws GlobalException {
		CustomerVo cusVo = new CustomerVo();
		if (StrUtils.isNotBlankOrNull(param)) {
			cusVo.setId(param);
			v.setCustomerVo(cusVo);
		} else {
			if (!StrUtils.isBlankOrNull(values[1])) {
				Customer customer = customerDao.findByName(values[1].trim());
				if (null != customer) {
					cusVo = cusVo.toVo(customer);
					v.setCustomerVo(cusVo);
				}
			}
		}
		v.setCode(values[2]);
		v.setName(values[3]);
		v.setSdate(values[4]);
		v.setEdime(values[5]);
		v.setNum(values[6]);
		v.setDiscount(values[7]);
		v.setContractSum(values[8]);
		v.setPayType(values[9]);
		v.setPayWay(values[10]);
		v.setRemark(values[11]);
	}

	@Override
	public List<ContractVo> list(ContractVo v) throws GlobalException {
		if (null != v.getCustomerVo()) {
			return toVoList(contractDao.listBycusId(v.getCustomerVo().getId()));
		} else {
			return super.list(v);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Execut(GridVo gridVo, ContractVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());

		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("sdate<='" + DateUtils.getCurrDateStr() + "'"));
		queryList.add(new QueryCondition("edime>='" + DateUtils.getCurrDateStr() + "'"));
		queryList.add(new QueryCondition("status!='" + Constants.CONTRACT_STATUS_YWJ + "' OR status IS NULL"));
		pageResult.addConditionList(queryList);

		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);

		pageResult = contractDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Contract>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Com(GridVo gridVo, ContractVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());

		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("status ='" + Constants.CONTRACT_STATUS_YWJ + "'"));
		pageResult.addConditionList(queryList);

		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);

		pageResult = contractDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Contract>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Fast(GridVo gridVo, ContractVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());

		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("edime <'" + DateUtils.getNextDate(DateUtils.getCurrDateStr(), +30) + "'"));
		queryList.add(new QueryCondition("edime >'" + DateUtils.getCurrDateStr() + "'"));
		queryList.add(new QueryCondition("status!='" + Constants.CONTRACT_STATUS_YWJ + "' or status IS NULL"));
		pageResult.addConditionList(queryList);

		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);

		pageResult = contractDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Contract>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Expired(GridVo gridVo, ContractVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());

		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("edime <'" + DateUtils.getCurrDateStr() + "'"));
		queryList.add(new QueryCondition("status!='" + Constants.CONTRACT_STATUS_YWJ + "' OR status IS NULL"));
		pageResult.addConditionList(queryList);

		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);

		pageResult = contractDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Contract>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	/**
	 * 获取给定日期N个工作后的日期
	 * 
	 * @param n个工作日
	 * @return
	 */
	public String getWorkDay(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int mod = num % 5;
		int other = num / 5 * 7;
		for (int i = 0; i < mod;) {
			cal.add(Calendar.DATE, 1);
			switch (cal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY:
			case Calendar.SATURDAY:
				break;
			default:
				i++;
				break;
			}
		}
		if (other > 0)
			cal.add(Calendar.DATE, other);
		return DateUtils.format(cal.getTime(), DateUtils.formatStr_yyyyMMdd);
	}

	/**
	 * 
	 * <p>
	 * Title: gridData4YHQ
	 * </p>
	 * <p>
	 * Description: 用于销售管理-客户回访获取合同截止日期以及状态
	 * </p>
	 * 
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.demi.cus.contract.service.IContractService#gridData4YHQ(cn.core.framework.common.page.GridVo,
	 *      cn.demi.cus.contract.vo.ContractVo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContractVo> gridData4YHQ(ContractVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("edime <'" + DateUtils.getNextDate(DateUtils.getCurrDateStr(), +30) + "'"));
		queryList.add(new QueryCondition("edime >'" + DateUtils.getCurrDateStr() + "'"));
		queryList.add(new QueryCondition("(status='" + Constants.CONTRACT_STATUS_YQD + "'  or status = '" + Constants.CONTRACT_STATUS_ZXZ + "')"));
		if (v != null) {
			if (!StrUtils.isBlankOrNull(v.getSalerId())) {
				queryList.add(new QueryCondition("  saler_id=\'" + v.getSalerId() + "\'"));
			}
			if (v.getCustomerVo() != null && !StrUtils.isBlankOrNull(v.getCustomerVo().getId())) {
				queryList.add(new QueryCondition("  customer_id=\'" + v.getCustomerVo().getId() + "\'"));
			}
		}

		return toVoList(contractDao.list(queryList));
	}

	@Override
	public GridVo gridData4Sale(GridVo gridVo, ContractVo v) throws GlobalException {
		if (v != null) {
			PageResult pageResult = getPageResult(gridVo, v);
			if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
				pageResult.setOrder(OrderCondition.ORDER_DESC);
				pageResult.setOrderColumn("code");
			} else {
				pageResult.setOrder(gridVo.getSord());
				pageResult.setOrderColumn(gridVo.getSidx());
			}
			List<QueryCondition> queryList = new ArrayList<QueryCondition>();
			if (!StrUtils.isBlankOrNull(v.getSalerId())) {
				queryList.add(new QueryCondition("  saler_id=\'" + v.getSalerId() + "\'"));
			}

			queryList.add(new QueryCondition(" status = '" + Constants.CONTRACT_STATUS_WQD + "' "));
			PageBean pageBean = pageResult.getPageBean();
			pageBean.setCurrentPage(gridVo.getPage());
			if (0 != gridVo.getRows())
				pageBean.setPageSize(gridVo.getRows());
			pageResult.setPageBean(pageBean);
			pageResult.addConditionList(queryList);

			 pageResult = contractDao.getPageResult(pageResult);
			gridVo.setDatas(poList2mapList((List<Contract>) pageResult.getResultList()));
			gridVo.setRecords(pageResult.getPageBean().getTotalRows());
			gridVo.setPage(pageResult.getPageBean().getCurrentPage());
			gridVo.setRows(pageResult.getPageBean().getPageSize());
			gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		}
		return gridVo;
	}

	@Override
	public GridVo gridData4FastSale(GridVo gridVo, ContractVo v) throws GlobalException {
		if (v != null) {
			PageResult pageResult = getPageResult(gridVo, v);
			if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
				pageResult.setOrder(OrderCondition.ORDER_DESC);
				pageResult.setOrderColumn("code");
			} else {
				pageResult.setOrder(gridVo.getSord());
				pageResult.setOrderColumn(gridVo.getSidx());
			}
			// 执行中的合同（快到期结合同）
			List<QueryCondition> queryList = new ArrayList<QueryCondition>();
			queryList.add(new QueryCondition("edime <'" + DateUtils.getNextDate(DateUtils.getCurrDateStr(), +30) + "'"));
			queryList.add(new QueryCondition("edime >'" + DateUtils.getCurrDateStr() + "'"));
			queryList.add(new QueryCondition("status!='" + Constants.CONTRACT_STATUS_YWJ + "' or status IS NULL"));
			if (!StrUtils.isBlankOrNull(v.getSalerId())) {
				queryList.add(new QueryCondition("  saler_id=\'" + v.getSalerId() + "\'"));
			}
			PageBean pageBean = pageResult.getPageBean();
			pageBean.setCurrentPage(gridVo.getPage());
			if (0 != gridVo.getRows())
				pageBean.setPageSize(gridVo.getRows());
			pageResult.setPageBean(pageBean);
			pageResult.addConditionList(queryList);
			 pageResult = contractDao.getPageResult(pageResult);
			gridVo.setDatas(poList2mapList((List<Contract>) pageResult.getResultList()));
			gridVo.setRecords(pageResult.getPageBean().getTotalRows());
			gridVo.setPage(pageResult.getPageBean().getCurrentPage());
			gridVo.setRows(pageResult.getPageBean().getPageSize());
			gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		}
		return gridVo;
	}

	/**
	 * 
	 * <p>
	 * Title: gridData4ExpiredSale
	 * </p>
	 * <p>
	 * Description: 已超期的合同
	 * </p>
	 * 
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.demi.cus.contract.service.IContractService#gridData4ExpiredSale(cn.core.framework.common.page.GridVo,
	 *      cn.demi.cus.contract.vo.ContractVo)
	 */
	@Override
	public GridVo gridData4ExpiredSale(GridVo gridVo, ContractVo v) throws GlobalException {
		if (v != null) {
			PageResult pageResult = getPageResult(gridVo, v);
			if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
				pageResult.setOrder(OrderCondition.ORDER_DESC);
				pageResult.setOrderColumn("code");
			} else {
				pageResult.setOrder(gridVo.getSord());
				pageResult.setOrderColumn(gridVo.getSidx());
			}
			// 已超期的合同
			List<QueryCondition> queryList = new ArrayList<QueryCondition>();
			queryList.add(new QueryCondition("edime <'" + DateUtils.getCurrDateStr() + "'"));
			queryList.add(new QueryCondition("status!='" + Constants.CONTRACT_STATUS_YWJ + "' OR status IS NULL"));
			if (!StrUtils.isBlankOrNull(v.getSalerId())) {
				queryList.add(new QueryCondition("  saler_id=\'" + v.getSalerId() + "\'"));
			}
			PageBean pageBean = pageResult.getPageBean();
			pageBean.setCurrentPage(gridVo.getPage());
			if (0 != gridVo.getRows())
				pageBean.setPageSize(gridVo.getRows());
			pageResult.setPageBean(pageBean);
			pageResult.addConditionList(queryList);
			 pageResult = contractDao.getPageResult(pageResult);
			gridVo.setDatas(poList2mapList((List<Contract>) pageResult.getResultList()));
			gridVo.setRecords(pageResult.getPageBean().getTotalRows());
			gridVo.setPage(pageResult.getPageBean().getCurrentPage());
			gridVo.setRows(pageResult.getPageBean().getPageSize());
			gridVo.setTotal(pageResult.getPageBean().getTotalPages());

		}
		return gridVo;
	}

	@Override
	public GridVo gridData4ExecuteSale(GridVo gridVo, ContractVo v) throws GlobalException {
		if (v != null) {
			PageResult pageResult = getPageResult(gridVo, v);
			if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
				pageResult.setOrder(OrderCondition.ORDER_DESC);
				pageResult.setOrderColumn("code");
			} else {
				pageResult.setOrder(gridVo.getSord());
				pageResult.setOrderColumn(gridVo.getSidx());
			}
			// 执行中的合同
			List<QueryCondition> queryList = new ArrayList<QueryCondition>();
			queryList.add(new QueryCondition("sdate<='" + DateUtils.getCurrDateStr() + "'"));
			queryList.add(new QueryCondition("edime>='" + DateUtils.getCurrDateStr() + "'"));
			queryList.add(new QueryCondition("status='" + Constants.CONTRACT_STATUS_ZXZ + "'  or status='" + Constants.CONTRACT_STATUS_YQD + "'"));
			if (!StrUtils.isBlankOrNull(v.getSalerId())) {
				queryList.add(new QueryCondition("  saler_id=\'" + v.getSalerId() + "\'"));
			}
			PageBean pageBean = pageResult.getPageBean();
			pageBean.setCurrentPage(gridVo.getPage());
			if (0 != gridVo.getRows())
				pageBean.setPageSize(gridVo.getRows());
			pageResult.setPageBean(pageBean);
			pageResult.addConditionList(queryList);
			 pageResult = contractDao.getPageResult(pageResult);
			gridVo.setDatas(poList2mapList((List<Contract>) pageResult.getResultList()));
			gridVo.setRecords(pageResult.getPageBean().getTotalRows());
			gridVo.setPage(pageResult.getPageBean().getCurrentPage());
			gridVo.setRows(pageResult.getPageBean().getPageSize());
			gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		}
		return gridVo;
	}

	@Override
	public GridVo gridData4ComSale(GridVo gridVo, ContractVo v) throws GlobalException {
		if (v != null) {
			PageResult pageResult = getPageResult(gridVo, v);
			if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
				pageResult.setOrder(OrderCondition.ORDER_DESC);
				pageResult.setOrderColumn("code");
			} else {
				pageResult.setOrder(gridVo.getSord());
				pageResult.setOrderColumn(gridVo.getSidx());
			}
			// 已完结的合同
			List<QueryCondition> queryList = new ArrayList<QueryCondition>();
			queryList.add(new QueryCondition("status ='" + Constants.CONTRACT_STATUS_YWJ + "'"));
			if (!StrUtils.isBlankOrNull(v.getSalerId())) {
				queryList.add(new QueryCondition("  saler_id=\'" + v.getSalerId() + "\'"));
			}
			PageBean pageBean = pageResult.getPageBean();
			pageBean.setCurrentPage(gridVo.getPage());
			if (0 != gridVo.getRows())
				pageBean.setPageSize(gridVo.getRows());
			pageResult.setPageBean(pageBean);
			pageResult.addConditionList(queryList);
			 pageResult = contractDao.getPageResult(pageResult);
			gridVo.setDatas(poList2mapList((List<Contract>) pageResult.getResultList()));
			gridVo.setRecords(pageResult.getPageBean().getTotalRows());
			gridVo.setPage(pageResult.getPageBean().getCurrentPage());
			gridVo.setRows(pageResult.getPageBean().getPageSize());
			gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		}
		return gridVo;
	}

	@Override
	public void executeSchedule() throws GlobalException {

		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("edime <'" + DateUtils.getNextDate(DateUtils.getCurrDateStr(), +30) + "'"));
		queryList.add(new QueryCondition("edime >'" + DateUtils.getCurrDateStr() + "'"));
		queryList.add(new QueryCondition("status='" + Constants.CONTRACT_STATUS_YQD + "' or status ='"+Constants.CONTRACT_STATUS_ZXZ+"' "));
		// if (!StrUtils.isBlankOrNull(getCurrent().getAccountId())) {
		// queryList.add(new QueryCondition(" saler_id=\'" + getCurrent().getAccountId()
		// + "\'"));
		// }

		List<Contract> contracts = contractDao.list(queryList);
		if (contracts.size() > 0) {
			for (Contract con : contracts) {
				// 查询visit表是否已有数据
				String hql = " from " + visitDao.getEntityName(Visit.class) + " where isDel = '" + Po.N + "' ";
				hql += " and contractId= '" + con.getId() + "'";
				List<Visit> visits = visitDao.list(hql);
				if (visits.size() <= 0) {
					Visit po = new Visit(); // visit
					po.setContractId(con.getId()); // 合同id
					po.setContractName(con.getCode());//合同编号
					po.setStatus(Constants.CUS_VISIT_STATUS_DHF);
					po.setUserId(con.getCustomer().getId());
					po.setUserName(con.getCustomer().getName());
					po.setUser(con.getContacts());
					po.setVdate(con.getEdime());
					po.setSaler(con.getSaler());
					po.setSalerId(con.getSalerId());
					po.setLeader(con.getLeadName());
					po.setLeaderId(con.getLeadId());
					visitDao.save(po);
				} else {
					for (int i = 0;i < visits.size();i ++)
					{
						Visit po = visits.get(i);
						if (po.getContractName().equals(con.getCode()))
						{
							visitDao.update(po);
						}
					}
				}
			}
		}

	}

	@Override
	public List<ContractVo> queryAllByAccountId() throws GlobalException {
		String accountId = getCurrent().getAccountId();
		String auth = userService.saleJLAuth(accountId);
		String hql = " from " + contractDao.getEntityName(Contract.class) + " where 1=1 ";
		if (auth.equals("XSJL")) {
			hql += " and  leadId='" + accountId + "'";
		} else if (auth.equals("XSRY")) {
			hql += " and salerId='" + accountId + "'";
		}
		hql += " and status ='" + Constants.CONTRACT_STATUS_YQD + "' ";
		return toVoList(contractDao.list(hql));
	}

	/**
	 * 
	 * <p>
	 * Title: gridListData
	 * </p>
	 * <p>
	 * Description:发票申请合同弹出窗
	 * </p>
	 * 
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.demi.cus.contract.service.IContractService#gridListData(cn.core.framework.common.page.GridVo,
	 *      cn.demi.cus.contract.vo.ContractVo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridListData(GridVo gridVo, ContractVo v) throws GlobalException {
		if (v != null) {
			PageResult pageResult = new PageResult();
			List<QueryCondition> queryList = new ArrayList<QueryCondition>();
			String accountId = getCurrent().getAccountId();
			String auth = userService.saleJLAuth(accountId);
			if (auth.equals("XSJL")) {
				queryList.add(new QueryCondition("  t0.leadId=\'" + accountId + "\'"));
			} else if (auth.equals("XSRY")) {
				queryList.add(new QueryCondition("  t0.salerId=\'" + accountId + "\'"));
			}
			queryList.add(new QueryCondition(" t0.status = '" + Constants.CONTRACT_STATUS_YQD + "' "));
			PageBean pageBean = pageResult.getPageBean();
			pageBean.setCurrentPage(gridVo.getPage());
			if (0 != gridVo.getRows())
				pageBean.setPageSize(gridVo.getRows());
			pageResult.setPageBean(pageBean);
			pageResult.addConditionList(queryList);
			pageResult = contractDao.getPageResult4Bill(pageResult);
			gridVo.setDatas(poList2mapList((List<Contract>) pageResult.getResultList()));
			gridVo.setRecords(pageResult.getPageBean().getTotalRows());
			gridVo.setPage(pageResult.getPageBean().getCurrentPage());
			gridVo.setRows(pageResult.getPageBean().getPageSize());
			gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		}
		return gridVo;
	}

}
