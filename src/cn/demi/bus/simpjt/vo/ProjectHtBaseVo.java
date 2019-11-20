package cn.demi.bus.simpjt.vo;
/**
 * 
 * @ClassName:  ProjectHtBaseVo   
 * @Description:合同基础vo  
 * @author: 吴华 
 * @date:   2019年2月22日 上午10:46:40       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
public class ProjectHtBaseVo {

	/**
	 * 合同起始年月
	 */
	private String beginyear;
	private String beginmonth;
	private String beginday;

	/**
	 * 合同截止年月日(预计完成日期)
	 */
	private String endyear;
	private String endmonth;
	private String endday;
	
	/**
	 * 合同签订日期
	 */
	private String qdDate;
	
	
	private String ydfs1; //双方约定方式1
	private String ydfs2; //双方约定方式2
	private String ydfs3; //双方约定方式3
	private String ydfs4; //双方约定方式4
	
	
	public String getQdDate() {
		return qdDate;
	}
	public void setQdDate(String qdDate) {
		this.qdDate = qdDate;
	}
	private String gz; //存放公章图片路径(受托人)
	
	public String getYdfs3() {
		return ydfs3;
	}
	public void setYdfs3(String ydfs3) {
		this.ydfs3 = ydfs3;
	}
	public String getYdfs4() {
		return ydfs4;
	}
	public void setYdfs4(String ydfs4) {
		this.ydfs4 = ydfs4;
	}
	public String getBeginyear() {
		return beginyear;
	}
	public void setBeginyear(String beginyear) {
		this.beginyear = beginyear;
	}
	public String getBeginmonth() {
		return beginmonth;
	}
	public void setBeginmonth(String beginmonth) {
		this.beginmonth = beginmonth;
	}
	public String getBeginday() {
		return beginday;
	}
	public void setBeginday(String beginday) {
		this.beginday = beginday;
	}
	public String getEndyear() {
		return endyear;
	}
	public void setEndyear(String endyear) {
		this.endyear = endyear;
	}
	public String getEndmonth() {
		return endmonth;
	}
	public void setEndmonth(String endmonth) {
		this.endmonth = endmonth;
	}
	public String getEndday() {
		return endday;
	}
	public void setEndday(String endday) {
		this.endday = endday;
	}
	public String getGz() {
		return gz;
	}
	public void setGz(String gz) {
		this.gz = gz;
	}
	public String getYdfs1() {
		return ydfs1;
	}
	public void setYdfs1(String ydfs1) {
		this.ydfs1 = ydfs1;
	}
	public String getYdfs2() {
		return ydfs2;
	}
	public void setYdfs2(String ydfs2) {
		this.ydfs2 = ydfs2;
	}
	
	 
	
	
}
