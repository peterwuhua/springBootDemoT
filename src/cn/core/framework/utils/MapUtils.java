package cn.core.framework.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
/**
 * Create on : 2016年11月3日 上午9:40:01  <br>
 * Description :  Map集合工具类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public class MapUtils {
	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:40:20 <br>
	 * Description : 对象转Map 只转换convertProperty <br>
	 * @param obj obj
	 * @param convertProperty convertProperty
	 * @return Map
	 */
	public static Map<String, Object> convertBean0(Object obj,
			String... convertProperty) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (String property : convertProperty) {
			try {
				Object value = PropertyUtils.getProperty(obj, property);
				resultMap.put(property, value);
			} catch (Exception e) {
				resultMap.put(property, null);
			}
		}
		return resultMap;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:40:53 <br>
	 * Description : 对象转Map 只转换convertProperty <br>
	 * @param map map
	 * @param obj obj
	 * @param convertProperty convertProperty
	 * @return Object
	 */
	public static Object convertBean0(Map<String,Object> map,Object obj,String... convertProperty){
		if(null==convertProperty) return obj;
		if(null==obj) return obj;
		if(null==map) return obj;
		
		for(String property : convertProperty){
			Field field = null;
			try {
				field = obj.getClass().getDeclaredField(property);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			
			field.setAccessible(true);
			try {
				field.set(property, 1);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:42:22 <br>
	 * Description : 对象转Map 只转换convertProperty <br>
	 * @param map map
	 * @param obj obj
	 * @param ignoreProperty ignoreProperty
	 * @return Object
	 * @throws GlobalException 
	 */
	public static Object convertBean1(Map<String,Object> map,Object obj,String... ignoreProperty) throws GlobalException{
		if(null==obj) return obj;
		if(null==map) return obj;
		
		for(String property : map.keySet()){
			List<String> ignoreList = Arrays.asList(ignoreProperty);
			if(ignoreList.contains(property))
	            continue;
			
			Field field = null;
			try {
				field = obj.getClass().getDeclaredField(property);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			field.setAccessible(true);
			try {
				field.set(property, 1);
			} catch (IllegalArgumentException e) {
				throw new GlobalException(e);
			} catch (IllegalAccessException e) {
				throw new GlobalException(e);
			}
		}
		return obj;
	}
	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:42:46 <br>
	 * Description : 对象转Map 除外ignoreProperty全转换 <br>
	 * @param obj obj
	 * @param ignoreProperty ignoreProperty
	 * @return Map
	 * @throws GlobalException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> convertBean1(Object obj,
			String... ignoreProperty) throws GlobalException {
		Class type = obj.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(type);
		} catch (IntrospectionException e) {
			return returnMap;
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		List ignoreList = Arrays.asList(ignoreProperty);
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (ignoreList.contains(propertyName))
				continue;
			
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = null;
				try {
					result = readMethod.invoke(obj, new Object[0]);
				} catch (IllegalArgumentException e) {
					throw new GlobalException(e);
				} catch (IllegalAccessException e) {
					throw new GlobalException(e);
				} catch (InvocationTargetException e) {
					throw new GlobalException(e);
				}
				
				if(result instanceof  Po){
					Po p = (Po)result;
					result = p.toMap(p);
					returnMap.put(propertyName+"Vo", result);
				}else{
					returnMap.put(propertyName, result);
				}
			}
		}
		return returnMap;
	}
	
	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:42:46 <br>
	 * Description : 对象转Map 只转换property <br>
	 * @param obj obj
	 * @param ignoreProperty ignoreProperty
	 * @return Map
	 * @throws GlobalException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> convertBean2(Object obj,
			String... property) throws GlobalException {
		Class type = obj.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(type);
		} catch (IntrospectionException e) {
			return returnMap;
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		List propertyList = Arrays.asList(property);
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyList.contains(propertyName))
				continue;
			
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = null;
				try {
					result = readMethod.invoke(obj, new Object[0]);
				} catch (IllegalArgumentException e) {
					throw new GlobalException(e);
				} catch (IllegalAccessException e) {
					throw new GlobalException(e);
				} catch (InvocationTargetException e) {
					throw new GlobalException(e);
				}
				
				if(result instanceof Po){
					Po p = (Po)result;
					result = p.toMapProperty(p, p.getPropertyToMap());
					returnMap.put(propertyName+"Vo", result);
				}else{
					returnMap.put(propertyName, result);
				}
			}
		}
		return returnMap;
	}

}
