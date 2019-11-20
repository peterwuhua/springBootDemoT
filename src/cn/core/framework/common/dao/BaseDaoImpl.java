package cn.core.framework.common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.po.TreePo;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ObjectUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
import cn.core.framework.utils.current.CurrentUtils;
/**
 * <strong>Create on : 2016年11月2日 上午10:49:01 </strong> <br>
 * <strong>Description : 通用BaseDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public class BaseDaoImpl<P extends Po<P>> implements IBaseDao<P> {

	@PersistenceContext private EntityManager entityManager;
	@Autowired(required=false) public HttpServletRequest request;
	private Class<P> entityClazz;
	protected String entityName;
	/**
	 * log
	 */
	public Logger log = Logger.getLogger(this.getClass());
	/**
	 * <strong>Create on : 2016年11月2日 上午10:49:32 </strong> <br>
	 * <strong>Description : 获取EntityManager</strong> <br>
	 * @return EntityManager
	 */
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午10:49:34 </strong> <br>
	 * <strong>Description : 获取entityClazz </strong> <br>
	 * @return entityClazz
	 */
	public Class<P> getEntityClazz() {
		return this.entityClazz;
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午10:50:33 </strong> <br>
	 * <strong>Description : 构造器</strong> <br>
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		@SuppressWarnings("rawtypes")
		Class c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.entityClazz = (Class<P>) parameterizedType[0];
			this.entityName = getEntityName(entityClazz);
		}
	}
	
	@Override
	public P findById(Serializable id) {
		return entityManager.find(entityClazz, id);
	}
	@Override
	public void add(P po) {
		po.onAdd();
		if(po.getSort()==null) {
			po.setSort(getMaxSort()+1);
		}
		entityManager.persist(po);
	}
	
	@Override
	public void save(P po) {
		po.onAdd();
		entityManager.persist(po);
	}	
	
	@Override
	public void saveOrUpdate(P po){
		if(StrUtils.isBlankOrNull(po.getId())){
			add(po);
		}else{
			update(po);
		}
	}

	@Override
	public void delete(P po) {
		entityManager.remove(po);
	}

	@Override
	public void deleteAll() {
		String hql = "DELETE FROM "+entityName;
		entityManager.createQuery(hql).executeUpdate();
	}
	
	@Override
	public void delete(Serializable id) {
		entityManager.remove(findById(id));
	}

	@Override
	public void deleteAll(Collection<P> coll) {
		for (P t : coll)
			delete(t);
	}

	protected void deleteAll(String jpql) {
		deleteAll(list(jpql));
	}

	@Override
	public void update(P po) {
		po.onUpdate();
		entityManager.merge(po);
	}
	/**
	 * EntityName
	 */
	@Override
	public String getEntityName(Class<?> entityCls){
		String entityName = entityCls.getSimpleName();
		Entity entityAnno = entityCls.getAnnotation(Entity.class);
		if(ObjectUtils.isNotNull(entityAnno)){
			String entityName4Anno = entityAnno.name();
			if(StringUtils.isNotBlank(entityName4Anno)){
				entityName = entityName4Anno;
			}
		}
		return entityName;
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
	@Override
	public PageResult getPageResult(String jpql,PageResult pageResult) {
		Query query = query(jpql);
		long totalRows = (long) query.getResultList().size();
		PageBean tempPageBean = pageResult.getPageBean();
		PageBean pager = new PageBean((int) totalRows, tempPageBean.getPageSize());
		pager.refresh(tempPageBean.getCurrentPage());
		if(pageResult.hasResultList()&&totalRows>0){//获取result数据，有记录在获取数据
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
	/**
	 * <strong>Create on : 2016年11月2日 上午10:51:41 </strong> <br>
	 * <strong>Description : 构建query对象</strong> <br>
	 * @param queryConditionsList 查询list
	 * @param trQueryConditionsList 行内查询list 
	 * @param orderConditionsList 排序list
	 * @return Query
	 */
	private Query getQuery(List<QueryCondition> queryConditionsList,List<QueryCondition> trQueryConditionsList,
			List<OrderCondition> orderConditionsList) {
		// 初始化查询语句
		StringBuffer jpql = new StringBuffer("FROM "
				+ getEntityName(entityClazz) + " WHERE isDel = "+Po.N);
		
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
	/**
	 * <strong>Create on : 2016年11月2日 上午10:55:16 </strong> <br>
	 * <strong>Description : 构建query对象</strong> <br>
	 * @param queryConditionsList 查询LIST
	 * @param orderConditionsList 排序LIST
	 * @return Query
	 */
	private Query getQuery(List<QueryCondition> queryConditionsList,
			List<OrderCondition> orderConditionsList) {
		// 初始化查询语句
		StringBuffer jpql = new StringBuffer("FROM "
				+ getEntityName(entityClazz) + " WHERE isDel = "+Po.N);
		
		Map<String, Object> map = null;
		if (CollectionUtils.isNotEmpty(queryConditionsList)) {
			map = new LinkedHashMap<String, Object>();
			for (QueryCondition condition : queryConditionsList) {
				query(jpql, map, condition);
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
	/**
	 * <strong>Create on : 2016年11月2日 上午10:55:51 </strong> <br>
	 * <strong>Description : 构建query对象</strong> <br>
	 * @param jpql jpql
	 * @param map map
	 * @param condition condition
	 */
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
			jpql.append(" AND ("+condition.getField().trim()+" "+condition.getOperator()+" :"+condition.getField().trim().replace(".", "_")+")");
			map.put(condition.getField().trim().replace(".", "_"),condition.getValue());
		}
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午10:56:12 </strong> <br>
	 * <strong>Description : 追加行内查询</strong> <br>
	 * @param jpql  jpql
	 * @param map Map
	 * @param condition condition
	 */
	private void getTrQuery(StringBuffer jpql, Map<String, Object> map,
			QueryCondition condition) {
		if(null==condition||null==condition.getValue()||"".equals(condition.getValue().toString()))
			return;
		
		query(jpql, map, condition);
	}
	
	@Override
	public int getMaxSort() {
		StringBuffer jpql = new StringBuffer("SELECT MAX(sort) FROM "
				+ getEntityName(entityClazz)+" WHERE isDel="+Po.N);
		Object obj = query(jpql.toString()).getSingleResult();
		int maxSort = 0;
		if(null != obj){
			maxSort = (Integer)obj;
		}
		return maxSort;
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午10:56:39 </strong> <br>
	 * <strong>Description : 获取记录总数</strong> <br>
	 * @param queryConditionsList  queryConditionsList
	 * @param trQueryConditionsList trQueryConditionsList
	 * @return long
	 */
	private long getCount(List<QueryCondition> queryConditionsList,List<QueryCondition> trQueryConditionsList) {
		StringBuffer jpql = new StringBuffer("SELECT COUNT(id) FROM "
				+ getEntityName(entityClazz)+ " WHERE isDel = "+Po.N);
		
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
	/**
	 * <strong>Create on : 2016年11月2日 上午10:56:59 </strong> <br>
	 * <strong>Description : 构建提交</strong> <br>
	 * @param map  Map
	 * @param query Query
	 */
	private void setParameter(Map<String, Object> map, Query query) {
		if (null != map) {
			for (String str : map.keySet()) {
				query.setParameter(str, map.get(str));
			}
		}
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午10:57:15 </strong> <br>
	 * <strong>Description :获取记录总数 </strong> <br>
	 * @param queryConditionsList queryConditionsList
	 * @return long
	 */
	protected long getCount(List<QueryCondition> queryConditionsList) {
		StringBuffer jpql = new StringBuffer("SELECT COUNT(id) FROM "
				+ getEntityName(entityClazz)+ " WHERE isDel = "+Po.N);
		
		Map<String, Object> map = null;
		if (CollectionUtils.isNotEmpty(queryConditionsList)) {
			map = new LinkedHashMap<String, Object>();
			for (QueryCondition condition : queryConditionsList) {
				query(jpql, map, condition);
			}
		}

		Query query = query(jpql.toString());
		setParameter(map, query);
		return (Long) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<P> list(List<QueryCondition> queryConditionsList,
			List<OrderCondition> orderConditionsList,int startRow,int pageSize) {
		Query query = getQuery(queryConditionsList, orderConditionsList);
		if(startRow>-1){
			query.setFirstResult(startRow);
			query.setMaxResults(pageSize);
		}
		
		return query.getResultList();
	}
	
	@Override
	public List<P> list(List<QueryCondition> queryConditionsList,
			List<OrderCondition> orderConditionsList) {
		return list(queryConditionsList, orderConditionsList,-1,-1);
	}

	@Override
	public List<P> list(List<QueryCondition> queryConditionsList) {
		return list(queryConditionsList, null,-1,-1);
	}
	
	@Override
	public String getParentName() {
		return TreePo.PARENT_NAME;
	}

	@Override
	public String getRootCondition() {
		return getParentName() + " IS NULL ";
	}

	@Override
	public P findRoot() {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" AND ");
		jpql.append(getRootCondition());
		return list(jpql.toString()).get(0);
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午10:57:45 </strong> <br>
	 * <strong>Description : 判断是否继承自TreePo</strong> <br>
	 * @return boolean
	 */
	public boolean isTreePo() {
		return TreePo.isTreePo(entityClazz);
	}

	@Override
	public List<P> list() {
		StringBuffer jpql = new StringBuffer("FROM "
				+ getEntityName(entityClazz) + " WHERE isDel= "+Po.N);
		jpql.append(" ORDER BY sort ASC");
		return list(jpql.toString());
	}

	@Override
	public List<P> listByPid(String pid) {
		StringBuffer jpql = new StringBuffer("FROM "
				+ getEntityName(entityClazz) + " WHERE  "+getParentName()+".id = '"+pid+"'");
		jpql.append(" AND isDel= "+Po.N);
		jpql.append(" ORDER BY sort ASC");
		return list(jpql.toString());
	}

	@Override
	public List<P> listByIds(String... ids) {
		String tempIds = toString(ids);
		if(tempIds.indexOf(",")>0){
			return listByIds(toString(ids));
		}else{
			List<P> pList = new ArrayList<>();
			P p = this.findById(tempIds);
			if(null!=p && Po.N == p.getIsDel())
				pList.add(p);
			return pList;
		}
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午10:58:44 </strong> <br>
	 * <strong>Description :根据IDS获取记录列表 </strong> <br>
	 * @param ids 多个id
	 * @return List
	 */
	public List<P> listByIds(String ids) {
		return listByIds(ids,null,null);
	}
	
	@Override
	public List<P> listByIds(String ids, String orderColumn, String order){
		ids = StrUtils.replaceAll(ids, " ", "");
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE id IN ('"+toString(ids).replace(",", "','")+"')");
		jpql.append(" AND isDel= "+Po.N);
		if(null!=order)
			jpql.append(" ORDER BY "+orderColumn+" "+order);
		return list(jpql.toString());
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午11:00:10 </strong> <br>
	 * <strong>Description : 数组转字符串</strong> <br>
	 * @param ids
	 * @return String
	 */
	public String toString(String... ids) {
		String str="";
		for(int i=0,k=ids.length; i<k; i++){
			if(0!=i) str += ","; 
			str += ids[i]; 
		}
		return str;
	}

	@Override
	public Class<?> findById(Class<?> clazz, String id) {
		return (Class<?>) getEntityManager().find(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Class<?>> listByIds(Class<?> clazz, String... ids) {
		String tempIds = toString(ids);
		if(tempIds.indexOf(",")>0){
			StringBuffer jpql = new StringBuffer("FROM ");
			jpql.append(getEntityName(clazz));
			jpql.append(" WHERE id IN ('"+toString(ids).replace(",", "','")+"')");
			jpql.append(" AND isDel ="+Po.N);
			return query(jpql.toString()).getResultList();
		}else{
			List<Class<?>> pList = new ArrayList<>();
			pList.add(findById(clazz, tempIds));
			return pList;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public P query0(List<QueryCondition> queryConditionsList,
			List<OrderCondition> orderConditionsList) {
		Query query = getQuery(queryConditionsList, orderConditionsList);
		query.setFirstResult(0);
		query.setMaxResults(1);
		P p = null;
		try {
			p = (P)query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return p;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<P> list(String jpql){
		return query(jpql).getResultList();
	}
	
	@Override
	public Object query0(String jpql) {
		return query(jpql).getSingleResult();
	}

	@Override
	public Query query(String jpql) {
		return query(jpql.toString(),false);
	}
	@Override
	public List<P> findByProperties(String[] propertyNames, Object[] values) {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= '"+Po.N+"'");
		for (int i = 0; i < propertyNames.length; i++) {
			jpql.append("  AND " + propertyNames[i] + "='"+values[i]+"'");
		}
		jpql.append("  ORDER BY sort ASC");
		return list(jpql.toString());
	}
	@Override
	public Query query(String jpql,boolean queryCache) {		
		Query query = entityManager.createQuery(jpql.toString());
		query.setHint("org.hibernate.cacheable", queryCache); 
		return query;
	}
	@Override
	public Current getCurrent() {
		return CurrentUtils.getCurrent();
	}
	
	@Override
	public List<P> findByProperty(String propertyName, Object value) {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= '"+Po.N+"'");
		jpql.append("  AND " + propertyName + "='"+value+"'");
		jpql.append("  ORDER BY sort ASC");
		return list(jpql.toString());
	}
	@Override
	@SuppressWarnings("rawtypes")
	public List queryBySql(String sql) {
		Query query = entityManager.createNativeQuery(sql);
		query.setHint("org.hibernate.cacheable", false); 
		List list = query.getResultList();
		return list;
	}
	@Override
	public Query queryBysql(String sql) {		
		Query query = entityManager.createNativeQuery(sql);
		return query;
	}
	@Override
	public void delete(String hql) {
		entityManager.createQuery(hql).executeUpdate();		
	}
}