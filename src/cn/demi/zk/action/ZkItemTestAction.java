package cn.demi.zk.action;

import java.util.ArrayList;
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
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.init.std.service.IItemMethodService;
import cn.demi.init.std.vo.MethodVo;
import cn.demi.zk.service.IZkItemTestService;
import cn.demi.zk.service.IZkProgressLogService;
import cn.demi.zk.vo.ZkItemTestVo;
import cn.demi.zk.vo.ZkProgressLogVo;

@Controller("zk.itemTestAction")
@RequestMapping("/zk/itemTest")
public class ZkItemTestAction extends BaseAction<ZkItemTestVo> {
	final String VIEW_PATH = "/zk/item_test/item_test";

	@Autowired
	private IZkItemTestService zkItemTestService;
	@Autowired
	private IItemMethodService itemMethodService;
	@Autowired
	private IZkProgressLogService progressLogService;
	@Autowired
	private IFilesService filesService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<ZkItemTestVo> baseService() {
		return zkItemTestService;
	}

	@Override
	public ModelAndView edit(ZkItemTestVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<ZkItemTestVo> itemList = null;
		if (null != v && !StrUtils.isBlankOrNull(v.getIds())) {
			itemList = zkItemTestService.listByIds(v.getIds());
			// 设置附件
			List<FilesVo> fileList = new ArrayList<FilesVo>();
			for (ZkItemTestVo itemTestVo : itemList) {
				if (null != itemTestVo.getFileList()) {
					fileList.addAll(itemTestVo.getFileList());
				}
			}
			v.setFileList(fileList);
		}
		mav.addObject("itemList", itemList);
		// 项目检测方法
		List<MethodVo> methodList = null;
		if (itemList.size() > 0) {
			ZkItemTestVo itVo = itemList.get(0);
			v.setIsBack(itVo.getIsBack());
			v.setId(itVo.getId());
			String itemId = itVo.getItemId();
			methodList = itemMethodService.findMethodList(itemId);
			v.setRemark(itVo.getRemark());
		}
		mav.addObject("methodList", methodList);
		// 若为退回项目，获取退回日志记录
		List<ZkProgressLogVo> progressLogList = null;
		if (itemList.size() == 1 && v.getIsBack() == 1) {
			progressLogList = progressLogService.findList(v.getId());
		}
		mav.addObject("progressLogList", progressLogList);
		// 当前检测人
		v.setTestMan(CurrentUtils.getCurrent().getUserName());
		v.setTestManId(CurrentUtils.getCurrent().getAccountId());
		v.setTestTime(DateUtils.getCurrDateStr());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	@Override
	public ModelAndView update(ZkItemTestVo v, RedirectAttributes attr) throws GlobalException {
		try {
			// if(!v.getFile().isEmpty()){
			// v.setFileName(getFileName(v));
			// v.setFilePath(getPath(v));
			// upload(v.getFile(), v.getFilePath());
			// }

			zkItemTestService.update(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}

	@RequestMapping(value = "update4Data.do")
	@Log(operation = Log.Operation.UPDATE, content = "保存录入结果", module = "质控数据录入")
	public ModelAndView update4Data(ZkItemTestVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file)
			throws GlobalException {
		// 文件上传
		List<FilesVo> fileList = uploads(file, null, Constants.FILE_TYPE_ZK);
		for (ZkItemTestVo itVo : v.getItemVoList()) {
			for (FilesVo filesVo : fileList) {
				filesVo.setBusId(itVo.getId());
			}
			filesService.saveFiles(fileList);
		}
		try {
			baseService().update(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "gridData.do")
	public GridVo gridData(GridVo gridVo, ZkItemTestVo v) throws GlobalException {
		return zkItemTestService.gridData(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = "gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, ZkItemTestVo v) throws GlobalException {
		return zkItemTestService.gridDatad(gridVo, v);
	}
}