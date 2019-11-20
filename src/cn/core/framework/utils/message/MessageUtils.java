package cn.core.framework.utils.message;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * <strong>Title : SmsUtils </strong>. <br>
 * <strong>Description : TODO�</strong> <br>
 * <strong>Create on : Oct 10, 2014 2:07:46 PM  </strong>. <br>
 * <p>
 * <strong>Copyright (C) Labsoft Pte,Ltd.</strong> <br>
 * </p>
 *
 * @author Carson Liu <br>
 * @version <strong>LabOSM1 v 1.0.0</strong> <br>
 *          <br>
 *          <strong>修改历史: .</strong> <br>
 *          修改人� 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class MessageUtils {
	
	/**
	 * 发送短信
	 * @Title:  
	 * @Description: TODO
	 * @param @param msg 短信内容
	 * @param @param toTels 多个以“,”分隔 
	 * @return 返回类型 
	 * @throws
	 */
	public static String sendSms(String msg,String toTels ) {
		String result = new Sms().sendSms(msg, toTels);
		return result;
	}
	/**
	 * 发送 email 
	 * @Title:  
	 * @Description: TODO
	 * @param @param msg 短信内容
	 * @param @param subject 主题
	 * @param @param toMails 多个以“,”分隔 
	 * @return 返回类型 
	 * @throws
	 */
	public static String sendMail(String toMails, String subject, String msg) {
		String result = new Mail().sendMail(toMails, subject,msg);
		return result;
	}
	/**
	 * 发送 email 
	 * @Title:  
	 * @Description: TODO
	 * @param @param msg 短信内容
	 * @param @param subject 主题
	 * @param @param toMails 多个以“,”分隔 
	 * @return 返回类型 
	 * @throws
	 */
	public static String sendMail(String host,String fromMail,String pwd,String post,String sends, String subject, String msg,MultipartFile[] file) {
		File[] files=null;
		if(null!=file) {
			files=new File[file.length];
			for (int i=0;i<file.length;i++) {
				files[i]=(File)file[i];
			}
		}
		String result =Mail.sendMail(host, fromMail, pwd, post, sends, subject, msg, files);
		return result;
	}
	/**
	 * Create on : Paddy Zhang 2017年4月11日 下午2:28:07 <br>
	 * Description :  <br>
	 * @param sends 收件人集合
	 * @param subject 主题
	 * @param msg 发送内容
	 * @param files 附件集合
	 * @return
	 */
	public static String sendMail4Customer(String sends, String subject, String msg,MultipartFile[] file) {
		File[] files=null;
		if(null!=file) {
			files=new File[file.length];
			for (int i=0;i<file.length;i++) {
				files[i]=(File)file[i];
			}
		}
		
		String result = Mail.sendMail4Customer(sends,subject,msg,files);
		return result;
	}
	
	public static void main(String[] args) {
		String result = MessageUtils.sendSms("测试内容","18292407677");
		System.out.println(result);
	}
	
	
}
