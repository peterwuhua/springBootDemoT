package cn.demi.bi.customer.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.export.ExportUtils;
import cn.demi.bi.customer.service.ICustomerService;
import cn.demi.cus.customer.vo.CustomerVo;

/**
 * <strong>创建信息: 2015年7月8日 上午10:27:00 </strong> <br>
 * <strong>概要 :客户action </strong> <br>
 */
@Controller("bi.customerAction")
@RequestMapping("bi/customer")
public class CustomerAction extends BaseAction<CustomerVo> {
	final String VIEW_PATH = "/bi/customer/customer";
	@Autowired
	private ICustomerService customerService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<CustomerVo> baseService() {
		return customerService;
	}
	
	
	/**
	 * Create on : Danny Zhang 2016年11月3日 下午5:26:46 <br>
	 * Description : ajax动态筛选客户经理数据<br>
	 * @param vo
	 * @return map
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/ajaxSeachSaler.do")
	public Map<String, Integer> ajaxSeachSaler(CustomerVo vo) throws GlobalException{
		List<CustomerVo> list = customerService.list(vo);
		Map<String, Integer> map = new HashMap<String,Integer>();
		for(CustomerVo v : list){
			if(!map.containsKey(v.getSaler())){
				map.put(v.getSaler(), 1);
			}else{
				map.put(v.getSaler(), map.get(v.getSaler()) + 1);
			}
		}
		return map;
	}
	
	/**
	 * Create on : Danny Zhang 2016年11月25日 下午2:28:34 <br>
	 * Description :  ajax动态筛选客户级别数据<br>
	 * @param vo
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/ajaxSeachBuild.do")
	public Map<String, Map<String,List<Integer>>> ajaxSeachBuild(CustomerVo vo) throws GlobalException{
		List<CustomerVo> list = customerService.list(vo);
		Map<String, Map<String,List<Integer>>> map = new HashMap<String,Map<String,List<Integer>>>();
		Map<String,List<Integer>> buildMap = new LinkedHashMap<String, List<Integer>>();
		Map<String,List<Integer>> gradeMap = new LinkedHashMap<String, List<Integer>>();
		List<String> gradeList = customerService.gradeList();
		for(String grade : gradeList){
			gradeMap.put(grade, null);
		}
		for(CustomerVo v : list){
			List<Integer> countList = new ArrayList<Integer>();
			for(String grade : gradeList){
				List<Integer> count = customerService.countBySalerAndGrade(v.getSaler(),grade);
				Integer intc = Integer.valueOf(String.valueOf(count.get(0)));
				countList.add(intc);
			}
			buildMap.put(v.getSaler(), countList);
			
		}
		map.put("build", buildMap);
		map.put("grade", gradeMap);
		return map;
	}
	
	/**
	 * Create on : Dave Yu 2017年1月24日 上午9:43:17 <br>
	 * Description : 客户级别统计表格取值 <br>
	 * @param vo
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/ajaxSeachSalerCount.do")
	public Map<String, String> ajaxSeachSalerCount(CustomerVo vo) throws GlobalException{
		Map<String, String> map = new HashMap<String,String>();
		List<String> salerList = customerService.salerList(vo);
		for(String saler : salerList){
			List<String> gradeList = customerService.findCountGradesBySaler(vo,saler);
			map.put(saler, String.valueOf(gradeList.get(0)));
		}
		return map;
	}
	
	/**
	 * Create on : Danny Zhang 2016年11月29日 下午12:25:35 <br>
	 * Description : ajax动态筛选客户行业数据 <br>
	 * @param vo
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/ajaxSeachIndustry.do")
	public Map<String, Integer> ajaxSeachIndustry(CustomerVo vo) throws GlobalException{
		List<CustomerVo> list = customerService.list(vo);
		Map<String, Integer> map = new HashMap<String,Integer>();
		for(CustomerVo v : list){
			if(!map.containsKey(v.getIndustry())){
				map.put(v.getIndustry(), 1);
			}else{
				map.put(v.getIndustry(), map.get(v.getIndustry()) + 1);
			}
		}
		return map;
	}
	
	/**
	 * Create on : Dave Yu 2016年12月21日 下午1:43:16 <br>
	 * Description : ajax动态筛选客户分布数据  <br>
	 * @param vo
	 * @return map
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/ajaxSeachNational.do")
	public Map<String, Integer> ajaxSeachNational(CustomerVo vo) throws GlobalException {
		List<CustomerVo> list = customerService.list(vo);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(CustomerVo v : list){
			if(!map.containsKey(v.getProvince())){
				map.put(v.getProvince(),1);
			}else{
				map.put(v.getProvince(), map.get(v.getProvince()) + 1);
			}
		}
		return map;
	}
	
	/**
	 * Create on : Danny Zhang 2016年11月29日 下午12:26:01 <br>
	 * Description :  ajax动态筛选客户类型数据 <br>
	 * @param vo
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/ajaxSeachSouce.do")
	public Map<String, Integer> ajaxSeachSouce(CustomerVo vo) throws GlobalException{
		List<CustomerVo> list = customerService.list(vo);
		Map<String, Integer> map = new HashMap<String,Integer>();
		for(CustomerVo v : list){
			if(!map.containsKey(v.getCusType())){
				map.put(v.getCusType(), 1);
			}else{
				map.put(v.getCusType(), map.get(v.getCusType()) + 1);
			}
		}
		return map;
	}
	
	/**
	 * Create on : Danny Zhang 2016年11月29日 下午12:26:37 <br>
	 * Description : ajax动态筛选客户比例数据 <br>
	 * @param vo
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/ajaxSeachGrade.do")
	public Map<String, Integer> ajaxSeachGrade(CustomerVo vo) throws GlobalException{
		List<CustomerVo> list = customerService.list(vo);
		Map<String, Integer> map = new HashMap<String,Integer>();
		for(CustomerVo v : list){
			if(!map.containsKey(v.getGrade())){
				map.put(v.getGrade(), 1);
			}else{
				map.put(v.getGrade(), map.get(v.getGrade()) + 1);
			}
		}
		return map;
	}
	
	/**
	 * Create on : Danny Zhang 2016年11月4日 下午1:32:09 <br>
	 * Description : 跳转弹窗页面 <br>
	 * @param pageType
	 * @return
	 * @throws GlobalException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/selectSeachValue.do")
	public ModelAndView selectSeachValue(String pageType,String pageValue,String inputValue) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		List<String> list = customerService.selectByPageType(pageType);
		/*CustomerVo vo = null;
		List<CustomerVo> voList = new ArrayList<CustomerVo>();
		String firstLetter = pageType.substring(0, 1).toUpperCase();
		String setter = "set" + firstLetter + pageType.substring(1);
		for(String str : list){
			vo = new CustomerVo(); 
			vo.getClass().getMethod(setter, String.class).invoke(vo, str); 
			voList.add(vo);
		}*/
		mav.addObject("list", list);
		mav.addObject("type", pageType);
		mav.addObject("value", pageValue);
		mav.addObject("inputValue", inputValue);
		mav.setViewName(getViewPath() + "_select_layer");
		return mav;
	}
	
	/**
	 * Create on : Danny Zhang 2016年11月25日 下午2:29:09 <br>
	 * Description :跳转地区弹窗 <br>
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping("/selectSeachArea.do")
	public ModelAndView selectSeachArea() throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.setViewName(getViewPath() + "_select_area");
		return mav;
	}
	
	/**
	 * Create on : Danny Zhang 2016年11月25日 下午2:29:36 <br>
	 * Description : 获取地区数据 <br>
	 * @param pageType
	 * @param parentType
	 * @param data
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/areaData.do")
	public List<String> areaData(String pageType,String parentType,String data) throws GlobalException {
		List<String> customerList = customerService.selectByArea(pageType,data,parentType);
		return customerList;
	}
	/**
	 * Create on : Dave Yu 2017年1月24日 上午9:04:54 <br>
	 * Description : 查看页面 <br>
	 * @param v
	 * @return mav
	 * @throws GlobalException
	 */
	@RequestMapping(value="seeData.do")
	public ModelAndView seeData(CustomerVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_data");
		return mav;
	}
	
	/**
	 * Create on : Dave Yu 2017年1月23日 下午3:27:16 <br>
	 * Description : 统计表格导出方法 <br>
	 * @param v
	 * @param pageResult
	 * @param type
	 * @param source
	 * @param target
	 * @return
	 * @throws GlobalException
	 */
 	@RequestMapping(value = "exportOther.do")
	public ResponseEntity<byte[]> exportOther(CustomerVo v,PageResult pageResult,
			@RequestParam(value = "_type", required = true)String type,
			@RequestParam(value = "_source", required = true)String source,
			@RequestParam(value = "_target", required = true)String target) throws GlobalException {
		String path = ExportUtils.export(source, target, getExportData(v, pageResult, type));
		return down(target, path);
	}
	/**
	 * Create on : Dave Yu 2017年1月23日 下午3:28:17 <br>
	 * Description :  <br>
	 * @param v
	 * @param pageResult
	 * @param type
	 * @return
	 * @throws GlobalException
	 */
	private Map<String, Object> getExportData(CustomerVo vo,PageResult pageResult,String type) throws GlobalException{
 		Map<String, Object> exportMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String,Object>();
 		if("saler".equals(vo.getStatus())){//全部数据
 			List<CustomerVo> list = customerService.list(vo);
 			for(CustomerVo v : list){
 				if(!map.containsKey(v.getSaler())){
 					map.put(v.getSaler(), 1);
 				}else{
 					map.put(v.getSaler(), Integer.valueOf(String.valueOf(map.get(v.getSaler()))) + 1);
 				}
 			}
 			exportMap.put("map", map);
 		}else if("build".equals(vo.getStatus())){
 			List<String> salerList = customerService.salerList(vo);
 			for(String saler : salerList){
 				List<String> gradeList = customerService.findCountGradesBySaler(vo,saler);
 				map.put(saler, String.valueOf(gradeList.get(0)));
 			}
 			exportMap.put("map", map);
 		}else if("industry".equals(vo.getStatus())){
 			List<CustomerVo> list = customerService.list(vo);
 			for(CustomerVo v : list){
 				if(!map.containsKey(v.getIndustry())){
 					map.put(v.getIndustry(), 1);
 				}else{
 					map.put(v.getIndustry(), Integer.valueOf(String.valueOf(map.get(v.getIndustry()))) + 1);
 				}
 			}
 			exportMap.put("map", map);
 		}else if("national".equals(vo.getStatus())){
 			List<CustomerVo> list = customerService.list(vo);
 			for(CustomerVo v : list){
 				if(!map.containsKey(v.getProvince())){
 					map.put(v.getProvince(),1);
 				}else{
 					map.put(v.getProvince(), Integer.valueOf(String.valueOf(map.get(v.getProvince()))) + 1);
 				}
 			}
 			exportMap.put("map", map);
 		}else if("souce".equals(vo.getStatus())){
 			List<CustomerVo> list = customerService.list(vo);
 			for(CustomerVo v : list){
 				if(!map.containsKey(v.getCusType())){
 					map.put(v.getCusType(),1);
 				}else{
 					map.put(v.getCusType(), Integer.valueOf(String.valueOf(map.get(v.getCusType()))) + 1);
 				}
 			}
 			exportMap.put("map", map);
 		}else if("grade".equals(vo.getStatus())){
 			List<CustomerVo> list = customerService.list(vo);
 			for(CustomerVo v : list){
 				if(!map.containsKey(v.getGrade())){
 					map.put(v.getGrade(),1);
 				}else{
 					map.put(v.getGrade(), Integer.valueOf(String.valueOf(map.get(v.getGrade()))) + 1);
 				}
 			}
 			exportMap.put("map", map);
 		}
 		return exportMap;
 	}
	/**
 	 * <strong>Create on : 2016年11月17日 上午8:21:38 </strong> <br>
 	 * <strong>Description : 构建下载文件</strong> <br>
 	 * @param trueName 真实文件名
 	 * @param downLoadPath 文件路径
 	 * @return ResponseEntity
 	 */
	@Override
	protected ResponseEntity<byte[]> down(String trueName, String downLoadPath) throws GlobalException{
		File file=new File(downLoadPath);  
	    HttpHeaders headers = new HttpHeaders();    
	    String fileName = null;
		try {
			String userAgent = request.getHeader("User-Agent");
			if(userAgent.toUpperCase().indexOf("MSIE") > 0 || userAgent.toUpperCase().indexOf("TRIDENT")>0 ){//判断是否为IE
				fileName = URLEncoder.encode(trueName, "UTF-8");
			}else {
				fileName = new String(trueName.getBytes("UTF-8"),"iso-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			throw new GlobalException("文件名转码失败",e);
		}//为了解决中文名称乱码问题  
	    headers.setContentDispositionFormData("attachment", fileName);   
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
	    byte[] o = null;
		try {
			o = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new GlobalException("读取文件失败",e);
		}
	    return new ResponseEntity<byte[]>(o,headers,HttpStatus.OK);
	}
	
}
