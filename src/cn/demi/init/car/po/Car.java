package cn.demi.init.car.po;

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
 * 车辆信息
 * 
 * @author QuJunLong
 */
@Entity(name = "init_car")
@Table(name = "init_car")
@Module(value = "init.car")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Car extends Po<Car> {

	public static final String ST_SY = "使用中";
	public static final String ST_WSY = "未使用";

	private static final long serialVersionUID = -8663429143515391863L;
	public String[] PROPERTY_TO_MAP = { "id", "sort", "code", "name", "rule", "userName", "status", "remark" };

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

	private String code;// 编号 车牌号
	private String name;// 名称
	private String rule;// 型号
	private String userId;// 保管人
	private String userName;
	private String status;// 状态
	private String buyDate;// 购买日期
	private String careDate;// 保养日期
	private String careCycle;// 保养周期
	private String oil;// 油
	private String remark;// 备注
	/**
	 * 车辆照片
	 */
	private String fileName;
	private String filePath;

	@Column(length = 32)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 64)
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@Column(length = 32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(length = 32)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length = 32)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Lob
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(length = 64)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(length = 128)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(length = 128)
	public String getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	@Column(length = 128)
	public String getCareDate() {
		return careDate;
	}

	public void setCareDate(String careDate) {
		this.careDate = careDate;
	}

	@Column(length = 128)
	public String getCareCycle() {
		return careCycle;
	}

	public void setCareCycle(String careCycle) {
		this.careCycle = careCycle;
	}

	@Column(length = 128)
	public String getOil() {
		return oil;
	}

	public void setOil(String oil) {
		this.oil = oil;
	}

	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Car.class, true, ActionType.JSP);
	}
}
