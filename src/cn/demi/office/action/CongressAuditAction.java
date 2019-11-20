package cn.demi.office.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.office.service.ICongressAuditService;
import cn.demi.office.vo.CongressVo;

/**
 * 
 * @ClassName: congressAuditAction
 * @Description:会务审核Action
 * @author: 吴华
 * @date: 2019年3月5日 下午6:02:11
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Controller("office.congressAuditAction")
@RequestMapping("/office/congressAudit")
public class CongressAuditAction extends BaseAction<CongressVo> {
	final String VIEW_PATH = "/office/congress/congress_audit";
	final String VIEWD_PATH = "/office/congress/congress_auditd";
	@Autowired
	private ICongressAuditService congressAuditService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<CongressVo> baseService() {
		return congressAuditService;
	}

	/**
	 * 
	 * <p>
	 * Title: gridPage
	 * </p>
	 * <p>
	 * Description: 会务审批未提交列表页面
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#gridPage(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value = GRID_PAGE)
	public ModelAndView gridPage(CongressVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = { GRID_DATA })
	public GridVo gridData(GridVo gridVo, CongressVo v) throws GlobalException {
		return congressAuditService.gridData(gridVo, v);
	}

	/**
	 * 
	 * <p>
	 * Title: gridPaged
	 * </p>
	 * <p>
	 * Description: 会务审批已提交列表页面
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#gridPaged(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value = GRID_PAGED)
	public ModelAndView gridPaged(CongressVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(VIEWD_PATH + "_page");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = { GRID_DATAD })
	public GridVo gridDatad(GridVo gridVo, CongressVo v) throws GlobalException {
		return congressAuditService.gridDatad(gridVo, v);
	}

	/**
	 * 
	 * <p>
	 * Title: edit
	 * </p>
	 * <p>
	 * Description: 跳转到编辑页
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#edit(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value = EDIT)
	public ModelAndView edit(CongressVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		boolean isEdit = false;
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
			isEdit = true;
		}
		v.setSumUserId(getCurrent().getAccountId());// 获取审批人员id
		v.setSumUserName(getCurrent().getUserName());// 获取审批人员名称
		v.setSumDate(DateUtils.getCurrDateStr());// 获取当前日期

		mav.addObject(VO, v);
		mav.addObject(IS_EDIT, isEdit);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	/**
	 * 
	 * @Title: auditSuccess @Description: 审批通过 @param: @param
	 * v @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@ResponseBody
	@RequestMapping(value = "auditSuccess.do")
	public Status auditSuccess(CongressVo v) throws GlobalException {
		if (v != null && !StrUtils.isBlankOrNull(v.getId())) {
			try {
				congressAuditService.updateAuditInfo(v);
				status = new Status("提交成功", Status.STATUS_SUCCESS);
			} catch (GlobalException e) {
				log.info("提交失败", e);
				status = new Status("提交失败", Status.STATUS_ERROR);
			}
		}
		return status;
	}

	/**
	 * 
	 * @Title: auditFailure @Description: 审批不通过 @param: @param
	 * v @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@ResponseBody
	@RequestMapping(value = "auditFailure.do")
	public Status auditFailure(CongressVo v) throws GlobalException {
		if (v != null && !StrUtils.isBlankOrNull(v.getId())) {
			try {
				congressAuditService.updateAuditInfo(v);
				status = new Status("提交成功", Status.STATUS_SUCCESS);
			} catch (GlobalException e) {
				log.info("提交失败", e);
				status = new Status("提交失败", Status.STATUS_ERROR);
			}
		}
		return status;
	}

}