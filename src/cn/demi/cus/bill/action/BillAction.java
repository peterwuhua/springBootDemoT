package cn.demi.cus.bill.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.pjt.service.IProjectService;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.cus.bill.service.IBillService;
import cn.demi.cus.bill.vo.BillVo;

@Controller("cus.billAction")
@RequestMapping("/cus/bill")
public class BillAction extends BaseAction<BillVo> {
	final String VIEW_PATH = "/cus/bill/bill";
	@Autowired
	private IBillService billService;
	@Autowired
	private IProjectService projectService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<BillVo> baseService() {
		return billService;
	}

	@RequestMapping(value = GRID_PAGE)
	public ModelAndView gridPage(BillVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}

	@RequestMapping(value = "auditPage.do")
	public ModelAndView auditPage(BillVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_audit_page");
		return mav;
	}

	@RequestMapping(value = "viewPage.do")
	public ModelAndView viewPage(BillVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_view_page");
		return mav;
	}

	@RequestMapping(value = SHOW)
	public ModelAndView show(BillVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = baseService().findById(v.getId());

			ProjectVo projectVo = projectService.findByHtId(v.getContractCode());
			v.setProjectVo(projectVo);
			v.setCustomerVo(projectVo.getCustVo().getCustomerVo());

		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_show");
		return mav;
	}

	@RequestMapping(value = EDIT)
	public ModelAndView edit(BillVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		boolean isEdit = false;
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
			isEdit = true;

		}
		if (!isEdit)// 跳转新增页
		{
			String timer = String.valueOf(System.currentTimeMillis());
			String billno = DateUtils.format(new Date(), "yyyyMMdd") + timer.substring(timer.length() - 5, timer.length() - 1);
			v.setBillno(billno);
			v.setPerson(getCurrent().getUserName());
			v.setPersonId(getCurrent().getAccountId());
			v.setSupportDate(DateUtils.getCurrDateStr());
			v.setFstatus(Constants.BILL_STATUS_WSP);
		} else {

			ProjectVo projectVo = projectService.findByHtId(v.getContractCode());
			v.setProjectVo(projectVo);
			v.setCustomerVo(projectVo.getCustVo().getCustomerVo());
		}

		mav.addObject(VO, v);
		mav.addObject(IS_EDIT, isEdit);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	@RequestMapping(value = "auditEdit.do")
	public ModelAndView auditEdit(BillVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		BillVo vo = billService.findBillById(v.getId());
		mav.addObject(VO, vo);
		mav.setViewName(getViewPath() + "_audit");
		return mav;
	}

	@RequestMapping(value = "auditPage4tgPage.do")
	public ModelAndView auditPage4tgPage(BillVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_auditd_page");
		return mav;
	}

	@RequestMapping(value = "viewPage4ykjPage.do")
	public ModelAndView viewPage4ykjPage(BillVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_viewd_page");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "queryInfosByHtId.do")
	public Map<String, Object> queryInfosByHtId(BillVo v) throws GlobalException {
		Map<String, Object> obj = new HashMap<>();
		ProjectVo projectVo = projectService.findByHtId(v.getContractCode());
		obj.put("projectVo", projectVo);
		return obj;
	}

	@RequestMapping(value = "addOrUpdate.do")
	public ModelAndView addOrUpdate(BillVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (StrUtils.isBlankOrNull(v.getId())) {
			try {

				v.setFstatus(Constants.BILL_STATUS_WSP);
				billService.add(v);
				status = new Status("新增成功", Status.STATUS_SUCCESS);
			} catch (GlobalException e) {
				log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
				status = new Status("新增失败", Status.STATUS_ERROR);
			}
		} else {
			try {
				v.setFstatus(Constants.BILL_STATUS_WSP);
				billService.update(v);
				status = new Status("修改成功", Status.STATUS_SUCCESS);
			} catch (GlobalException e) {
				log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
				status = new Status("修改失败", Status.STATUS_ERROR);
			}
		}
		attr.addFlashAttribute(STATUS, status);
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = { GRID_DATA, GRID_DATA_JSON })
	public GridVo gridData(GridVo gridVo, BillVo v) throws GlobalException {
		return billService.gridData(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = { "auditData.do", "auditData.json" })
	public GridVo auditData(GridVo gridVo, BillVo v) throws GlobalException {
		return billService.gridData4Waudit(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = { "viewData.do", "viewData.json" })
	public GridVo viewData(GridVo gridVo, BillVo v) throws GlobalException {
		return billService.gridData4Wview(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = "auditPage4success.do")
	public GridVo auditPage4success(GridVo gridVo, BillVo v) throws GlobalException {
		return billService.gridData4audit(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = "viewPage4success.do")
	public GridVo viewPage4success(GridVo gridVo, BillVo v) throws GlobalException {
		return billService.gridData4view(gridVo, v);
	}

	@RequestMapping(value = "auditSuccess.do")
	public ModelAndView auditSuccess(BillVo v) throws GlobalException {
		if (v != null && !StrUtils.isBlankOrNull(v.getId())) {
			v.setFstatus(Constants.BILL_STATUS_WKJ);
			billService.update(v);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName(getViewPath() + "_audit_page");
		return mav;
	}

	@RequestMapping(value = "auditFailure.do")
	public ModelAndView auditFailure(BillVo v) throws GlobalException {
		if (v != null && !StrUtils.isBlankOrNull(v.getId())) {
			v.setFstatus(Constants.BILL_STATUS_WTG);
			billService.update(v);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName(getViewPath() + "_audit_page");
		return mav;
	}

//	@ResponseBody
//	@RequestMapping(value = "batchKjfp.do")
//	public Map<String, Object> batchKjfp(BillVo v) throws GlobalException {
//		Map<String, Object> obj = new HashMap<>();
//		String ids = v.getIds();
//		billService.updateStatus4KJ(ids);
//
//		status = new Status("修改成功", Status.STATUS_SUCCESS);
//		obj.put("status", status);
//		return obj;
//	}

	@ResponseBody
	@RequestMapping(value = "update2delete.do")
	public Map<String, Object> update2delete(BillVo v) throws GlobalException {
		Map<String, Object> obj = new HashMap<>();
		String ids = v.getIds();
		billService.update2del(ids);
		status = new Status("删除成功", Status.STATUS_SUCCESS);
		obj.put("status", status);
		return obj;
	}

	@RequestMapping(value = "operate.do")
	public ModelAndView operate(BillVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		boolean isEdit = false;
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
			isEdit = true;
		}
		ProjectVo projectVo = projectService.findByHtId(v.getContractCode());
		if (projectVo!=null)
		{
			v.setProjectVo(projectVo);
			v.setCustomerVo(projectVo.getCustVo().getCustomerVo());
		}
		mav.addObject(VO, v);
		mav.addObject(IS_EDIT, isEdit);
		mav.setViewName(getViewPath() + "_operate");
		return mav;
	}
	
	
	@RequestMapping(value = "fpykj.do")
	public ModelAndView fpykj(BillVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		String ids = v.getId();
		billService.updateStatus4KJ(ids);
		mav.setViewName(getViewPath() + "_view_page");
		return mav;
	}
	
	
	

}