package cn.demi.bus.file.action;

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
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.file.service.IArchiveConfirmService;
import cn.demi.bus.file.service.IArchiveTypeService;
import cn.demi.bus.file.vo.ArchiveTypeVo;
import cn.demi.bus.file.vo.ArchiveVo;

/**
 * 
 * @ClassName: ArchiveConfirmAction
 * @Description:归档确认
 * @author: 吴华
 * @date: 2019年4月28日 上午11:01:21
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Controller("bus.archiveConfirmAction")
@RequestMapping("/bus/archiveConfirm")
public class ArchiveConfirmAction extends BaseAction<ArchiveVo> {
	final String VIEW_PATH = "/bus/archive/file/confirm/archive";
	@Autowired
	private IArchiveConfirmService archiveConfirmService;
	@Autowired
	private IArchiveTypeService archiveTypeService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<ArchiveVo> baseService() {
		return archiveConfirmService;
	}

	@Override
	public ModelAndView gridPage(ArchiveVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		ArchiveTypeVo typeVo = new ArchiveTypeVo();
		if (StrUtils.isNotBlankOrNull(v.getDirIds())) {
			typeVo = archiveTypeService.findById(v.getDirIds());
		}
		v.setArchiveTypeVo(typeVo);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}

	@Override
	public ModelAndView edit(ArchiveVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dirIds", v.getDirIds());
		ArchiveTypeVo typeVo = new ArchiveTypeVo();
		if (StrUtils.isNotBlankOrNull(v.getDirIds())) {
			typeVo = archiveTypeService.findById(v.getDirIds());
		} else {
			typeVo = archiveTypeService.findRoot();
		}
		v.setArchiveTypeVo(typeVo);
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = archiveConfirmService.findById(v.getId());
		} else {
			v.setUserId(getCurrent().getAccountId());
			v.setUserName(getCurrent().getUserName());
			v.setTime(DateUtils.getCurrDateTimeStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	/**
	 * 
	 * <p>Title: updateData</p>   
	 * <p>Description: 提交通过</p>   
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException   
	 * @see cn.core.framework.common.action.BaseAction#updateData(cn.core.framework.common.vo.Vo, org.springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@RequestMapping(value = "updateData.do")
	@ResponseBody
	@Log(operation = Log.Operation.UPDATE, content = "确认通过", module = "归档管理")
	public Status updateData(ArchiveVo v, RedirectAttributes attr) throws GlobalException {
		try {
			archiveConfirmService.update(v);
			status = new Status("更新成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "更新失败", e);
			status = new Status("更新失败", Status.STATUS_ERROR);
		}
		return status;
	}



}