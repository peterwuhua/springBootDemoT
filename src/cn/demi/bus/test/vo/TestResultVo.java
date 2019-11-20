package cn.demi.bus.test.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.bus.sample.vo.SamplingVo;

public class TestResultVo extends Vo<TestResultVo> {
	/**
	 *样品信息
	 */
	private SamplingVo sampVo;
	private TestItemVo itVo;
	/**
	 * 该项目里 对应样品的检查结果
	 * 检测结果
	 */
	private String value; //实测值
	private String value2;//仪器值(部分业务需要)
	private String sl;//速率（气）
	
	private String oper;//1可删除
	
	public SamplingVo getSampVo() {
		return sampVo;
	}
	public void setSampVo(SamplingVo sampVo) {
		this.sampVo = sampVo;
	}
	public TestItemVo getItVo() {
		return itVo;
	}
	public void setItVo(TestItemVo itVo) {
		this.itVo = itVo;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getSl() {
		return sl;
	}
	public void setSl(String sl) {
		this.sl = sl;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	
}

