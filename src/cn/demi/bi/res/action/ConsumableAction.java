package cn.demi.bi.res.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
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
import cn.demi.bi.res.service.IConsumableService;
import cn.demi.bi.res.vo.ConsumableVo;

@Controller("bi.consumableAction")
@RequestMapping("/bi/consumable")
public class ConsumableAction extends BaseAction<ConsumableVo> {
	final String VIEW_PATH = "/bi/consumable/consumable";
	@Autowired private IConsumableService consumableService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ConsumableVo> baseService() {
		return consumableService;
	}
	/**
	 * Create on : Dave Yu 2016年12月14日 上午9:25:24 <br>
	 * Description : 跳转弹窗页面 <br>
	 * @param pageType
	 * @param pageValue
	 * @param inputValue
	 * @return mav
	 * @throws GlobalException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@RequestMapping("/selectSeachValue.do")
	public ModelAndView selectSeachValue(String pageType,String pageValue,String inputValue) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		List<String> list = consumableService.selectByPageType(pageType);
		mav.addObject("list", list);
		mav.addObject("type", pageType);
		mav.addObject("value", pageValue);
		mav.addObject("inputValue", inputValue);
		mav.setViewName(getViewPath() + "_select_layer");
		return mav;
	}
	/**
	 * Create on : Dave Yu 2017年1月23日 下午3:01:07 <br>
	 * Description : 数据查看页面跳转 <br>
	 * @param v
	 * @return mav
	 * @throws GlobalException
	 */
	@RequestMapping(value="seeData.do")
	public ModelAndView seeData(ConsumableVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_data");
		return mav;
	}
	/**
	 * Create on : Dave Yu 2016年12月15日 下午4:50:30 <br>
	 * Description : ajax动态筛选客户缴费情况数据 <br>
	 * @param vo
	 * @return Map
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/ajaxSeachStates.do")
	public Map<String, Map<String, Integer>> ajaxSeachStates(ConsumableVo vo) throws GlobalException {
		Map<String, Map<String, Integer>> map = new LinkedHashMap<String, Map<String, Integer>>();
		Map<String, Integer> jgMap = new LinkedHashMap<String, Integer>();
		Map<String, Integer> sjMap = new LinkedHashMap<String, Integer>();
		
		List<Object> reagentList = consumableService.MaxPayTenConsumable(vo);
		for (int i = 0; i < reagentList.size(); i++) {
			Object[] Object = (java.lang.Object[]) reagentList.get(i);
			if(String.valueOf(Object[1]).indexOf(".") > -1){
				String str = String.valueOf(Object[1]).substring(0 , String.valueOf(Object[1]).indexOf("."));
				jgMap.put(String.valueOf(Object[0]), Integer.valueOf(str));
			}else{
				if(null != String.valueOf(Object[1]) && !"".equals(String.valueOf(Object[1]))){
					jgMap.put(String.valueOf(Object[0]), Integer.valueOf(Object[1].toString()));
				}
			}
			if(String.valueOf(Object[2]).indexOf(".") > -1){
				String str = String.valueOf(Object[2]).substring(0,String.valueOf(Object[2]).indexOf("."));
				sjMap.put(String.valueOf(Object[0]), Integer.valueOf(str));
			}else{
				if(null != Object[2] &&  !"".equals(String.valueOf(Object[2]))){
					sjMap.put(String.valueOf(Object[0]), Integer.valueOf(Object[2].toString()));
				}
			}
		}
		
		map.put("jgMap", jgMap);
		map.put("sjMap", sjMap);
		return map;
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
	public ResponseEntity<byte[]> exportOther(ConsumableVo v,PageResult pageResult,
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
	private Map<String, Object> getExportData(ConsumableVo vo,PageResult pageResult,String type) throws GlobalException{
 		Map<String, Object> exportMap = new HashMap<String, Object>();
 		if("states".equals(vo.getDisPage())){//全部数据
 			List<Object> reagentList = consumableService.MaxPayTenConsumable(vo);
 			exportMap.put("list", reagentList);
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