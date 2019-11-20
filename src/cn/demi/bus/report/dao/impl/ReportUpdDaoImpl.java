package cn.demi.bus.report.dao.impl;

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
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.report.dao.IReportUpdDao;
import cn.demi.bus.report.po.ReportUpd;

@Repository("bus.reportUpdDao")
public class ReportUpdDaoImpl extends BaseDaoImpl<ReportUpd> implements IReportUpdDao {

	@Override
	public void deleteByReportId(String reportId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(ReportUpd.class)+" WHERE report.id ='"+reportId+"'").executeUpdate();
	}

	@Override
	public List<ReportUpd> listByReportId(String reportId) {
		String hql="FROM "+getEntityName(ReportUpd.class)+" WHERE isDel='"+Po.N+"' AND report.id ='"+reportId+"' ORDER BY sort asc";
		return super.list(hql);
	}
	
	@Override
	public PageResult getPageResult(PageResult pageResult) {
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
	
	private Query getQuery(List<QueryCondition> queryConditionsList,List<QueryCondition> trQueryConditionsList,
			List<OrderCondition> orderConditionsList) {
		// 初始化查询语句
		StringBuffer jpql = new StringBuffer("FROM "
				+ getEntityName(ReportUpd.class) + " WHERE isDel = "+Po.N);
		
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
			jpql.append(" ORDER BY sort ASC");
		}
		Query query = query(jpql.toString());
		setParameter(map, query);
		return query;
	}
	
	
	private long getCount(List<QueryCondition> queryConditionsList,List<QueryCondition> trQueryConditionsList) {
		StringBuffer jpql = new StringBuffer("SELECT COUNT(id) FROM "
				+ getEntityName(ReportUpd.class)+ " WHERE isDel = "+Po.N);
		
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
	
	private void setParameter(Map<String, Object> map, Query query) {
		if (null != map) {
			for (String str : map.keySet()) {
				query.setParameter(str, map.get(str));
			}
		}
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
			jpql.append(" AND ("+condition.getField().trim()+" "+condition.getOperator()+" "+String.valueOf(condition.getValue()).trim()+")");
		}
	}
	
}
