package cn.demi.cus.sale.action;

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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IUserService;
import cn.demi.cus.customer.service.IContactsService;
import cn.demi.cus.customer.service.ICustomerService;
import cn.demi.cus.customer.service.IVisitService;
import cn.demi.cus.customer.vo.ContactsVo;
import cn.demi.cus.customer.vo.CustomerVo;
import cn.demi.cus.sale.service.ISaleContactsService;
import cn.demi.cus.sale.vo.SaleContactsVo;

/**
 * 
 * @ClassName: SaleContactsAction
 * @Description:客户跟踪Action
 * @author: 吴华
 * @date: 2019年1月29日 下午5:39:24
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Controller("cus.saleContactAction")
@RequestMapping("/cus/saleContact")
public class SaleContactsAction extends BaseAction<SaleContactsVo> {
	final String VIEW_PATH = "/cus/saleContact/saleContacts";
	@Autowired
	private ISaleContactsService saleContactsService;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IVisitService visitService;
	@Autowired
	private IContactsService contactsService;
	@Autowired
	private IUserService userService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<SaleContactsVo> baseService() {
		return saleContactsService;
	}

	/**
	 * 
	 * <p>
	 * Title: gridDatad
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#gridDatad(cn.core.framework.common.page.GridVo,
	 *      cn.core.framework.common.vo.Vo)
	 */
	@ResponseBody
	@RequestMapping(value = { GRID_DATAD })
	public GridVo gridDatad(GridVo gridVo, SaleContactsVo v) throws GlobalException {
		List<SaleContactsVo> voList = saleContactsService.listByIds(v.getCustomerId(), "");
		gridVo.setDatas(voList);
		return gridVo;
	}

	/**
	 * 
	 * <p>
	 * Title: gridDatad
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#gridDatad(cn.core.framework.common.page.GridVo,
	 *      cn.core.framework.common.vo.Vo)
	 */
	@ResponseBody
	@RequestMapping(value = { "gridDatad3.do" })
	public GridVo gridDatad3(GridVo gridVo, SaleContactsVo v) throws GlobalException {
		List<SaleContactsVo> voList = saleContactsService.listBypubcId(v.getCustomerId());
		gridVo.setDatas(voList);
		return gridVo;
	}

	/**
	 * 
	 * <p>
	 * Title: gridPaged
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#gridPaged(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value = GRID_PAGED)
	public ModelAndView gridPaged(SaleContactsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_ed_page");
		return mav;
	}
	
	/**
	 * 
	 * @Title: gridDatad4Past   
	 * @Description: 客户跟踪历史提交记录   
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = { "gridDatad4Past.do" })
	public GridVo gridDatad4Past(GridVo gridVo, SaleContactsVo v) throws GlobalException {
		List<SaleContactsVo> voList = saleContactsService.listBySalerId(getCurrent().getAccountId());
		gridVo.setDatas(voList);
		return gridVo;
	}

	/**
	 * 
	 * @Title: gridPaged @Description: 客户编辑页-客户跟踪列表 @param: @param
	 * v @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@RequestMapping(value = "gridPage4Cus.do")
	public ModelAndView gridPage4Cus(SaleContactsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_tab");
		return mav;
	}

	/**
	 * 
	 * @Title: 公共客户编辑页-客户跟踪列表 @Description: @param: @param
	 * v @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@RequestMapping(value = "gridPage4PubCus.do")
	public ModelAndView gridPage4PubCus(SaleContactsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_tab3");
		return mav;
	}

	/**
	 * 
	 * @Title: gridPage4Cus2 @Description: 客户显示页客户跟踪列表 @param: @param
	 * v @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@RequestMapping(value = "gridPage4Cus2.do")
	public ModelAndView gridPage4Cus2(SaleContactsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_tab2");
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Title: edit
	 * </p>
	 * <p>
	 * Description: 显示客户跟踪编辑页面
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#edit(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value = EDIT)
	public ModelAndView edit(SaleContactsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (!StrUtils.isBlankOrNull(v.getId())) {
			v = saleContactsService.findById(v.getId());
		}

		CustomerVo cusObj = customerService.findById(v.getCustomerId());
		if (null != cusObj && !StrUtils.isBlankOrNull(cusObj.getId())) {
			List<ContactsVo> contactsList = contactsService.listByCustId(cusObj.getId());
			v.setCustomerName(cusObj.getName());
			v.setAddress(cusObj.getAddress());
			mav.addObject("contactsList", contactsList);// 联系人列表
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Title: addData
	 * </p>
	 * <p>
	 * Description:保存跟踪记录
	 * </p>
	 * 
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#addData(cn.core.framework.common.vo.Vo,
	 *      org.springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@RequestMapping(value = "saveDatad.do")
	public ModelAndView saveDatad(SaleContactsVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		try {
			// 新增跟踪记录
			baseService().add(v);
			// 回访记录更新(页面没有这个)
			// visitService.updateVisitByGZId(v);
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		mav.setViewName("redirect:gridPage.do");
		return mav;
	}

}