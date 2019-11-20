package cn.demi.bus.file.action;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.Action;
import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.file.service.IArchiveService;
import cn.demi.bus.file.service.IArchiveTypeService;
import cn.demi.bus.file.vo.ArchiveTypeVo;
import cn.demi.bus.file.vo.ArchiveVo;

@Controller("bus.archiveAction")
@RequestMapping("/bus/archive")
public class ArchiveAction extends BaseAction<ArchiveVo> {
	final String VIEW_PATH = "/bus/archive/file/archive";
	@Autowired
	private IArchiveService archiveService;
	@Autowired
	private IArchiveTypeService archiveTypeService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<ArchiveVo> baseService() {
		return archiveService;
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
			v = archiveService.findById(v.getId());
		} else {
			v.setUserId(getCurrent().getAccountId());
			v.setUserName(getCurrent().getUserName());
			v.setTime(DateUtils.getCurrDateTimeStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	@RequestMapping(value = "save4Data.do")
	@Log(operation = Log.Operation.SAVE, content = "保存档案", module = "档案管理")
	public ModelAndView save4Data(ArchiveVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().save(v);
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

	@RequestMapping(value = "add4Data.do")
	@ResponseBody
	@Log(operation = Log.Operation.ADD, content = "新增档案", module = "档案管理")
	public Status add4Data(ArchiveVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}

	@RequestMapping(value = "update4Data.do")
	@ResponseBody
	@Log(operation = Log.Operation.UPDATE, content = "修改档案", module = "档案管理")
	public Status update4Data(ArchiveVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}

	@Override
	public ModelAndView update2del(ArchiveVo v, RedirectAttributes attr) throws GlobalException {
		try {
			archiveService.update2del(v.getIds());
			status = new Status("删除成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败", e);
			status = new Status("删除失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("dirIds", v.getDirIds());
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}

	@RequestMapping(value = DOWNLOAD)
	public ResponseEntity<byte[]> download(@RequestParam(value = "filePath", required = true) String filePath,
			@RequestParam(value = "trueName", required = true) String trueName) throws GlobalException {
		String ctxPath = Action.SERVER_BASE;
		String downLoadPath = ctxPath + filePath.replace("/", File.separator);
		return down(trueName, downLoadPath);
	}
}