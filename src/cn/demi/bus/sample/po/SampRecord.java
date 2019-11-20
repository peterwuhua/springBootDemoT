package cn.demi.bus.sample.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 样品测量记录
 * @author QuJunLong
 */
@Entity(name = "bus_samp_record")
@Table(name = "bus_samp_record")
@Module(value = "bus.sampRecord")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SampRecord extends Po<SampRecord>{
	  
 
	private static final long serialVersionUID = -4958636571133964375L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","demo1","demo2","demo3","demo4","demo5","demo6","demo7","demo8","demo9","demo10"
			,"demo11","demo12","demo13","demo14","demo15","demo16","demo17","demo18","demo19","demo20","demo21","demo22","demo23","demo24","demo25"
			,"demo26","demo27","demo28","demo29","demo30","v1","v2","v3","v4","v5","v6","v7","v8","v9","v10","v11","v12","avg1","avg2","avg3","pl"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	//设置 现场检测项目的值
	private String v1;
	private String v2;
	private String v3;
	private String v4;
	private String v5;
	private String v6;
	private String v7;
	private String v8;
	private String v9;
	private String v10;
	private String v11;
	private String v12;
	private String avg1;//均值1  电场强度均值
	private String avg2;//均值2 磁场强度均值
	private String avg3;//均值3
	
	private String pl;//频率
	
	private String demo1;//
	private String demo2;//
	private String demo3;//
	private String demo4;//
	private String demo5;//
	private String demo6;//
	private String demo7;//
	private String demo8;//
	private String demo9;//
	private String demo10;//
	private String demo11;//
	private String demo12;// 
	private String demo13;//
	private String demo14;//
	private String demo15;//
	private String demo16;
	private String demo17;//
	private String demo18;
	private String demo19;//
	private String demo20;
	private String demo21;
	private String demo22;
	private String demo23;
	private String demo24;
	private String demo25;
	private String demo26;
	private String demo27;
	private String demo28;
	private String demo29; 
	private String demo30; 
	 
	@Column(length = 32)
	public String getDemo29() {
		return demo29;
	}
	public void setDemo29(String demo29) {
		this.demo29 = demo29;
	}
	@Column(length = 32)
	public String getDemo30() {
		return demo30;
	}
	public void setDemo30(String demo30) {
		this.demo30 = demo30;
	}
	@Column(length = 32)
	public String getDemo26() {
		return demo26;
	}
	public void setDemo26(String demo26) {
		this.demo26 = demo26;
	}
	@Column(length = 32)
	public String getDemo27() {
		return demo27;
	}
	public void setDemo27(String demo27) {
		this.demo27 = demo27;
	}
	@Column(length = 32)
	public String getDemo28() {
		return demo28;
	}
	public void setDemo28(String demo28) {
		this.demo28 = demo28;
	}
	@Column(length = 32)
	public String getDemo1() {
		return demo1;
	}
	public void setDemo1(String demo1) {
		this.demo1 = demo1;
	}
	@Column(length = 32)
	public String getDemo2() {
		return demo2;
	}
	public void setDemo2(String demo2) {
		this.demo2 = demo2;
	}
	@Column(length = 32)
	public String getDemo3() {
		return demo3;
	}
	public void setDemo3(String demo3) {
		this.demo3 = demo3;
	}
	@Column(length = 32)
	public String getDemo4() {
		return demo4;
	}
	public void setDemo4(String demo4) {
		this.demo4 = demo4;
	}
	@Column(length = 32)
	public String getDemo5() {
		return demo5;
	}
	public void setDemo5(String demo5) {
		this.demo5 = demo5;
	}
	@Column(length = 32)
	public String getDemo6() {
		return demo6;
	}
	public void setDemo6(String demo6) {
		this.demo6 = demo6;
	}
	@Column(length = 32)
	public String getDemo7() {
		return demo7;
	}
	public void setDemo7(String demo7) {
		this.demo7 = demo7;
	}
	@Column(length = 32)
	public String getDemo8() {
		return demo8;
	}
	public void setDemo8(String demo8) {
		this.demo8 = demo8;
	}
	@Column(length = 32)
	public String getDemo9() {
		return demo9;
	}
	public void setDemo9(String demo9) {
		this.demo9 = demo9;
	}
	@Column(length = 32)
	public String getDemo10() {
		return demo10;
	}
	public void setDemo10(String demo10) {
		this.demo10 = demo10;
	}
	@Column(length = 32)
	public String getDemo11() {
		return demo11;
	}
	public void setDemo11(String demo11) {
		this.demo11 = demo11;
	}
	@Column(length = 32)
	public String getDemo12() {
		return demo12;
	}
	public void setDemo12(String demo12) {
		this.demo12 = demo12;
	}
	@Column(length = 32)
	public String getDemo13() {
		return demo13;
	}
	public void setDemo13(String demo13) {
		this.demo13 = demo13;
	}
	@Column(length = 32)
	public String getDemo14() {
		return demo14;
	}
	public void setDemo14(String demo14) {
		this.demo14 = demo14;
	}
	@Column(length = 32)
	public String getDemo15() {
		return demo15;
	}
	public void setDemo15(String demo15) {
		this.demo15 = demo15;
	}
	@Column(length = 32)
	public String getDemo16() {
		return demo16;
	}
	public void setDemo16(String demo16) {
		this.demo16 = demo16;
	}
	@Column(length = 32)
	public String getDemo17() {
		return demo17;
	}
	public void setDemo17(String demo17) {
		this.demo17 = demo17;
	}
	@Column(length = 32)
	public String getDemo18() {
		return demo18;
	}
	public void setDemo18(String demo18) {
		this.demo18 = demo18;
	}
	@Column(length = 32)
	public String getDemo19() {
		return demo19;
	}
	public void setDemo19(String demo19) {
		this.demo19 = demo19;
	}
	@Column(length = 32)
	public String getDemo20() {
		return demo20;
	}
	public void setDemo20(String demo20) {
		this.demo20 = demo20;
	}
	@Column(length = 32)
	public String getDemo21() {
		return demo21;
	}
	public void setDemo21(String demo21) {
		this.demo21 = demo21;
	}
	@Column(length = 32)
	public String getDemo22() {
		return demo22;
	}
	public void setDemo22(String demo22) {
		this.demo22 = demo22;
	}
	@Column(length = 32)
	public String getDemo23() {
		return demo23;
	}
	public void setDemo23(String demo23) {
		this.demo23 = demo23;
	}
	@Column(length = 32)
	public String getDemo24() {
		return demo24;
	}
	public void setDemo24(String demo24) {
		this.demo24 = demo24;
	}
	@Column(length = 32)
	public String getDemo25() {
		return demo25;
	}
	public void setDemo25(String demo25) {
		this.demo25 = demo25;
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
	public String getV6() {
		return v6;
	}
	public void setV6(String v6) {
		this.v6 = v6;
	}
	@Column(length = 32)
	public String getV7() {
		return v7;
	}
	public void setV7(String v7) {
		this.v7 = v7;
	}
	@Column(length = 32)
	public String getV8() {
		return v8;
	}
	public void setV8(String v8) {
		this.v8 = v8;
	}
	@Column(length = 32)
	public String getV9() {
		return v9;
	}
	public void setV9(String v9) {
		this.v9 = v9;
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
	public String getAvg3() {
		return avg3;
	}
	public void setAvg3(String avg3) {
		this.avg3 = avg3;
	}
	@Column(length = 32)
	public String getV10() {
		return v10;
	}
	public void setV10(String v10) {
		this.v10 = v10;
	}
	@Column(length = 32)
	public String getV11() {
		return v11;
	}
	public void setV11(String v11) {
		this.v11 = v11;
	}
	@Column(length = 32)
	public String getV12() {
		return v12;
	}
	public void setV12(String v12) {
		this.v12 = v12;
	}
	@Column(length = 32)
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	
}
