package cn.demi.cus.customer.unit;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.core.framework.utils.ApplicationUtils;


/**
 * 客户服务模块数据对接接口工具类
 * 
 */
public class HttpInterfaceUtil {
	
	private static Logger log = LoggerFactory.getLogger(HttpInterfaceUtil.class);
	
	public static final String TOKEN = ApplicationUtils.getValue("config.css.lims.token").toString(); //接口认证token
	public static final String SERVER_URL = ApplicationUtils.getValue("config.css.lims.url").toString(); //服务器地址
	public static final String SYNCHRO = ApplicationUtils.getValue("config.css.lims.synchro").toString(); //是否同步
	public static final String REPORT_FILE_NAME = "report_"+System.currentTimeMillis(); //检测报告名称
	public static final String ANAREPORT_FILE_NAME = "anaReport_"+System.currentTimeMillis(); //检验报告名称
	
	public static final String ADD_CUSTOMER_URL = SERVER_URL + "/css/customer/sync/addCustomer.do"; //添加、修改客户信息接口url
	public static final String DELETE_CUSTOMER_URL = SERVER_URL + "/css/customer/sync/deleteCustomer.do"; //删除客户信息接口url
	public static final String ADD_SAMPLE_URL = SERVER_URL + "/css/sample/sync/addSample.do"; //添加、修改送检信息接口url
	public static final String DELETE_SAMPLE_URL = SERVER_URL + "/css/sample/sync/deleteSample.do"; //删除送检信息接口url
	public static final String UPDATE_SAMPLE_TEST_STATUS_URL = SERVER_URL + "/css/sample/sync/updateSampTestStatus.do";//修改送检状态接口url
	public static final String UPDATE_SAMPLE_STOP_URL = SERVER_URL + "/css/sample/sync/updateSampStop.do";//流程终止接口url
	public static final String UPDATE_ITEM_URL = SERVER_URL + "/css/sample/sync/updateItem.do";//增减项接口url
	public static final String UPLOAD_FILE_URL = SERVER_URL + "/css/sample/sync/httpUploadFile.do";//文件上传接口url
	public static final String ADD_ACCOUNT_URL = SERVER_URL + "/css/account/sync/addAccount.do";//添加、修改对账单接口url
	public static final String DELETE_ACCOUNT_URL = SERVER_URL + "/css/account/sync/deleteAccount.do";//删除对账单接口url
	
	
	
	
	
	/**
	 * Create on : Megan Zhang 2017年3月30日 下午1:54:50 <br>
	 * Description : 创建签名 <br>
	 * @return map
	 */
	public static Map<String, Object> creatSign(){
		Map<String, Object> map = new HashMap<String, Object>();
		String signature = null;
		String timestamp = String.valueOf(System.currentTimeMillis());
		String nonce = CreateNoncestr();
		String[] arr = new String[] {TOKEN, timestamp, nonce};
		// 将token、timestamp、nonce三个参数进行字典序排序
		try{
			Arrays.sort(arr);
		}catch(Exception e){
			log.info("排序异常");
		}
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			signature = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			log.info("加密异常");
		}
		content = null;
		
		map.put("signature", signature);
		map.put("timestamp", timestamp);
		map.put("nonce", nonce);
		
		return map;
	}
	
	/**
	 * Create on : Megan Zhang 2017年3月30日 下午3:19:46 <br>
	 * Description : 随机字符串  <br>
	 * @return
	 */
	public static String CreateNoncestr() {
		Random random = new Random();
		return String.valueOf(random.nextInt(10000000));
	}
	
	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
}