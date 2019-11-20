package cn.demi.cus.customer.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.service.INormalListService;
import cn.demi.cus.customer.vo.NormalListVo;

/**
 * 
 * @ClassName:  NormalListAction   
 * @Description:常用字段列表  
 * @author: 吴华 
 * @date:   2019年1月22日 下午2:53:00       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Controller("cus.normalListAction")
@RequestMapping("/cus/normalList")
public class NormalListAction extends BaseAction<NormalListVo> {
	final String VIEW_PATH = "/cus/normalList/normalList";
	@Autowired private INormalListService normalListService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<NormalListVo> baseService() {
		return normalListService;
	}
	
	/**
	 * @throws GlobalException 
	 * 
	 * @Title: fetchCustCates   
	 * @Description: 获取客户类型 （大客户、长期客户...）   
	 * @param: @return      
	 * @return: List<String>      
	 * @throws
	 */
	@RequestMapping(value="fetchCustCates.do")
	@ResponseBody
	public List<String> fetchCustCates() throws GlobalException
	{
		return normalListService.getCusCatesList(); 
	}
	
	/**
	 * 
	 * @Title: fetchGjStatus   
	 * @Description: 获取跟进状态（未签客户、已签客户）   
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: List<String>      
	 * @throws
	 */
	@RequestMapping(value="fetchGjStatus.do")
	@ResponseBody
	public List<String> fetchGjStatus() throws GlobalException
	{
		return normalListService.getGjStatusList(); 
	}
	
	/**
	 * 
	 * @Title: fetchPayWay   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: List<String>      
	 * @throws
	 */
	@RequestMapping(value="fetchPayWay.do")
	@ResponseBody
	public List<String> fetchPayWay() throws GlobalException
	{
		return normalListService.getFetchPayWay(); 
	}
	

	
	
}