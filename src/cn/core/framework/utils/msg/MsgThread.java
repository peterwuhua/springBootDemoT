package cn.core.framework.utils.msg;

import org.apache.log4j.Logger;

public class MsgThread extends Thread{

	private String mobile;
	private String user;
	private String inst;
	private String times;
	private String reason;
	
	public MsgThread(String mobile, String user, String inst, String times, String reason) {
		this.mobile = mobile;
		this.user = user;
		this.inst = inst;
		this.times = times;
		this.reason = reason;
	}
	@Override
	public void run() {
		try {
			String result = MobileMsgUtils.doPost(mobile,TempInst.getTemp(user,inst,times,reason));
			System.out.println(result);
		} catch (Exception e) {
			Logger.getLogger(MsgThread.class).error("短信发送异常",e);
		}
	}
}
