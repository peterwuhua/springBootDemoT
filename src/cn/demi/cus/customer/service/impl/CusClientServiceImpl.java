package cn.demi.cus.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.customer.dao.IClientDao;
import cn.demi.cus.customer.dao.ICusClientDao;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Client;
import cn.demi.cus.customer.po.CusClient;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.service.ICusClientService;
import cn.demi.cus.customer.vo.ClientVo;
import cn.demi.cus.customer.vo.CusClientVo;
import cn.demi.cus.customer.vo.CustomerVo;

/**
 * Create on : 2016年11月15日 下午3:00:34  <br>
 * Description : CusClientServiceImpl <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Service("cus.cus_clientService")
public class CusClientServiceImpl extends BaseServiceImpl<CusClientVo,CusClient> implements
		ICusClientService {

	@Autowired private ICusClientDao cusClientDao;
	@Autowired private ICustomerDao customerDao;
	@Autowired private IClientDao clientDao;

	@Override
	public void add(CusClientVo v) throws GlobalException {
		Client client = new Client();
		BeanUtils.copyProperties(v.getClientVo(), client);
		clientDao.add(client);

		CusClient p = new CusClient();
		p.setClient(client);
		if(null!=v.getCustomerVo()&&null!=v.getCustomerVo().getId()){
			p.setCustomer(customerDao.findById(v.getCustomerVo().getId()));
		}
		cusClientDao.add(p);
		v.setId(p.getId());
	}

	@Override
	public void update(CusClientVo v) throws GlobalException {
		CusClient p = cusClientDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		if(null!=v.getCustomerVo()&&null!=v.getCustomerVo().getId()){
			p.setCustomer(customerDao.findById(v.getCustomerVo().getId()));
		}

		Client client = clientDao.findById(p.getClient().getId());
		BeanUtils.copyProperties(v.getClientVo(), client,client.IGNORE_PROPERTY_TO_PO);
		p.setClient(client);
		cusClientDao.update(p);

	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Tab(GridVo gridVo, CusClientVo v) throws GlobalException {
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

		pageResult = cusClientDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<CusClient>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(CusClientVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(null!=v.getCustomerVo()&&!StrUtils.isBlankOrNull(v.getCustomerVo().getId()))
		queryList.add(new QueryCondition("customer.id", QueryCondition.EQ, v.getCustomerVo().getId()));
		return queryList;
	}

	@Override
	public void data2Vo(String[] values, CusClientVo v, String param) throws GlobalException {
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
		ClientVo clientVo =new ClientVo();
		clientVo.setName(values[2]);
		clientVo.setIndustry(values[3]);
		clientVo.setAreaPath(values[4]);
		clientVo.setAddress(values[5]);
		clientVo.setPhone(values[6]);
		clientVo.setEmail(values[7]);
		clientVo.setPerson(values[8]);
		clientVo.setRemark(values[9]);
		v.setClientVo(clientVo);
	}

	@Override
	public void update2del(String... ids) throws GlobalException {
		for(CusClient p:cusClientDao.listByIds(ids)){
			p.setIsDel(Po.Y);
			cusClientDao.update(p);
			Client c = p.getClient();
			c.setIsDel(Po.Y);
			clientDao.update(c);
		}
	}

	@Override
	public List<CusClientVo> list(CusClientVo v) throws GlobalException {
		if(null!=v.getCustomerVo()){
			return toVoList(cusClientDao.listBycusId(v.getCustomerVo().getId()));
		}else{
			return super.list(v);
		}
	}

	@Override
	public void importData(CusClientVo v, String type, String param, String[][] dataArrays) throws GlobalException {
		//先删再加
		if(CLEAR_ADD.equals(type)) {
			if(!StrUtils.isBlankOrNull(param)){
				List<CusClient> list = cusClientDao.listBycusId(param);
				for (CusClient po:list) {
					this.delete(po.getId());
					clientDao.delete(po.getClient().getId());
				}
			}else{
				this.deleteAll();
				clientDao.deleteAll();
			}
		}

		CusClientVo vo = null;
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
