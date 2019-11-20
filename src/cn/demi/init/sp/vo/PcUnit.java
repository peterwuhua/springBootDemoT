package cn.demi.init.sp.vo;

import java.util.ArrayList;
import java.util.List;

public enum PcUnit {

	CT("次/天"),CZ("次/周"),Y("月"),CY("次/月"),CDY("次/单月"),CN("次/年"),
	C("次"),T("天"),TC("天/次");
	private String name;
	// 构造方法  
    private PcUnit(String name) {  
        this.name = name;  
    }
    //例行 频次 单位集合
    public static List<PcUnit> getLxList() { 
    	List<PcUnit> list=new ArrayList<PcUnit>();
    	list.add(CT);
    	list.add(CZ);
    	list.add(Y);
    	list.add(CY);
    	list.add(CDY);
    	list.add(CN);
        return list;
    }
    //任务登记频次单位集合
    public static List<PcUnit> getRwList() { 
    	List<PcUnit> list=new ArrayList<PcUnit>();
    	list.add(C);
    	list.add(CT);
        return list;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
