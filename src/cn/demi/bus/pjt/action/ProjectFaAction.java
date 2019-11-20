package cn.demi.bus.pjt.action;

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
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.ICodeService;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.pjt.service.ICustMaterialService;
import cn.demi.bus.pjt.service.ICustPointService;
import cn.demi.bus.pjt.service.ICustWorkService;
import cn.demi.bus.pjt.service.IProjectFaService;
import cn.demi.bus.pjt.service.ISchemePointService;
import cn.demi.bus.pjt.service.ISurveyService;
import cn.demi.bus.pjt.vo.CustMaterialVo;
import cn.demi.bus.pjt.vo.CustPointVo;
import cn.demi.bus.pjt.vo.CustWorkVo;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.bus.pjt.vo.SchemePointVo;
import cn.demi.bus.pjt.vo.SurveyVo;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;
/**
 * 方案编制
 * @author QuJunLong
 *
 */
@Controller("bus.projectFaAction")
@RequestMapping("/bus/projectFa")
public class ProjectFaAction extends BaseAction<ProjectVo> {
	public static final String SERVER_EXPORT_DIR = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");
	public static final String SERVER_EXPORT_DIR2 = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");

	final String VIEW_PATH = "/bus/project/fa/project_fa";
	@Autowired
	private IProjectFaService projectFaService;
	@Autowired 
	private IProgressLogService progressLogService;
	@Autowired
	private IFilesService filesService;
	@Autowired
	private ISurveyService surveyService;
	@Autowired 
	private ISampTypeService sampTypeService;
	@Autowired 
	private ISchemePointService schemePointService;
	@Autowired 
	private ICustPointService custPointService;
	@Autowired 
	private ICustMaterialService custMaterialService;
	@Autowired 
	private ICustWorkService custWorkService;
	@Autowired 
	private ICodeService codeService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<ProjectVo> baseService() {
		return projectFaService;
	}
	@Override
	public ModelAndView edit(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=projectFaService.find(v.getId());
		}
		//最近一次日志
		if(null!=v.getIsBack()&&v.getIsBack().equals(Constants.Y)) {
			ProgressLogVo logVo=progressLogService.findBack(v.getId());
			mav.addObject("logVo", logVo);
		}
		//附件
		List<FilesVo> fileList=filesService.listByBusId(v.getId());
		v.setFileList(fileList);
		mav.addObject(VO, v);
		if(v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)||v.getSampType().equals(EnumBus.SAMP_TYPE_GW)) {
			//检测点信息
			List<CustPointVo> potList=custPointService.listByProjectId(v.getId());
			mav.addObject("potList", potList);
			//物料集合
			List<CustMaterialVo> mtList=custMaterialService.listByProjectId(v.getId());
			mav.addObject("mtList", mtList);
			//采样方式
			mav.addObject("cyList",codeService.listByCode("cy-type"));
			//写实调查集合
			List<CustWorkVo> workList=custWorkService.listByProjectId(v.getId());
			mav.addObject("workList", workList);
			mav.setViewName(getViewPath()+"_zw_edit");
		}else {
			//本项目的样品类型集合
			List<SampTypeVo> sampTypeList=sampTypeService.listByIds(v.getSampTypeId());
			mav.addObject("sampTypeList", sampTypeList);
			//踏勘信息
			SurveyVo surveyVo=surveyService.findByProjectId(v.getId());
			mav.addObject("surveyVo", surveyVo);
			mav.setViewName(getViewPath()+"_edit");
		}
		return mav;
	}
	@Override
	public ModelAndView show(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = projectFaService.findById(v.getId());
		}
		List<FilesVo> fileList=filesService.listByBusId(v.getId());
		v.setFileList(fileList);
		mav.addObject(VO, v);
		if(v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)||v.getSampType().equals(EnumBus.SAMP_TYPE_GW)) {
			//检测点信息
			List<CustPointVo> potList=custPointService.listByProjectId(v.getId());
			mav.addObject("potList", potList);
			//物料集合
			List<CustMaterialVo> mtList=custMaterialService.listByProjectId(v.getId());
			mav.addObject("mtList", mtList);
			//写实调查集合
			List<CustWorkVo> workList=custWorkService.listByProjectId(v.getId());
			mav.addObject("workList", workList);
			mav.setViewName(getViewPath()+"_zw_show");
		}else {
			SurveyVo surveyVo=surveyService.findByProjectId(v.getId());
			mav.addObject("surveyVo", surveyVo);
			mav.setViewName(getViewPath()+"_show");
		}
		return mav;
	}
	@ResponseBody
	@RequestMapping(value="update4Data.do")
	@Log(operation=Log.Operation.UPDATE,content="更新方案信息",module="方案编制")
	public Status update4Data(ProjectVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException {
		try {
			projectFaService.update(v);
			//文件上传
			List<FilesVo> fileList=uploads(file, v.getId(),Constants.FILE_TYPE_PROJECT);
			filesService.saveFiles(fileList);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存方案信息",module="方案编制")
	public Status save4Data(ProjectVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException {
		try {
			projectFaService.save(v);
			//文件上传
			List<FilesVo> fileList=uploads(file, v.getId(),Constants.FILE_TYPE_PROJECT);
			filesService.saveFiles(fileList);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.error("保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
	@ResponseBody
	@RequestMapping(value="addPoint.do")
	@Log(operation=Log.Operation.SAVE,content="添加点位信息",module="方案编制")
	public Status addPoint(SchemePointVo v) throws GlobalException {
		try {
			schemePointService.addSp(v.getSchemeVo().getId(),v.getProjectId(),v.getIds());
			status = new Status("添加成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.error("添加失败",e);
			status = new Status("添加失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="deletePoint.do")
	@Log(operation=Log.Operation.SAVE,content="删除点位信息",module="方案编制")
	public Status deletePoint(SchemePointVo v, RedirectAttributes attr) throws GlobalException {
		try {
			schemePointService.delete(v.getId());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.error("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		return status;
	}
	//根据踏勘信息添加检测点    职业
	@ResponseBody
	@RequestMapping(value="addPoint4Tk.do")
	@Log(operation=Log.Operation.ADD,content="添加检测点信息",module="方案编制")
	public Status addPoint4Tk(ProjectVo v) throws GlobalException {
		try {
			projectFaService.addPoint(v.getId());
			status = new Status("添加成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.error("添加失败",e);
			status = new Status("添加失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
	@RequestMapping(value = DOWNLOAD)
	public ResponseEntity<byte[]> download(
			@RequestParam(value = "filePath", required = true) String filePath,
			@RequestParam(value = "trueName", required = true) String trueName) throws GlobalException {
		String ctxPath = SERVER_EXPORT_DIR2;
		String downLoadPath = ctxPath + filePath.replace("/", File.separator);  
        return down(trueName, downLoadPath);
	}
	
	
	
	
}