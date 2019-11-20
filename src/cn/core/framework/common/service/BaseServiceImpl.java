package cn.core.framework.common.service;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.po.TreePo;
import cn.core.framework.common.vo.Vo;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.CollectionUtils;
import cn.core.framework.utils.PropertiesTools;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
/**
 * <strong>Create on : 2016年11月2日 下午12:17:25 </strong> <br>
 * <strong>Description :通用baseservice实现 </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public class BaseServiceImpl<V extends Vo<V>,P extends Po<P>> implements IBaseService<V>{
	
	protected String tablePrefix = PropertiesTools.getPropertiesValueByFileAndKey("resources/jdbc.properties","namingStrategy.tablePrefix");// 数据库表名前缀
	
	public Logger log = Logger.getLogger(this.getClass());
	protected final String APPEND_ADD = "1";//追加数据导入
	protected final String CLEAR_ADD = "2";//数据导入时先删再加
	protected final String MATCHING_ADD = "3";//匹配导入
	@Autowired private IBaseDao<P> baseDao;
	private Class<V> vClazz;
	private Class<P> pClazz;
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.vClazz = (Class<V>) parameterizedType[0];
            this.pClazz = (Class<P>) parameterizedType[1];
        }
	}
	
	@Override
	public void save(V v) throws GlobalException {
		if(StrUtils.isEmpty(v.getId())){
			add(v);
		}else{
			update(v);
		}
	}

	@Override
	public void update(V v) throws GlobalException {
		P p = baseDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		baseDao.update(p);
	}
	
	@Override
	public String copy(String sourceId) throws GlobalException  {
		V v = findById(sourceId);
		v.setId(null);
		
		P p = vo2Po(v);
		baseDao.add(p);
		return p.getId();
	}
	
	@Override
	public String copy(V v) throws GlobalException  {
		v = findById(v.getId());
		v.setId(null);
		
		P p = vo2Po(v);
		baseDao.add(p);
		return p.getId();
	}
	
	@Override
	public void delete(String... ids) throws GlobalException {
		baseDao.deleteAll(baseDao.listByIds(ids));
	}
	
	@Override
	public void deleteAll() throws GlobalException {
		baseDao.deleteAll();
	}
	
	@Override
	public void update2del(String... ids) throws GlobalException {
		for(P p:baseDao.listByIds(ids)){
			p.setIsDel(Po.Y);
			baseDao.update(p);
		}
	}
	
	@Override
	public PageResult pageResult(V v, PageResult pageResult)
			throws GlobalException {
		List<QueryCondition> queryList = pageResult.getQueryList();
		if(CollectionUtils.isEmpty(queryList))
			queryList = new ArrayList<QueryCondition>();
		return pageResult(pageResult);
	}
	
	@Override
	public PageResult pageResulted(V v, PageResult pageResult)
			throws GlobalException {
		return pageResult(pageResult);
	}
	/**
	 * <strong>Create on : 2016年11月2日 下午12:17:57 </strong> <br>
	 * <strong>Description : PageResult</strong> <br>
	 * @param pageResult 
	 * @return PageResult
	 * @throws GlobalException
	 */
	public PageResult pageResult(PageResult pageResult)
			throws GlobalException {
		PageResult pr = baseDao.getPageResult(pageResult);
		@SuppressWarnings("unchecked")
		List<P> pList = (List<P>) pr.getResultList();
		pr.setResultList(toVoList(pList));
		return pr;
	}

	@Override
	public List<V> list() throws GlobalException {
		return toVoList(baseDao.list());
	}
	
	@Override
	public List<V> listByIds(String... ids) throws GlobalException {
		return toVoList(baseDao.listByIds(ids));
	}

	@Override
	public List<V> listByIds(String ids, String orderColumn, String order)
			throws GlobalException {
		return toVoList(baseDao.listByIds(ids,orderColumn,order));
	}
	/**
	 * <strong>Create on : 2016年11月2日 下午12:18:19 </strong> <br>
	 * <strong>Description : po形LIST转换为Vo形LIST</strong> <br>
	 * @param pList
	 * @return List
	 * @throws GlobalException
	 */
	public List<V> toVoList(List<P> pList) throws GlobalException {
		List<V> vList = new ArrayList<V>();
		if(null==pList||pList.size()==0)
			return vList;
		
		for(P p:pList)
			vList.add(po2Vo(p));
		return vList;
	}
	
	@Override
	public List<V> list(V v) throws GlobalException {
		return list();
	}
	@Override
	public List<V> list(List<QueryCondition> queryConditionsList, String orderColumn, String order) throws GlobalException {
		List<OrderCondition> orderConditionsList = new ArrayList<OrderCondition>();
		orderConditionsList.add(new OrderCondition(orderColumn, order));
		return list(queryConditionsList, orderConditionsList);
	}
	@Override
	public List<V> list(List<QueryCondition> queryConditionsList, List<OrderCondition> orderConditionsList,int startRow,int pageSize) throws GlobalException {
		List<P> pList = baseDao.list(queryConditionsList,orderConditionsList,startRow,pageSize);
		return toVoList(pList);
	}
	@Override
	public List<V> list(List<QueryCondition> queryConditionsList, List<OrderCondition> orderConditionsList) throws GlobalException {
		return list(queryConditionsList, orderConditionsList,-1,-1);
	}
	
	@Override
	public List<V> list(List<QueryCondition> queryConditionsList) throws GlobalException {
		return list(queryConditionsList,null);
	}
	
	@Override
	public V findById(String id) throws GlobalException{
		P po;
		try {
			po = baseDao.findById(id);
		} catch (Exception e) {
			throw new GlobalException("根据id获取对象失败  id:"+id,e);
		} 
		if(null==po) return null;
		return po2Vo(po);
	}
	
	public P vo2Po(V v) throws GlobalException{
		P po;
		try {
			po = pClazz.newInstance();
		} catch (InstantiationException e) {
			throw new GlobalException("pClazz.newInstance出错",e);
		} catch (IllegalAccessException e) {
			throw new GlobalException("pClazz.newInstance出错",e);
		}
		return po.toPo(v, po,po.getIgnorePropertyToPo());
	}
	
	public V po2Vo(P p) throws GlobalException{
		V vo;
		try {
			vo = vClazz.newInstance();
		} catch (InstantiationException e) {
			throw new GlobalException("vClazz.newInstance出错",e);
		} catch (IllegalAccessException e) {
			throw new GlobalException("vClazz.newInstance出错",e);
		}
		vo = vo.toVo(p,vo);
		if (isTreePo()) {
			TreePo<?> tree = (TreePo<?>)p;
			if(null!=tree.getParent())
				vo.setPid(tree.getParent().getId());
		}
		return vo;
	}
	
	@Override
	public void add(V v) throws GlobalException {
		P p = vo2Po(v);
		baseDao.add(p);
		v.setId(p.getId());
	}

	@Override
	public List<V> listByPid(String pId) throws GlobalException {
		return toVoList(baseDao.listByPid(pId));
	}
	
	@Override
	public V findRoot() throws GlobalException {
		V v = null;
		if(isTreePo()){
			v =  po2Vo(baseDao.findRoot());
		}
		return v ;
	}
	
	@Override
	public void importData(V v,String type, String param,String[][] dataArrays) throws GlobalException {
		//先删再加
		try {
			if(CLEAR_ADD.equals(type)) this.deleteAll();
		} catch (Exception e) {
			log.info("删除数据失败");
			throw new GlobalException("删除数据失败",e);
		}
		V vo = null;
		for (int i = 4, j = dataArrays.length; i < j; i++) {
			if (dataArrays[i].length == 0)
				continue;
				
			String[] values = dataArrays[i];
			if(null==values[0] || "".equals(values[0].trim())){
				log.info("第 "+ i +"行无效数据不做导入");
				continue;
			}
			vo = instanceVo(values,v,type,param);
			try {
				vo.setSort(Integer.valueOf(values[0]));
			} catch (Exception e) {
				vo.setSort(i);
			}
			// 数组数据对应至对象
			data2Vo(values,vo,param);
			save(vo);
		}
	}
	/**
	 * <strong>Create on : 2016年11月2日 下午12:20:12 </strong> <br>
	 * <strong>Description : 概要 : 数据类型转至vo</strong> <br>
	 * @param values 从excel文件解析到的数组 
	 * @param v VO对象
	 * @param param 参数
	 * @throws GlobalException
	 */
	public void data2Vo(String[] values,V v,String param) throws GlobalException{
		log.info("请根据业务 重写此方法,数组转至vo");
	}
	public V instanceVo(String[] values,V v,String type,String param) throws GlobalException{
		log.info("请根据业务 重写此方法,实例化Vo");
		return v.instance();
	}
	@Override
	public void update4Grid(V v) throws GlobalException{
		update(v);
		log.info("请根据业务 重写此方法,和页面的 editable 对应");
	}
	@Override
	public void add4Grid(V v) throws GlobalException{
		add(v);
		log.info("请根据业务 重写此方法,和页面的 editable 对应");
	}

	/**
	 * <strong>Create on : 2016年11月2日 下午12:21:00 </strong> <br>
	 * <strong>Description : vo转换为queryConditionList</strong> <br>
	 * @param v V
	 * @return List
	 * @throws GlobalException
	 */
	public List<QueryCondition> toQueryList(V v) throws GlobalException{
		log.info("请根据业务 重写此方法,vo转换为queryConditionList个，用于构建条件查询,默认不构建Vo中的条件");
		return null;
	}
	
	/**
	 * <strong>Create on : 2016年11月2日 下午12:21:29 </strong> <br>
	 * <strong>Description : isTreePo</strong> <br>
	 * @return boolean
	 */
	private boolean isTreePo() {
		return TreePo.isTreePo(pClazz);
	}

	@Override
	public List<V> listByIds(String ids) throws GlobalException {
		return toVoList(baseDao.listByIds(ids));
	}

	/**
	 * <strong>Create on : 2016年11月2日 下午12:21:46 </strong> <br>
	 * <strong>Description : 验证数据有效性</strong> <br>
	 * @param tempVersion 页面传输过来的版本号
	 * @param dbVersion 数据库的版本号
	 * @throws GlobalException
	 */
	public void valVersion(int tempVersion,int dbVersion) throws GlobalException {
		if(0==tempVersion){
			log.info("未加入版本验证，存在数据重复操作情况");
			return;
		}
		if(tempVersion != dbVersion)
			throw new GlobalException("stale_data","数据已被更新");
	}
	
	@Override
	public boolean isValid(V v) throws GlobalException {
		P p = baseDao.findById(v.getId());
		return p.getVersion() == v.getVersion();
	}
	
	@Override
	public int getMaxSort() {
		return baseDao.getMaxSort();
	}
	/**
	 * <strong>Create on : 2016年11月2日 下午12:22:04 </strong> <br>
	 * <strong>Description : getCurrent</strong> <br>
	 * @return Current
	 */
	protected Current getCurrent(){
		return baseDao.getCurrent();
	}

	@Override
	public List<V> treeList(String pid) throws GlobalException {
		List<V> list = new ArrayList<V>();
		if(StrUtils.isBlank(pid))
			throw new GlobalException("pid 不能为空");
		V v = findById(pid);
		list.add(v);
		return appendSubsetList(list, v.getId(),pid);
	}
	
	@Override
	public List<V> treeList() throws GlobalException {
		List<V> list = new ArrayList<V>();
		V v = this.findRoot();
		list.add(v);
		return appendSubsetList(list, v.getId(),v.getId());
	}
	/**
	 * <strong>Create on : 2016年11月2日 下午12:22:17 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param allList 所有LIST
	 * @param pid 上级节点ID
	 * @param currentTreeNodeId 当前参数 currentTreeNodeId 
	 * @return allList
	 * @throws GlobalException
	 */
	private List<V> appendSubsetList(List<V> allList,String pid,String currentTreeNodeId) throws GlobalException {
		List<V> tempList = listByPid(pid);
					
		if(!CollectionUtils.isEmpty(tempList)){
			for(V v:tempList){
				// 过滤当前树节点以及当前节点的子节点
				if(StringUtils.isNotBlank(currentTreeNodeId) && StringUtils.equalsIgnoreCase(currentTreeNodeId, v.getId())){
					continue;
				}
				allList.add(v);
				appendSubsetList(allList, v.getId(),currentTreeNodeId);
			}
		}
		return allList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo,V v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = baseDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<P>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo,V v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = baseDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<P>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	/**
	 * Create on : Paddy Zhang 2017年2月23日 下午2:55:03 <br>
	 * Description : 获取pageResult <br>
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public PageResult getPageResult(GridVo gridVo, V v) throws GlobalException {
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
		return pageResult;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<?, Object>> poList2mapList(List<P> list) throws GlobalException{
		List tempList = new ArrayList();
		for(P p:list){
			tempList.add(po2map(p));
		}
		return tempList;
	}
	/**
	 * <strong>Create on : 2016年11月2日 下午12:23:33 </strong> <br>
	 * <strong>Description : p 转 Map</strong> <br>
	 * @param p  p
	 * @return Map<String,Object>
	 * @throws GlobalException
	 */
	public Map<String,Object> po2map(P p) throws GlobalException{
		log.info("请根据业务 重写此方法,po对象转MAP，保证数据安全性和效率，只回传指定属性既可");
		return p.toMapProperty(p, p.getPropertyToMap());
	}
	/**
	 * <strong>Create on : 2016年11月2日 下午12:24:01 </strong> <br>
	 * <strong>Description : 构建 List<QueryCondition></strong> <br>
	 * @param filters 查询字串
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<QueryCondition> getFilterRules(String filters)throws GlobalException{
		List<QueryCondition> returnList = null;
		if(null==filters) return returnList;
		ObjectMapper mapper = new ObjectMapper();  
		List<Map<String,String>> rulesList = null;
		try {
			if(StrUtils.isBlankOrNull(filters))
				return null;
			
			rulesList = (List<Map<String,String>>) mapper.readValue(filters, Map.class).get("rules");
			if(CollectionUtils.isEmpty(rulesList))
				return  returnList;
			
			returnList = new ArrayList<QueryCondition>();
			for(Map<String,String> ruleMap:rulesList){
				QueryCondition q = new QueryCondition();
				q.setField(ruleMap.get("field"));
				q.setOperator(q.getOperator(ruleMap.get("op")));
				q.setValue(getValue(q.getField(),StringUtils.trim(ruleMap.get("data"))));
				returnList.add(q);
			}
		} catch (JsonParseException e) {
			throw new GlobalException(e);
		} catch (JsonMappingException e) {
			throw new GlobalException(e);
		} catch (IOException e) {
			throw new GlobalException(e);
		}
		
		return returnList;
	} 
	/**
	 * <strong>Create on : 2016年11月2日 下午12:24:29 </strong> <br>
	 * <strong>Description : 特殊查询字段处理</strong> <br>
	 * @param filter 字段
	 * @param value 值
	 * @return  Object
	 */
	public Object getValue(String filter,String value){
		if("sort".equals(filter)) 
			return Integer.valueOf(value);
		return value;
	}

	@Override
	public boolean update4Pull(String selectId, String placeId) throws GlobalException {
		if(StrUtils.isBlankOrNull(selectId) || StrUtils.isBlankOrNull(placeId)) return false;
		try {
			P selectP = baseDao.findById(selectId);
			P placeP = baseDao.findById(placeId);
			int selectSort = selectP.getSort();
			int placeSort = placeP.getSort();
			selectP.setSort(placeSort);
			placeP.setSort(selectSort);
			baseDao.update(selectP);
			baseDao.update(placeP);
			return true;
		} catch (Exception e) {
			 log.info("保存序号出错，请检查参数是否异常");
			return false;
		}
	}

	@Override
	public boolean update4Sort(String id, String sort) throws GlobalException {
		if(StrUtils.isBlankOrNull(id)) throw new GlobalException("参数异常");
		try {
			P p = baseDao.findById(id);
			p.setSort(Integer.valueOf(sort));
			baseDao.update(p);
			return true;
		} catch (Exception e) {
			log.info("保存序号出错，请检查参数是否异常");
			return false;
		}
	}

	@Override
	public List<V> findByProperties(String propertyName, Object value) throws GlobalException {
		return this.findByProperties(new String[] { propertyName },
				new Object[] { value });
	}

	@Override
	public List<V> findByProperties(String[] propertyNames, Object[] values) throws GlobalException {
		return toVoList(baseDao.findByProperties(propertyNames,values));
	}
	
	protected void query(StringBuffer jpql,QueryCondition condition) {
		if (QueryCondition.LK.equals(condition.getOperator())) {
			if(StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) 
				return;
			jpql.append(" AND (t."+condition.getField().trim()+" like '%"+String.valueOf(condition.getValue()).trim()+"')");
		} else if (QueryCondition.RK.equals(condition.getOperator())) {
			if(StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) 
				return;
			jpql.append(" AND (t."+condition.getField().trim()+" like '"+String.valueOf(condition.getValue()).trim()+"%')");
		} else if (QueryCondition.AK.equals(condition.getOperator())) {
			if(StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) 
				return;
			jpql.append(" AND (t."+condition.getField().trim()+" like '%"+String.valueOf(condition.getValue()).trim()+"%')");
		}  else {
			if(StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) 
				return; 
			jpql.append(" AND (t."+condition.getField().trim()+" "+condition.getOperator()+" :"+condition.getField().trim().replace(".", "_")+")");
		}
	}
}