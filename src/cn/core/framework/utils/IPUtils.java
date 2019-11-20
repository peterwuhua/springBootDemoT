package cn.core.framework.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.core.framework.exception.GlobalException;
import cn.core.framework.log.Logger;

/**
 * Create on : 2017年2月24日 下午9:40:17  <br>
 * Description :  ip工具类<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public class IPUtils {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(IPUtils.class);
	private static final String IPV4_REGEX = "((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})"; 
	private static  boolean ipislimit = "true".equalsIgnoreCase((String)ApplicationUtils.getValue("config.ip.isiplimit"))? true:false;
	private static  String whitelist = ApplicationUtils.getValue("config.ip.whitelist").toString();
	private static  String blacklist = ApplicationUtils.getValue("config.ip.blacklist").toString();
	private static  String ipSection = ApplicationUtils.getValue("config.ip.ipSection").toString();
	
	/**
	 * Create on : Paddy Zhang 2017年2月24日 下午2:10:18 <br>
	 * Description : 获取客户端IP地址 <br>
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
	       String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       if(ip.equals("0:0:0:0:0:0:0:1")){
	        	ip = "127.0.0.1";
	        }
	       return ip;
	   }
	public static String getClientIpAddr(HttpServletRequest request) {    
        String ip = request.getHeader("X-Forwarded-For");    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("WL-Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_X_FORWARDED");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_CLIENT_IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_FORWARDED_FOR");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_FORWARDED");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_VIA");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("REMOTE_ADDR");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getRemoteAddr();    
        }
        if(ip.equals("0:0:0:0:0:0:0:1")){
        	ip = "127.0.0.1";
        }
        return ip;    
    }
	/**
	 * Create on : Paddy Zhang 2017年2月24日 下午11:44:53 <br>
	 * Description : 获取IP地址 <br>
	 * @return
	 */
	public static String getIp() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return getIpAddr(request);
	}
	/**
	 * Create on : Paddy Zhang 2017年2月24日 下午11:01:16 <br>
	 * Description : 判断是否开启IP限制  <br>
	 * @return
	 */
	public static boolean isIpLimit() {
		return ipislimit;
	}
	/**
	 * Create on : Paddy Zhang 2017年2月24日 下午11:03:07 <br>
	 * Description : 检查IP是否在黑名单中 <br>
	 * @return
	 * @throws GlobalException 
	 */
	public static boolean checkIPBlackList(String ip) throws GlobalException {
		if (StrUtils.isBlankOrNull(blacklist)) {
			return false;
		}
		String[] ipStrings= blacklist.trim().split("\\,");
		for (String ipString : ipStrings) {
			if (!ipValidate(ipString))
				throw new GlobalException("IP黑名单配置出错");
			if (ipequal(ip, ipString))
				return true ;
		}
		return false;
	}
	/**
	 * Create on : Paddy Zhang 2017年2月24日 下午11:21:37 <br>
	 * Description : 判断IP是否在白名单中 <br>
	 * @param ip
	 * @return
	 * @throws GlobalException
	 */
	public static boolean checkIPWhiteList(String ip) throws GlobalException {
		if (StrUtils.isBlankOrNull(whitelist)) {
			return false;
		}
		String[] ipStrings= whitelist.trim().split("\\,");
		for (String ipString : ipStrings) {
			if (!ipValidate(ipString))
				throw new GlobalException("IP白名单配置出错");
			if (ipequal(ip,ipString))
				return true ;
		}
		return false;
	}
	/**
	 * Create on : Paddy Zhang 2017年2月24日 下午11:29:30 <br>
	 * Description : 判断IP是否在某个区段 <br>
	 * @param ip
	 * @return
	 * @throws GlobalException
	 */
	public static boolean checkIPInRange(String ip) throws GlobalException {
		if (StrUtils.isBlankOrNull(ipSection)) {
			return false;
		}
		String[] ipStrings= ipSection.trim().split("-");
		for (String ipString : ipStrings) {
			if (!ipValidate(ipString))
				throw new GlobalException("IP区域配置出错");
		}
		if (ipExistsInRange(ip, ipSection))
			return true ;
		return false;
	}
	/**
	 * Create on : Paddy Zhang 2017年2月24日 下午10:07:05 <br>
	 * Description : 判断IP是否属于某个区段  <br>
	 * @param ip
	 * @param ipSection
	 * @return
	 */
	 public static boolean ipExistsInRange(String ip,String ipSection) {
		ipSection = ipSection.trim();
		ip = ip.trim();
		int idx = ipSection.indexOf('-');
		String beginIP = ipSection.substring(0, idx);
		String endIP = ipSection.substring(idx + 1);
		return getIp2long(beginIP)<=getIp2long(ip) &&getIp2long(ip)<=getIp2long(endIP);
    }
	 /**
	  * Create on : Paddy Zhang 2017年2月25日 下午4:54:21 <br>
	  * Description : 检查用户IP是否可登陆  <br>
	  * @param ip
	  * @return
	  * @throws GlobalException
	  */
	 public static boolean checkIp(String ip) throws GlobalException {
		 String clentIp = getIp();
		if (checkIPWhiteList(clentIp))
			return true;
		if (checkIPBlackList(clentIp)) 
			return false;
		if (checkIPInRange(clentIp))
			return true;
		if (StrUtils.isBlankOrNull(ip))
			throw new GlobalException("请联系管理员配置IP信息");
		if (ipequal(ip, clentIp))
			return true;
		return false;
	  }
	
	 /**
	  * Create on : Paddy Zhang 2017年2月24日 下午10:01:54 <br>
	  * Description : 将ip转换为long型 <br>
	  * @param ip
	  * @return
	  */
	 public static long getIp2long(String ip) {
		ip = ip.trim();
		String[] ips = ip.split("\\.");
		long ip2long = 0L;
		for (int i = 0; i < 4; ++i) {
		        ip2long = ip2long << 8 | Integer.parseInt(ips[i]);
        }
		return ip2long;
    }
	 /**
	  * Create on : Paddy Zhang 2017年2月24日 下午10:01:54 <br>
	  * Description : 将ip转换为long型 <br>
	  * @param ip
	  * @return
	  */
	 public static long getIp2long2(String ip) {
	 	ip = ip.trim();
		String[] ips = ip.split("\\.");
		long ip1 = Integer.parseInt(ips[0]);
		long ip2 = Integer.parseInt(ips[1]);
		long ip3 = Integer.parseInt(ips[2]);
		long ip4 = Integer.parseInt(ips[3]);
		long ip2long =1L* ip1 * 256 * 256 * 256 + ip2 * 256 * 256 + ip3 * 256 + ip4;
		return ip2long;
	 }
	 /**
	  * Create on : Paddy Zhang 2017年2月24日 下午10:20:30 <br>
	  * Description : 判断ip是否合法 <br>
	  * @param ip
	  * @return
	  */
	 public static boolean ipValidate(String ip){  
         return ipValidate(ip,IPV4_REGEX);  
	 } 
	 /**
	  * Create on : Paddy Zhang 2017年2月24日 下午10:20:54 <br>
	  * Description : 判断IP是否合法 <br>
	  * @param addr ip地址
	  * @param regex 判断IP的正则表达式
	  * @return
	  */
	 public static boolean ipValidate(String addr,String regex){
		if(null == addr || ""==addr){ 
			return false;  
		}else{  
			return Pattern.matches(regex, addr.trim());  
		}  
	 } 
	 /**
	  * Create on : Paddy Zhang 2017年2月24日 下午11:16:48 <br>
	  * Description : 判断IP是否相等 <br>
	  * @param ip1
	  * @param ip2
	  * @return
	  */
	 public static boolean ipequal(String ip1,String ip2){
		 long ipone = getIp2long2(ip1);
		 long iptow = getIp2long2(ip2);
		 return ipone ==iptow ? true : false;
	 }
	 public static void main(String[] args) throws GlobalException{
		String ip="10.10.10.116";
		String ipSection="10.10.1.00-10.10.255.255";
		boolean exists=ipExistsInRange(ip,ipSection);
		System.out.println(exists);
		System.out.println(getIp2long(ip));
		System.out.println(getIp2long2("127.0.0.1"));
		System.out.println(IPUtils.ipValidate("10.10.10.16"));
		System.out.println(IPUtils.checkIPInRange(ip));
	 }

	
}
