package cn.demi.bus.test.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.bus.sample.po.Sampling;

/**
 * 样品测试结果表
 * @author QuJunLong
 */
@Entity(name = "bus_test_result")
@Table(name = "bus_test_result")
@Module(value = "bus.testResult")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TestResult extends Po<TestResult>{
	
	private static final long serialVersionUID = 281643730922913971L;
	public String[] PROPERTY_TO_MAP = {"id","sort","createUser","isDel","samp","it","value","value2","sl"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 *样品信息
	 */
	private Sampling samp;
	private TestItem it;
	/**
	 * 该项目里 对应样品的检查结果
	 * 检测结果
	 */
	private String value; //实测值
	private String value2;//仪器值(部分业务需要)
	private String sl;//速率（气）
	
	@ManyToOne
	@JoinColumn(name = "samp_id")
	public Sampling getSamp() {
		return samp;
	}
	public void setSamp(Sampling samp) {
		this.samp = samp;
	}
	@ManyToOne
	@JoinColumn(name = "it_id")
	public TestItem getIt() {
		return it;
	}
	public void setIt(TestItem it) {
		this.it = it;
	}
	@Column(length = 32)
	public String getSl() {
		return sl;
	}
	public void setSl(String sl) {
		this.sl = sl;
	}
	@Column(length = 128)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Column(length = 128)
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(TestResult.class, true, ActionType.JSP);
	}
}
