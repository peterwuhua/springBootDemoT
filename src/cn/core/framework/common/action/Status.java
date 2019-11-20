package cn.core.framework.common.action;

import java.io.Serializable;
/**
 * <strong>Create on : 2016年11月2日 上午10:47:24 </strong> <br>
 * <strong>Description : 操作状态回执</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public class Status implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_ERROR = "error";
	/**
	 * 操作状态
	 */
	private String status = STATUS_ERROR;
	/**
	 * 操作回执码
	 */
	private String code;
	/**
	 * 回执消息
	 */
	private String message;
	/**
	 * 回传值
	 */
	private Object object;
	
	public Status() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * <strong>Create on : 2016年11月27日 上午10:08:00 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @version <strong> v 1.0.0 </strong> <br>
	 * @author <strong>Carson Liu</strong><br>
	 * @param message 回执消息
	 * @param status 回执状态
	 */
	public Status(String message,String status) {
		this.message = message;
		this.status = status;
	}
	
	/**
	 * <strong>Create on : 2016年11月27日 上午10:08:00 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @version <strong> v 1.0.0 </strong> <br>
	 * @author <strong>Carson Liu</strong><br>
	 * @param status 回执状态
	 * @param object 回执值
	 */
	public Status(String status,Object object) {
		this.status = status;
		this.object = object;
	}
	
	/**
	 * <strong>Create on : 2016年11月27日 上午10:10:19 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @version <strong> v 1.0.0 </strong> <br>
	 * @author <strong>Carson Liu</strong><br>
	 * @param message 回执信息
	 * @param status 回执状态
	 * @param object 回执值
	 */
	public Status(String message,String status,Object object) {
		this.message = message;
		this.status = status;
		this.object = object;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	
}