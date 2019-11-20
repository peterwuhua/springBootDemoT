package cn.core.framework.common.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.exception.GlobalException;
/**
 * <strong>Create on : 2016年11月2日 下午12:25:18 </strong> <br>
 * <strong>Description : 通用baseService接口</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
@Transactional
public interface IBaseService<V> {
	/**
	 * <strong>Create on : 2016年11月2日 下午12:25:37 </strong> <br>
	 * <strong>Description : 保存</strong> <br>
	 * @param v V
	 * @throws GlobalException
	 */
	public void save(V v) throws GlobalException;
	/**
	 * <strong>Create on : 2016年11月2日 下午12:25:48 </strong> <br>
	 * <strong>Description : 新增</strong> <br>
	 * @param v V
	 * @throws GlobalException
	 */
	public void add(V v) throws GlobalException;
	/**
	 * <strong>Create on : 2016年11月2日 下午12:25:57 </strong> <br>
	 * <strong>Description : 修改</strong> <br>
	 * @param v V
	 * @throws GlobalException
	 */
	public void update(V v) throws GlobalException;
	/**
	 * <strong>Create on : 2016年11月2日 下午12:26:07 </strong> <br>
	 * <strong>Description : 修改</strong> <br>
	 * @param v V
	 * @throws GlobalException
	 */
	public void update4Grid(V v) throws GlobalException;
	/**
	 * <strong>Create on : 2016年11月2日 下午12:26:17 </strong> <br>
	 * <strong>Description : 新增</strong> <br>
	 * @param v
	 * @throws GlobalException
	 */
	public void add4Grid(V v) throws GlobalException;
	/**
	 * <strong>Create on : 2016年11月2日 下午12:26:26 </strong> <br>
	 * <strong>Description : 逻辑删除</strong> <br>
	 * @param ids ids
	 * @throws GlobalException
	 */
	public void update2del(String... ids) throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:26:36 </strong> <br>
	 * <strong>Description : copy</strong> <br>
	 * @param sourceId 源数据ID
	 * @return targetId
	 * @throws GlobalException
	 */
	public String copy(String sourceId) throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:27:08 </strong> <br>
	 * <strong>Description :源数据 </strong> <br>
	 * @param v V 
	 * @return targetId
	 * @throws GlobalException
	 */
	public String copy(V v) throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:27:33 </strong> <br>
	 * <strong>Description : 物理删除</strong> <br>
	 * @param ids ids
	 * @throws GlobalException
	 */
	public void delete(String... ids) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2016年12月21日 下午12:08:00 <br>
	 * Description : 物理删除所有数据 <br>
	 * @throws GlobalException
	 */
	public void deleteAll() throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:28:13 </strong> <br>
	 * <strong>Description : PageResult</strong> <br>
	 * @param v v
	 * @param pageResult PageResult
	 * @return PageResult
	 * @throws GlobalException
	 */
	public PageResult pageResult(V v, PageResult pageResult)
			throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:28:26 </strong> <br>
	 * <strong>Description : pageResulted</strong> <br>
	 * @param v V 
	 * @param pageResult PageResult
	 * @return PageResult
	 * @throws GlobalException
	 */
	public PageResult pageResulted(V v, PageResult pageResult)
			throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:28:39 </strong> <br>
	 * <strong>Description : 获取数据</strong> <br>
	 * @return list
	 * @throws GlobalException
	 */
	public List<V> list() throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:29:05 </strong> <br>
	 * <strong>Description :list </strong> <br>
	 * @param v  V
	 * @return List
	 * @throws GlobalException
	 */
	public List<V> list(V v) throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:29:23 </strong> <br>
	 * <strong>Description : ids</strong> <br>
	 * @param ids ids
	 * @return List
	 * @throws GlobalException
	 */
	public List<V> listByIds(String ids) throws GlobalException;
	/**
	 * <strong>Create on : 2016年11月2日 下午12:29:32 </strong> <br>
	 * <strong>Description : listByIds</strong> <br>
	 * @param ids  ids
	 * @param orderColumn orderColumn
	 * @param order order
	 * @return List
	 * @throws GlobalException
	 */
	public List<V> listByIds(String ids, String orderColumn,String order) throws GlobalException;
	
	/**
	 * <strong>Create on : 2016年11月2日 下午12:29:48 </strong> <br>
	 * <strong>Description : listByIds</strong> <br>
	 * @param id id
	 * @return List
	 * @throws GlobalException
	 */
	public List<V> listByIds(String... id) throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:29:57 </strong> <br>
	 * <strong>Description : listByPid</strong> <br>
	 * @param pId pId
	 * @return List
	 * @throws GlobalException
	 */
	public List<V> listByPid(String pId) throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:30:09 </strong> <br>
	 * <strong>Description : findRoot</strong> <br>
	 * @return V
	 * @throws GlobalException
	 */
	public V findRoot() throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:30:16 </strong> <br>
	 * <strong>Description : findById</strong> <br>
	 * @param id id
	 * @return V
	 * @throws GlobalException
	 */
	public V findById(String id) throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:30:30 </strong> <br>
	 * <strong>Description : treeList</strong> <br>
	 * @param pid pid
	 * @return List
	 * @throws GlobalException
	 */
	public List<V> treeList(String pid) throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:30:41 </strong> <br>
	 * <strong>Description : treeList</strong> <br>
	 * @return List
	 * @throws GlobalException
	 */
	public List<V> treeList() throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:30:51 </strong> <br>
	 * <strong>Description : getMaxSort</strong> <br>
	 * @return int
	 */
	public int getMaxSort();

	/**
	 * <strong>Create on : 2016年11月2日 下午12:31:04 </strong> <br>
	 * <strong>Description : 数据导入,默认从第四行开始</strong> <br>
	 * @param v 操作对象
	 * @param type  导入模式 2表示先删在加
	 * @param param  自定义参数
	 * @param dataArrays 数据对象（解析自excel）
	 * @throws GlobalException
	 */
	public void importData(V v, String type, String param, String[][] dataArrays)
			throws GlobalException;

	/**
	 * <strong>Create on : 2016年11月2日 下午12:31:25 </strong> <br>
	 * <strong>Description : 当前对象的数据是否过期（被更新）</strong> <br>
	 * @param v 当前对象
	 * @return boolean
	 * @throws GlobalException
	 */
	public boolean isValid(V v) throws GlobalException;

	/**
	 * <strong>Description : gridData </strong> <br>
	 * @param gridVo gridVo
	 * @param v v
	 * @return GridVo
	 * @throws GlobalException
	 */
	public GridVo gridData(GridVo gridVo,V v) throws GlobalException;
	/**
	 * <strong>Description : gridDatad </strong> <br>
	 * @param gridVo gridVo
	 * @param v v
	 * @return GridVo
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,V v) throws GlobalException;
	/**
	 * <strong>Create on : 2016年11月2日 下午12:31:59 </strong> <br>
	 * <strong>Description : list</strong> <br>
	 * @param queryConditionsList queryConditionsList
	 * @param orderColumn orderColumn
	 * @param order order
	 * @return List
	 * @throws GlobalException
	 */
	public List<V> list(List<QueryCondition> queryConditionsList, String orderColumn, String order) throws GlobalException;
	/**
	 * <strong>Create on : 2016年11月2日 下午12:32:18 </strong> <br>
	 * <strong>Description : list</strong> <br>
	 * @param queryConditionsList queryConditionsList
	 * @param orderConditionsList orderConditionsList
	 * @param startRow startRow
	 * @param pageSize pageSize
	 * @return List
	 * @throws GlobalException
	 */
	public List<V> list(List<QueryCondition> queryConditionsList, List<OrderCondition> orderConditionsList, int startRow,
			int pageSize) throws GlobalException;
	/**
	 * <strong>Create on : 2016年11月2日 下午12:32:18 </strong> <br>
	 * <strong>Description : list</strong> <br>
	 * @param queryConditionsList queryConditionsList
	 * @param orderConditionsList orderConditionsList
	 * @return List
	 * @throws GlobalException
	 */
	public List<V> list(List<QueryCondition> queryConditionsList, List<OrderCondition> orderConditionsList)
			throws GlobalException;
	/**
	 * <strong>Create on : 2016年11月2日 下午12:32:18 </strong> <br>
	 * <strong>Description : list</strong> <br>
	 * @param queryConditionsList queryConditionsList
	 * @return List
	 * @throws GlobalException
	 */
	public List<V> list(List<QueryCondition> queryConditionsList) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2017年4月21日 上午9:18:29 <br>
	 * Description : 拖拽行保存序号  <br>
	 * @param selectId 选择的行的id
	 * @param placeId 需要交换行的id
	 * @return
	 * @throws GlobalException
	 */
	public boolean update4Pull(String selectId,String placeId) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2017年4月21日 上午11:31:06 <br>
	 * Description : 修改排序  <br>
	 * @param id
	 * @param sort
	 * @return
	 */
	public boolean update4Sort(String id, String sort) throws GlobalException;;
	
	/**
	 * Create on : Paddy Zhang 2017年4月21日 上午11:31:06 <br>
	 * Description : 修改排序  <br>
	 * @param id
	 * @param sort
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<V> findByProperties(String propertyName,Object value) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2017年4月21日 上午11:31:06 <br>
	 * Description : 修改排序  <br>
	 * @param id
	 * @param sort
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<V> findByProperties(String[] propertyNames,Object[] values) throws GlobalException;
}
