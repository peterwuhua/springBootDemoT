package cn.demi.cus.customer.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.ICodeService;
import cn.demi.base.system.service.IUserService;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.service.IContactsService;
import cn.demi.cus.customer.service.ICustomerAuditService;
import cn.demi.cus.customer.service.INormalListService;
import cn.demi.cus.customer.vo.ContactsVo;
import cn.demi.cus.customer.vo.CustomerVo;

/**
 * 
 * @ClassName: CustomerAuditAction
 * @Description:客户审核页面
 * @author: 吴华
 * @date: 2019年1月24日 下午5:26:44
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Controller("cus.customerAuditAction")
@RequestMapping("/cus/customerAudit")
public class CustomerAuditAction extends BaseAction<CustomerVo> {

	protected static final String AUDIT_SUCCESS = "审批通过";

	@Autowired
	private ICustomerAuditService customerAuditService;
	@Autowired
	private INormalListService normalListService;
	@Autowired
	private ICodeService codeService;
	@Autowired
	private IContactsService contactsService;
	@Autowired
	private IUserService userService;

	final String VIEW_PATH = "/cus/customerAudit/customerAudit";

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<CustomerVo> baseService() {
		return customerAuditService;
	}

	/**
	 * 
	 * @Title: gridPage @Description: 客户审核列表页 @param: @param
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
	 * @Title: gridData4Used @Description: 根据条件查询 @param: @param
	 *         gridVo @param: @param v @param: @return @param: @throws
	 *         GlobalException @return: GridVo @throws
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4Used.do")
	public GridVo gridData4Used(GridVo gridVo, CustomerVo v) throws GlobalException {
		return customerAuditService.gridData(gridVo, v);
	}

	/**
	 * 
	 * @Title: editPage @Description: 审核页面 @param: @param
	 *         v @param: @return @param: @throws GlobalException @return:
	 *         ModelAndView @throws
	 */
	@Override
	public ModelAndView edit(CustomerVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = customerAuditService.findById(v.getId());
		}
		v.setSumUserName(getCurrent().getUserName());// 审核人

		Date now = new Date();
		String today = DateUtils.format(now);
		v.setSumDate(today);// 审核日期
		List<ContactsVo> contactsList = contactsService.listByCustId(v.getId());
		List<String> cusCateList = codeService.listByCode("cus-cates");// 客户类型列表
		mav.addObject(VO, v);
		mav.addObject("contactsList", contactsList);

		mav.addObject("cusCateList", cusCateList);
		mav.setViewName(getViewPath() + "_audit");
		return mav;
	}

	/**
	 * 
	 * @Title: auditSuccess @Description: 审批通过 @param: @param
	 *         v @param: @return @param: @throws GlobalException @return:
	 *         ModelAndView @throws
	 */
	@RequestMapping(value = "auditSuccess.do")
	public ModelAndView auditSuccess(CustomerVo v) throws GlobalException {
		if (v != null && !StrUtils.isBlankOrNull(v.getId())) {
			v.setFstatus(Customer.ST_2); // 审批通过
			v.setAuditStatus(Customer.ST_1);
			v.setGjStatus(Constants.CUS_GJ_STATUS_YQ);
			// 将审批状态更新为{审批通过}
			customerAuditService.update(v);
			// 查询出公司下的所有联系人
			List<ContactsVo> contacts = contactsService.listByCustId(v.getId());
			// 并新增一条记录到我的客户跟踪列表
			String auth = v.getAuth();
			customerAuditService.add2mySaleContact(contacts, auth, v);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_2_GRID_PAGE);
		return mav;
	}

	/**
	 * 
	 * @Title: auditFailure @Description: 审批不通过 @param: @param
	 *         v @param: @return @param: @throws GlobalException @return:
	 *         ModelAndView @throws
	 */
	@RequestMapping(value = "auditFailure.do")
	public ModelAndView auditFailure(CustomerVo v) throws GlobalException {
		if (v != null && !StrUtils.isBlankOrNull(v.getId())) {
			v.setFstatus(Customer.ST_F1);
			v.setAuditStatus(Customer.ST_2);
			customerAuditService.update(v);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_2_GRID_PAGE);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = { GRID_DATA, GRID_DATA_JSON })
	public GridVo gridData(GridVo gridVo, CustomerVo v) throws GlobalException {
		// 查询当前用户是销售还是经理
		String auth = userService.saleJLAuth(getCurrent().getAccountId());
		v.setAuth(auth);
		return customerAuditService.gridData(gridVo, v);
	}

}
