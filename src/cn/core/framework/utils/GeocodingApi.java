package cn.core.framework.utils;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

/**
 * 地址解析
 * 根据地址获取经纬度
 * 根据经纬度获取地址
 * address 地址
 * output 接收http请求返回的数据（支持json和xml格式）
 * key 用户密钥
 * city 城市名
 *  1. city属于可选参数，通常情况可以不使用，若解析无结果，请尝试增加此字段。
 *	2. 支持名胜古迹、标志性建筑物名称解析返回百度经纬度坐标，如address=“百度大厦”。
 *	3. 支持使用“*路与*路交叉口”方式解析返回百度经纬度坐标，若地址库中存在该地址描述，返回百度经纬度坐标。
 *	4. 若解析status字段为OK，若结果内容为空，原因分析及可尝试方法：
		地址库里无此数据，本次结果为空。
		加入city字段重新解析；
		将过于详细或简单的地址更改至省市区县街道重新解析；
	5. 特别提醒：逆地址解析location参数传入的参数格式是(纬度lat，经度lng)。
	
 *	Java中可以使用函数URLEncoder.encode对特殊字符进行编码。
 * @author QuJunLong
 */
public class GeocodingApi {

	public static final String key="DSzRGBqXDEwOxbas3qFQZyLSYj79rdg6";
	
	/**
	 * 根据地址获取经纬度
	 * @param adress 地址 （必填）
	 * @param city 城市 （可选）
	 * @return
	 * {status: '字符串状态常量', 取值如下： //OK 成功 INVILID_KEY 非法密钥  INVALID_PARAMETERS 非法参数，参数错误时候给出。
		result: {    
			location: {
				lat: 纬度：数值，
				lng: 经度：数值
			},
		 	precise:’位置的附加信息，是否精确查找’（1为精确查找，0为不精确查找）,
		 	confidence: 可信度,
		 	level:'级别'
		 },
		}
	 */
	public static String getLocation(String adress,String city) {
		String url="http://api.map.baidu.com/geocoder/v2/?address="+adress+"&output=json&ak="+key;
		if(null!=city && !city.trim().equals("")) {
			url+="&city="+city;
		}
		String reuslt=null;
		try {
			reuslt=HttpClientUtil.httpGetRequest(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reuslt;
	}
	
	/**
	 * 根据经纬度获取地址
	 * @param jd 经度
	 * @param wd 纬度
	 * @return
		{status: '字符串状态常量', 取值如下：
		 //OK 成功  INVILID_KEY 非法密钥 INVALID_PARAMETERS 非法参数，参数错误时候给出。
		 result: {    
			 location: {
				 lat: 纬度：数值，
				 lng: 经度：数值
			 },
			 formatted_address: ‘详细地址描述’,
			 business: '周围商圈',
			 addressComponent:{
				 city:’城市名称’,
				 district: ‘区县名称’,
				 province:’省份名称’, 
				 street: ‘街道名称’,
				 streetNumber: '门牌号码' 
		 	},
		 	cityCode: '城市代码'
		 }
		 }
	 */
	public static String getAddress(String jd,String wd) {
		String url="http://api.map.baidu.com/geocoder/v2/?location="+jd+","+wd+"&output=json&ak="+key;
		String reuslt=null;
		try {
			reuslt=HttpClientUtil.httpGetRequest(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reuslt;
	}
	public static void main(String[] args) {
		String result=getLocation("西安市长安区韦曲北街长盛家园", "西安市");
		System.out.println(result);
	}
}
