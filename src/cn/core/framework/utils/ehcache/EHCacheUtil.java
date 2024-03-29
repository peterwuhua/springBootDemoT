package cn.core.framework.utils.ehcache;

import java.util.List;

import cn.core.framework.utils.StrUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

/**
 * ehcache工具类
 */

public class EHCacheUtil {
	private static CacheManager cacheManager = null;
	private static Cache cache = null;

	static{
		EHCacheUtil.initCacheManager();
		EHCacheUtil.initCache("cache");
	}
	/**
	 * 
	 * 初始化缓存管理容器
	 */
	public static CacheManager initCacheManager() {
		try {
			if (cacheManager == null)
				cacheManager = CacheManager.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cacheManager;
	}

	/**
	 * 
	 * 初始化缓存管理容器
	 * 
	 * @param path
	 *            ehcache.xml存放的路徑
	 */
	@SuppressWarnings("static-access")
	public static CacheManager initCacheManager(String path) {
		try {
			if (cacheManager == null) {
				CacheManager.getInstance();
				cacheManager = CacheManager.create(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cacheManager;
	}

	/**
	 * 初始化cache
	 */

	public static Cache initCache(String cacheName) {
		checkCacheManager();
		if (null == cacheManager.getCache(cacheName)) {
			cacheManager.addCache(cacheName);
		}
		cache = cacheManager.getCache(cacheName);
		return cache;
	}

	/**
	 * 
	 * 添加缓存
	 * 
	 * @param key
	 *            关键字
	 * @param value
	 *            值
	 */
	public static void put(Object key, Object value) {
		checkCache();
		// 创建Element,然后放入Cache对象中
		Element element = new Element(key, value);
		cache.put(element);
	}

	/**
	 * 获取cache
	 * 
	 * @param key
	 *            关键字
	 * @return Object
	 */
	public static Object get(Object key) {
		checkCache();
		Element element = cache.get(key);
		if (null == element) {
			return null;
		}
		return element.getObjectValue();
	}

	/**
	 * 初始化缓存
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @param maxElementsInMemory
	 *            元素最大数量
	 * @param overflowToDisk
	 *            是否持久化到硬盘
	 * @param eternal
	 *            是否会死亡
	 * @param timeToLiveSeconds
	 *            缓存存活时间
	 * @param timeToIdleSeconds
	 *            缓存的间隔时间
	 * @return 缓存
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static Cache initCache(String cacheName, int maxElementsInMemory, boolean overflowToDisk, boolean eternal,
			long timeToLiveSeconds, long timeToIdleSeconds) throws Exception {
		try {
			CacheManager singletonManager = CacheManager.create();
			Cache myCache = singletonManager.getCache(cacheName);
			if (myCache != null) {
				CacheConfiguration config = cache.getCacheConfiguration();
				config.setTimeToLiveSeconds(timeToLiveSeconds);
				config.setMaxElementsInMemory(maxElementsInMemory);
				config.setOverflowToDisk(overflowToDisk);
				config.setEternal(eternal);
				config.setTimeToIdleSeconds(timeToIdleSeconds);
			}
			if (myCache == null) {
				Cache memoryOnlyCache = new Cache(cacheName, maxElementsInMemory, overflowToDisk, eternal, timeToLiveSeconds,
						timeToIdleSeconds);
				singletonManager.addCache(memoryOnlyCache);
				myCache = singletonManager.getCache(cacheName);
			}
			return myCache;
		} catch (Exception e) {
			throw new Exception("init cache " + cacheName + " failed!!!");
		}
	}

	/**
	 * 初始化cache
	 * 
	 * @param cacheName
	 *            cache的名字
	 * @param timeToLiveSeconds
	 *            有效时间
	 * @return cache 缓存
	 * @throws Exception
	 */
	public static Cache initCache(String cacheName, long timeToLiveSeconds) throws Exception {
		return EHCacheUtil.initCache(cacheName, EHCacheConfig.MAXELEMENTSINMEMORY, EHCacheConfig.OVERFLOWTODISK,
				EHCacheConfig.ETERNAL, timeToLiveSeconds, EHCacheConfig.TIMETOIDLESECONDS);
	}

	/**
	 * 初始化Cache
	 * 
	 * @param cacheName
	 *            cache容器名
	 * @return cache容器
	 * @throws Exception
	 */
	public static Cache initMyCache(String cacheName) throws Exception {
		return EHCacheUtil.initCache(cacheName, EHCacheConfig.TIMETOlIVESECONDS);
	}

	/**
	 * 修改缓存容器配置
	 * 
	 * @param cacheName
	 *            缓存名
	 * @param timeToLiveSeconds
	 *            有效时间
	 * @param maxElementsInMemory
	 *            最大数量
	 * @throws Exception
	 */

	@SuppressWarnings("deprecation")
	public static boolean modifyCache(String cacheName, long timeToLiveSeconds, int maxElementsInMemory) throws Exception {
		try {
			if (StrUtils.isBlankOrNull(cacheName) && timeToLiveSeconds != 0L && maxElementsInMemory != 0) {
				CacheManager myManager = CacheManager.create();
				Cache myCache = myManager.getCache(cacheName);
				CacheConfiguration config = myCache.getCacheConfiguration();
				config.setTimeToLiveSeconds(timeToLiveSeconds);
				config.setMaxElementsInMemory(maxElementsInMemory);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new Exception("modify cache " + cacheName + " failed!!!");
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午10:05:57 <br>
	 * Description : 向指定容器中设置值 <br>
	 * @param cacheName 容器名
	 * @param key 键
	 * @param value 值
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean setValue(String cacheName, String key, Object value) throws Exception {
		try {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName);
			if (myCache == null) {
				myCache = initCache(cacheName);
			}
			myCache.put(new Element(key, value));
			return true;
		} catch (Exception e) {
			throw new Exception("set cache " + cacheName + " failed!!!");
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午10:07:22 <br>
	 * Description : 向指定容器中设置值 <br>
	 * @param cacheName 容器名
	 * @param key 键
	 * @param value 值
	 * @param timeToLiveSeconds 存活时间
	 * @return boolean
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static boolean setValue(String cacheName, String key, Object value, Integer timeToLiveSeconds) throws Exception {
		try {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName);
			if (myCache == null) {
				initCache(cacheName, timeToLiveSeconds);
				myCache = myManager.getCache(cacheName);
			}
			myCache.put(new Element(key, value, EHCacheConfig.ETERNAL, EHCacheConfig.TIMETOIDLESECONDS, timeToLiveSeconds));
			return true;
		} catch (Exception e) {
			throw new Exception("set cache " + cacheName + " failed!!!");
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午10:16:29 <br>
	 * Description : 从ehcache的指定容器中取值 <br>
	 * @param cacheName 容器名
	 * @param key 键
	 * @return Object
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static Object getValue(String cacheName, String key) throws Exception {
		try {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName);
			if (myCache == null) {
				myCache = initMyCache(cacheName);
			}
			return myCache.get(key).getValue();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午10:11:37 <br>
	 * Description : 删除指定的ehcache容器 <br>
	 * @param cacheName cacheName
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean removeEhcache(String cacheName) throws Exception {
		try {
			CacheManager myManager = CacheManager.create();
			myManager.removeCache(cacheName);
			return true;
		} catch (Exception e) {
			throw new Exception("remove cache " + cacheName + " failed!!!");
		}
	}

	/**
	 * 
	 * 删除所有的EHCache容器
	 * 
	 * @param cacheName
	 *            容器名
	 * 
	 * @return 返回真
	 * 
	 * @throws Exception
	 *             失败抛出异常
	 */

	@SuppressWarnings("deprecation")
	public static boolean removeAllEhcache(String cacheName) throws Exception {
		try {
			CacheManager myManager = CacheManager.create();
			myManager.removalAll();
			return true;
		} catch (Exception e) {
			throw new Exception("remove cache " + cacheName + " failed!!!");
		}
	}

	/**
	 * 
	 * 删除EHCache容器中的元素
	 * 
	 * @param cacheName
	 *            容器名
	 * 
	 * @param key
	 *            键
	 * 
	 * @return 真
	 * 
	 * @throws Exception
	 *             失败抛出异常
	 */

	public static boolean removeElment(String cacheName, String key) throws Exception {
		try {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName);
			myCache.remove(key);
			return true;
		} catch (Exception e) {
			throw new Exception("remove cache " + cacheName + " failed!!!");
		}
	}

	/**
	 * 
	 * 删除指定容器中的所有元素
	 * 
	 * @param cacheName
	 *            容器名
	 * 
	 * @param key
	 *            键
	 * 
	 * @return 真
	 * 
	 * @throws Exception
	 *             失败抛出异常
	 */

	public static boolean removeAllElment(String cacheName, String key) throws Exception {
		try {
			CacheManager myManager = CacheManager.create();
			Cache myCache = myManager.getCache(cacheName);
			myCache.removeAll();
			return true;
		} catch (Exception e) {
			throw new Exception("remove cache " + cacheName + " failed!!!");
		}
	}

	/**
	 * 释放CacheManage
	 */

	public static void shutdown() {
		cacheManager.shutdown();
	}

	/**
	 * 移除cache
	 * 
	 * @param cacheName
	 */

	public static void removeCache(String cacheName) {
		checkCacheManager();
		cache = cacheManager.getCache(cacheName);
		if (null != cache) {
			cacheManager.removeCache(cacheName);
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午10:17:34 <br>
	 * Description : 移除cache中的key <br>
	 * @param key 键
	 */
	public static void remove(String key) {
		checkCache();
		cache.remove(key);
	}

	/**
	 * 移除所有cache
	 */

	@SuppressWarnings("deprecation")
	public static void removeAllCache() {
		checkCacheManager();
		cacheManager.removalAll();
	}

	/**
	 * 
	 * 移除所有Element
	 */

	public static void removeAllKey() {
		checkCache();
		cache.removeAll();
	}

	/**
	 * 
	 * 获取所有的cache名称
	 * 
	 * @return String
	 */

	public static String[] getAllCaches() {
		checkCacheManager();
		return cacheManager.getCacheNames();
	}

	/**
	 * 
	 * 获取Cache所有的Keys
	 * 
	 * @return List
	 */

	public static List<?> getKeys() {
		checkCache();
		return cache.getKeys();
	}

	/**
	 * 
	 * 检测cacheManager
	 */

	private static void checkCacheManager() {
		if (null == cacheManager) {
			throw new IllegalArgumentException("调用前请先初始化CacheManager值：EHCacheUtil.initCacheManager");
		}
	}

	private static void checkCache() {
		if (null == cache) {
			throw new IllegalArgumentException("调用前请先初始化Cache值：EHCacheUtil.initCache(参数)");
		}
	}

	public static void main(String[] arg) {
		// 初始化--必须
		//EHCacheUtil.initCacheManager();
		//EHCacheUtil.initCache("cache");
		EHCacheUtil.put("A", "AAAAA");
		EHCacheUtil.put("B", "BBBBB");
		EHCacheUtil.put("F", "FFFFF");
		System.out.println(EHCacheUtil.get("F"));
		List<?> keys = EHCacheUtil.getKeys();
		for(int i=0;i<keys.size();i++){
			System.out.println(keys.get(i));
		}
		EHCacheUtil.shutdown();
	}
}