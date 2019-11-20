package cn.demi.cus.bill.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.bill.dao.IBillDao;
import cn.demi.cus.bill.po.Bill;
import cn.demi.cus.bill.service.IBillService;
import cn.demi.cus.bill.vo.BillVo;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.fee.dao.IFeeDao;
import cn.demi.cus.fee.po.Fee;

@Service("cus.billService")
public class BillServiceImpl extends BaseServiceImpl<BillVo, Bill> implements IBillService {
	@Autowired
	private IBillDao billDao;
	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private IFeeDao feeDao;
	
	@Override
	public void add(BillVo v) throws GlobalException {
		Bill p = vo2Po(v);
		if(!StrUtils.isBlankOrNull(v.getCustomerId())) {
			Customer cust=customerDao.findById(v.getCustomerId());
			p.setCustomer(cust);
		}
		billDao.add(p);
		v.setId(p.getId());
	}
	
	@Override
	public void updateStatus4KJ(String ids) throws GlobalException {
		ids = "'" + ids.replaceAll(",", "','") + "'";
		String hql = " from " + billDao.getEntityName(Bill.class) + " where id in (" + ids + ")";
		List<Bill> billList = billDao.list(hql);
		if (billList.size() > 0) {
			for (Bill bill : billList) {
				bill.setFstatus(Constants.BILL_STATUS_YKJ);
				bill.setPstatus(Constants.CHARGE_STATUS_WSF);
				billDao.update(bill);
				Customer cust = customerDao.findById(bill.getCustId());
				// 新增一条收费记录状态“未收费”
				List<Fee> fees = feeDao.findByProperty("fpId", bill.getId()); 
				Fee fee = null;
				if (fees.size() > 0) {
					fee = fees.get(0);
					fee.setFstatus(Constants.CHARGE_STATUS_WSF);
					fee.setBillno(bill.getBillno());
					fee.setFpId(bill.getId());
					fee.setCustId(bill.getCustId());
					fee.setHtId(bill.getHtId());
					fee.setPjtId(bill.getPjtId());
					fee.setSupportDate(DateUtils.getCurrDateStr());
					fee.setPerson(bill.getPerson());
					fee.setPersonId(bill.getPersonId());
					fee.setContractCode(bill.getContractCode());
					fee.setCustomerName(cust.getName());
					feeDao.update(fee);
				} else {
					fee = new Fee();
					fee.setFstatus(Constants.CHARGE_STATUS_WSF);
					fee.setBillno(bill.getBillno());
					fee.setFpId(bill.getId());
					fee.setCustId(bill.getCustId());
					fee.setHtId(bill.getHtId());
					fee.setPjtId(bill.getPjtId());
					fee.setSupportDate(DateUtils.getCurrDateStr());
					fee.setPerson(bill.getPerson());
					fee.setPersonId(bill.getPersonId());
					fee.setContractCode(bill.getContractCode());
					fee.setCustomerName(cust.getName());
					feeDao.add(fee);
				}
			}
		}
	}

	
	@Override
	public List<QueryCondition> toQueryList(BillVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if (!StrUtils.isBlankOrNull(v.getBillno())) {
			queryList.add(new QueryCondition("billno", QueryCondition.AK, v.getBillno()));
		}
		
		return queryList;
	}
	
	
	@Override
	public GridVo gridData(GridVo gridVo, BillVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_ASC);
			pageResult.setOrderColumn("createTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult.addConditionList(queryList);
		pageResult = billDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Bill>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public GridVo gridData4audit(GridVo gridVo, BillVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_ASC);
			pageResult.setOrderColumn("createTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition(" fstatus != '" + Constants.BILL_STATUS_WSP + "'"));
		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult.addConditionList(queryList);
		pageResult = billDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Bill>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public GridVo gridData4view(GridVo gridVo, BillVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_ASC);
			pageResult.setOrderColumn("createTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition(" fstatus = '" + Constants.BILL_STATUS_YKJ + "'"));
		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult.addConditionList(queryList);
		pageResult = billDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Bill>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public GridVo gridData4Waudit(GridVo gridVo, BillVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_ASC);
			pageResult.setOrderColumn("createTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition(" fstatus = '" + Constants.BILL_STATUS_WSP + "'"));
		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult.addConditionList(queryList);
		pageResult = billDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Bill>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public GridVo gridData4Wview(GridVo gridVo, BillVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_ASC);
			pageResult.setOrderColumn("createTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition(" fstatus = '" + Constants.BILL_STATUS_WKJ + "'"));
		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult.addConditionList(queryList);
		pageResult = billDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Bill>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public BillVo findBillById(String id) throws GlobalException {
		Bill po = billDao.findById(id);
		if (!StrUtils.isBlankOrNull(po.getCustId())) {
			Customer customer = customerDao.findById(po.getCustId());
			po.setCustomer(customer);
		}
		po.setSumDate(DateUtils.getCurrDateStr());
		po.setSumUserName(getCurrent().getUserName());
		return po2Vo(po);
	}

}
