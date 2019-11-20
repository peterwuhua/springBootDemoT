package cn.core.framework.common.page;

/**
 * <strong>Create on : 2016年11月2日 上午11:16:16 </strong> <br>
 * <strong>Description : GridVo</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public class GridVo {
	/**
	 * 当前页
	 */
	private int page;
	/**
	 * 总条数
	 */
	private int total;
	/**
	 * 记录数
	 */
	private int records;
	/**
	 * 每页显示条数
	 */
	private int rows; 
	/**
	 * 排序规则
	 */
	private String sidx;
	/**
	 * 排序字段
	 */
	private String sord;
	/**
	 * 数据
	 */
	private Object datas;
	/**
	 * 数据
	 */
	private String filters;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	
	public Object getDatas() {
		return datas;
	}
	public void setDatas(Object datas) {
		this.datas = datas;
	}
	
	public String getFilters() {
		//filters = "{\"groupOp\":\"AND\",\"rules\":[{\"field\":\"no\",\"op\":\"cn\",\"data\":\"sd\"},{\"field\":\"name\",\"op\":\"cn\",\"data\":\"12\"}]}";
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}
	
	public static void main(String[] args) {
		
	}
}
	


	
