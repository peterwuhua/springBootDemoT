package cn.demi.cus.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.dao.IFinanceDao;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.po.Finance;
import cn.demi.cus.customer.service.IFinanceService;
import cn.demi.cus.customer.vo.CustomerVo;
import cn.demi.cus.customer.vo.FinanceVo;

/**
 * Create on : 2016年11月15日 下午3:01:03  <br>
 * Description : FinanceServiceImpl <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Service("cus.financeService")
public class FinanceServiceImpl extends BaseServiceImpl<FinanceVo,Finance> implements
		IFinanceService {
	
	@Autowired private IFinanceDao financeDao;
	@Autowired private ICustomerDao customerDao;
	
	@Override
	public void add(FinanceVo v) throws GlobalException {
		Finance p = vo2Po(v);
		if(null!=v.getCustomerVo()&&null!=v.getCustomerVo().getId())
			p.setCustomer(customerDao.findById(v.getCustomerVo().getId()));
		financeDao.add(p);
		v.setId(p.getId());
	}
	
	@Override
	public void update(FinanceVo v) throws GlobalException {
		Finance p = financeDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if(null!=v.getCustomerVo()&&null!=v.getCustomerVo().getId())
			p.setCustomer(customerDao.findById(v.getCustomerVo().getId()));
		financeDao.update(p);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Tab(GridVo gridVo, FinanceVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());
		
		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		pageResult.addConditionList(toQueryList(v));
		
		PageBean pageBean =	pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if(0!=gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		
		pageResult = financeDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Finance>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(FinanceVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(null!=v.getCustomerVo()&&!StrUtils.isBlankOrNull(v.getCustomerVo().getId()))
		queryList.add(new QueryCondition("customer.id", QueryCondition.EQ, v.getCustomerVo().getId()));
		return queryList;
	}
	
	@Override
	public void data2Vo(String[] values, FinanceVo v, String param) throws GlobalException {
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
		v.setAddress(values[3]);
		v.setBank(values[4]);
		v.setAccountNum(values[5]);
		v.setBankNum(values[6]);
		v.setSettlementNum(values[7]);
		v.setRegisterNum(values[8]);
		v.setZipCode(values[9]);
		v.setPhone(values[10]);
		v.setFax(values[11]);
		v.setContacts(values[12]);
		v.setRemark(values[13]);
	}
	
	@Override
	public List<FinanceVo> list(FinanceVo v) throws GlobalException {
		if(null!=v.getCustomerVo()){
			return toVoList(financeDao.listBycusId(v.getCustomerVo().getId()));
		}else{
			return super.list(v);
		}
	}
	
	@Override
	public void importData(FinanceVo v, String type, String param, String[][] dataArrays) throws GlobalException {
		//先删再加
		if(CLEAR_ADD.equals(type)) {
			if(!StrUtils.isBlankOrNull(param)){
				List<Finance> list = financeDao.listBycusId(param);
				for (Finance po:list) {
					this.delete(po.getId());
				}
			}else{
				this.deleteAll();
			}
		}
		
		FinanceVo vo = null;
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
}
