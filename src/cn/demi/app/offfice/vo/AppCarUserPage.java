package cn.demi.app.offfice.vo;

/**
 * 车辆使用申请 列表
 * 
 * @author user
 *
 */
public class AppCarUserPage {
	private String id;
	private String no;
	private String destRynum;
	private String userName;
	private String date;
	private String startTime;
	private String endTime;
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getDestRynum() {
		return destRynum;
	}

	public void setDestRynum(String destRynum) {
		this.destRynum = destRynum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
