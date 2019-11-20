package cn.demi.office.action;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.ICodeService;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.office.service.ICmService;
import cn.demi.office.service.IOfficeAuditHistoryService;
import cn.demi.office.vo.CmVo;
import cn.demi.office.vo.OfficeAuditHistoryVo;

@Controller("office.cmAction")
@RequestMapping("/office/cm")
public class CmAction extends BaseAction<CmVo> {
	protected static final String DOWNLOAD_DIR = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");
	final String VIEW_PATH = "/office/cm/cm";
	@Autowired
	private ICmService cmService;
	@Autowired
	private ICodeService codeService;
	@Autowired
	private IFilesService filesService;
	@Autowired
	private IOfficeAuditHistoryService officeAuditHisService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<CmVo> baseService() {
		return cmService;
	}

	@Override
	public ModelAndView edit(CmVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
		} else {
			v.setSdate(DateUtils.getCurrDateStr());
			v.setViewIds(getCurrent().getAccountId());
			v.setViewNames(getCurrent().getUserName());
			v.setUserIds(getCurrent().getAccountId());
			v.setUserNames(getCurrent().getUserName());
			v.setUserId(getCurrent().getAccountId());
			v.setUserName(getCurrent().getUserName());
			v.setPrice("0.0");
			v.setHours("0.0");
		}
		List<FilesVo> fileList=filesService.listByBusId(v.getId());
		v.setFileList(fileList);
		List<String> typeList = codeService.listByCode("xzgl-type"); // 行政类型
		mav.addObject("typeList", typeList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	@Override
	public ModelAndView show(CmVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
			List<FilesVo> fileList=filesService.listByBusId(v.getId());
			v.setFileList(fileList);
		}
		List<OfficeAuditHistoryVo> auditList = officeAuditHisService.listByCmId(v.getId());
		mav.addObject("auditList",auditList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}

	@RequestMapping(value="save4Data.do")
	@Log(operation = Log.Operation.ADD, content = "申请单保存", module = "行政模块：申请单")
	public ModelAndView save4Data(CmVo v, RedirectAttributes attr,@RequestParam(value="files", required=false)MultipartFile[] files) throws GlobalException {
		try {
			cmService.save(v);
			//文件上传
			List<FilesVo> fileList=uploads(files, v.getId(),Constants.FILE_TYPE_CM);
			filesService.saveFiles(fileList);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", v.getId());
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}

	@RequestMapping(value="add4Data.do")
	@ResponseBody
	@Log(operation = Log.Operation.ADD, content = "申请单新增", module = "行政模块：申请单")
	public Status add4Data(CmVo v, RedirectAttributes attr,@RequestParam(value="files", required=false)MultipartFile[] files) throws GlobalException {
		try {
			cmService.add(v);
			//文件上传
			List<FilesVo> fileList=uploads(files, v.getId(),Constants.FILE_TYPE_CM);
			filesService.saveFiles(fileList);
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping(value="update4Data.do")
	@ResponseBody
	@Log(operation = Log.Operation.UPDATE, content = "申请单修改", module = "行政模块：申请单")
	public Status update4Data(CmVo v, RedirectAttributes attr,@RequestParam(value="files", required=false)MultipartFile[] files) throws GlobalException {
		try {
			cmService.update(v);
			//文件上传
			List<FilesVo> fileList=uploads(files, v.getId(),Constants.FILE_TYPE_CM);
			filesService.saveFiles(fileList);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * 
	 * <p>Title: download</p>   
	 * <p>Description: 文件下载</p>   
	 * @param filePath
	 * @param trueName
	 * @return
	 * @throws GlobalException   
	 * @see cn.core.framework.common.action.BaseAction#download(java.lang.String, java.lang.String)
	 */
	@RequestMapping(value = DOWNLOAD)
	public ResponseEntity<byte[]> download(
			@RequestParam(value = "filePath", required = true) String filePath,
			@RequestParam(value = "trueName", required = true) String trueName) throws GlobalException {
		String ctxPath = DOWNLOAD_DIR + filePath;
		String downLoadPath = ctxPath.replace("/", File.separator);
        return down(trueName, downLoadPath);
	}
	
}