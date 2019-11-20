package cn.core.framework.utils.message;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.StrUtils;

/**
 * 
 * <strong>Title : Mail </strong>. <br>
 * <strong>Description : TODO�</strong> <br>
 * <strong>Create on : Oct 10, 2014 5:54:30 PM  </strong>. <br>
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
public class Mail{
	private static Logger log = Logger.getLogger(Mail.class);
	private static String host;
	private static String fromMail ;
	private static String pwd;
	private static String port;
	private static String auth;
	private static boolean isSend = false;
	static{
		try {
			host = (String)ApplicationUtils.getValue("config.msg.mail.host");
			fromMail = (String)ApplicationUtils.getValue("config.msg.mail.fromMail");
			pwd = (String)ApplicationUtils.getValue("config.msg.mail.pwd");
			port = (String)ApplicationUtils.getValue("config.msg.mail.port");
			auth = (String)ApplicationUtils.getValue("config.msg.mail.auth");
			isSend= "true".equalsIgnoreCase((String)ApplicationUtils.getValue("config.msg.mail.isSend"))? true:false;
		} catch (Exception e) {
			log.info("获取短信配置信息出错");
		}
	}
	
	String sendMail(String sends, String subject, String msg) {
		if(!isSend)
			return "发送失败,未开启发送功能";
		if(null==msg||"".equals(msg))
			return "内容为空";
		if(null==sends||"".equals(sends))
			return "收件人为空";
		
		String result = "";
		try {
			Properties props = new Properties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", auth);
			props.put("mail.smtp.port", port);
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(false);

			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(fromMail));
			sends = sends.replace("，", ",");
			List<String> tomails = StrUtils.splitList(sends, ',');
			InternetAddress[] internetAddresses = new InternetAddress[tomails.size()];
			if (null != tomails && tomails.size() > 0) {
				for (int i = 0; i < tomails.size(); i++) {
					internetAddresses[i] = new InternetAddress(tomails.get(i).trim());
				}
			}

			mimeMessage.setRecipients(Message.RecipientType.TO, internetAddresses);
			mimeMessage.setSentDate(new Date());
			mimeMessage.setSubject(subject);
			mimeMessage.setContent(msg, "text/html;charset=UTF-8");
			mimeMessage.saveChanges();// 存储邮件信息

			Transport transport = session.getTransport("smtp");
			transport.connect(host, fromMail, pwd);// 以smtp方式登录邮箱
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());// 发送邮件,其中第二个参数是所有
			transport.close();
			
			result = "发送成功";
		} catch (Exception e) {
			log.info(e);
			e.printStackTrace();
			result = "发送失败";
		}
		return result;
	
	}
	/**
	 * 
	 * @param sends To email
	 * @param subject  主题
	 * @param msg 
	 * @param files 附件
	 * @return
	 */
	public static String sendMail4Customer(String sends, String subject, String msg,File[] files) {

		String result = "";
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", true);
			Authenticator authenticator = new Authenticator()
			{
				@Override
				protected javax.mail.PasswordAuthentication getPasswordAuthentication()
				{
					return new javax.mail.PasswordAuthentication(fromMail, pwd);
				}
			};
			Session session = Session.getDefaultInstance(props, authenticator);
			session.setDebug(false);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, fromMail, pwd);// 以smtp方式登录邮箱
			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(fromMail));
			sends = sends.replace("，", ",");
			List<String> tomails = StrUtils.splitList(sends, ',');
			InternetAddress[] internetAddresses = new InternetAddress[tomails.size()];
			if (null != tomails && tomails.size() > 0) {
				for (int i = 0; i < tomails.size(); i++) {
					internetAddresses[i] = new InternetAddress(tomails.get(i).trim());
				}
			}

			mimeMessage.setRecipients(Message.RecipientType.TO, internetAddresses);
			mimeMessage.setSentDate(new Date());
			mimeMessage.setSubject(subject);
		    //Multipart mp = new MimeMultipart();
			Multipart mp = new MimeMultipart("related");// related意味着可以发送html格式的邮件 
			if(StrUtils.isNotBlankOrNull(msg)){
				/** *************************************************** 邮件正文*/  
				BodyPart bodyPart = new MimeBodyPart();// 正文   
				//String str = "<table width=40% border='1'><tr><td>我的邮箱</td><td><input type='text' id='myEmail' name='myEmail'></input></td></tr>    <tr><td>好友的邮箱</td><td><input type='text' id='freEmail' name='freEmail'></td></tr><tr><td colspan=2 align=center><input type='button' value='推荐给好友'></td></tr></table>";
				bodyPart.setDataHandler(new DataHandler(msg, "text/html;charset=utf-8"));// 网页格式   
				/** *************************************************** */ 
				 mp.addBodyPart(bodyPart); //邮件正文添加到信箱       
			}
	        /**邮件附件 */
            if (files != null && files.length > 0)
    		{// 判断是否有附件
    		 //存在附件就将附件全部加入到BodyPart
    			for (File file : files)
    			{
    				MimeBodyPart mbp = new MimeBodyPart();  
    				FileDataSource file_datasource = new FileDataSource(file);  
    		        DataHandler dh = new DataHandler(file_datasource);  
    		        mbp.setDataHandler(dh);  
    		        // 附件区别内嵌内容的一个特点是有文件名，为防止中文乱码要编码  
    		        mbp.setFileName(MimeUtility.encodeText(dh.getName())); 
    		        mp.addBodyPart(mbp);
    			}
    		}
            /** ********************* 附件图标   ****************************** */  
            /* MimeBodyPart imgBodyPart = new MimeBodyPart(); // 附件图标   
            byte[] bytes = readFile("C:/button.gif");   
            ByteArrayDataSource fileds = new ByteArrayDataSource(bytes,   
                    "application/octet-stream");   
            imgBodyPart.setDataHandler(new DataHandler(fileds));   
            imgBodyPart.setFileName("button.gif");   
            imgBodyPart.setHeader("Content-ID", "<IMG1></IMG1>");// 在html中使用该图片方法src="cid:IMG1"   
            mp.addBodyPart(imgBodyPart);   */
            
            mimeMessage.setContent(mp); //Multipart加入到信件  
			
			mimeMessage.saveChanges();// 存储邮件信息
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());// 发送邮件,其中第二个参数是所有
			transport.close();
			result = "true";
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result = "false";
		}
		return result;
	
	}
	/**
	 * 
	 * @param sends To email
	 * @param subject  主题
	 * @param msg 
	 * @param files 附件
	 * @return
	 */
	public static String sendMail(String host,String fromMail,String pwd,String post,String sends, String subject, String msg,File[] files) {

		String result = "";
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", true);
			Authenticator authenticator = new Authenticator()
			{
				@Override
				protected javax.mail.PasswordAuthentication getPasswordAuthentication()
				{
					return new javax.mail.PasswordAuthentication(fromMail, pwd);
				}
			};
			Session session = Session.getDefaultInstance(props, authenticator);
			session.setDebug(false);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, fromMail, pwd);// 以smtp方式登录邮箱
			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(fromMail));
			sends = sends.replace("，", ",");
			List<String> tomails = StrUtils.splitList(sends, ',');
			InternetAddress[] internetAddresses = new InternetAddress[tomails.size()];
			if (null != tomails && tomails.size() > 0) {
				for (int i = 0; i < tomails.size(); i++) {
					internetAddresses[i] = new InternetAddress(tomails.get(i).trim());
				}
			}

			mimeMessage.setRecipients(Message.RecipientType.TO, internetAddresses);
			mimeMessage.setSentDate(new Date());
			mimeMessage.setSubject(subject);
		    //Multipart mp = new MimeMultipart();
			Multipart mp = new MimeMultipart("related");// related意味着可以发送html格式的邮件 
			if(StrUtils.isNotBlankOrNull(msg)){
				/** *************************************************** 邮件正文*/  
				BodyPart bodyPart = new MimeBodyPart();// 正文   
				//String str = "<table width=40% border='1'><tr><td>我的邮箱</td><td><input type='text' id='myEmail' name='myEmail'></input></td></tr>    <tr><td>好友的邮箱</td><td><input type='text' id='freEmail' name='freEmail'></td></tr><tr><td colspan=2 align=center><input type='button' value='推荐给好友'></td></tr></table>";
				bodyPart.setDataHandler(new DataHandler(msg, "text/html;charset=utf-8"));// 网页格式   
				/** *************************************************** */ 
				 mp.addBodyPart(bodyPart); //邮件正文添加到信箱       
			}
	        /**邮件附件 */
            if (files != null && files.length > 0)
    		{// 判断是否有附件
    		 //存在附件就将附件全部加入到BodyPart
    			for (File file : files)
    			{
    				MimeBodyPart mbp = new MimeBodyPart();  
    				FileDataSource file_datasource = new FileDataSource(file);  
    		        DataHandler dh = new DataHandler(file_datasource);  
    		        mbp.setDataHandler(dh);  
    		        // 附件区别内嵌内容的一个特点是有文件名，为防止中文乱码要编码  
    		        mbp.setFileName(MimeUtility.encodeText(dh.getName())); 
    		        mp.addBodyPart(mbp);
    			}
    		}
            mimeMessage.setContent(mp); //Multipart加入到信件  
			
			mimeMessage.saveChanges();// 存储邮件信息
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());// 发送邮件,其中第二个参数是所有
			transport.close();
			result = "true";
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result = "false";
		}
		return result;
	
	}
	public static void main(String[] args) {
		Mail.sendMail4Customer("835912806@qq.com", "测试",  "<div><span></span>sssss</div>"+
				"<div>dddddddddd</div><hr style=\"width: 210px; height: 1px;\" color=\"#b5c4df\" size=\"1\" align=\"left\"  />"+"<div><span><div style=\"MARGIN: 10px; FONT-FAMILY: verdana; FONT-SIZE: 10pt\"><div style=\"font-family: 微软雅黑; font-size: 14px; line-height: 21px;\"><div style=\"position: static !important; margin: 10px; font-family: verdana; font-size: 10pt;\"><div style=\"font-family: 微软雅黑; font-size: 14px;\"><span style=\"background-color: rgba(0, 0, 0, 0);\">如有任何问题，请及时与我联系 </span></div><div style=\"font-family: 微软雅黑; font-size: 14px;\"><span style=\"background-color: rgba(0, 0, 0, 0);\">谢谢<br  /></span><div>张成平</div><div>paddy@labsoft.cn</div><span style=\"background-color: rgba(0, 0, 0, 0);\">西安凡特网络有限公司 <br  />西安市科技二路西安软件园秦风阁D301<br  />手机：</span><span style=\"background-color: window;\">18292407677</span></div></div></div><blockquote style=\"font-family: 微软雅黑; font-size: 14px; line-height: 21px; margin-top: 0px; margin-bottom: 0px; margin-left: 0.5em;\">&nbsp;</blockquote></div></span></div>",new File[]{new File("F:\\project\\eclipse\\WorkSpace\\base\\base\\src\\main\\java\\cn\\core\\framework\\utils\\message\\Mail.java")} );
	}
}
