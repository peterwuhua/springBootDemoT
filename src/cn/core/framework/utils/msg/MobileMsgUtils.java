package cn.core.framework.utils.msg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/*
功能:		web.cr6868.com HTTP接口 发送短信

说明:		http://web.cr6868.com/asmx/smsservice.aspx?name=登录名&pwd=接口密码&mobile=手机号码&content=内容&sign=签名&stime=发送时间&type=pt&extno=自定义扩展码
*/
public class MobileMsgUtils {
	public static final String URL="http://web.cr6868.com/asmx/smsservice.aspx?";
	public static final String NAME="15618898230";
	public static final String PWD="21F260D16DF72257F21A31280080";
	public static final String SIGN="浙大电镜中心【系统消息】";
	
	public static void main(String[] args) throws Exception {
 
		// 内容字符串
		System.out.println(doPost("13201803356",TempInst.getTemp("屈军龙","放大镜 SD12", "2017-12-26 12:25~14:30", "测试")));
	}

	/**
	 * 发送短信
	 * 
	 * @param name 用户名
	 * @param pwd 密码
	 * @param mobileString 电话号码字符串，中间用英文逗号间隔
	 * @param contextString  内容字符串
	 * @param sign 签名
	 * @param stime 追加发送时间，可为空，为空为及时发送
	 * @param extno  扩展码，必须为数字 可为空
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String name, String pwd, StringBuffer mobileString, StringBuffer contextString, String sign, String stime, StringBuffer extno) throws Exception {
		StringBuffer param = new StringBuffer();
		param.append("name=" + name);
		param.append("&pwd=" + pwd);
		param.append("&mobile=").append(mobileString);
		param.append("&content=").append(URLEncoder.encode(contextString.toString(), "UTF-8"));
		param.append("&stime=" + stime);
		param.append("&sign=").append(URLEncoder.encode(sign, "UTF-8"));
		param.append("&type=pt");
		param.append("&extno=").append(extno);

		URL localURL = new URL("http://web.cr6868.com/asmx/smsservice.aspx?");
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length", String.valueOf(param.length()));

		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		String resultBuffer = "";

		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);

			outputStreamWriter.write(param.toString());
			outputStreamWriter.flush();

			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
			}

			inputStream = httpURLConnection.getInputStream();
			resultBuffer = convertStreamToString(inputStream);

		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return resultBuffer;
	}
	/**
	 * 发送短信
	 * 
	 * @param name 用户名
	 * @param pwd 密码
	 * @param mobileString 电话号码字符串，中间用英文逗号间隔
	 * @param contextString  内容字符串
	 * @param sign 签名
	 * @param stime 追加发送时间，可为空，为空为及时发送
	 * @param extno  扩展码，必须为数字 可为空
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String mobile, StringBuffer contextString) throws Exception {
		StringBuffer param = new StringBuffer();
		param.append("name=" + NAME);
		param.append("&pwd=" + PWD);
		param.append("&mobile=").append(mobile);
		param.append("&content=").append(URLEncoder.encode(contextString.toString(), "UTF-8"));
		param.append("&stime=");
		param.append("&sign=").append(URLEncoder.encode(SIGN, "UTF-8"));
		param.append("&type=pt");
		param.append("&extno=");

		URL localURL = new URL(URL);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length", String.valueOf(param.length()));

		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		String resultBuffer = "";

		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);

			outputStreamWriter.write(param.toString());
			outputStreamWriter.flush();

			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
			}

			inputStream = httpURLConnection.getInputStream();
			resultBuffer = convertStreamToString(inputStream);

		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return resultBuffer;
	}
	/**
	 * 转换返回值类型为UTF-8格式.
	 * 
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		StringBuilder sb1 = new StringBuilder();
		byte[] bytes = new byte[4096];
		int size = 0;

		try {
			while ((size = is.read(bytes)) > 0) {
				String str = new String(bytes, 0, size, "UTF-8");
				sb1.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb1.toString();
	}
}
