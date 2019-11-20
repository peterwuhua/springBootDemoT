package cn.demi.cus.customer.service.impl;

import java.util.ArrayList;
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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.vo.UserVo;
import cn.demi.cus.contract.dao.IContractDao;
import cn.demi.cus.contract.po.Contract;
import cn.demi.cus.customer.dao.IContactsDao;
import cn.demi.cus.customer.dao.ICusClientDao;
import cn.demi.cus.customer.dao.ICusProduceDao;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.dao.IFinanceDao;
import cn.demi.cus.customer.dao.IVisitDao;
import cn.demi.cus.customer.po.Contacts;
import cn.demi.cus.customer.po.CusClient;
import cn.demi.cus.customer.po.CusProduce;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.po.Finance;
import cn.demi.cus.customer.po.Visit;
import cn.demi.cus.customer.service.ICustomerService;
import cn.demi.cus.customer.vo.CustVo;
import cn.demi.cus.customer.vo.CustomerVo;

/**
 * Create on : 2016年11月15日 下午3:00:53 <br>
 * Description : CusProduceServiceImpl <br>
 *
 * @author Danlee Li<br>
 * @version v 1.0.0 <br>
 */
@Service("cus.customerService")
public class CustomerServiceImpl extends BaseServiceImpl<CustomerVo, Customer> implements ICustomerService {
	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private ICusProduceDao cusProduceDao;
	@Autowired
	private ICusClientDao cusClientDao;
	@Autowired
	private IFinanceDao financeDao;
	@Autowired
	private IContractDao contractDao;
	@Autowired
	private IContactsDao contactsDao;
	@Autowired
	private IVisitDao visitDao;

	@Override
	public void data2Vo(String[] values, CustomerVo v, String param) throws GlobalException {
		v.setCode(values[1]);
		v.setName(values[2]);
		v.setCusType(values[3]);
		v.setGrade(values[4]);
		v.setIndustry(values[5]);
		v.setBuildDate(values[6]);
		v.setAreaPath(values[7]);
		v.setSaler(values[8]);
		v.setAddress(values[9]);
		v.setPhone(values[10]);
		v.setEmail(values[11]);
		v.setPerson(values[12]);
		v.setPayType(values[13]);
		v.setPayWay(values[14]);
		v.setRemark(values[15]);

	}

	@Override
	public void add(CustomerVo v) throws GlobalException {
		Customer p = vo2Po(v);
		if (!StrUtils.isBlankOrNull(p.getLastTestDate()) && !StrUtils.isNull(p.getCycle())) {
			p.setTestDate(DateUtils.getNextDate4M(p.getLastTestDate(), p.getCycle()));
		}
		customerDao.add(p);
		if (StrUtils.isNotBlankOrNull(v.getPerson())) {
			Contacts ct = new Contacts();
			ct.setName(p.getPerson().trim());
			ct.setPhone(p.getPhone());
			ct.setEmail(p.getEmail());
			ct.setCustomer(p);
			contactsDao.add(ct);
		}
		v.setId(p.getId());
	}

	@Override
	public void update(CustomerVo v) throws GlobalException {
		Customer p = customerDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if (!StrUtils.isBlankOrNull(p.getLastTestDate()) && !StrUtils.isNull(p.getCycle())) {
			p.setTestDate(DateUtils.getNextDate4M(p.getLastTestDate(), p.getCycle()));
		}
		customerDao.update(p);
		if (StrUtils.isNotBlankOrNull(v.getPerson())) {
			Contacts ct = contactsDao.findBycusId(p.getId(), v.getPerson());
			if (null == ct) {
				ct = new Contacts();
				ct.setName(p.getPerson().trim());
				ct.setPhone(p.getPhone());
				ct.setEmail(p.getEmail());
				ct.setCustomer(p);
				contactsDao.add(ct);
			}
		}
	}

	@Override
	public CustomerVo findById(String id) throws GlobalException {
		Customer po = customerDao.findById(id);
		CustomerVo vo = po2Vo(po);
		return vo;
	}

	@Override
	public List<CustomerVo> listByIds(String... ids) throws GlobalException {
		List<CustomerVo> voList = new ArrayList<CustomerVo>();
		List<Customer> list = customerDao.listByIds(ids);
		for (Customer po : list) {
			CustomerVo vo = po2Vo(po);
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public boolean isDelete4All(String... ids) throws GlobalException {
		List<Contacts> contactsList = contactsDao.listBycusId(ids);
		if (null != contactsList && contactsList.size() > 0)
			return false;
		List<CusProduce> cusProduceList = cusProduceDao.listBycusIds(ids);
		if (null != cusProduceList && cusProduceList.size() > 0)
			return false;
		List<CusClient> cusClientList = cusClientDao.listBycusIds(ids);
		if (null != cusClientList && cusClientList.size() > 0)
			return false;
		List<Finance> financeList = financeDao.listBycusId(ids);
		if (null != financeList && financeList.size() > 0)
			return false;
		List<Contract> contractList = contractDao.listBycusId(ids);
		if (null != contractList && contractList.size() > 0)
			return false;
		List<Visit> visitList = visitDao.listBycusId(ids);
		if (null != visitList && visitList.size() > 0)
			return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDate4UnSysn(GridVo gridVo, CustomerVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());

		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));

		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isSysn", QueryCondition.EQ, Po.N));

		pageResult.addConditionList(queryList);

		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult = customerDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Customer>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDate4Sysn(GridVo gridVo, CustomerVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());

		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isSysn", QueryCondition.EQ, Po.Y));
		pageResult.addConditionList(queryList);

		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult = customerDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Customer>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CustomerVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		List<QueryCondition> list = new ArrayList<>();
		QueryCondition con0 = new QueryCondition("saler_id", QueryCondition.EQ, getCurrent().getAccountId());
		list.add(con0);
		pageResult.addConditionList(list);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_ASC);
			pageResult.setOrderColumn("createTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = customerDao.getPageResult(pageResult);

		// 更新客户的跟进状态
		gridVo.setDatas(poList2mapList((List<Customer>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<QueryCondition> toQueryList(CustomerVo v) throws GlobalException {
		return super.toQueryList(v);
	}

	@Override
	public void update4Sysn(String... ids) throws GlobalException {
		List<Customer> list = customerDao.listByIds(ids);
		for (Customer po : list) {
			po.setIsSysn(Po.Y);
			customerDao.update(po);
		}

	}

	@Override
	public CustomerVo find(String name) throws GlobalException {
		Customer cus = customerDao.findByName(name);
		if (null == cus)
			return null;
		CustomerVo vo = po2Vo(cus);
		return vo;
	}

	/**
	 * 
	 * @Title: gridData4Gz @Description: 客户跟踪 @param: @param gridVo @param: @param
	 *         v @param: @return @param: @throws GlobalException @return:
	 *         GridVo @throws
	 */
	@SuppressWarnings("unchecked")
	public GridVo gridData4Gz(GridVo gridVo, CustomerVo v) throws GlobalException {

		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addOrder("status", OrderCondition.ORDER_DESC);
		pageResult.addOrder("testDate", OrderCondition.ORDER_ASC);
		pageResult.addCondition(new QueryCondition("  status='" + Customer.ST_2 + "'"));
		pageResult = customerDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Customer>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<CustVo> list4Sim(CustomerVo v) throws GlobalException {
		String hql = "FROM " + customerDao.getEntityName(Customer.class) + " WHERE isDel='" + Po.N + "' ";
		if (StrUtils.isNotBlankOrNull(v.getName())) {
			hql += "AND name like '%" + v.getName().trim() + "%' ";
		}
		if (StrUtils.isNotBlankOrNull(v.getCode())) {
			hql += "AND code like '%" + v.getCode() + "%' ";
		}
		hql += "order by sort asc";
		List<Customer> custList = customerDao.list(hql);
		List<CustVo> custVoList = new ArrayList<CustVo>();
		if (null != custList) {
			for (Customer po : custList) {
				CustVo vo = new CustVo();
				vo.setId(po.getId());
				vo.setName(po.getName());
				vo.setAddress(po.getAddress());
				vo.setCode(po.getCode());
				vo.setCustCode(po.getCustCode());
				vo.setEmail(po.getEmail());
				vo.setFax(po.getFax());
				vo.setPerson(po.getPerson());
				vo.setPhone(po.getPhone());
				vo.setZip(po.getZip());
				custVoList.add(vo);
			}
		}
		return custVoList;
	}

	@Override
	public List<CustVo> list4Full(CustomerVo v) throws GlobalException {
		String sql = "select cust.id,cust.name,cust.code,cust.address,cust.cust_code,cust.fax,cust.zip,"
				+ "user.name as person,user.phone,cust.email,cust.telephone,cust.cust_address,cust.cust_tel,cust.cust_bank,cust.cust_account,cust.cus_type "
				+ " FROM " + tablePrefix + customerDao.getEntityName(Customer.class) + " cust LEFT JOIN " + tablePrefix
				+ customerDao.getEntityName(Contacts.class) + " user on cust.id=user.customer_id AND user.is_del='" + Po.N + "' WHERE cust.is_del='" + Po.N
				+ "' ";
		if (StrUtils.isNotBlankOrNull(v.getName())) {
			sql += "AND cust.name like '%" + v.getName().trim() + "%' ";
		}
		if (StrUtils.isNotBlankOrNull(v.getCode())) {
			sql += "AND cust.code like '%" + v.getCode() + "%' ";
		}
		sql += "order by cust.sort asc";
		@SuppressWarnings("unchecked")
		List<Object[]> custList = customerDao.queryBySql(sql);
		List<CustVo> custVoList = new ArrayList<CustVo>();
		if (null != custList) {
			for (Object[] obj : custList) {
				CustVo vo = new CustVo();
				vo.setId(String.valueOf(obj[0]));
				vo.setName(String.valueOf(obj[1]));
				String code = String.valueOf(obj[2]);
				vo.setCode((code == null || code.equals("null")) ? "" : code);
				String address = String.valueOf(obj[3]);
				vo.setAddress((address == null || address.equals("null")) ? "" : address);
				String custCode = String.valueOf(obj[4]);
				vo.setCustCode((custCode == null || custCode.equals("null")) ? "" : custCode);
				String fax = String.valueOf(obj[5]);
				vo.setFax((fax == null || fax.equals("null")) ? "" : fax);
				String zip = String.valueOf(obj[6]);
				vo.setZip((zip == null || zip.equals("null")) ? "" : zip);
				String person = String.valueOf(obj[7]);
				vo.setPerson((person == null || person.equals("null")) ? "" : person);
				String phone = String.valueOf(obj[8]);
				vo.setPhone((phone == null || phone.equals("null")) ? "" : phone);
				String email = String.valueOf(obj[9]);
				vo.setEmail((email == null || email.equals("null")) ? "" : email);
				String telephone = String.valueOf(obj[10]);
				vo.setTelephone((telephone == null || telephone.equals("null")) ? "" : telephone);
				String custAddress = String.valueOf(obj[11]);
				vo.setCustAddress((custAddress == null || custAddress.equals("null")) ? "" : custAddress);
				String custTel = String.valueOf(obj[12]);
				vo.setCustTel((custTel == null || custTel.equals("null")) ? "" : custTel);
				String custBank = String.valueOf(obj[13]);
				vo.setCustBank((custBank == null || custBank.equals("null")) ? "" : custBank);
				String custAccount = String.valueOf(obj[14]);
				vo.setCustAccount((custAccount == null || custAccount.equals("null")) ? "" : custAccount);
				String custType = String.valueOf(obj[15]);
				vo.setCusType((custType == null || custType.equals("null")) ? "" : custType);
				custVoList.add(vo);
			}
		}
		return custVoList;
	}

	/**
	 * <p>
	 * Title: fetchMycustomer
	 * </p>
	 * <p>
	 * Description:获取我的跟踪所有客户
	 * </p>
	 *
	 * @return
	 * @throws GlobalException
	 * @see cn.demi.cus.customer.service.ICustomerService#fetchMycustomer()
	 */
	@Override
	public List<CustomerVo> fetchMycustomer(String id, String authName) throws GlobalException {
		List<QueryCondition> list = new ArrayList<>();
		if (authName.equals(UserVo.USER_AUTH_XSJL)) {
			QueryCondition con0 = new QueryCondition("leader_id", QueryCondition.EQ, id);
			list.add(con0);
		} else if (authName.equals(UserVo.USER_AUTH_XSRY)) {
			QueryCondition con0 = new QueryCondition("saler_id", QueryCondition.EQ, id);
			list.add(con0);
		}

		QueryCondition con1 = new QueryCondition("is_del", QueryCondition.EQ, Customer.ST_0);
		QueryCondition con2 = new QueryCondition("fstatus", QueryCondition.GE, Customer.ST_2);
		list.add(con1);
		list.add(con2);
		List<Customer> pos = customerDao.list(list);
		List<CustomerVo> vos = new ArrayList<>();
		for (Customer po : pos) {
			CustomerVo vo = po2Vo(po);
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public void updateFieldById(String id, String name) throws GlobalException {
		// 查询客户表数据
		Customer po = customerDao.findById(id);
		if (StrUtils.isBlankOrNull(name)) {
			po.setId(id);
			po.setPerson("");
			customerDao.update(po);
		} else {
			po.setId(id);
			po.setPerson(name);
			customerDao.update(po);
		}
	}

	@Override
	public Customer fetchCustomerById(String id) throws GlobalException {
		return customerDao.findById(id);
	}

}
