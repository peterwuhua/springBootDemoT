package cn.core.framework.common.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;
/**
 * <strong>Create on : 2016年11月2日 上午11:19:29 </strong> <br>
 * <strong>Description : PageResult</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public class PageResult implements Serializable {
	private static final long serialVersionUID = -4500981451969706619L;
	/**
	 * 每页显示记录数
	 */
	private int pageSize = 15;
	/**
	 * 当前请求action
	 */
	private String action;
	/**
	 * 排序方式
	 */
	private String order;
	/**
	 * 排序字段
	 */
	private String orderColumn;
	/**
	 * 查询字段
	 */
	private String queryColumn;
	/**
	 * 查询值
	 */
	private String queryValue;
	/**
	 * 是否获取result数据，如果不获取则值返回记录数
	 */
	private boolean hasResultList = true;// 是否获取result数据
	private PageBean pageBean = new PageBean(1, pageSize);
	@SuppressWarnings("rawtypes")
	private List<?> resultList = new ArrayList();
	private List<OrderCondition> orderList = new ArrayList<OrderCondition>();
	private List<QueryCondition> queryList = new ArrayList<QueryCondition>();
	private List<QueryCondition> trQueryList;// 行内查询

	public PageResult() {
	}

	/**
	 * <strong>author : Carson Liu </strong> <br>
	 * <strong>Create on : 2016年6月12日 下午2:56:16 </strong> <br>
	 * <strong>Description : false只返回记录条数，不返回数据；反之条数+数据</strong> <br>
	 * @param hasResultList 是否获取分页数据
	 * @exception 无
	 */
	public PageResult(boolean hasResultList) {
		setHasResultList(hasResultList);
	}

	public String getQueryColumn() {
		return queryColumn;
	}

	public void setQueryColumn(String queryColumn) {
		this.queryColumn = queryColumn;
	}

	public String getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public List<?> getResultList() {
		return resultList;
	}

	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}

	public List<OrderCondition> getOrderList() {
		return orderList;
	}

	public List<QueryCondition> getQueryList() {
		return queryList;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * <strong>创建信息: </strong> 2015年7月20日 上午9:22:21 <br>
	 * <strong>概要 : </strong> <br>
	 * 向查询容器内，添加查询条件 <strong>修改记录 : </strong> <br>
	 * 
	 * @param qc
	 *            查询条件
	 */
	public void addCondition(QueryCondition qc) {
		Assert.notNull(qc, "条件查询对象不允许为空");
		this.queryList.add(qc);
	}

	/**
	 * <strong>创建信息: </strong> 2015年7月20日 上午9:22:52 <br>
	 * <strong>概要 : </strong> <br>
	 * 向查询容器内，添加查询条件 <strong>修改记录 : </strong> <br>
	 * 
	 * @param field
	 *            查询字段
	 * @param operator
	 *            匹配操作，相等 || 小于 || 大于 || like 等...
	 * @param value
	 *            查询条件匹配值
	 */
	public void addCondition(String field, String operator, Object value) {
		Assert.hasText(field, "添加查询条件：查询字段不允许为空");
		Assert.hasText(field, "添加查询条件：查询类型不允许为空");
		Assert.notNull(value, "添加查询条件：value不允许为空");
		this.queryList.add(new QueryCondition(field, operator, value));
	}

	/**
	 * <strong>创建信息: </strong> 2015年7月20日 上午9:22:52 <br>
	 * <strong>概要 : </strong> <br>
	 * @param tempQueryList 查询字段组
	 */
	public void addConditionList(List<QueryCondition> tempQueryList){
		if(CollectionUtils.isNotEmpty(tempQueryList))
			this.queryList.addAll(tempQueryList);
	}
	
	/**
	 * <strong>创建信息: </strong> 2015年7月20日 上午9:23:42 <br>
	 * <strong>概要 : </strong> <br>
	 * 向查询容器内，添加查询条件 <strong>修改记录 : </strong> <br>
	 * 
	 * @param jpql
	 *            查询条件jpql语句
	 */
	public void addCondition(String jpql) {
		Assert.hasText(jpql, "添加查询条件：jpql语句不允许为空");
		this.queryList.add(new QueryCondition(jpql));
	}

	/**
	 * <strong>创建信息: </strong> 2015年7月20日 上午9:24:03 <br>
	 * <strong>概要 : </strong> <br>
	 * 想查询容器内，添加排序条件 <strong>修改记录 : </strong> <br>
	 * 
	 * @param orderColumn
	 *            排序字段
	 * @param order
	 *            排序类型
	 */
	public void addOrder(String orderColumn, String order) {
		Assert.hasText(orderColumn, "添加排序条件：排序字段不允许为空");
		Assert.hasText(order, "添加排序条件：排序类型不允许为空");
		this.orderList.add(new OrderCondition(orderColumn, order));
	}

	public boolean hasResultList() {
		return hasResultList;
	}

	public void setHasResultList(boolean hasResultList) {
		this.hasResultList = hasResultList;
	}

	public void setQueryList(List<QueryCondition> queryList) {
		this.queryList = queryList;
	}

	public List<QueryCondition> getTrQueryList() {
		return trQueryList;
	}

	public void setTrQueryList(List<QueryCondition> trQueryList) {
		this.trQueryList = trQueryList;
	}

}
