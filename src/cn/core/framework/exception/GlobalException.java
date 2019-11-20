package cn.core.framework.exception;
/**
 * <strong>Create on : 2016年11月2日 下午1:03:15 </strong> <br>
 * <strong>Description : </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong></strong><br>
 */
@SuppressWarnings("serial")
public class GlobalException extends Exception {
	private String message = "";
	private String code = "";
	
	public GlobalException() {
		super();
	}

	public GlobalException(String message) {
		this.message = message;
	}

	public GlobalException(String code, String message, Throwable cause) {
		super(null, cause);
		this.code = code;
		this.message = message;
	}

	public GlobalException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public GlobalException(String message, Throwable cause) {
		super(null, cause);
		this.message = message;
	}

	public GlobalException(Throwable cause) {
		super(cause);
	}

	public GlobalException(Exception e) {
		super(e);
	}

	public GlobalException(String message, Exception e) {
		super(null, e);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public String getCode() {
		return code;
	}

}
