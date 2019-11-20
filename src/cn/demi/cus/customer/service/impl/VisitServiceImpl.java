package cn.demi.cus.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IUserService;
import cn.demi.base.system.vo.UserVo;
import cn.demi.cus.contract.service.IContractService;
import cn.demi.cus.contract.vo.ContractVo;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.dao.IVisitDao;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.po.Visit;
import cn.demi.cus.customer.service.ICustomerService;
import cn.demi.cus.customer.service.IVisitService;
import cn.demi.cus.customer.vo.CustomerVo;
import cn.demi.cus.customer.vo.VisitVo;
import cn.demi.cus.sale.vo.SaleContactsVo;
import cn.demi.ext.common.vo.TxVo;

@Service("cus.visitService")
public class VisitServiceImpl extends BaseServiceImpl<VisitVo, Visit> implements IVisitService {
	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private IVisitDao visitDao;
	@Autowired
	private IUserService userService;
	@Autowired
	private IContractService contractService;
	@Autowired
	private ICustomerService customerService;

	@Override
	public VisitVo findById(String id) throws GlobalException {
		return super.findById(id);
	}

	@Override
	public void add(VisitVo v) throws GlobalException {
		Visit p = vo2Po(v);
		Customer customer = customerDao.findById(v.getUserId());
		p.setCustomer(customer);
		visitDao.add(p);
		v.setId(p.getId());
		customer.setLastTestDate(p.getVdate());
		if (StrUtils.isNotBlankOrNull(p.getVdate()) && null != customer.getCycle()) {
			customer.setTestDate(DateUtils.getNextDate4M(customer.getLastTestDate(), customer.getCycle()));
		}
		customer.setStatus(v.getStatus());
		customerDao.update(customer);
	}

	@Override
	public void update(VisitVo v) throws GlobalException {
		Visit p = visitDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		Customer customer = customerDao.findById(v.getCustomerVo().getId());
		p.setCustomer(customer);
		visitDao.update(p);
		customer.setLastTestDate(p.getVdate());
		customer.setTestDate(DateUtils.getNextDate4M(customer.getLastTestDate(), customer.getCycle()));
		customer.setStatus(v.getStatus());
		customerDao.update(customer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, VisitVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = visitDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Visit>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public GridVo gridData4Visit(GridVo gridVo, VisitVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addOrder("vdate", OrderCondition.ORDER_DESC);
		pageResult.addCondition(new QueryCondition("saler_id", QueryCondition.EQ, getCurrent().getAccountId()));
		pageResult = visitDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Visit>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	public List<VisitVo> listByIds(String sid, String cid) throws GlobalException {

		String hql = " from " + visitDao.getEntityName(Visit.class) + " where 1=1";
		if (!StrUtils.isBlankOrNull(sid)) {
			hql += " and salerId='" + sid + "'";
		}
		if (!StrUtils.isBlankOrNull(cid)) {
			hql += " and userId='" + cid + "'";
		}
		return toVoList(visitDao.list(hql));
	}

	/**
	 * 
	 * @Title: list4Tx @Description: TODO(这里用一句话描述这个方法的作用) @param: @param
	 *         list @param: @return @param: @throws GlobalException @return:
	 *         List<TxVo> @throws
	 */
	public List<TxVo> list4Tx(List<TxVo> list) throws GlobalException {
		String count = userService.saleJLAuth(getCurrent().getAccountId());
		TxVo tx = new TxVo();
		tx.setIcon("fa fa-flask");
		tx.setStatus(Constants.CUS_VISIT_STATUS_DHF);
		String hql = "from " + visitDao.getEntityName(Visit.class) + " where status='" + Constants.CUS_VISIT_STATUS_DHF + "' ";
		if (count.equals(UserVo.USER_AUTH_XSRY)) {
			hql += "and salerId= '" + getCurrent().getAccountId() + "'";
		}
		hql += " order by vdate desc";
		List<VisitVo> voList = toVoList(visitDao.list(hql));
		for (int i = 0; i < voList.size(); i++) {
			ContractVo contract = contractService.findById(voList.get(i).getContractId());
			if (contract != null) {
				tx.setName(voList.get(i).getUserName()); // 客户名称
				String content = " 客户的合同<a href=\"/cus/contract/download.do?filePath=" + contract.getPath() + "&trueName=" + contract.getTrueName() + "\">"
						+ contract.getCode() + "</a>快到期!";
				tx.setContent(content);
				list.add(tx);
			}
		}
		return list;
	}

	@Override
	public void updateVisitByGZId(SaleContactsVo v) throws GlobalException {
		List<QueryCondition> qlist = new ArrayList<QueryCondition>();
		QueryCondition con = new QueryCondition("user_id", QueryCondition.EQ, v.getCustomerId());
		qlist.add(con);
		String accountId = getCurrent().getAccountId();
		String auth = userService.saleJLAuth(accountId);
		if (auth.equals("XSRY"))
		{
			QueryCondition con1 = new QueryCondition("saler_id", QueryCondition.EQ, accountId);
			qlist.add(con1);
		}else if (auth.equals("XSJL"))
		{
			QueryCondition con1 = new QueryCondition("leader_id", QueryCondition.EQ, accountId);
			qlist.add(con1);
		}
		
		
		List<Visit> visits = visitDao.list(qlist);
		// List<Visit> vos = visitDao.listByIds(v.getSalerId(), v.getCustomerId());

		if (visits.size() > 0) {
			Visit vo = visits.get(0);
			vo.setSalerId(v.getSalerId());// 销售id
			vo.setSaler(v.getSaler());// 销售
			vo.setUser(v.getContactPerson());// 联系人
			vo.setUserId(v.getCustomerId());// 客户id
			vo.setUserName(v.getCustomerName());// 客户
			vo.setContactId(v.getId());// 跟踪id
			vo.setVdate(v.getGzDate());// 回访时间
			vo.setStatus(Constants.CUS_VISIT_STATUS_YHF);
			visitDao.update(vo);
		} else {
			Visit vo = new Visit();
			vo.setSalerId(v.getSalerId());// 销售id
			vo.setSaler(v.getSaler());// 销售
			vo.setUser(v.getContactPerson());// 联系人
			vo.setUserId(v.getCustomerId());// 客户id
			vo.setUserName(v.getCustomerName());// 客户
			vo.setContactId(v.getId());// 跟踪id
			vo.setVdate(v.getGzDate());// 回访时间
			vo.setStatus(Constants.CUS_VISIT_STATUS_YHF);
			visitDao.save(vo);
		}
	}

	@Override
	public void saveVisitsByLoginUser(String authName) throws GlobalException {
		List<CustomerVo> voList = null;
		String accountId = getCurrent().getAccountId();
		String userName = getCurrent().getUserName();
		try {
			voList = customerService.fetchMycustomer(accountId, authName);
		} catch (Exception e) {
			e.printStackTrace();
		} // 我负责的客户
			// 获取需要回访的客户，并新增需要回访记录
		for (int i = 0; i < voList.size(); i++) {
			ContractVo v0 = new ContractVo();// 合同
			CustomerVo cusvo = voList.get(i);// 客户
			v0.setCustomerVo(cusvo);
			Customer cuspo = customerService.fetchCustomerById(cusvo.getId()); // vo2Po(cust);
			if (authName.equals(UserVo.USER_AUTH_XSJL)) {
				v0.setSalerId(null);
			} else {
				v0.setSalerId(accountId);
			}
			try {
				List<ContractVo> contractVos = contractService.gridData4YHQ(v0);
				if (contractVos.size() > 0) {
					for (int j = 0; j < contractVos.size(); j++) {
						Visit v = new Visit();
						v.setSalerId(accountId);
						v.setSaler(userName);
						v.setStatus(Constants.CUS_VISIT_STATUS_DHF);// 待回访
						v.setCustomer(cuspo);
						v.setVdate("");// 上次拜访日期为空
						visitDao.save(v);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
