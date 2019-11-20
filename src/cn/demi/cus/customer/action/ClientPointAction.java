package cn.demi.cus.customer.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.customer.service.IClientPointService;
import cn.demi.cus.customer.vo.ClientPointVo;

@Controller("cus.clientPointAction")
@RequestMapping("/cus/clientPoint")
public class ClientPointAction extends BaseAction<ClientPointVo> {
	final String VIEW_PATH = "/cus/client_point/client_point";
	@Autowired 
	private IClientPointService clientPointService;	
//	@Autowired 
//	private ISampTypeService sampTypeService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ClientPointVo> baseService() {
		return clientPointService;
	}
	/**
	 * Description : gridTab <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="gridTab.do")
	public ModelAndView gridTab(ClientPointVo v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_tab");
		return mav;
	}
	/**
	 * Description : gridData4Tab <br>
	 * @param gridVo
	 * @param v
	 * @return 返回JSON
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4Tab.do")
	public GridVo gridData4Tab(GridVo gridVo,ClientPointVo v) throws GlobalException{
		return clientPointService.gridData4Tab(gridVo, v);
	}
	/**
	 * Description :客户管理tab标签页点位新增修改 <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="editTab.do")
	public ModelAndView editTab(ClientPointVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=clientPointService.findById(v.getId());
		}
//		List<SampTypeVo> sampTypeList=sampTypeService.listByPid(sampTypeService.findRoot().getId());
//		mav.addObject("sampTypeList", sampTypeList);
		mav.addObject("envList", EnumBus.getAllEnvs());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_tab_edit");
		return mav;
	}
	/**
	 * Description : 保存客户管理tab标签中点位信息 <br>
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value={"addTab.do","updateTab.do"})
	@Log(operation=Log.Operation.ADD,content="保存/修改客户监测点位",module="监测点位管理")
	public Status addOrUpdateTab(ClientPointVo v,RedirectAttributes attr) throws GlobalException{
		try {
			clientPointService.save(v);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * Description :  客户管理tab标签监测点位删除<br>
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="update2del4Tab.do")
	public ModelAndView update2del4Tab(ClientPointVo v,RedirectAttributes attr) throws GlobalException{
		ModelAndView mav = super.update2del(v, attr);
		mav.addObject("clientVo.id", v.getClientVo().getId());
		mav.setViewName("redirect:gridTab.do");
		return mav;
	}
	/**
	 * 获取客户的点位集合
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value="listPoint.do")
	public List<ClientPointVo> listPoint(ClientPointVo v) throws GlobalException {
		return clientPointService.list(v);
	}
}