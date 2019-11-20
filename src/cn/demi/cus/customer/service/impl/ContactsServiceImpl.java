package cn.demi.cus.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.customer.dao.IContactsDao;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Contacts;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.service.IContactsService;
import cn.demi.cus.customer.vo.ContactsVo;
import cn.demi.cus.customer.vo.CustomerVo;

/**
 * Create on : 2016年11月15日 下午2:56:06  <br>
 * Description : ContactsServiceImpl  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Service("cus.contactsService")
public class ContactsServiceImpl extends BaseServiceImpl<ContactsVo,Contacts> implements
		IContactsService {
	@Autowired private IContactsDao contactsDao;
	@Autowired private ICustomerDao customerDao;
	
	@Override
	public void add(ContactsVo v) throws GlobalException {
		Contacts p = vo2Po(v);
		if(null!=v.getCustomerVo()&&null!=v.getCustomerVo().getId())
			p.setCustomer(customerDao.findById(v.getCustomerVo().getId()));
		contactsDao.add(p);
		v.setId(p.getId());
	}
	
	@Override
	public void update(ContactsVo v) throws GlobalException {
		Contacts p = contactsDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if(null!=v.getCustomerVo()&&null!=v.getCustomerVo().getId())
			p.setCustomer(customerDao.findById(v.getCustomerVo().getId()));
		contactsDao.update(p);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ContactsVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = contactsDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Contacts>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Tab(GridVo gridVo, ContactsVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = contactsDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Contacts>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(ContactsVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(null!=v.getCustomerVo()&&!StrUtils.isBlankOrNull(v.getCustomerVo().getId()))
		queryList.add(new QueryCondition("customer.id", QueryCondition.EQ, v.getCustomerVo().getId()));
		return queryList;
	}
	
	@Override
	public void data2Vo(String[] values, ContactsVo v, String param) throws GlobalException {
		CustomerVo cusVo = new CustomerVo();
		if(StrUtils.isNotBlankOrNull(param)){
			cusVo.setId(param);
			v.setCustomerVo(cusVo);
		}else{
			if(!StrUtils.isBlankOrNull(values[1])){
			Customer customer=customerDao.findByName(values[1].trim());
			if(null != customer){
				cusVo = cusVo.toVo(customer);
				v.setCustomerVo(cusVo);
			}
			}
		}
		v.setName(values[2]);
		v.setDepart(values[3]);
		v.setDuty(values[4]);
		v.setAddress(values[5]);
		v.setPhone(values[6]);
		v.setEmail(values[7]);
		v.setRemark(values[8]);
	}
	
	@Override
	public List<ContactsVo> list(ContactsVo v) throws GlobalException {
		if(null!=v.getCustomerVo()){
			return  toVoList(contactsDao.listBycusId(v.getCustomerVo().getId()));
		}else{
			return super.list(v);
		}
	}
	@Override
	public void importData(ContactsVo v, String type, String param, String[][] dataArrays) throws GlobalException {
			//先删再加
			if(CLEAR_ADD.equals(type)) {
				if(!StrUtils.isBlankOrNull(param)){
					List<Contacts> list = contactsDao.listBycusId(param);
					for (Contacts po:list) {
						this.delete(po.getId());
					}
				}else{
					this.deleteAll();
				}
			}
			
			ContactsVo vo = null;
			for (int i = 4, j = dataArrays.length; i < j; i++) {
				if (dataArrays[i].length == 0)
					continue;
					
				String[] values = dataArrays[i];
				if(null==values[0] || "".equals(values[0].trim())){
					log.info("第 "+ i +"行无效数据不做导入");
					continue;
				}
				
				vo = v.instance();
				try {
					vo.setSort(Integer.valueOf(values[0]));
				} catch (Exception e) {
					vo.setSort(i);
				}
				// 数组数据对应至对象
				data2Vo(values, vo, param);
				add(vo);
			}
	}

	@Override
	public List<ContactsVo> listByCustId(String custId) throws GlobalException {
		String jpql="FROM "+contactsDao.getEntityName(Contacts.class)+" WHERE isDel='"+Po.N+"' AND customer.id='"+custId+"' order by sort asc";
		List<Contacts>  pList=contactsDao.list(jpql);
		return toVoList(pList);
	}
	@Override
	public List<ContactsVo> listByCustName(String custName) throws GlobalException {
		String jpql="FROM "+contactsDao.getEntityName(Contacts.class)+" WHERE isDel='"+Po.N+"' AND customer.name like '"+custName+"' order by sort asc";
		List<Contacts>  pList=contactsDao.list(jpql);
		return toVoList(pList);
	}
}
