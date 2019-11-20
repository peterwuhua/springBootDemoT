package cn.demi.office.action;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.office.service.ICmAuditService;
import cn.demi.office.service.IOfficeAuditHistoryService;
import cn.demi.office.vo.CmVo;
import cn.demi.office.vo.OfficeAuditHistoryVo;

@Controller("office.cmAuditAction")
@RequestMapping("/office/cmAudit")
public class CmAuditAction extends BaseAction<CmVo> {
	protected static final String DOWNLOAD_DIR = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");
	final String VIEW_PATH = "/office/cm/cm_audit";
	@Autowired 
	private ICmAuditService cmAuditService;	
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
		return cmAuditService;
	}
	
	@Override
	public ModelAndView edit(CmVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
		}
		v.setAuditTime(DateUtils.getCurrDateTimeStr());
		List<FilesVo> fileList=filesService.listByBusId(v.getId());
		v.setFileList(fileList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
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
		mav.addObject(VO, v);
		mav.addObject("auditList",auditList);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@Override
	public GridVo gridData(GridVo gridVo, CmVo v) throws GlobalException {
		return super.gridData(gridVo, v);
	}
 
	@ResponseBody
	@RequestMapping(value ="updateData")
	@Log(operation=Log.Operation.UPDATE,content="审批更新",module="行政模块：申请单")
	public Status updateData(CmVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	
	@RequestMapping(value = DOWNLOAD)
	public ResponseEntity<byte[]> download(
			@RequestParam(value = "filePath", required = true) String filePath,
			@RequestParam(value = "trueName", required = true) String trueName) throws GlobalException {
		String ctxPath = DOWNLOAD_DIR + filePath;
		String downLoadPath = ctxPath.replace("/", File.separator);
        return down(trueName, downLoadPath);
	}
}