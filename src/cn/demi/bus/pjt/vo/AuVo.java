package cn.demi.bus.pjt.vo;

import java.util.Comparator;

public class AuVo implements Comparator<AuVo>{

	private String id;
	private String no;
	private String name;
	private String mobile;
	private int num;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Override
	public int compare(AuVo o1, AuVo o2) {
		if(o1.getNum() > o2.getNum()){
	            return 1;
        }else{
            return -1;
        }
	}
	public AuVo(String id, String no, String name, String mobile, int num) {
		super();
		this.id = id;
		this.no = no;
		this.name = name;
		this.mobile = mobile;
		this.num = num;
	}
	public AuVo() {
	}
	
}
