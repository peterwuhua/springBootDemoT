package cn.demi.cus.customer.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.importx.ImportUtils;
import cn.demi.cus.customer.service.ICusClientService;
import cn.demi.cus.customer.vo.CusClientVo;

/**
 * Description :客户受检单位Action  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Controller("cus.cusClientAction")
@RequestMapping("/cus/cusClient")
public class CusClientAction extends BaseAction<CusClientVo> {
	final String VIEW_PATH = "/cus/cus_client/cus_client";
	@Autowired private ICusClientService cusClientService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<CusClientVo> baseService() {
		return cusClientService;
	}
	
	/**
	 * Description : gridTab <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="gridTab.do")
	public ModelAndView gridTab(CusClientVo v) throws GlobalException{
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
	public GridVo gridData4Tab(GridVo gridVo,CusClientVo v) throws GlobalException{
		return cusClientService.gridData4Tab(gridVo, v);
	}
	
	
	
	@Override
	public ModelAndView importExcel(CusClientVo v, String param, String type, MultipartFile file,
			RedirectAttributes attr) throws GlobalException {
		try {
			cusClientService.importData(v, type,param,ImportUtils.importData(file));
			status = new Status("导入成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.error("数据导入失败 ",e);
			status = new Status("导入失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		attr.addAttribute("stat",status.getStatus());
		attr.addAttribute("msg",status.getMessage());
		ModelAndView mav = new ModelAndView();
		if(StrUtils.isNotBlankOrNull(param)){
			mav.setViewName("redirect:gridTab.do?customerVo.id="+param);
		}else {
			mav.setViewName(REDIRECT_PAGE);
		}
		return mav;
	}
	/**
	 * Description :客户管理tab标签页受检单位新增修改 <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="editTab.do")
	public ModelAndView editTab(CusClientVo v) throws GlobalException {
		ModelAndView mav = super.edit(v);
		mav.setViewName(getViewPath()+"_tab_edit");
		return mav;
	}
	/**
	 * Description : 客户管理tab标签页受检单位新增修改保存 <br>
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value={"addTab.do","updateTab.do"})
	public ModelAndView addOrUpdateTab(CusClientVo v,RedirectAttributes attr) throws GlobalException{
		ModelAndView mav = super.save(v, attr);
		mav.addObject("customerVo.id", v.getCustomerVo().getId());
		mav.setViewName("redirect:gridTab.do");
		return mav;
	}
	/**
	 * Description :  客户管理tab标签受检单位删除<br>
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="update2del4Tab.do")
	public ModelAndView update2del4Tab(CusClientVo v,RedirectAttributes attr) throws GlobalException{
		ModelAndView mav = super.update2del(v, attr);
		mav.addObject("customerVo.id", v.getCustomerVo().getId());
		mav.setViewName("redirect:gridTab.do");
		return mav;
	}
}