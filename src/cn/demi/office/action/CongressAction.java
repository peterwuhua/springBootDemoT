package cn.demi.office.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.office.service.ICongressService;
import cn.demi.office.vo.CongressVo;

/**
 * 
 * @ClassName: CongressAction
 * @Description:会务申请Action
 * @author: 吴华
 * @date: 2019年3月5日 下午6:02:11
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Controller("office.congressAction")
@RequestMapping("/office/congress")
public class CongressAction extends BaseAction<CongressVo> {
	final String VIEW_PATH = "/office/congress/congress";
	@Autowired
	private ICongressService congressService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<CongressVo> baseService() {
		return congressService;
	}

	
	
	
	/**
	 * 
	 * @Title: save4Data @Description: 保存方法 @param: @param v @param: @param
	 * attr @param: @return @param: @throws GlobalException @return: Status @throws
	 */
	@RequestMapping(value = "save4Data.do")
	public ModelAndView save4Data(CongressVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().save(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", v.getId());
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}

	/**
	 * 
	 * @Title: update4Data @Description: 更新并提交 @param: @param v @param: @param
	 * attr @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@ResponseBody
	@RequestMapping(value = "update4Data.do")
	public Status update4Data(CongressVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().save(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		
		return status;
	}

	/**
	 * 
	 * @Title: add4Data @Description: 新增并提交 @param: @param v @param: @param
	 * attr @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@ResponseBody
	@RequestMapping(value = "add4Data.do")
	@Log(operation = Log.Operation.ADD, content = "申请单", module = "行政模块：会务申请")
	public Status add4Data(CongressVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().save(v);
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * Title: edit
	 * Description: 跳转编辑页面
	 * @param v
	 * @throws GlobalException
	 */
	@RequestMapping(value = EDIT)
	public ModelAndView edit(CongressVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		boolean isEdit = false;
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
			isEdit = true;
		}
		v.setSqrId(getCurrent().getAccountId());
		v.setSqr(getCurrent().getUserName());
		v.setSupportDate(DateUtils.getCurrDateStr());
		mav.addObject(VO, v);
		mav.addObject(IS_EDIT, isEdit);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

}