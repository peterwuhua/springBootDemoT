package cn.core.framework.common.page;
/**
 * <strong>Create on : 2016年11月2日 上午11:17:22 </strong> <br>
 * <strong>Description : 排序条件 </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public class OrderCondition {

	public static final String ORDER_DESC = "DESC";
	public static final String ORDER_ASC = "ASC";
	/**
	 * 排序字段
	 */
	private String orderColumn;
	/**
	 * 排序方式  DESC/ASC
	 */
	private String order;
	/**
	 * <strong>Create on : 2016年11月2日 上午11:17:57 </strong> <br>
	 * <strong>Description : 构造器</strong> <br>
	 */
	public OrderCondition() {
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午11:18:15 </strong> <br>
	 * <strong>Description : 构造器</strong> <br>
	 * @param orderColumn 排序字段
	 * @param order 排序方式
	 */
	public OrderCondition(String orderColumn, String order) {
		this.orderColumn = orderColumn;
		this.order = order;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}