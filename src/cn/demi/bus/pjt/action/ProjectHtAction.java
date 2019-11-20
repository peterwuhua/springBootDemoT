package cn.demi.bus.pjt.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhuozhengsoft.pageoffice.FileSaver;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.export.ExportUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.pjt.service.IProjectHtService;
import cn.demi.bus.pjt.service.ISchemeService;
import cn.demi.bus.pjt.vo.ProjectVo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 合同签订
 * 
 * @author QuJunLong
 *
 */
@Controller("bus.projectHtAction")
@RequestMapping("/bus/projectHt")
public class ProjectHtAction extends BaseAction<ProjectVo> {

	public static final String SERVER_EXPORT_DIR = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");

	final String VIEW_PATH = "/bus/project/ht/project_ht";
	@Autowired
	private IProjectHtService projectHtService;
	@Autowired
	private IProgressLogService progressLogService;
	@Autowired
	private IFilesService filesService;
	@Autowired
	private ISchemeService schemeService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<ProjectVo> baseService() {
		return projectHtService;
	}

	/**
	 * 
	 * <p>Title: edit</p>   
	 * <p>Description: 跳转到编辑页</p>   
	 * @param v
	 * @return
	 * @throws GlobalException   
	 * @see cn.core.framework.common.action.BaseAction#edit(cn.core.framework.common.vo.Vo)
	 */
	@Override
	public ModelAndView edit(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = projectHtService.find(v.getId());
		}
		List<FilesVo> fileList = filesService.listByBusId(v.getId());
		v.setFileList(fileList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	@Override
	public ModelAndView show(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = projectHtService.findById(v.getId());
			ProgressLogVo logVo = progressLogService.find(v.getId(), EunmTask.PROJECT_QD.getStatus());
			mav.addObject("logVo", logVo);
		}
		List<FilesVo> fileList = filesService.listByBusId(v.getId());
		v.setFileList(fileList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_show");
		return mav;
	}

	/**
	 * 
	 * <p>Title: updateData</p>   
	 * <p>Description:保存并提交 </p>   
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException   
	 */
	@Override
	@Log(operation = Log.Operation.UPDATE, content = "更新合同信息", module = "合同签订")
	public Status updateData(ProjectVo v, RedirectAttributes attr) throws GlobalException {
		try {
			projectHtService.update(v);
			schemeService.executeSchedule();
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "gridData.do")
	public GridVo gridData(GridVo gridVo, ProjectVo v) throws GlobalException {
		v.setStatus(EunmTask.PROJECT_QD.getStatus());
		return projectHtService.gridData(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = "gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException {
		v.setStatus(EunmTask.PROJECT_QD.getStatus());
		return projectHtService.gridDatad(gridVo, v);
	}
    /**
     * 
     * @Title: saveData4Ht   
     * @Description: 重置合同或生成合同的方法
     * @param: @param vo
     * @param: @param attr
     * @param: @return
     * @param: @throws GlobalException
     * @param: @throws IOException      
     * @return: ModelAndView      
     * @throws
     */
	@RequestMapping(value = "saveData4Ht.do")
	public ModelAndView saveData4Ht(ProjectVo vo, RedirectAttributes attr) throws GlobalException, IOException {
		try {
			projectHtService.update(vo);
			ProjectVo v = projectHtService.find4Ht(vo.getId());
			v = createWordRepFile(v, v.getHtTemp());// 生成报告文件
			projectHtService.update2Ht(v);
			status = new Status("生成合同成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("生成合同失败", e);
			status = new Status("生成合同失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", vo.getId());
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}

	/**
	 * Description : 修改报告 <br>
	 * 
	 * @param v
	 * @param tempPath
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value = "editWord.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新合同内容", module = "合同签订")
	public ModelAndView editWord(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = projectHtService.findById(v.getId());
		mav.addObject("id", v.getId());
		mav.addObject("tempPath", v.getHtPath());
		mav.setViewName(getViewPath() + "_file_open");
		return mav;

	}

	/**
	 * Description : 在线保存报告 <br>
	 * 
	 * @param v
	 *            ReportVo
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxSaveFile.do")
	public void ajaxSaveFile(ProjectVo v) throws GlobalException {
		v = projectHtService.findById(v.getId());
		FileSaver fs = new FileSaver(request, response);
		String targetFile = SERVER_BASE + v.getHtPath().replace("/", "\\");
		fs.saveToFile(targetFile);
		fs.close();
	}

	@RequestMapping(value = "showWord.do")
	public ModelAndView showWord(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = projectHtService.findById(v.getId());
		mav.addObject("tempPath", v.getHtPath());
		mav.setViewName(getViewPath() + "_file_show");
		return mav;
	}

	public ProjectVo createWordRepFile(ProjectVo v, String source) throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(VO, v);
		String reportPath = UPLOAD_DIR + Constants.FILE_TYPE_PROJECT + File.separator + v.getId();
		String fileName = v.getHtNo() + "_ht.doc";// 生成的文件存放路径
		String targetPath = reportPath + File.separator + fileName;// 生成的文件存放路径
		v.setHtName(fileName);
		v.setHtPath(targetPath.replace("\\", "/"));
		String filePath = SERVER_BASE + reportPath;// 生成的文件存放路径
		File file = new File(filePath);
		if (!file.exists())
			file.mkdirs();
		OutputStream os = null;
		OutputStreamWriter osw = null;

		File docFile = new File(SERVER_BASE + targetPath);
		if (!docFile.exists()) {
			docFile.createNewFile();
		} else {
			docFile.delete();
			docFile.createNewFile();
		}
		try {
			Configuration cfg = new Configuration();
			cfg.setDefaultEncoding("utf-8");
			cfg.setDirectoryForTemplateLoading(new File(SERVER_BASE + SERVER_EXPORT_DIR));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate(source, "utf-8");

			os = new FileOutputStream(SERVER_BASE + targetPath);
			osw = new OutputStreamWriter(os);
			Writer docout = new BufferedWriter(osw);
			temp.process(dataMap, docout);
		} catch (Exception e) {
			Logger.getLogger(ExportUtils.class).error("生成文件出错", e);
		} finally {
			if (null != osw) {
				osw.flush();
				osw.close();
			}
			if (null != os) {
				os.flush();
				os.close();
			}
		}
		return v;
	}
}