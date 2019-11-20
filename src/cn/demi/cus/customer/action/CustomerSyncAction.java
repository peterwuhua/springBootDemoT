package cn.demi.cus.customer.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.HttpClientUtil;
import cn.demi.cus.customer.service.ICustomerService;
import cn.demi.cus.customer.unit.HttpInterfaceUtil;
import cn.demi.cus.customer.vo.CustomerVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
/**
 * Create on : 2016年11月15日 下午3:03:06  <br>
 * Description : 客户信息Action <br>
 * @version  v 1.0.0  <br>
 * @author  Danlee Li<br>
 */
@Controller("cus.customerSyncAction")
@RequestMapping("/cus/customer/sync")
public class CustomerSyncAction extends BaseAction<CustomerVo> {
	final String VIEW_PATH = "/cus/customer/customer";
	@Autowired private ICustomerService customerService;
	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<CustomerVo> baseService() {
		return customerService;
	}
	
	/**
	 * Create on : Danlee Li 2017年4月7日 上午9:01:52 <br>
	 * Description : 客户信息保存并同步至客户服务系统 <br>
	 * @param v CustomerVo
	 * @param attr RedirectAttributes
	 * @return
	 * @throws GlobalException
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@RequestMapping(value="save4Sysnchro.do")
	public ModelAndView save4Sysnchro(CustomerVo v, RedirectAttributes attr) throws GlobalException, ClientProtocolException, IOException {
		ModelAndView mav = super.save(v, attr);  
		
		Map<String, Object> map = this.save4Sysnchro(v.getId());
		String resp = HttpClientUtil.httpPostRequest(HttpInterfaceUtil.ADD_CUSTOMER_URL,map);
		syncResponse(resp);
		
		customerService.update4Sysn(v.getId());
		return mav;
	}
	/**
	 * Create on : Danlee Li 2017年4月7日 上午9:02:41 <br>
	 * Description : 客户信息未同步记录同步至客户服务系统 <br>
	 * @param v CustomerVo
	 * @param attr RedirectAttributes
	 * @return
	 * @throws GlobalException
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@RequestMapping(value="add4Sysnchro.do")
	public ModelAndView add4Sysnchro(CustomerVo v, RedirectAttributes attr) throws GlobalException, ClientProtocolException, IOException {
		
		Map<String, Object> map = this.save4Sysnchro(v.getIds());
		String resp = HttpClientUtil.httpPostRequest(HttpInterfaceUtil.ADD_CUSTOMER_URL,map);
		syncResponse(resp);
	
		customerService.update4Sysn(v.getIds());
		ModelAndView mav = new ModelAndView(); 
		status = new Status("保存成功",Status.STATUS_SUCCESS);
		attr.addFlashAttribute(STATUS, status);
		mav.setViewName("redirect:gridPage4UnSysn.do");
		return mav;
	}
	
	/**
	 * Create on : Danlee Li 2017年4月7日 上午9:02:41 <br>
	 * Description : 客户信息已同步记录重新同步至客户服务系统 <br>
	 * @param v CustomerVo
	 * @param attr RedirectAttributes
	 * @return
	 * @throws GlobalException
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@RequestMapping(value="update4Sysnchro.do")
	public ModelAndView update4Sysnchro(CustomerVo v, RedirectAttributes attr) throws GlobalException, ClientProtocolException, IOException {
		
		Map<String, Object> map = this.save4Sysnchro(v.getIds());
		String resp = HttpClientUtil.httpPostRequest(HttpInterfaceUtil.ADD_CUSTOMER_URL,map);
		syncResponse(resp);
		
		customerService.update4Sysn(v.getIds());
		ModelAndView mav = new ModelAndView(); 
		status = new Status("保存成功",Status.STATUS_SUCCESS);
		attr.addFlashAttribute(STATUS, status);
		mav.setViewName("redirect:gridPage4Sysn.do");
		return mav;
	}
	
	/**
	 * Create on : Danlee Li 2017年4月7日 上午9:03:54 <br>
	 * Description : 封装数据同步所需要的数据 <br>
	 * @param ids 客户ids
	 * @return
	 * @throws GlobalException
	 */
	public Map<String, Object> save4Sysnchro(String... ids) throws GlobalException{
		Map<String, Object> map = HttpInterfaceUtil.creatSign();
		JSONArray array = new JSONArray();
		List<CustomerVo> list = customerService.listByIds(ids);
		if(null!=list && list.size()>0){
			for(CustomerVo v:list){
				JSONObject cusData = new JSONObject();
				cusData.put("syncId", v.getId());
				cusData.put("no", v.getCode());
				cusData.put("name", v.getName());
				cusData.put("user", v.getPerson());
				cusData.put("phone", v.getPhone());
				cusData.put("email", v.getEmail());
				cusData.put("address", v.getAddress());
				cusData.put("cusType", v.getCusType());
				cusData.put("cusLevel", v.getGrade().trim());
				cusData.put("industryType", v.getIndustry());
				cusData.put("remark", v.getRemark());
				if(StringUtils.isNotBlank(v.getAccountVo().getLoginName())){
					cusData.put("loginName", v.getAccountVo().getLoginName());
					JSONObject accData = new JSONObject();
					accData.put("loginName", v.getAccountVo().getLoginName());
					accData.put("isUse", v.getAccountVo().getIsUse());
					cusData.put("accountVo", accData);
				}
				array.add(cusData);
			}
		}
		String outputStr = JSONUtils.valueToString(array);
		map.put("dataMap", outputStr);
		return map;
	}
	
	/**
	 * Create on : Megan Zhang 2017年5月4日 下午2:39:15 <br>
	 * Description : 同步删除客户服务系统中的客户信息 <br>
	 * @param ids
	 * @throws GlobalException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void delete4Synchro(List<CustomerVo> list) throws GlobalException, ClientProtocolException, IOException{
		Map<String, Object> map = sysnchro4Del(list);
		String resp = HttpClientUtil.httpPostRequest(HttpInterfaceUtil.DELETE_CUSTOMER_URL,map);
		syncResponse(resp);
	}
	
	
	/**
	 * Create on : Danlee Li 2017年4月7日 上午9:04:42 <br>
	 * Description : 封装需要删除的客户信息 <br>
	 * @param ids String
	 * @return
	 * @throws GlobalException
	 */
	public Map<String, Object> sysnchro4Del(List<CustomerVo> list)throws GlobalException{
		Map<String, Object> map = HttpInterfaceUtil.creatSign();
		JSONArray array = new JSONArray();
		for(CustomerVo v:list){
			JSONObject cusData = new JSONObject();
			cusData.put("syncId", v.getId());
			array.add(cusData);
		}
		String outputStr = JSONUtils.valueToString(array);
		map.put("dataMap", outputStr);
		return map;
	}
	
	/**
	 * Create on : Megan Zhang 2017年5月4日 上午11:28:21 <br>
	 * Description : 接口调用后响应处理 <br>
	 * @param resp
	 * @param message
	 */
	private void syncResponse(String resp){
		JSONObject jsonObject = JSONObject.fromObject(resp);
		if (null != jsonObject) {
            String status = jsonObject.getString("status");
            if(status.equals("success")){
            	log.info("客户服务端接口调用成功！");
            }else{
            	log.info("客户服务端接口调用失败！");
            }
		}
	}
	
	/**
	 * Create on : Danlee Li 2017年4月7日 上午9:05:29 <br>
	 * Description : 未同步页面跳转 <br>
	 * @param v CustomerVo
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="gridPage4UnSysn.do")
	public ModelAndView gridPage4UnSysn(CustomerVo v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_un_page");
		return mav;
	}
	/**
	 * Create on : Danlee Li 2017年4月7日 上午9:06:08 <br>
	 * Description :已同步数据跳转  <br>
	 * @param v CustomerVo
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="gridPage4Sysn.do")
	public ModelAndView gridPage4Sysn(CustomerVo v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_sysn_page");
		return mav;
	}
    /**
     * Create on : Danlee Li 2017年4月7日 上午9:06:32 <br>
     * Description : 未同步数据 <br>
     * @param gridVo GridVo
     * @param v CustomerVo
     * @return
     * @throws GlobalException
     */
	@ResponseBody
	@RequestMapping(value = "gridData4UnSysn.do")
	public GridVo gridData4UnSysn(GridVo gridVo, CustomerVo v) throws GlobalException {
		return customerService.gridDate4UnSysn(gridVo, v);
	}
	/**
     * Create on : Danlee Li 2017年4月7日 上午9:06:32 <br>
     * Description : 已同步数据 <br>
     * @param gridVo GridVo
     * @param v CustomerVo
     * @return
     * @throws GlobalException
     */
	@ResponseBody
	@RequestMapping(value = "gridData4Sysn.do")
	public GridVo gridData4Sysn(GridVo gridVo, CustomerVo v) throws GlobalException {
		return customerService.gridDate4Sysn(gridVo, v);
	}
}