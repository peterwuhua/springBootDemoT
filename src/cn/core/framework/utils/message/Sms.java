package cn.core.framework.utils.message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;

class Sms implements Runnable {
	private static Logger log = Logger.getLogger(Sms.class);
	private String msg;//短信内容
	private String phoneNums;//号码
	private static String sn;//账号
	private static String pwd;//密码
	private static boolean isSend = false;//是否发送
	private static String sign="";
	
	static{
		try {
			sign =(String)ApplicationUtils.getValue("config.msg.sms.sign");
			sn = (String)ApplicationUtils.getValue("config.msg.sms.sn");
			pwd = (String)ApplicationUtils.getValue("config.msg.sms.pwd");
			isSend= "true".equalsIgnoreCase((String)ApplicationUtils.getValue("config.msg.sms.isSend"))? true:false;
		} catch (Exception e) {
			log.info("获取短信配置信息出错");
		}
	}
	
	
	public Sms(){
		
	}
	
	public  String sendSms(String msg, String phoneNums) {
		if(!isSend)
			return "发送失败,未开启发送功能";
		if(null==msg||"".equals(msg))
			return "短信内容为空";
		if(null==phoneNums||"".equals(phoneNums))
			return "电话号码为空";
		//输入软件序列号和密码
		String content=null;
		try {
			content = URLEncoder.encode(msg + sign, "utf8");
		} catch (UnsupportedEncodingException e) {
			log.info(e);
		}
		Client client = new Client();
		String result_mt = client.smsSend(sn,pwd,phoneNums, content, "", "", "");
		String result="";
		if(result_mt.startsWith("-")||result_mt.equals(""))//发送短信，如果是以负号开头就是发送失败。
		{
			//result = "发送失败！返回值为："+result_mt+"请查看webservice返回值对照表";
			result = "发送失败！返回值为："+result_mt;
		}
		//输出返回标识，为小于19位的正数，String类型的。记录您发送的批次。
		else
		{
			result = "发送成功";
		}
		return result;
		
	}
	@Override
	public void run() {
		sendSms(msg,phoneNums);
	}
		
}
