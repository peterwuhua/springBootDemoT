package cn.demi.bus.test.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 
 * @ClassName: StantSolutMx
 * @Description:标准溶液明细
 * @author: 吴华
 * @date: 2019年7月3日 上午10:05:50
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Entity(name = "bus_stant_solut_mx")
@Table(name = "bus_stant_solut_mx")
@Module(value = "bus.stantSolutMx")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StantSolutMx extends Po<StantSolutMx> {

	private static final long serialVersionUID = 1L;

	public String[] PROPERTY_TO_MAP = {"id", "sort", "isDel",  "deme1","deme2", "v1", "v2", "v3", "v4", "v5", "avg1", "avg2", "jc1", "jc2", "jcall","pId" };
	public String[] IGNORE_PROPERTY_TO_PO = {  "createTime", "lastUpdTime", "version"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}
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
	
	@Column(length = 6)
	public String getDeme1() {
		return deme1;
	}

	public void setDeme1(String deme1) {
		this.deme1 = deme1;
	}
	@Column(length = 10)
	public String getDeme2() {
		return deme2;
	}
	public void setDeme2(String deme2) {
		this.deme2 = deme2;
	}



	@Column(length = 32)
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	@Column(length = 32)
	public String getV1() {
		return v1;
	}

	public void setV1(String v1) {
		this.v1 = v1;
	}

	@Column(length = 32)
	public String getV2() {
		return v2;
	}

	public void setV2(String v2) {
		this.v2 = v2;
	}

	@Column(length = 32)
	public String getV3() {
		return v3;
	}

	public void setV3(String v3) {
		this.v3 = v3;
	}

	@Column(length = 32)
	public String getV4() {
		return v4;
	}

	public void setV4(String v4) {
		this.v4 = v4;
	}

	@Column(length = 32)
	public String getV5() {
		return v5;
	}

	public void setV5(String v5) {
		this.v5 = v5;
	}

	@Column(length = 32)
	public String getAvg1() {
		return avg1;
	}

	public void setAvg1(String avg1) {
		this.avg1 = avg1;
	}

	@Column(length = 32)
	public String getAvg2() {
		return avg2;
	}

	public void setAvg2(String avg2) {
		this.avg2 = avg2;
	}

	@Column(length = 32)
	public String getJc1() {
		return jc1;
	}

	public void setJc1(String jc1) {
		this.jc1 = jc1;
	}

	@Column(length = 32)
	public String getJc2() {
		return jc2;
	}

	public void setJc2(String jc2) {
		this.jc2 = jc2;
	}

	@Column(length = 32)
	public String getJcall() {
		return jcall;
	}

	public void setJcall(String jcall) {
		this.jcall = jcall;
	}


	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(StantSolutMx.class, true, ActionType.JSP);
	}

}
