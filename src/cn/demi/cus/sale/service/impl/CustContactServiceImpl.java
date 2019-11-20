package cn.demi.cus.sale.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IUserService;
import cn.demi.cus.customer.service.ICustomerService;
import cn.demi.cus.customer.vo.ContactsVo;
import cn.demi.cus.customer.vo.CustomerVo;
import cn.demi.cus.sale.dao.ICustContactDao;
import cn.demi.cus.sale.po.CustContact;
import cn.demi.cus.sale.service.ICustContactService;
import cn.demi.cus.sale.vo.CustContactVo;

@Service("cus.custContactService")
public class CustContactServiceImpl extends BaseServiceImpl<CustContactVo, CustContact> implements ICustContactService {

	@Autowired
	private ICustContactDao custContactDao;
	@Autowired
	private IUserService userService;
	@Autowired
	private ICustomerService customerService;

	@Override
	public GridVo query3dsCustBirth(GridVo gridVo, CustContactVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		StringBuffer sqlbf = new StringBuffer();
		sqlbf.append(
				" SELECT a.id,a.customer_id,a.customer,a.saler_id,a.saler,a.leader_id,a.leader,a.contact_name,a.contact_id,a.birth_date,a.phone,a.duty,a.notes,a.depart_ment	 from ( SELECT *, DATE_FORMAT(birth_date,'%m-%d') AS birth_day from v_cus_cust_contact ) a ");
		sqlbf.append(" where 1=1 ");
		if (!StrUtils.isBlankOrNull(v.getSalerId())) {
			sqlbf.append(" and a.saler_id = '").append(v.getSalerId()).append("' ");
		}
		if (!StrUtils.isBlankOrNull(v.getLeaderId())) {
			sqlbf.append(" and a.leader_id = '").append(v.getLeaderId()).append("' ");
		}
		sqlbf.append(" and a.birth_day >= DATE_FORMAT(DATE_ADD(NOW(),interval 0 day),'%m-%d') ");
		sqlbf.append(" and a.birth_day <= DATE_FORMAT(DATE_ADD(NOW(),interval 3 day),'%m-%d')");
		List<Object[]> datas = custContactDao.queryBySql(sqlbf.toString());
		List<CustContactVo> vos = new ArrayList<CustContactVo>();
		if (datas != null) {
			for (Object[] obj : datas) {
				CustContactVo vo = new CustContactVo();
				vo.setId(String.valueOf(obj[0]));
				vo.setCustomerId(String.valueOf(obj[1]));
				vo.setCustomer(String.valueOf(obj[2]));
				vo.setSalerId(String.valueOf(obj[3]));
				vo.setSaler(String.valueOf(obj[4]));
				vo.setLeaderId(String.valueOf(obj[5]));
				vo.setLeader(String.valueOf(obj[6]));
				vo.setContactName(String.valueOf(obj[7]));
				vo.setContactId(String.valueOf(obj[8]));
				vo.setBirthDate(String.valueOf(obj[9]));
				vo.setPhone(String.valueOf(obj[10]));
				vo.setDuty(String.valueOf(obj[11]));
				String notes = String.valueOf(obj[12]);
				vo.setNotes((notes == null || notes.equals("null") ? "" : notes));
				vo.setDepartMent(String.valueOf(obj[13]));
				vos.add(vo);
			}
		}

		gridVo.setDatas(vos);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public void update4Contacts(List<CustContactVo> cusVos, ContactsVo v) throws GlobalException {
		String auth = userService.saleJLAuth(getCurrent().getAccountId());
		CustomerVo custv = customerService.findById(v.getCustomerVo().getId());
		if (cusVos.size() > 0) {
			CustContact po = custContactDao.findById(cusVos.get(0).getId());
			po.setId(cusVos.get(0).getId());
			po.setContactId(v.getId());
			po.setContactName(v.getName());
			po.setBirthDate(v.getBirthDate());
			po.setCustomerId(custv.getId());
			po.setCustomer(custv.getName());
			po.setDepartMent(v.getDepart());
			po.setDuty(v.getDuty());
			if (auth.equals("XSJL")) {
				po.setLeaderId(getCurrent().getAccountId());
				po.setLeader(getCurrent().getUserName());
			} else if (auth.equals("XSRY")) {
				po.setSalerId(getCurrent().getAccountId());
				po.setSaler(getCurrent().getUserName());
			}
			po.setPhone(v.getPhone());
			try {
				custContactDao.update(po);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			CustContact vo = new CustContact();
			vo.setContactId(v.getId());
			vo.setContactName(v.getName());
			vo.setBirthDate(v.getBirthDate());
			vo.setCustomerId(custv.getId());
			vo.setCustomer(custv.getName());
			vo.setDepartMent(v.getDepart());
			vo.setDuty(v.getDuty());
			if (auth.equals("XSJL")) {
				vo.setLeaderId(getCurrent().getAccountId());
				vo.setLeader(getCurrent().getUserName());
			} else if (auth.equals("XSRY")) {
				vo.setSalerId(getCurrent().getAccountId());
				vo.setSaler(getCurrent().getUserName());
			}
			vo.setPhone(v.getPhone());
			custContactDao.save(vo);
		}
	}

	@Override
	public String[] listByContactIds(String ids) throws GlobalException {

		ids = "'" + ids.replaceAll(",", "','") + "'";
		String sql = "select id from v_cus_cust_contact where contact_id in ( " + ids + " ) and is_del = 0";
		List<Object[]> list = custContactDao.queryBySql(sql);
		String[] idstr = null;
		if (list != null) {
			idstr = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				idstr[i] = String.valueOf(list.get(i));
			}
		}
		return idstr;
	}

}
