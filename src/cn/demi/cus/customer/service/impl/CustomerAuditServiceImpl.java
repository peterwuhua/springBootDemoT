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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.service.ICustomerAuditService;
import cn.demi.cus.customer.vo.ContactsVo;
import cn.demi.cus.customer.vo.CustomerVo;
import cn.demi.cus.sale.dao.ISaleContactsDao;
import cn.demi.cus.sale.po.SaleContacts;

@Service("cus.customerAuditService")
public class CustomerAuditServiceImpl extends BaseServiceImpl<CustomerVo, Customer> implements ICustomerAuditService {

	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private ISaleContactsDao saleContactsDao;

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CustomerVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_ASC);
			pageResult.setOrderColumn("createTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}

		pageResult = customerDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Customer>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	/**
	 * 
	 * <p>
	 * Title: toQueryList
	 * </p>
	 * <p>
	 * Description: 查询条件
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.service.BaseServiceImpl#toQueryList(cn.core.framework.common.vo.Vo)
	 */
	@Override
	public List<QueryCondition> toQueryList(CustomerVo v) throws GlobalException {
		List<QueryCondition> conditions = new ArrayList<QueryCondition>();
		QueryCondition con0 = new QueryCondition("fstatus", QueryCondition.EQ, Customer.ST_1);// 显示销售新增需要审批的
		conditions.add(con0);
		return conditions;
	}

	@Override
	public CustomerVo findById(String id) throws GlobalException {
		Customer po = customerDao.findById(id);
		CustomerVo vo = po2Vo(po);
		return vo;
	}

	/**
	 * 
	 * <p>
	 * Title: add2mySaleContact
	 * </p>
	 * <p>
	 * Description: 添加一条记录到客户跟踪
	 * </p>
	 * 
	 * @param contacts
	 * @param auth
	 * @throws GlobalException
	 * @see cn.demi.cus.customer.service.ICustomerAuditService#add2mySaleContact(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	public void add2mySaleContact(List<ContactsVo> contacts, String auth, CustomerVo v) throws GlobalException {
		if (contacts.size() > 0) {
			for (ContactsVo cvo : contacts) {
				SaleContacts scpo = new SaleContacts();
				scpo.setAddress(v.getAddress());
				scpo.setBirthDate(cvo.getBirthDate());
				scpo.setContactId(cvo.getId());
				scpo.setContactPerson(cvo.getName());
				scpo.setContactWay(cvo.getPhone());
				scpo.setCustomerId(v.getId());
				scpo.setCustomerName(v.getName());
				scpo.setDepartment(cvo.getDepart());
				scpo.setDuty(cvo.getDuty());
				scpo.setEmail(cvo.getEmail());
				scpo.setGzDate(DateUtils.getCurrDateStr());
				scpo.setSaler(v.getSaler());
				scpo.setSalerId(v.getSalerId());
				saleContactsDao.saveOrUpdate(scpo);
			}
		}

	}

}
