package cn.demi.cus.customer.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.importx.ImportUtils;
import cn.demi.cus.customer.service.IContactsService;
import cn.demi.cus.customer.service.ICustomerService;
import cn.demi.cus.customer.vo.ContactsVo;
import cn.demi.cus.sale.service.ICustContactService;
import cn.demi.cus.sale.vo.CustContactVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Create on : 2016年11月15日 下午3:02:07 <br>
 * Description :联系人Action <br>
 * 
 * @version v 1.0.0 <br>
 * @author Danlee Li<br>
 */
@Controller("cus.contactsAction")
@RequestMapping("/cus/contacts")
public class ContactsAction extends BaseAction<ContactsVo> {
	final String VIEW_PATH = "/cus/contacts/contacts";
	@Autowired
	private IContactsService contactsService;
	@Autowired
	private ICustContactService custContactService;
	@Autowired
	private ICustomerService customerService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<ContactsVo> baseService() {
		return contactsService;
	}

	/**
	 * 
	 * @Title: gridTab @Description: 我的客户联系人tab页 @param: @param
	 * v @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@RequestMapping(value = "gridTab.do")
	public ModelAndView gridTab(ContactsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_tab");
		return mav;
	}

	/**
	 * 
	 * @Title: gridTab @Description: 公共池客户联系人tab页 @param: @param
	 * v @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@RequestMapping(value = "gridTab4PubCust.do")
	public ModelAndView gridTab4PubCust(ContactsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_tab_pub");
		return mav;
	}

	/**
	 * Description : gridData4Tab <br>
	 * 
	 * @param gridVo
	 * @param v
	 * @return 返回JSON
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4Tab.do")
	public GridVo gridData4Tab(GridVo gridVo, ContactsVo v) throws GlobalException {
		return contactsService.gridData4Tab(gridVo, v);
	}

	/**
	 * 
	 * @Title: gridList @Description: 联系人列表用于纯显示联系人没有tab页选择 @param: @param
	 * v @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@RequestMapping(value = "select.do")
	public ModelAndView select(ContactsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_select");
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Title: gridData
	 * </p>
	 * <p>
	 * Description:联系人列表数据
	 * </p>
	 * 
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#gridData(cn.core.framework.common.page.GridVo,
	 *      cn.core.framework.common.vo.Vo)
	 */
	@ResponseBody
	@RequestMapping(value = { GRID_DATA, GRID_DATA_JSON })
	public GridVo gridData(GridVo gridVo, ContactsVo v) throws GlobalException {
		return baseService().gridData(gridVo, v);
	}

	/**
	 * 
	 * @Title: update2del4Tab @Description: 联系人 @param: @param v @param: @param
	 * attr @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@RequestMapping(value = "update2del4List.do")
	public ModelAndView update2del4List(ContactsVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = super.update2del(v, attr);

		// 更新客户的person字段
		List<ContactsVo> vos = contactsService.listByCustId(v.getCustomerVo().getId());
		if (vos.size() > 0) {
			customerService.updateFieldById(v.getCustomerVo().getId(), vos.get(0).getName());
		} else {
			customerService.updateFieldById(v.getCustomerVo().getId(), "");
		}
		// 同时删除客户关怀表数据
		String[] ids = custContactService.listByContactIds(v.getIds());
		custContactService.update2del(ids);

		mav.addObject("customerVo.id", v.getCustomerVo().getId());
		mav.setViewName("redirect:gridList.do");
		return mav;
	}

	@Override
	public ModelAndView importExcel(ContactsVo v, @RequestParam(value = "param") String param, @RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "file", required = false) MultipartFile file, RedirectAttributes attr) throws GlobalException {
		try {
			contactsService.importData(v, type, param, ImportUtils.importData(file));
			status = new Status("导入成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.error("数据导入失败 ", e);
			status = new Status("导入失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		attr.addAttribute("stat", status.getStatus());
		attr.addAttribute("msg", status.getMessage());
		ModelAndView mav = new ModelAndView();
		if (StrUtils.isNotBlankOrNull(param)) {
			mav.setViewName("redirect:gridTab.do?customerVo.id=" + param);
		} else {
			mav.setViewName(REDIRECT_PAGE);
		}
		return mav;
	}

	/**
	 * Description :客户管理tab标签页联系人显示（新增修改） <br>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value = "editTab.do")
	public ModelAndView editTab(ContactsVo v) throws GlobalException {
		ModelAndView mav = super.edit(v);
		mav.setViewName(getViewPath() + "_tab_edit");
		return mav;
	}

	/**
	 * Description : 保存客户管理tab标签中联系人信息 <br>
	 * 
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = { "addTab.do", "updateTab.do" })
	@Log(operation = Log.Operation.ADD, content = "保存/修改客户联系人", module = "联系人管理")
	public Status addOrUpdateTab(ContactsVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().save(v);
			status = new Status("保存成功", Status.STATUS_SUCCESS);
			// 更新客户表person
			customerService.updateFieldById(v.getCustomerVo().getId(), v.getName());
			// 更新客户关怀表
			List<CustContactVo> cusVos = custContactService.findByProperties("contactId", v.getId());
			custContactService.update4Contacts(cusVos, v);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "保存失败", e);
			status = new Status("保存失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * Description : 客户管理tab标签联系人删除<br>
	 * 
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value = "update2del4Tab.do")
	public ModelAndView update2del4Tab(ContactsVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = super.update2del(v, attr);
		mav.addObject("customerVo.id", v.getCustomerVo().getId());
		mav.setViewName("redirect:gridTab.do");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "ajaxGetUser.do")
	public ContactsVo ajaxGetUser(String id) throws GlobalException {
		ContactsVo v = null;
		if (StrUtils.isNotBlankOrNull(id)) {
			v = contactsService.findById(id);
		}
		return v;
	}

	/**
	 * 业务受理 自动补全功能
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxListUser.do")
	public JSONObject ajaxListUser(ContactsVo v) throws GlobalException {
		// System.out.println(v.getName());
		JSONObject json = new JSONObject();
		json.put("name", v.getName());
		List<ContactsVo> list = contactsService.listByCustName(v.getName());
		json.put("suggestions", JSONArray.fromObject(list));
		return json;
	}
}