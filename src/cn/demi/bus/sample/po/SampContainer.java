package cn.demi.bus.sample.po;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
/**
 * 采样容器
 * @author QuJunLong
 */
@Entity(name = "bus_samp_container")
@Table(name = "bus_samp_container")
@Module(value = "bus.sampContainer")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SampContainer extends Po<SampContainer>{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","no","taskId","sampId","container","code","num"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 任务编号
	 */
	private String no;
	/**
	 * 任务Id
	 */
	private String taskId;
	/**
	 * 点位Id
	 */
	private String pointId;
	/**
	 * 样品Id
	 * 自送样需要
	 */
	private String sampId;
	/**
	 * 容器
	 */
	private String containerId;
	/**
	 * 容器
	 */
	private String container;
	/**
	 * 数量
	 */
	private int num;
	/**
	 * 备注
	 */
	private String remark;
	
	@Column(length = 32)
	public String getSampId() {
		return sampId;
	}

	public void setSampId(String sampId) {
		this.sampId = sampId;
	}
	@Column(length = 64)
	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}
	@Column(length = 8)
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	@Column(length = 32)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	@Column(length = 32)
	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	@Lob
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	@Column(length = 32)
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	
}
