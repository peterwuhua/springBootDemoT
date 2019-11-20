package cn.core.framework.utils;

import java.util.Locale;

import org.springframework.context.ApplicationContext;

public class SystemInstance {

	private static SystemInstance instance = null;

	private ApplicationContext appContext;

	private static boolean isMutiTenant;

	public SystemInstance() {
	}

	public static boolean isMutiTenant() {
		return isMutiTenant;
	}

	public static void setMutiTenant(boolean isMutiTenant) {
		SystemInstance.isMutiTenant = isMutiTenant;
	}

	public static SystemInstance getInstance() {
		if (instance == null) {
			instance = new SystemInstance();
		}
		return instance;
	}

	public ApplicationContext getAppContext() {
		return appContext;
	}

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	public Object getBean(String beanName) {
		return getAppContext().getBean(beanName);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:57:55 <br>
	 * Description : 得到实例化对象 <br>
	 * @param clazz 要获取的Bean对象的类实例
	 * @return Object
	 */
	public Object getBean(Class<?> clazz) {
		System.out.println(getAppContext());
		return getAppContext().getBean(clazz);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:58:51 <br>
	 * Description : get message by specific key <br>
	 * @param key key
	 * @return String
	 */
	public String getMessage(String key) {
		return appContext.getMessage(key, null, Locale.getDefault());
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:59:16 <br>
	 * Description : get message by specific key <br>
	 * @param key key
	 * @param args args
	 * @return String
	 */
	public String getMessage(String key, Object[] args) {
		return appContext.getMessage(key, args, Locale.getDefault());
	}

	/**
	 * get message by specific key
	 * 
	 * @param key
	 * @param locale
	 * @return String
	 */
	public String getMessage(String key, Locale locale) {
		return appContext.getMessage(key, null, locale);
	}

	/**
	 * get message by specific key
	 * 
	 * @param key
	 * @param args
	 * @param locale
	 * @return String
	 */
	public String getMessage(String key, Object[] args, Locale locale) {
		return appContext.getMessage(key, args, locale);
	}
}