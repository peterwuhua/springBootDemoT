package cn.core.framework.constant;

import java.util.ArrayList;
import java.util.List;

public enum EnumBus {

	FS("放射性检测","FS"),YJ("应急检测", "YJ"),ZF("执法检测", "ZF"),RC("日常检测", "R"),HP("环评检测", "H"),WT("委托检测", "W"),YS("验收检测", "Y"),BY("比对验收", "BY"), BD("比对检测", "B"), PJ("评价检测", "P"), DQ("定期检测", "D"), JD("监督检测", "J"), ;

	public static final String SAMP_TYPE_HJ = "环境";
	public static final String SAMP_TYPE_ZW = "职业卫生";
	public static final String SAMP_TYPE_GW = "公共卫生";
	// 成员变量
	private String name;
	private String code;

	// 构造方法
	private EnumBus(String name, String code) {
		this.name = name;
		this.code = code;
	}

	// 根据名称回去编号
	public static String getCode(String name) {
		for (EnumBus c : EnumBus.values()) {
			if (c.getName().equals(name)) {
				return c.getCode();
			}
		}
		return null;
	}

	// 任务类型
	public static List<EnumBus> getBusList(String type) {
		List<EnumBus> busList = new ArrayList<EnumBus>();
		if (type.equals(SAMP_TYPE_HJ)) {
			busList.add(RC);
			busList.add(HP);
			busList.add(YS);
			busList.add(BD);
			busList.add(BY);
			busList.add(YJ);
			busList.add(ZF);
		} else if (type.equals(SAMP_TYPE_ZW)) {
			busList.add(WT);
			busList.add(DQ);
			busList.add(PJ);
			busList.add(JD);
			busList.add(FS);
		} else if (type.equals(SAMP_TYPE_GW)) {
			busList.add(WT);
			busList.add(DQ);
			busList.add(PJ);
			busList.add(JD);
		} else {
			busList.add(RC);
			busList.add(HP);
			busList.add(WT);
			busList.add(YS);
			busList.add(BD);
			busList.add(BY);
			busList.add(FS);
			busList.add(DQ);
			busList.add(PJ);
			busList.add(JD);
			busList.add(YJ);
			busList.add(ZF);
		}
		return busList;
	}

	// 简易任务类型
	public static List<EnumBus> getBusList4sim(String type) {
		List<EnumBus> busList = new ArrayList<EnumBus>();
			busList.add(RC);
			busList.add(HP);
			busList.add(WT);
			busList.add(YS);
			busList.add(BD);
			busList.add(BY);
			busList.add(FS);
			busList.add(DQ);
			busList.add(PJ);
			busList.add(JD);
			busList.add(YJ);
			busList.add(ZF);
		return busList;
	}
	
	// 全部类型
	public static List<EnumBus> getALLList() {
		List<EnumBus> busList = new ArrayList<EnumBus>();
		busList.add(RC);
		busList.add(HP);
		busList.add(WT);
		busList.add(YS);
		busList.add(BD);
		busList.add(BY);
		busList.add(FS);
		busList.add(PJ);
		busList.add(DQ);
		busList.add(YJ);
		busList.add(ZF);
		return busList;
	}

	// 全部监测场景
	public static List<String> getAllEnvs() {
		List<String> busList = new ArrayList<String>();
		busList.add(SAMP_TYPE_HJ);
		busList.add(SAMP_TYPE_ZW);
		busList.add(SAMP_TYPE_GW);
		return busList;
	}
	
	
	public static List<String> getBusNameList(String type) {
		List<String> busList = new ArrayList<String>();
		if (type.equals(SAMP_TYPE_HJ)) {
			busList.add(RC.getName());
			busList.add(HP.getName());
			busList.add(YS.getName());
			busList.add(BD.getName());
			busList.add(BY.getName());
			busList.add(YJ.getName());
			busList.add(ZF.getName());
		} else if (type.equals(SAMP_TYPE_ZW)) {
			busList.add(WT.getName());
			busList.add(DQ.getName());
			busList.add(PJ.getName());
			busList.add(JD.getName());	
			busList.add(FS.getName());
		} else if (type.equals(SAMP_TYPE_GW)) {
			busList.add(WT.getName());
			busList.add(DQ.getName());
			busList.add(PJ.getName());
			busList.add(JD.getName());
		} else {
			busList.add(RC.getName());
			busList.add(HP.getName());
			busList.add(WT.getName());
			busList.add(YS.getName());
			busList.add(BD.getName());
			busList.add(BY.getName());
			busList.add(FS.getName());
			busList.add(DQ.getName());
			busList.add(PJ.getName());
			busList.add(JD.getName());
			busList.add(YJ.getName());
			busList.add(ZF.getName());
		}
		return busList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
