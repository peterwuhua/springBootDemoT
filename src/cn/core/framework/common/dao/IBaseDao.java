package cn.core.framework.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;

import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.current.Current;
/**
 * <strong>Create on : 2016年11月2日 上午11:02:46 </strong> <br>
 * <strong>Description : 基础的数据交互接口，提供了增/删/改/查相关的基础接口</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public interface IBaseDao<P> {
	/**
	 * <strong>Create on : 2016年11月2日 上午11:05:15 </strong> <br>
	 * <strong>Description : 根据ID获取对象</strong> <br>
	 * @param id
	 * @return P
	 */
	public P findById(Serializable id);
	
	/**
	 * <strong>Create on : 2016年11月2日 上午11:05:50 </strong> <br>
	 * <strong>Description : 根据ID获取对象</strong> <br>
	 * @param clazz Class
	 * @param id id
	 * @return Class
	 */
	@Cacheable(value = "findById", key = "#id")
	public Class<?> findById(Class<?> clazz, String id);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:06:22 </strong> <br>
	 * <strong>Description : 获取tree结构的根节点</strong> <br>
	 * @return P
	 */
	public P findRoot();

	/**
	 * <strong>Create on : 2016年11月2日 上午11:06:39 </strong> <br>
	 * <strong>Description : 获取所有数据</strong> <br>
	 * @return List<P>
	 */
	public List<P> list();

	/**
	 * <strong>Create on : 2016年11月2日 上午11:07:03 </strong> <br>
	 * <strong>Description :获取pid的自己LIST列表 </strong> <br>
	 * @param pid 
	 * @return List
	 */
	public List<P> listByPid(String pid);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:07:30 </strong> <br>
	 * <strong>Description : 获取ids对象</strong> <br>
	 * @param ids
	 * @return List<P>
	 */
	public List<P> listByIds(String... ids);
	/**
	 * <strong>Create on : 2016年11月2日 上午11:08:51 </strong> <br>
	 * <strong>Description :根据指定字段及排序方式获取数据 </strong> <br>
	 * @param ids ids 
	 * @param orderColumn 排序字段
	 * @param order 排序方式
	 * @return  List
	 * @throws GlobalException
	 */
	public List<P> listByIds(String ids, String orderColumn,String order) throws GlobalException;
	
	/**
	 * <strong>Create on : 2016年11月2日 上午11:09:31 </strong> <br>
	 * <strong>Description : 根据指定IDS及对象获取数据</strong> <br>
	 * @param clazz Class
	 * @param ids String
	 * @return List<Class<?>>
	 */
	public List<Class<?>> listByIds(Class<?> clazz, String... ids);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:09:57 </strong> <br>
	 * <strong>Description : 根据指定条件返回数据</strong> <br>
	 * @param queryConditionsList 查询条件
	 * @param orderConditionsList 排序条件
	 * @param startRow 开始记录
	 * @param pageSize 获取条数
	 * @return List<P> 
	 */
	public List<P> list(List<QueryCondition> queryConditionsList,
			List<OrderCondition> orderConditionsList, int startRow, int pageSize);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:10:52 </strong> <br>
	 * <strong>Description :根据指定条件返回数据 </strong> <br>
	 * @param queryConditionsList 过滤条件
	 * @param orderConditionsList 排序条件
	 * @return List<P>
	 */
	public List<P> list(List<QueryCondition> queryConditionsList,
			List<OrderCondition> orderConditionsList);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:11:27 </strong> <br>
	 * <strong>Description : 根据指定条件返回数据</strong> <br>
	 * @param queryConditionsList 过滤条件
	 * @return List<P>
	 */
	public List<P> list(List<QueryCondition> queryConditionsList);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:11:47 </strong> <br>
	 * <strong>Description : 获取唯一记录</strong> <br>
	 * @param queryConditionsList 条件
	 * @param orderConditionsList 排序
	 * @return P
	 */
	public P query0(List<QueryCondition> queryConditionsList,
			List<OrderCondition> orderConditionsList);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:12:16 </strong> <br>
	 * <strong>Description : 获取唯一记录</strong> <br>
	 * @param jpql String
	 * @return Object
	 */
	public Object query0(String jpql);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:12:31 </strong> <br>
	 * <strong>Description :获取指定记录数 </strong> <br>
	 * @param jpql String
	 * @return  List<P> 
	 */
	public List<P> list(String jpql);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:12:49 </strong> <br>
	 * <strong>Description : 获取 Query</strong> <br>
	 * @param jpql String
	 * @return Query
	 */
	public Query query(String jpql);
	/**
	 * <strong>Create on : 2016年11月2日 上午11:12:49 </strong> <br>
	 * <strong>Description : 获取 Query</strong> <br>
	 * @param jpql String
	 * @return Query
	 */
	@SuppressWarnings("rawtypes")
	public List queryBySql(String sql);
	/**
	 * 获取sql query
	 * <strong>Create on : 2016年11月2日 上午11:12:49 </strong> <br>
	 * <strong>Description : 获取 Query</strong> <br>
	 * @param jpql String
	 * @return Query
	 */
	public Query queryBysql(String sql);
	/**
	 * <strong>Create on : 2016年11月2日 上午11:13:05 </strong> <br>
	 * <strong>Description : 获取 Query</strong> <br>
	 * @param jpql String
	 * @param queryCache 是否使用缓存
	 * @return Query
	 */
	public Query query(String jpql, boolean queryCache);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:13:36 </strong> <br>
	 * <strong>Description : PageResult</strong> <br>
	 * @param pageResult
	 * @return PageResult
	 */
	public PageResult getPageResult(PageResult pageResult);
	/**
	 * <strong>Create on : 2016年11月2日 上午11:13:36 </strong> <br>
	 * <strong>Description : PageResult</strong> <br>
	 * @param pageResult
	 * @return PageResult
	 */
	public PageResult getPageResult(String jpql, PageResult pageResult);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:13:45 </strong> <br>
	 * <strong>Description : 新增记录</strong> <br>
	 * @param po
	 */
	public void add(P po);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:13:52 </strong> <br>
	 * <strong>Description : 保存记录</strong> <br>
	 * @param po
	 */
	public void save(P po);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:14:01 </strong> <br>
	 * <strong>Description : 新增或修改</strong> <br>
	 * @param po
	 */
	public void saveOrUpdate(P po);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:14:12 </strong> <br>
	 * <strong>Description :物理删除 </strong> <br>
	 * @param po
	 */
	public void delete(P po);
	/**
	 * Description :  物理删除<br>
	 */
	public void deleteAll();
	/**
	 * <strong>Description :物理删除 </strong> <br>
	 * @param id
	 */
	public void delete(Serializable id);
	/**
	 * <strong>Description :物理删除 </strong> <br>
	 * @param id
	 */
	public void delete(String hql);
	/**
	 * <strong>Create on : 2016年11月2日 上午11:14:39 </strong> <br>
	 * <strong>Description : 物理删除</strong> <br>
	 * @param coll Collection
	 */
	public void deleteAll(Collection<P> coll);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:14:51 </strong> <br>
	 * <strong>Description : 修改数据</strong> <br>
	 * @param po
	 */
	public void update(P po);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:15:03 </strong> <br>
	 * <strong>Description :获取上级名称 </strong> <br>
	 * @return String
	 */
	public String getParentName();

	/**
	 * <strong>Create on : 2016年11月2日 上午11:15:12 </strong> <br>
	 * <strong>Description : 获取根节点数据语句</strong> <br>
	 * @return String
	 */
	public String getRootCondition();

	/**
	 * <strong>Create on : 2016年11月2日 上午11:15:32 </strong> <br>
	 * <strong>Description :获取最大序号</strong> <br>
	 * @return int
	 */
	public int getMaxSort();

	/**
	 * <strong>Create on : 2016年11月2日 上午11:15:45 </strong> <br>
	 * <strong>Description : 获取EntityName</strong> <br>
	 * @param entityCls
	 * @return String
	 */
	public String getEntityName(Class<?> entityCls);

	/**
	 * <strong>Create on : 2016年11月2日 上午11:16:01 </strong> <br>
	 * <strong>Description : Current</strong> <br>
	 * @return Current
	 */
	public Current getCurrent();
	/**
	 * <strong>Create on : 2016年11月2日 上午11:16:01 </strong> <br>
	 * <strong>Description : Current</strong> <br>
	 * @return Current
	 */
	public List<P> findByProperty(String propertyName, Object value);
		
	/**
	 * <strong>Create on : 2016年11月2日 上午11:16:01 </strong> <br>
	 * <strong>Description : Current</strong> <br>
	 * @return Current
	 */
		public List<P> findByProperties(String[] propertyNames, Object[] values);
}
