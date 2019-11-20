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
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IUserService;
import cn.demi.cus.customer.service.IPubCustomerService;
import cn.demi.cus.customer.vo.CustomerVo;
import cn.demi.cus.customer.vo.Saler;

/**
 * 
 * @ClassName: PubCustomerAction
 * @Description: 公共池客户
 * @author: 吴华
 * @date: 2019年1月22日 上午10:05:22
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Controller("cus.pubCustomerAction")
@RequestMapping("/cus/pubcustomer")
public class PubCustomerAction extends BaseAction<CustomerVo> {

	final String VIEW_PATH = "/cus/pubcustomer/pubcustomer";
	@Autowired
	private IPubCustomerService pubCustomerService;
	@Autowired
	private IUserService userService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<CustomerVo> baseService() {
		return pubCustomerService;
	}

	/**
	 * 
	 * @Title: gridPage @Description: 公共池客户列表展示 @param: @param
	 *         v @param: @return @param: @throws GlobalException @return:
	 *         ModelAndView @throws
	 */
	@RequestMapping(value = GRID_PAGE)
	public ModelAndView gridPage(CustomerVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}

	/**
	 * 
	 * @Title: gridData4Used @Description: 根据字段查询 @param: @param
	 *         gridVo @param: @param v @param: @return @param: @throws
	 *         GlobalException @return: GridVo @throws
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4Used.do")
	public GridVo gridData4Used(GridVo gridVo, CustomerVo v) throws GlobalException {
		return pubCustomerService.gridData(gridVo, v);
	}



	/**
	 * 
	 * @Title: fetchSaleRler @Description: 获取销售员 @param: @return @param: @throws
	 *         GlobalException @return: List<String> @throws
	 */
	@ResponseBody
	@RequestMapping(value = { "fetchSaleRler.json", "fetchSaleRler.do" })
	public List<Saler> fetchSaleRler() throws GlobalException {
		return userService.fetchSaleRler();
	}
	
	/**
	 * 
	 * <p>Title: edit</p>   
	 * <p>Description: </p>   
	 * @param v
	 * @return
	 * @throws GlobalException   
	 * @see cn.core.framework.common.action.BaseAction#edit(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value=EDIT)
	public ModelAndView edit(CustomerVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		boolean isEdit = false; 
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
			isEdit = true;
		}
		String auth = userService.saleJLAuth(getCurrent().getAccountId());
		v.setAuth(auth);
		mav.addObject(VO, v);
		mav.addObject(IS_EDIT, isEdit);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	
	/**
	 * 
	 * <p>Title: update2del</p>   
	 * <p>Description: 删除公共池客户</p>   
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException   
	 * @see cn.core.framework.common.action.BaseAction#update2del(cn.core.framework.common.vo.Vo, org.springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@RequestMapping(value=UPDATE_2_DEL)
	public ModelAndView update2del(CustomerVo v,RedirectAttributes attr) throws GlobalException{
		try {
			baseService().update2del(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	
	
	

}
