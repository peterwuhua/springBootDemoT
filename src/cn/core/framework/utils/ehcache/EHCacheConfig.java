package cn.core.framework.utils.ehcache;
/**
 * Create on : 2016年11月3日 上午10:18:09  <br>
 * Description : EHCacheConfig <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public class EHCacheConfig {

	/**
	 * 元素最大数量
	 */

	public static int MAXELEMENTSINMEMORY = 50000;

	/**
	 * 
	 * 是否把溢出数据持久化到硬盘
	 */

	public static boolean OVERFLOWTODISK = true;

	/**
	 * 
	 * 是否会死亡
	 */

	public static boolean ETERNAL = false;

	/**
	 * 
	 * 缓存的间歇时间
	 */

	public static int TIMETOIDLESECONDS = 600;

	/**
	 * 
	 * 存活时间(默认一天)
	 */

	public static int TIMETOlIVESECONDS = 86400;

	/**
	 * 
	 * 需要持久化到硬盘否
	 */

	public static boolean DISKPERSISTENT = false;

	/**
	 * 
	 * 内存存取策略
	 */

	public static String MEMORYSTOREEVICTIONPOLICY = "LFU";

}