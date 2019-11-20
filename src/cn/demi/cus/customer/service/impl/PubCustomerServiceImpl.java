package cn.demi.cus.customer.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IUserDao;
import cn.demi.base.system.vo.UserVo;
import cn.demi.cus.customer.dao.IContactsDao;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Contacts;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.service.IPubCustomerService;
import cn.demi.cus.customer.vo.CustomerVo;
import cn.demi.cus.sale.dao.ISaleContactsDao;
import cn.demi.cus.sale.po.SaleContacts;

/**
 * 
 * @ClassName: PubCustomerServiceImpl
 * @Description:公共池客户业务实现层
 * @author: 吴华
 * @date: 2019年1月22日 上午10:17:13
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Service("cus.pubCustomerService")
public class PubCustomerServiceImpl extends BaseServiceImpl<CustomerVo, Customer> implements IPubCustomerService {

	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private IContactsDao contactsDao;
	@Autowired
	private ISaleContactsDao saleContactsDao;
	@Autowired
	private IUserDao userDao;

	/**
	 * 
	 * <p>
	 * Title: gridData
	 * </p>
	 * <p>
	 * Description: 公共池客户显示所有的客户
	 * </p>
	 * 
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.service.BaseServiceImpl#gridData(cn.core.framework.common.page.GridVo,
	 *      cn.core.framework.common.vo.Vo)
	 */
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

	@Override
	public void add(CustomerVo v) throws GlobalException {
		v = fenpei(v);
		Customer p = vo2Po(v);
		customerDao.add(p);
		v.setId(p.getId());
	}

	@Override
	public void update(CustomerVo v) throws GlobalException {
		v = fenpei(v);
		Customer p = customerDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		customerDao.update(p);
	}

	/**
	 * 
	 * @Title: fenpei @Description: 编辑 - 保存提交 @param: @param v @param: @throws
	 *         GlobalException @return: void @throws
	 */
	private CustomerVo fenpei(CustomerVo v) throws GlobalException {
		v.setFstatus(Customer.ST_3);// 经理分配
		// 更新销售经理
		String auth = v.getAuth();
		if (auth.equals("XSJL")) {
			v.setLeader(getCurrent().getUserName());
			v.setLeaderId(getCurrent().getAccountId());
		}
		// 权限跟客户经理分配的销售人员挂钩
		if (!StrUtils.isBlankOrNull(v.getSalerId())) {
			String sauth = fetchAuth(v.getSalerId()); // 经理也可能分配给自己，所以不一定都是销售员
			v.setAuth(sauth);
		}
		// 查询出公司下的所有联系人
		String jpql = "FROM " + contactsDao.getEntityName(Contacts.class) + " WHERE isDel='" + Po.N + "' AND customer.id='" + v.getId() + "' order by sort asc";
		List<Contacts> contacts = contactsDao.list(jpql);

		// 并新增一条记录到我的客户跟踪列表
		if (contacts.size() > 0) {
			for (Contacts po : contacts) {
				SaleContacts scpo = new SaleContacts();
				scpo.setAddress(v.getAddress());
				scpo.setBirthDate(po.getBirthDate());
				scpo.setContactId(po.getId());
				scpo.setContactPerson(po.getName());
				scpo.setContactWay(po.getPhone());
				scpo.setCustomerId(v.getId());
				scpo.setCustomerName(v.getName());
				scpo.setDepartment(po.getDepart());
				scpo.setDuty(po.getDuty());
				scpo.setEmail(po.getEmail());
				scpo.setGzDate(DateUtils.getCurrDateStr());
				if (auth.equals("XSJL")) {
					scpo.setLeaderId(v.getSalerId());
					scpo.setLeaderName(v.getSaler());
				} else if (auth.equals("XSRY")) {
					scpo.setSaler(v.getSaler());
					scpo.setSalerId(v.getSalerId());
				}
				saleContactsDao.saveOrUpdate(scpo);
			}
		}
		return v;
	}

	/**
	 * 
	 * @Title: saleJLAuth @Description: 更据用户Accountid获取角色 @param: @param
	 * accountId @param: @return @param: @throws GlobalException @return:
	 * String @throws
	 */
	private String fetchAuth(String accountId) throws GlobalException {
		String sql = "SELECT count(*) FROM v_sys_role t0 INNER JOIN v_sys_account_role t1 ON t0.id = t1.role_id INNER JOIN v_sys_account t2 ON t1.account_id = t2.id "
				+ " WHERE t0. CODE = 'XSJL' and t2.id='" + accountId + "'";
		Query query = userDao.queryBysql(sql);
		BigInteger count = (BigInteger) query.getSingleResult();
		String name = "";
		if (count.equals(BigInteger.ONE)) {
			name = UserVo.USER_AUTH_XSJL;
		} else {
			name = UserVo.USER_AUTH_XSRY;
		}
		return name;
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
		return super.toQueryList(v);
	}

}
