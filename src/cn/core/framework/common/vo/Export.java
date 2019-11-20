package cn.core.framework.common.vo;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Export {
	/**
	 * 列名
	 */
	public String label();
	
	/**
	 * 是否为对象
	 */
	public boolean isObject() default false;

	/**
	 * 序号
	 */
	public int sort() default 0;

}
