package cn.demi.cus.fee.action;

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
import cn.demi.bus.pjt.service.IProjectService;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.cus.bill.service.IBillService;
import cn.demi.cus.bill.vo.BillVo;
import cn.demi.cus.fee.service.IFeeService;
import cn.demi.cus.fee.vo.FeeVo;

@Controller("cus.feeAction")
@RequestMapping("/cus/fee")
public class FeeAction extends BaseAction<FeeVo> {
	final String VIEW_PATH = "/cus/fee/fee";
	@Autowired
	private IFeeService feeService;
	@Autowired
	private IBillService billService;
	@Autowired
	private IProjectService projectService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<FeeVo> baseService() {
		return feeService;
	}

	@RequestMapping(value = GRID_PAGE)
	public ModelAndView gridPage(FeeVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = { GRID_DATA, GRID_DATA_JSON })
	public GridVo gridData(GridVo gridVo, FeeVo v) throws GlobalException {
		return feeService.gridData(gridVo, v);
	}

	@RequestMapping(value = GRID_PAGED)
	public ModelAndView gridPaged(FeeVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_ed_page");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = { GRID_DATAD })
	public GridVo gridDatad(GridVo gridVo, FeeVo v) throws GlobalException {
		return feeService.gridDatad(gridVo, v);
	}

	@RequestMapping(value = "noticePage.do")
	public ModelAndView noticePage(FeeVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_notice_page");
		return mav;
	}

	@RequestMapping(value = EDIT)
	public ModelAndView edit(FeeVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = feeService.findById(v.getId());
		BillVo billvo = billService.findById(v.getFpId());
		v.setBillVo(billvo);
		v.setSumDate(DateUtils.getCurrDateStr());
		v.setSumUserName(getCurrent().getUserName());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	@RequestMapping(value = SHOW)
	public ModelAndView show(FeeVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = baseService().findById(v.getId());
			BillVo billVo = billService.findById(v.getFpId());
			ProjectVo projectVo = projectService.findByHtId(billVo.getContractCode());
			billVo.setProjectVo(projectVo);
			billVo.setCustomerVo(projectVo.getCustVo().getCustomerVo());
			v.setBillVo(billVo);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_show");
		return mav;
	}

	@RequestMapping(value = "auditSuccess.do")
	public ModelAndView auditSuccess(FeeVo v) throws GlobalException {
		if (v != null && !StrUtils.isBlankOrNull(v.getId())) {
			v.setFstatus(Constants.CHARGE_STATUS_YYF);
			feeService.update(v);
			// 发票收费状态为“已预付”
			BillVo bvo = billService.findById(v.getFpId());
			bvo.setPstatus(Constants.CHARGE_STATUS_YYF);
			billService.update(bvo);
			// 合同所属的项目的收费状态由“未收费”变成“已预付”
			projectService.updatePaySt(v.getPjtId());

		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}

	@RequestMapping(value = "auditFailure.do")
	public ModelAndView auditFailure(FeeVo v) throws GlobalException {
		if (v != null && !StrUtils.isBlankOrNull(v.getId())) {
			// 逻辑删除费用
			feeService.update2del(v.getId());
			// 发票状态为提交中，收费状态为审批不通过
			BillVo bvo = billService.findById(v.getFpId());
			bvo.setFstatus(Constants.BILL_STATUS_WSP);
			bvo.setPstatus(Constants.BILL_STATUS_WTG);
			billService.update(bvo);

		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}

}