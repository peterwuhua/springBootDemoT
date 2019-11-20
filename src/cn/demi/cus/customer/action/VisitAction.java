package cn.demi.cus.customer.action;

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
import cn.demi.cus.contract.service.IContractService;
import cn.demi.cus.customer.service.ICustomerService;
import cn.demi.cus.customer.service.IVisitService;
import cn.demi.cus.customer.vo.CustomerVo;
import cn.demi.cus.customer.vo.VisitVo;

@Controller("cus.visitAction")
@RequestMapping("/cus/visit")
public class VisitAction extends BaseAction<VisitVo> {
	final String VIEW_PATH = "/cus/visit/visit";
	@Autowired
	private IVisitService visitService;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IContractService contractService;
	@Autowired
	private IUserService userService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<VisitVo> baseService() {
		return visitService;
	}

	@Override
	public ModelAndView edit(VisitVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = visitService.findById(v.getId());
		} else if (null != v.getCustomerVo() && !StrUtils.isBlankOrNull(v.getCustomerVo().getId())) {
			CustomerVo customerVo = customerService.findById(v.getCustomerVo().getId());
			v.setCustomerVo(customerVo);
			v.setUserId(getCurrent().getAccountId());
			v.setUserName(getCurrent().getUserName());
			v.setVdate(DateUtils.getCurrDateStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	@Override
	@Log(operation = Log.Operation.ADD, content = "新增回访信息", module = "客户回访管理")
	public Status addData(VisitVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}

	@Override
	@Log(operation = Log.Operation.UPDATE, content = "更新回访信息", module = "客户回访管理")
	public Status updateData(VisitVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}

	@Override
	@Log(operation = Log.Operation.DELETE, content = "删除回访信息", module = "客户回访管理")
	public ModelAndView delete(VisitVo v, RedirectAttributes attr) throws GlobalException {
		return super.delete(v, attr);
	}

	/**
	 * 访问page页 data数据来源cust
	 */
	@Override
	public ModelAndView gridPage(VisitVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}

	@Override
	public ModelAndView gridPaged(VisitVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_ed_page");
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Title: gridData
	 * </p>
	 * <p>
	 * Description:
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
	@RequestMapping(value = "gridData4Visit.do")
	public GridVo gridData4Visit(GridVo gridVo, VisitVo v) throws GlobalException {
		// 判断当前登录用户的角色
		String name = userService.saleJLAuth(getCurrent().getAccountId());
		if (name.equals(UserVo.USER_AUTH_XSJL)) {
			visitService.saveVisitsByLoginUser(UserVo.USER_AUTH_XSJL);
		} else {
			visitService.saveVisitsByLoginUser(UserVo.USER_AUTH_XSRY);
		}
		return visitService.gridData4Visit(gridVo, v);
	}

}