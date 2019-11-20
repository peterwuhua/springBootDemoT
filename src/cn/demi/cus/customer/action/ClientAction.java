package cn.demi.cus.customer.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.customer.service.IClientService;
import cn.demi.cus.customer.vo.ClientVo;
import cn.demi.cus.customer.vo.CustVo;
import net.sf.json.JSONObject;

/**
 * Description :受检单位Action  <br>
 */
@Controller("cus.clientAction")
@RequestMapping("/cus/client")
public class ClientAction extends BaseAction<ClientVo> {
	final String VIEW_PATH = "/cus/client/client";
	@Autowired private IClientService clientService;
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ClientVo> baseService() {
		return clientService;
	}
	@Override
	public ModelAndView edit(ClientVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView show(ClientVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新信息",module="受检单位")
	public Status updateData(ClientVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增信息",module="受检单位")
	public Status addData(ClientVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存信息",module="受检单位")
	public ModelAndView save(ClientVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}
	/**
	 * 受检单位
	 * 自动补全功能
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajax4Json.do")
	public JSONObject ajax4Json(ClientVo v) throws GlobalException {
		JSONObject obj=new JSONObject();
		try {
			List<CustVo> list = clientService.list4Sim(v);
			obj.put("code", 200);
			obj.put("value",list.toArray());
			obj.put("message", "");
			obj.put("redirect","");
		} catch (GlobalException e) {
			log.info("删除失败",e);
			obj.put("code", 404);
			obj.put("message", e.getMessage());
		}
		return obj;
	}
	/**
	 * ajax 获取受检单位信息
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxGetClient.do")
	public ClientVo ajaxGetCust(ClientVo v) throws GlobalException {
		if(StrUtils.isNotBlankOrNull(v.getId())) {
			v = clientService.findById(v.getId());
		}else if(StrUtils.isNotBlankOrNull(v.getName())) {
			v=clientService.find(v.getName());
		}
		return v ;
	}
}