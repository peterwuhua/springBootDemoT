package cn.demi.cus.customer.action;

import java.io.IOException;
import java.util.HashMap;
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
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IUserService;
import cn.demi.base.system.vo.UserVo;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.service.IAccountService;
import cn.demi.cus.customer.service.IContactsService;
import cn.demi.cus.customer.service.ICustomerService;
import cn.demi.cus.customer.vo.AccountVo;
import cn.demi.cus.customer.vo.ContactsVo;
import cn.demi.cus.customer.vo.CustVo;
import cn.demi.cus.customer.vo.CustomerVo;
import net.sf.json.JSONObject;

/**
 * Create on : 2016年11月15日 下午3:03:06 <br>
 * Description : 我的客户信息Action <br>
 * 
 * @version v 1.0.0 <br>
 * @author Danlee Li<br>
 */
@Controller("cus.customerAction")
@RequestMapping("/cus/customer")
public class CustomerAction extends BaseAction<CustomerVo> {
	final String VIEW_PATH = "/cus/customer/customer";
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IContactsService contactsService;
	@Autowired
	private IUserService userService;

	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<CustomerVo> baseService() {
		return customerService;
	}

	/**
	 * 
	 * @Title: gridData4Used @Description: 1\根据字段查询2\
	 *         客户的跟进状态与合同状态进行挂钩 @param: @param gridVo @param: @param
	 *         v @param: @return @param: @throws GlobalException @return:
	 *         GridVo @throws
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4Used.do")
	public GridVo gridData4Used(GridVo gridVo, CustomerVo v) throws GlobalException {
		return customerService.gridData(gridVo, v);
	}

	/**
	 * 
	 * <p>
	 * Title: gridPage
	 * </p>
	 * <p>
	 * Description: 表格分页
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#gridPage(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value = GRID_PAGE)
	public ModelAndView gridPage(CustomerVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		String count = userService.saleJLAuth(getCurrent().getAccountId());
		if (count.equals(UserVo.USER_AUTH_XSJL)) {
			mav.addObject("fenpei", true); // 显示分配
		} else {
			mav.addObject("fenpei", false); // 不显示分配
		}
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}

	@Override
	public ModelAndView edit(CustomerVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v.setSupportDate(DateUtils.getCurrDateStr());
		v.setFstatus(Customer.ST_0);
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = customerService.findById(v.getId());
		} else {
			String auth = userService.saleJLAuth(getCurrent().getAccountId());
			v.setAuth(auth);
			v.setSaler(getCurrent().getUserName());
			v.setSalerId(getCurrent().getAccountId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	/**
	 * Description :客户信息批量删除 <br>
	 * 
	 * @throws GlobalException
	 * @throws IOException
	 */
	@RequestMapping(value = "update2delete.do")
	@ResponseBody
	@Log(operation = Log.Operation.UPDATE, content = "删除客户信息", module = "客户管理")
	public String update2delete(CustomerVo v, RedirectAttributes attr) throws GlobalException, ClientProtocolException, IOException {
		boolean flag = customerService.isDelete4All(v.getIds());
		try {
			if (flag) {
				baseService().update2del(v.getIds());
				status = new Status("删除成功", Status.STATUS_SUCCESS);
			} else {
				status = new Status("该客户不能被删除，请先删除关联数据！", Status.STATUS_SUCCESS);
			}
		} catch (GlobalException e) {
			log.info("删除失败", e);
			status = new Status("删除失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return String.valueOf(flag);
	}

	@Override
	public Status gridEdit(CustomerVo v) throws GlobalException {
		String act = request.getParameter("oper");
		try {
			if ("edit".equals(act)) {
				baseService().update4Grid(v);
				status = new Status("修改成功", Status.STATUS_SUCCESS);
			} else if ("add".equals(act)) {
				baseService().add4Grid(v);
				status = new Status("添加成功", Status.STATUS_SUCCESS);
			} else if ("del".equals(act)) {
				boolean flag = customerService.isDelete4All(v.getId());
				if (flag) {
					baseService().update2del(v.getId());
					status = new Status("删除成功", Status.STATUS_SUCCESS);
				} else {
					status = new Status("该客户不能被删除，请先删除关联数据！", Status.STATUS_SUCCESS);
				}
			}
		} catch (GlobalException e) {
			log.warn(e);
			status = new Status("操作失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * Create on : Danlee Li 2017年4月7日 上午9:07:13 <br>
	 * Description :验证账户是否存在 <br>
	 * 
	 * @param v
	 *            AccountVo
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "checkLoginName.do")
	public Map<String, Object> checkLoginName(AccountVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		/*
		 * if(StrUtils.isBlankOrNull(v.getLoginName())){
		 * map.put("type",Status.STATUS_ERROR); map.put("message","请输入账户名！！"); return
		 * map; }
		 */
		String loginName = v.getLoginName().trim();
		AccountVo oldVo = null;
		if (StrUtils.isNotBlankOrNull(v.getId())) {
			AccountVo accountVo = accountService.findById(v.getId());
			if (!loginName.equals(accountVo.getLoginName())) {
				oldVo = accountService.find(loginName);
				if (null != oldVo) {
					map.put("type", Status.STATUS_ERROR);
					map.put("message", "账户已存在：" + oldVo.getLoginName());
					return map;
				}
			}
		} else {
			oldVo = accountService.find(loginName);
			if (null != oldVo) {
				map.put("type", Status.STATUS_ERROR);
				map.put("message", "账户已存在：" + oldVo.getLoginName());
				return map;
			}
		}
		map.put("type", Status.STATUS_SUCCESS);
		map.put("message", "账户可以使用");
		return map;

	}

	/**
	 * Create on : Danlee Li 2017年4月7日 上午9:07:48 <br>
	 * Description : 验证客户名称是否存在 <br>
	 * 
	 * @param v
	 *            CustomerVo
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "checkCusName.do")
	public Map<String, Object> checkCusName(CustomerVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StrUtils.isBlankOrNull(v.getName())) {
			map.put("type", Status.STATUS_ERROR);
			map.put("message", "请输入客户名称！！");
			return map;
		}
		String cusName = v.getName().trim();
		CustomerVo oldVo = null;
		if (StrUtils.isNotBlankOrNull(v.getId())) {
			CustomerVo cusVo = customerService.findById(v.getId());
			if (!cusName.equals(cusVo.getName())) {
				oldVo = customerService.find(cusName);
				if (null != oldVo) {
					map.put("type", Status.STATUS_ERROR);
					map.put("message", "客户名称已存在：" + oldVo.getName());
					return map;
				}
			}
		} else {
			oldVo = customerService.find(cusName);
			if (null != oldVo) {
				map.put("type", Status.STATUS_ERROR);
				map.put("message", "客户名称已存在：" + oldVo.getName());
				return map;
			}
		}
		map.put("type", Status.STATUS_SUCCESS);
		map.put("message", "客户名称可以使用");
		return map;

	}

	/**
	 * 
	 * <p>
	 * Title: show
	 * </p>
	 * <p>
	 * Description: 显示
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#show(cn.core.framework.common.vo.Vo)
	 */
	@Override
	public ModelAndView show(CustomerVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = customerService.findById(v.getId());
		List<ContactsVo> contactsList = contactsService.listByCustId(v.getId());
		mav.addObject(VO, v);
		mav.addObject("contactsList", contactsList);
		mav.setViewName(getViewPath() + "_show");
		return mav;
	}

	/**
	 * 
	 * @Title: gridData4Visit @Description: 客户跟踪 @param: @param
	 *         gridVo @param: @param v @param: @return @param: @throws
	 *         GlobalException @return: GridVo @throws
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4Gz.do")
	public GridVo gridData4Gz(GridVo gridVo, CustomerVo v) throws GlobalException {
		return customerService.gridData4Gz(gridVo, v);
	}

	/**
	 * 业务受理 自动补全功能
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajax4Json.do")
	public JSONObject ajax4Json(CustomerVo v) throws GlobalException {
		JSONObject obj = new JSONObject();
		try {
			List<CustVo> list = customerService.list4Sim(v);
			obj.put("code", 200);
			obj.put("value", list.toArray());
			obj.put("message", "");
			obj.put("redirect", "");
		} catch (GlobalException e) {
			log.info("删除失败", e);
			obj.put("code", 404);
			obj.put("message", e.getMessage());
		}
		return obj;
	}

	/**
	 * 业务受理 自动补全功能 join联系人的并集 集合
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajax4FullJson.do")
	public JSONObject ajax4FullJson(CustomerVo v) throws GlobalException {
		JSONObject obj = new JSONObject();
		try {
			List<CustVo> list = customerService.list4Full(v);
			obj.put("code", 200);
			obj.put("value", list.toArray());
			obj.put("message", "");
			obj.put("redirect", "");
		} catch (GlobalException e) {
			log.info("删除失败", e);
			obj.put("code", 404);
			obj.put("message", e.getMessage());
		}
		return obj;
	}

	@ResponseBody
	@RequestMapping(value = "ajaxGetCust.do")
	public CustomerVo ajaxGetCust(CustomerVo v) throws GlobalException {
		if (StrUtils.isNotBlankOrNull(v.getId())) {
			v = customerService.findById(v.getId());
		} else if (StrUtils.isNotBlankOrNull(v.getName())) {
			v = customerService.find(v.getName());
		}
		return v;
	}

	/**
	 * 
	 * <p>
	 * Title: updateData
	 * </p>
	 * <p>
	 * Description:修改并提交
	 * </p>
	 * 
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#updateData(cn.core.framework.common.vo.Vo,
	 *      org.springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@RequestMapping(value = UPDATE_DATA)
	@ResponseBody
	public Status updateData(CustomerVo v, RedirectAttributes attr) throws GlobalException {
		try {
			v.setFstatus(Customer.ST_1);
			customerService.update(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * 
	 * <p>
	 * Title: addData
	 * </p>
	 * <p>
	 * Description:新增提交
	 * </p>
	 * 
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#addData(cn.core.framework.common.vo.Vo,
	 *      org.springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@RequestMapping(value = ADD_DATA)
	@ResponseBody
	public Status addData(CustomerVo v, RedirectAttributes attr) throws GlobalException {
		try {
			v.setFstatus(Customer.ST_1);
			customerService.add(v);
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * 
	 * <p>
	 * Title: save
	 * </p>
	 * <p>
	 * Description: 保存方法
	 * </p>
	 * 
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#save(cn.core.framework.common.vo.Vo,
	 *      org.springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@RequestMapping(value = SAVE)
	public ModelAndView save(CustomerVo v, RedirectAttributes attr) throws GlobalException {
		try {
			v.setFstatus(Customer.ST_0);
			customerService.save(v);
			status = new Status("保存成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败", e);
			status = new Status("保存失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", v.getId());

		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description:删除客户信息
	 * </p>
	 * 
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#delete(cn.core.framework.common.vo.Vo,
	 *      org.springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@RequestMapping(value = DELETE)
	@Log(operation = Log.Operation.UPDATE, content = "删除客户信息", module = "客户管理")
	public ModelAndView delete(CustomerVo v, RedirectAttributes attr) throws GlobalException {
		boolean flag = customerService.isDelete4All(v.getIds());
		try {
			if (flag) {
				baseService().update2del(v.getIds());
				status = new Status("删除成功", Status.STATUS_SUCCESS);
			} else {
				status = new Status("该客户不能被删除，请先删除关联数据！", Status.STATUS_SUCCESS);
			}
		} catch (GlobalException e) {
			log.info("删除失败", e);
			status = new Status("删除失败", Status.STATUS_ERROR);
		}

		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}

}