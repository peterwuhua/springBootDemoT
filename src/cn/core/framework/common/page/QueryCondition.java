package cn.core.framework.common.page;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * <strong>Create on : 2016年11月2日 下午12:32:52 </strong> <br>
 * <strong>Description : 查询条件</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public class QueryCondition implements Serializable {
	/** Create on : Paddy Zhang 2017年2月4日 下午12:15:09  <br>
	 * Description :  <br>
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 * 等于
	*/
	public static final String EQ = "=";
	/** 
	 * 不等于
	 */
	public static final String NE = "!=";
	/**
	 *  小于
	*/
	public static final String LT = "<";
	/** 
	 * 大于
	 */
	public static final String GT = ">";
	/**
	 * 小于等于
	 */
	public static final String LE = "<=";
	/**
	 * 大于等于
	 */
	public static final String GE = ">=";
	/**
	 * 左匹配
	 * %XXX
	 */
	public static final String LK = "lk";
	
	/**
	 * 右匹配
	 * XXX%
	 */
	public static final String RK = "rk";
	
	/**
	 * 左右匹配
	 * %XXX%
	 */
	public static final String AK = "ak";
	/** 
	 * 自定义jpql语句 
	 * AND subhql
	 */
	public static final String CUSTOM = "custom";
	/** 
	 * 属性名 
	 */
	private String field;
	/**
	 *  操作符
	 */
	private String operator;
	/**
	 *  值
	 */
	private Object value;
	/** 
	 * 自定义jpql语句
	 */
	private String customJPQL;

	/**
	 * 传入自定义语句
	 * @param customJPQL
	 */
	public QueryCondition(String customJPQL) {
		this.customJPQL = customJPQL;
		this.operator = CUSTOM;
	}

	/**
	 * <strong>创建信息: Carson Liu 2016年4月5日 下午5:09:54 </strong> <br>
	 * <strong>概要 : </strong> <br>
	 * <strong>修改记录 : (修改人： ;时间： ;目的: ;)</strong> <br>
	 */
	public QueryCondition() {
	}

	/**
	 * @param field 属性名
	 * @param operator 操作符
	 * @param value
	 *            值 如果属性是日期类型,需将字符串格式为日期 如new
	 *            SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
	 *            ).parse("2012-03-23 10:22:22")
	 */
	public QueryCondition(String field, String operator, Object value) {
		this.field = field;
		this.operator = operator;
		if(value instanceof String)
			value = StringUtils.trim((String)value);
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOperator() {
		return operator;
	}
	
	public String getOperator(Object op) {
		if("cn".equals(op)){
			return AK;
		}else if("le".equals(op)){
			return LE;
		}else if("lt".equals(op)){
			return LT;
		}else if("gt".equals(op)){
			return GT;
		}else if("ge".equals(op)){
			return GE;
		}else if("eq".equals(op)){
			return EQ;
		}else if("ne".equals(op)){
			return NE;
		}else if("ak".equals(op)){
			return AK;
		}else if("rk".equals(op)){
			return RK;
		}else if("lk".equals(op)){
			return LK;
		}
		return AK;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getCustomJPQL() {
		return customJPQL;
	}

	public void setCustomJPQL(String customJPQL) {
		this.customJPQL = customJPQL;
	}
}