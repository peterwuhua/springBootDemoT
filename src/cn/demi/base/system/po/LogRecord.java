package cn.demi.base.system.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name = "sys_log_record")
@Table(name = "sys_log_record")
@Module(value = "sys.logRecord")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LogRecord extends Po<LogRecord>{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","ctime","ip","user","module","opt","content","method","params"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

	//时间
	private String ctime;
	//IP地址
	private String ip;
	//操作人
	private String user;
	//模块
	private String module;
	//操作
	private String opt;
	//描述
	private String content;
	//操作方法
	private String method;
	//参数
	private String params;
	@Column(length = 20)
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	@Column(length = 32)
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Column(length = 64)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@Column(length = 32)
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	@Column(length = 32)
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	@Column(length = 128)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length = 64)
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	@Column(length = 4000)
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * 代码生成
	 * @param args
	 */
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(LogRecord.class, false, ActionType.JSP);
	}
}
