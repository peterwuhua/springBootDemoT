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
import cn.demi.cus.customer.dao.ICusProduceDao;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.dao.IProduceDao;
import cn.demi.cus.customer.po.CusProduce;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.po.Produce;
import cn.demi.cus.customer.service.ICusProduceService;
import cn.demi.cus.customer.vo.CusProduceVo;
import cn.demi.cus.customer.vo.CustomerVo;
import cn.demi.cus.customer.vo.ProduceVo;

/**
 * Create on : 2016年11月15日 下午3:00:45  <br>
 * Description : CusProduceServiceImpl <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Service("cus.cus_produceService")
public class CusProduceServiceImpl extends BaseServiceImpl<CusProduceVo,CusProduce> implements
		ICusProduceService {


	@Autowired private ICusProduceDao cusProduceDao;
	@Autowired private ICustomerDao customerDao;
	@Autowired private IProduceDao produceDao;
	
	@Override
	public void add(CusProduceVo v) throws GlobalException {
		Produce pro = new Produce();
		BeanUtils.copyProperties(v.getProduceVo(), pro);
		produceDao.add(pro);
		
		CusProduce p = new CusProduce();
		p.setProduce(pro);
		if(null!=v.getCustomerVo()&&null!=v.getCustomerVo().getId()){
			p.setCustomer(customerDao.findById(v.getCustomerVo().getId()));
		}
		cusProduceDao.add(p);
		v.setId(p.getId());
	}
	
	@Override
	public void update(CusProduceVo v) throws GlobalException {
		CusProduce p = cusProduceDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		if(null!=v.getCustomerVo()&&null!=v.getCustomerVo().getId()){
			p.setCustomer(customerDao.findById(v.getCustomerVo().getId()));
		}
		
		Produce pro = produceDao.findById(p.getProduce().getId());
		BeanUtils.copyProperties(v.getProduceVo(), pro,pro.IGNORE_PROPERTY_TO_PO);
		p.setProduce(pro);
		cusProduceDao.update(p);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Tab(GridVo gridVo, CusProduceVo v) throws GlobalException {
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
		
		pageResult = cusProduceDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<CusProduce>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(CusProduceVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(null!=v.getCustomerVo()&&!StrUtils.isBlankOrNull(v.getCustomerVo().getId()))
		queryList.add(new QueryCondition("customer.id", QueryCondition.EQ, v.getCustomerVo().getId()));
		return queryList;
	}
	
	@Override
	public void data2Vo(String[] values, CusProduceVo v, String param) throws GlobalException {
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
		ProduceVo proVo =new ProduceVo();
		proVo.setName(values[2]);
		proVo.setIndustry(values[3]);
		proVo.setAreaPath(values[4]);
		proVo.setAddress(values[5]);
		proVo.setPhone(values[6]);
		proVo.setEmail(values[7]);
		proVo.setPerson(values[8]);
		proVo.setRemark(values[9]);
		proVo.setCusVo(cusVo);
		v.setProduceVo(proVo);
	}
	
	@Override
	public void update2del(String... ids) throws GlobalException {
		for(CusProduce p:cusProduceDao.listByIds(ids)){
			p.setIsDel(Po.Y);
			cusProduceDao.update(p);
			Produce c = p.getProduce();
			c.setIsDel(Po.Y);
			produceDao.update(c);
		}
	}
	
	@Override
	public List<CusProduceVo> list(CusProduceVo v) throws GlobalException {
		if(null!=v.getCustomerVo()){
			return toVoList(cusProduceDao.listBycusId(v.getCustomerVo().getId()));
		}else{
			return super.list(v);
		}
	}
	
	@Override
	public void importData(CusProduceVo v, String type, String param, String[][] dataArrays) throws GlobalException {
		//先删再加
				if(CLEAR_ADD.equals(type)) {
					if(!StrUtils.isBlankOrNull(param)){
						List<CusProduce> list = cusProduceDao.listBycusId(param);
						for (CusProduce po:list) {
							this.delete(po.getId());
							produceDao.delete(po.getProduce().getId());
						}
					}else{
						this.deleteAll();
						produceDao.deleteAll();
					}
				}
				
				CusProduceVo vo = null;
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
