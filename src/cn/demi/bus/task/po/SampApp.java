package cn.demi.bus.task.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 采样设备
 * @author QuJunLong
 *
 */
@Entity(name = "bus_samp_app")
@Table(name = "bus_samp_app")
@Module(value = "bus.sampApp")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SampApp extends Po<SampApp>{
	
	private static final long serialVersionUID = 7998153638582744154L;
	public String[] PROPERTY_TO_MAP = {"id","sort"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 业务Id
	 */
	private String busId;
	/**
	 * 设备信息
	 */
	private String appId;
	private String appName;
	private String appCode;
	/**
	 * 备注
	 */
	private String remark;
 
	@Column(length = 32)
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	@Column(length = 64)
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	@Column(length = 64)
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(SampApp.class, true, ActionType.JSP);
	}
}
