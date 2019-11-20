package cn.core.framework.common.po;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.hibernate.annotations.GenericGenerator;

import cn.core.framework.exception.GlobalException;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.MapUtils;
import cn.core.framework.utils.current.Current;
import cn.core.framework.utils.current.CurrentUtils;

/**
 * <strong>Create on : 2016年11月2日 上午11:23:22 </strong> <br>
 * <strong>Description : 基础的Po - Persistence Object(持久化对象)，提供了可持久化对象的基础属性</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
@MappedSuperclass
public abstract class Po<P> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为po需要例外的属性
	 */
	public String[] IGNORE_PROPERTY_TO_PO = {"id","createTime","lastUpdTime","version"};
	/**
	 * 对象转换为map时需要例外的属性
	 */
	public String[] IGNORE_PROPERTY_TO_MAP= {"isDel","version"};
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort"};
	
	/**
	 *	日志
	 */
	public Logger log = Logger.getLogger(this.getClass());
	/**
	 * N = 0
	 */
	public static final int N = 0; 
	/**
	 * Y = 1
	 */
	public static final int Y = 1; 
	/**
	 *  主键
	 */
	private String id;
	/**
	 *  逻辑删除标志 
	 */
	private int isDel = N;
	/**
	 * 设置实体版本，实体每次更新后，版本号会增加1，以保证实体为最新状态，当更新实体的时候，如果提交实体的版本号小于或者等于数据库版本号，则会更新失败，
	 * 以此防止并发性更新<br>
	 * 此属性禁止手动设定，由系统底层自动完成设定<br>
	 */
	private int version;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 最后更新时间
	 */
	private long lastUpdTime;
	/**
	 * 最后修改人
	 */
	private String lastUpdUser;
	/**
	 * 数据排序规则
	 */
	private Integer sort;
	/**
	 * 继承类的类型
	 */
	private Class<P> pClazz;

	/**
	 * <strong>Create on : 2016年11月2日 上午11:24:50 </strong> <br>
	 * <strong>Description :  初始化Po对象包含以下步骤：自动获取继承者的类型</strong> <br>
	 */
	@SuppressWarnings("unchecked")
	public Po() {
		Class<?> c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.pClazz = (Class<P>) parameterizedType[0];
		}
	}
	/**
	 * <strong>Create on : Carson Liu 2016年11月25日 上午9:52:29 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @return
	 */
	@Transient
	public abstract String[] getPropertyToMap();
	/**
	 * <strong>Create on : Carson Liu 2016年11月25日 上午9:52:32 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @return
	 */
	@Transient
	public String[] getIgnorePropertyToPo(){
		log.info("请根据自己的业务重新此方法，例外掉不需要转换至Po的属性");
		return IGNORE_PROPERTY_TO_PO;
	}
	/**
	 * <strong>Create on : Carson Liu 2016年11月25日 上午9:52:35 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @return
	 */
	@Transient
	public String[] getIgnorePropertyToMap(){
		log.info("请根据自己的业务重新此方式，例外掉不需要转换至MAP的属性");
		return IGNORE_PROPERTY_TO_MAP;
	}
	
	@Column(length = 8)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Id
	@Column(length = 32, nullable = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 13, nullable = true)
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Column(length = 13, nullable = true)
	public long getLastUpdTime() {
		return lastUpdTime;
	}

	public void setLastUpdTime(long lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	@Column(length = 8, nullable = true)
	@Version
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	@Column(length = 64)
	public String getCreateUser() {
		return createUser;
	}
	
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Column(length = 64)
	public String getLastUpdUser() {
		return lastUpdUser;
	}

	public void setLastUpdUser(String lastUpdUser) {
		this.lastUpdUser = lastUpdUser;
	}

	@Column(length = 8, nullable = true)
	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:26:10 </strong> <br>
	 * <strong>Description : 将Po（Persistence Object - 持久化对象） 转换为 Vo（View Object - 视图对象）</strong> <br>
	 * @param vo  视图对象
	 * @return 转换后的Po对象
	 * @throws GlobalException
	 */
	public Object toPo(Object vo) throws GlobalException {
		return toPo(vo, instance(),getIgnorePropertyToPo()) ;
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:26:25 </strong> <br>
	 * <strong>Description : 将Po（Persistence Object - 持久化对象） 转换为 Vo（View Object - 视图对象）</strong> <br>
	 * @param vo 视图对象
	 * @param po 视图对象
	 * @return 转换完成后的Po对象
	 */	
	public P toPo(Object vo,P po) {
		return toPo(vo, po, getIgnorePropertyToPo()) ;
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:26:45 </strong> <br>
	 * <strong>Description : 将Vo对象转换为可使用的Po对象</strong> <br>
	 * @param vo  需要进行数据转换的VO对象
	 * @param po 接受数据的Po对象
	 * @param ignoreProperty 转换过程中需要忽略的属性
	 * @return 转换完成后的Po对象
	 */
	public P toPo(Object vo, P po, String[] ignoreProperty) {
		BeanUtils.copyProperties(vo, po, ignoreProperty);
		return po;
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:27:07 </strong> <br>
	 * <strong>Description : 将Vo对象转换为可使用的Po对象</strong> <br>
	 * @param vo 需要进行数据转换的Po对象
	 * @param ignoreProperty 转换过程中需要忽略的属性
	 * @return 转换完成后的Po对象
	 * @throws GlobalException
	 */
	public P toPo(Object vo, String[] ignoreProperty) throws GlobalException {
		return toPo(vo, instance(), ignoreProperty);
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:27:27 </strong> <br>
	 * <strong>Description : 将PO转换为MAP</strong> <br>
	 * @param p P
	 * @return 转换后的MAP 对象
	 * @throws GlobalException
	 */
	public Map<String, Object> toMap(P p) throws GlobalException {
		return toMapProperty(p, getIgnorePropertyToMap());
	}
	
	/**
	 * <strong>Create on : 2016年11月2日 上午11:27:27 </strong> <br>
	 * <strong>Description : 将PO转换为MAP，黑名单机制，存在数据安全问题，建议是调用toMapProperty，运用白名单机制</strong> <br>
	 * @param p P
	 * @param string[] ignore
	 * @return 转换后的MAP 对象
	 * @throws GlobalException
	 */
	public Map<String, Object> toMapIgnoreProperty(P p,String[] ignore) throws GlobalException {
		log.info("黑名单机制，存在数据安全问题，建议是调用toMapProperty，运用白名单机制");
		return MapUtils.convertBean1(p, ignore);
	}
	
	/**
	 * <strong>Create on : 2016年11月2日 上午11:27:27 </strong> <br>
	 * <strong>Description : 将PO转换为MAP</strong> <br>
	 * @param p P
	 * @param string[] property
	 * @return 转换后的MAP 对象
	 * @throws GlobalException
	 */
	public Map<String, Object> toMapProperty(P p,String[] property) throws GlobalException {
		if(null==property) property = PROPERTY_TO_MAP; 
		return MapUtils.convertBean2(p, property);
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:27:59 </strong> <br>
	 * <strong>Description : 获取一个实例 </strong> <br>
	 * @return P
	 * @throws GlobalException
	 */
	public P instance() throws GlobalException {
		P po;
		try {
			po = pClazz.newInstance();
		} catch (InstantiationException e) {
			throw new GlobalException("pClazz.newInstance出错", e);
		} catch (IllegalAccessException e) {
			throw new GlobalException("pClazz.newInstance出错", e);
		}
		return po;
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:28:16 </strong> <br>
	 * <strong>Description : 实体持久化事件，当对实体进行初次持久化（Add）操作的时候，触发此事件，完成对 实体的最后一次设定</strong> <br>
	 * @param request
	 */
	@Transient
	public void onAdd() {
		try{
			Current current = CurrentUtils.getCurrent();
			if(null!=current){
				this.setCreateUser(current.getKey());
				this.setLastUpdUser(current.getKey());
			}else{
				this.setCreateUser("lims【dataSync】");
				this.setLastUpdUser("lims【dataSync】");
			}
		}catch(UnavailableSecurityManagerException e){
			this.setCreateUser("lims【dataSync】");
			this.setLastUpdUser("lims【dataSync】");
		}
		this.setCreateTime(System.currentTimeMillis());
		this.setLastUpdTime(System.currentTimeMillis());
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:28:32 </strong> <br>
	 * <strong>Description : 实体更新时间，当对实体进行更新操作的时候，触发此事件</strong> <br>
	 * @param request
	 */
	@Transient
	public void onUpdate() {
		try{
			Current current = CurrentUtils.getCurrent();
			if(null!=current){
				this.setLastUpdUser(current.getKey());
			}else{
				this.setLastUpdUser("lims【dataSync】");
			}
		}catch(UnavailableSecurityManagerException e){
			this.setLastUpdUser("lims【dataSync】");
		}
		this.setLastUpdTime(System.currentTimeMillis());
	}

	@Override
	public int hashCode() {
		if (StringUtils.isBlank(this.getId())) {
			return 0;
		} else {
			return this.getId().hashCode();
		}
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
}
