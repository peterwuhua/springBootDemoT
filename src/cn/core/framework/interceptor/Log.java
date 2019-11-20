package cn.core.framework.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
	/**
	 * 操作
	 */
	public Operation operation();

	/**
	 * 模块
	 */
	public String module();

	/**
	 * 内容
	 */
	public String content();

	enum Operation {
		ADD("新增"), DELETE("删除"), UPDATE("修改"),SAVE("保存"), SHOW("查看"), EXPT("导出");
		private String oper;
		
		private Operation(String oper) {
			this.oper = oper;
		}
		
		@Override
		public String toString() {
			return this.oper;
		}
	}

}
