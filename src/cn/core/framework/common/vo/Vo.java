package cn.core.framework.common.vo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.PropertyConvert;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
/**
 * <strong>Create on : 2016年11月2日 上午11:30:55 </strong> <br>
 * <strong>Description :通用Vo </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
//@JsonIgnoreProperties(value={"log"})
public class Vo<V> {

	
	/**
	 * id
	 */
	private String id;
	/**
	 * 版本号
	 */
	private int version;
	/**
	 * ids 逗号分隔
	 */
	private String ids;
	/**
	 * 树形结构上级节点ID
	 */
	private String pid;
	/**
	 * uuid
	 */
	private String uuid;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * createTimeStr
	 */
	private String createTimeStr; 
	/**
	 * 创建时间
	 */
	private String updateTime; 
	private long lastUpdTime;
	/**
	 * 序号
	 */
	private Integer sort;
	/**
	 * 记录数
	 */
	private int count = 0;
	/**
	 * 开始日期
	 */
	private String startDate;
	/**
	 * 截止日期
	 */
	private String endDate;
	/**
	 * 是否提交
	 */
	private String isCommit;
	/**
	 * level
	 */
	private int level;
	/**
	 * list
	 */
	private List<?> list;
	/**
	 * arrays
	 */
	private String[] arrays;
	/**
	 * vClazz
	 */
	private Class<V> vClazz;
	/**
	 * current
	 */
	private Current current;// 当前登录对象
 
	@SuppressWarnings("unchecked")
	public Vo() {
		Class<?> c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.vClazz = (Class<V>) parameterizedType[0];
		}
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:33:59 </strong> <br>
	 * <strong>Description : 获取一个实例</strong> <br>
	 * @return V
	 * @throws GlobalException
	 */
	public V instance() throws GlobalException {
		V vo;
		try {
			vo = vClazz.newInstance();
		} catch (InstantiationException e) {
			throw new GlobalException("vClazz.newInstance出错", e);
		} catch (IllegalAccessException e) {
			throw new GlobalException("vClazz.newInstance出错", e);
		}
		return vo;
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:34:13 </strong> <br>
	 * <strong>Description : 转换为当前Vo</strong> <br>
	 * @param source 
	 * @return V
	 * @throws GlobalException
	 */
	public V toVo(Object source) throws GlobalException {
		if (null == source)
			return null;
		return toVo(source, instance(), new String[] {});
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:34:33 </strong> <br>
	 * <strong>Description : 转换为当前Vo</strong> <br>
	 * @param source  source
	 * @param target target
	 * @return target
	 */
	public V toVo(Object source, V target) {
		return toVo(source, target, new String[] {});
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午11:34:52 </strong> <br>
	 * <strong>Description : 转换为Vo</strong> <br>
	 * @param po po
	 * @param vo vo
	 * @param ignoreProperty ignoreProperty
	 * @return vo
	 */
	public V toVo(Object po, V vo, String[] ignoreProperty) {
		return toVo(po, vo, null, ignoreProperty);
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:35:13 </strong> <br>
	 * <strong>Description : 转换为Vo</strong> <br>
	 * @param po po
	 * @param vo vo
	 * @param pc pc
	 * @param ignoreProperty ignoreProperty
	 * @return vo
	 */
	public V toVo(Object po, V vo, PropertyConvert pc, String[] ignoreProperty) {
		cn.core.framework.utils.BeanUtils.copyProperties(po, vo, pc,
				ignoreProperty);
		return vo;
	}

	public final String getIds() {
		return ids;
	}

	public final void setIds(String ids) {
		this.ids = ids;
	}

	public final String getUuid() {
		if (uuid == null)
			uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}
	
	public final void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public final long getCreateTime() {
		return createTime;
	}

	public final void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getUpdateTime() {
		updateTime = DateUtils.parseToDateStr(lastUpdTime);
		return updateTime;
	}

	public final void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public final Integer getSort() {
		return sort;
	}

	public final void setSort(Integer sort) {
		this.sort = sort;
	}

	public final String getStartDate() {
		return startDate;
	}

	public final void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public final String getEndDate() {
		return endDate;
	}

	public final void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public final List<?> getList() {
		return list;
	}

	public final void setList(List<?> list) {
		this.list = list;
	}

	public final int getCount() {
		return count;
	}

	public final void setCount(int count) {
		this.count = count;
	}

	public final String getIsCommit() {
		return isCommit;
	}

	public final void setIsCommit(String isCommit) {
		this.isCommit = isCommit;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Current getCurrent() {
		return current;
	}

	public void setCurrent(Current current) {
		this.current = current;
	}

	public String getCreateTimeStr() {
		createTimeStr = DateUtils.parseToDateStr(createTime);
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String[] getArrays() {
		return arrays;
	}

	public void setArrays(String[] arrays) {
		this.arrays = arrays;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	public long getLastUpdTime() {
		return lastUpdTime;
	}

	public void setLastUpdTime(long lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	public Map<String, String> getExportMap(Class<?> cla,String preName){
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 获取对象内定义的fields
	    Field[] files = cla.getDeclaredFields();
	    for (int i = 0; i < files.length; i++) {  
            Field f = files[i]; 
            Export exp = f.getAnnotation(Export.class); // 获取field对应的annotation 
            if (exp == null) continue;
            
            String tempPreName = StrUtils.isBlank(preName)? f.getName(): preName+"."+f.getName();
            if(exp.isObject() && !cla .equals(f.getType())){//tree结构的只处理一级
            	map.putAll(getExportMap(f.getType(),tempPreName));
            }else{
            	map.put(tempPreName, exp.label());
            }
        } 
	    return map;
	}
	
	@Override
	public int hashCode() {
		return this.getId() == null ? super.hashCode() : this.getId()
				.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
}
