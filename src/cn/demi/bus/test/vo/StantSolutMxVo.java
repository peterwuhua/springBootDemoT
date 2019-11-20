package cn.demi.bus.test.vo;

import cn.core.framework.common.vo.Vo;

public class StantSolutMxVo extends Vo<StantSolutMxVo> {
	private String deme1;// 序号
	private String deme2;// 基准物质或溶液取样量
	/**
	 * 消耗量
	 */
	private String v1;// 终读
	private String v2;// 始读
	private String v3;// 消耗量
	private String v4;// 减空白

	private String v5;// 溶液浓度值
	private String avg1;// 均值1
	private String avg2;// 均值2
	private String jc1;// 极差相对值1(初标)
	private String jc2;// 极差相对值1(复标)
	private String jcall;// 极差相对值2
	private String pId;//主表id
	
	public String getDeme2() {
		return deme2;
	}
	public void setDeme2(String deme2) {
		this.deme2 = deme2;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getDeme1() {
		return deme1;
	}
	public void setDeme1(String deme1) {
		this.deme1 = deme1;
	}
	public String getV1() {
		return v1;
	}
	public void setV1(String v1) {
		this.v1 = v1;
	}
	public String getV2() {
		return v2;
	}
	public void setV2(String v2) {
		this.v2 = v2;
	}
	public String getV3() {
		return v3;
	}
	public void setV3(String v3) {
		this.v3 = v3;
	}
	public String getV4() {
		return v4;
	}
	public void setV4(String v4) {
		this.v4 = v4;
	}
	public String getV5() {
		return v5;
	}
	public void setV5(String v5) {
		this.v5 = v5;
	}
	public String getAvg1() {
		return avg1;
	}
	public void setAvg1(String avg1) {
		this.avg1 = avg1;
	}
	public String getAvg2() {
		return avg2;
	}
	public void setAvg2(String avg2) {
		this.avg2 = avg2;
	}
	public String getJc1() {
		return jc1;
	}
	public void setJc1(String jc1) {
		this.jc1 = jc1;
	}
	public String getJc2() {
		return jc2;
	}
	public void setJc2(String jc2) {
		this.jc2 = jc2;
	}
	public String getJcall() {
		return jcall;
	}
	public void setJcall(String jcall) {
		this.jcall = jcall;
	}
	
}

