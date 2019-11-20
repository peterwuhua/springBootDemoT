package cn.demi.cus.contract.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.contract.dao.IContractDao;
import cn.demi.cus.contract.po.Contract;
/**
 * Create on : 2016年11月15日 下午2:43:46  <br>
 * Description : ContractDaoImpl <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Repository("cus.contractDao")
public class ContractDaoImpl extends BaseDaoImpl<Contract> implements IContractDao {

	@Override
	public List<Contract> listBycusId(String... cusId) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("customer.id in ('"+toString(cusId).replace(",", "','")+"')" ));
		queryList.add(new QueryCondition("status!='"+Constants.CONTRACT_STATUS_YWJ+"' OR status IS NULL"));
		return super.list(queryList, null);
	}

	@Override
	public Contract findByCode(String code) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("code", QueryCondition.EQ, code));
		return super.query0(queryConditions, null);
	}

	@Override
	public Contract findByCusId(String cusId) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("customer.id", QueryCondition.EQ, cusId));
		return super.query0(queryConditions, null);
	}
	
	@Override
	public List<Contract> list() {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("status!='"+Constants.CONTRACT_STATUS_YWJ+"' OR status IS NULL"));
		return super.list(queryList, null);
	}
	
	
	public PageResult getPageResult4Bill(PageResult pageResult) {
		//添加排序条件，如果存在
		List<OrderCondition> orderList = pageResult.getOrderList();
		if(CollectionUtils.isEmpty(orderList)&&!StrUtils.isEmpty(pageResult.getOrderColumn())){
			orderList = new ArrayList<OrderCondition>();
			orderList.add(new OrderCondition(pageResult.getOrderColumn(),pageResult.getOrder()));
		}
		
		// 添加查询条件，如果存在
		List<QueryCondition> queryList = pageResult.getQueryList();
		if(!StrUtils.isEmpty(pageResult.getQueryValue())){
			if(CollectionUtils.isEmpty(queryList))
				queryList = new ArrayList<QueryCondition>();
			queryList.add(new QueryCondition(pageResult.getQueryColumn(),QueryCondition.LK,StringUtils.trim(pageResult.getQueryValue())));
		}
		
		queryList.add(new QueryCondition("isDel",QueryCondition.EQ,Po.N));
		//添加行内查询条件
		List<QueryCondition> trQueryList = pageResult.getTrQueryList();
		
		long totalRows = getCount(queryList,trQueryList);
		PageBean tempPageBean = pageResult.getPageBean();
		PageBean pager = new PageBean((int) totalRows, tempPageBean.getPageSize());
		pager.refresh(tempPageBean.getCurrentPage());
		if(pageResult.hasResultList()&&totalRows>0){//获取result数据，有记录在获取数据
			Query query = getQuery(queryList,trQueryList,orderList);
			query.setFirstResult(pager.getStartRow());
			
			//如果为-1则获取全部数据
			if(PageBean.ALL_PAGE == pager.getPageSize()){
				query.setMaxResults((int)totalRows);
			}else{
				query.setMaxResults(pager.getPageSize());
			}
			
			pageResult.setResultList(query.getResultList());
		}
		pageResult.setPageBean(pager);
		return pageResult;
	}
	
	
	private long getCount(List<QueryCondition> queryConditionsList,List<QueryCondition> trQueryConditionsList) {
		StringBuffer jpql = new StringBuffer("SELECT COUNT(t0.id) FROM cus_contract t0, bus_project t1 where t0.code=t1.htNo  ");
		
		Map<String, Object> map = null;
		if (CollectionUtils.isNotEmpty(queryConditionsList)) {
			map = new LinkedHashMap<String, Object>();
			for (QueryCondition condition : queryConditionsList) {
				query(jpql, map, condition);
			}
		}
		if (CollectionUtils.isNotEmpty(trQueryConditionsList)) {
			if(null==map)map = new LinkedHashMap<String, Object>();
			for (QueryCondition condition : trQueryConditionsList) {
				getTrQuery(jpql, map, condition);
			}
		}
		Query query = query(jpql.toString());
		setParameter(map, query);
		return (Long) query.getSingleResult();
	}
	
	private void getTrQuery(StringBuffer jpql, Map<String, Object> map,
			QueryCondition condition) {
		if(null==condition||null==condition.getValue()||"".equals(condition.getValue().toString()))
			return;
		
		query(jpql, map, condition);
	}
	
	
	private void query(StringBuffer jpql, Map<String, Object> map,
			QueryCondition condition) {
		if (QueryCondition.CUSTOM.equals(condition.getOperator())) {
			jpql.append(" AND ("+condition.getCustomJPQL()+") ");
		}else if (QueryCondition.LK.equals(condition.getOperator())) {
			if(StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) 
				return;
			jpql.append(" AND ("+condition.getField().trim()+" like '%"+String.valueOf(condition.getValue()).trim()+"')");
		} else if (QueryCondition.RK.equals(condition.getOperator())) {
			if(StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) 
				return;
			jpql.append(" AND ("+condition.getField().trim()+" like '"+String.valueOf(condition.getValue()).trim()+"%')");
		} else if (QueryCondition.AK.equals(condition.getOperator())) {
			if(StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) 
				return;
			jpql.append(" AND ("+condition.getField().trim()+" like '%"+String.valueOf(condition.getValue()).trim()+"%')");
		}  else {
			if(StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) 
				return; 
			jpql.append(" AND (t0."+condition.getField().trim()+" "+condition.getOperator()+" "+String.valueOf(condition.getValue()).trim()+")");
		}
	}
	
	private Query getQuery(List<QueryCondition> queryConditionsList,List<QueryCondition> trQueryConditionsList,
			List<OrderCondition> orderConditionsList) {
		// 初始化查询语句
		StringBuffer jpql = new StringBuffer(" select t0 from "+getEntityName(Contract.class)+" t0 , bus_project t1 where t0.code=t1.htNo  ");
		
		Map<String, Object> map = null;
		if (CollectionUtils.isNotEmpty(queryConditionsList)) {
			map = new LinkedHashMap<String, Object>();
			for (QueryCondition condition : queryConditionsList) {
				query(jpql, map, condition);
			}
		}
		
		if (CollectionUtils.isNotEmpty(trQueryConditionsList)) {
			if(null==map)map = new LinkedHashMap<String, Object>();
			for (QueryCondition condition : trQueryConditionsList) {
				getTrQuery(jpql, map, condition);
			}
		}

		if (CollectionUtils.isNotEmpty(orderConditionsList)) {
			jpql.append(" ORDER BY");
			for (int i = 0, k = orderConditionsList.size(); i < k; i++) {
				jpql.append(" ");
				jpql.append(orderConditionsList.get(i).getOrderColumn());
				jpql.append(" ");
				jpql.append(orderConditionsList.get(i).getOrder());

				if (i != k - 1)
					jpql.append(",");
			}
		}else{
			jpql.append(" ORDER BY t0.sort ASC");
		}
		Query query = query(jpql.toString());
		setParameter(map, query);
		return query;
	}
	private void setParameter(Map<String, Object> map, Query query) {
		if (null != map) {
			for (String str : map.keySet()) {
				query.setParameter(str, map.get(str));
			}
		}
	}

}
