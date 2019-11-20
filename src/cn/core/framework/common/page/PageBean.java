package cn.core.framework.common.page;

import java.io.Serializable;

/**
 * <strong>Create on : 2016年11月2日 上午11:18:35 </strong> <br>
 * <strong>Description :PageBean </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public class PageBean implements Serializable{
	/** Create on : Paddy Zhang 2017年2月4日 下午12:09:51  <br>
	 * Description :  <br>
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 记录总行数
	 */
	private int totalRows;
	/**
	 * 设置一页显示的条数
	 */
	private int pageSize;
	/**
	 * 当前页面
	 */
	private int currentPage;
	/**
	 *总页数 
	 */
	private int totalPages;
	/**
	 * 当前页码开始的行数
	 */
	private int startRow;
	/**
	 * 当前页码开始的行数
	 */
	public final static int ALL_PAGE = -1;
	public PageBean(int totalRows, int pageSize) {
		this.pageSize = pageSize;
		this.totalRows = totalRows;
		totalPages = totalRows / pageSize;
		if (totalRows % pageSize > 0) {
			totalPages++;
		}
		currentPage = 1;
		startRow = 0;
	}

	// 刷新page对象
	public void refresh(int _currentPage) {
		currentPage = _currentPage;
		if (currentPage > totalPages) {
			// last();
			currentPage = totalPages;
		}
		if (currentPage < 1) {
			// first();
			currentPage = 0;
		}
		if (currentPage == 0)
			currentPage = 1;
		startRow = (currentPage - 1) * pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

}
