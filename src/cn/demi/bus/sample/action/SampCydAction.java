package cn.demi.bus.sample.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhuozhengsoft.pageoffice.FileSaver;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.export.ExportUtils;
import cn.demi.bus.sample.service.ISampCydService;
import cn.demi.bus.sample.vo.SampCydVo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

@Controller("bus.sampCydAction")
@RequestMapping("/bus/sampCyd")
public class SampCydAction extends BaseAction<SampCydVo> {
	public static final String EXPORT_PATH = ApplicationUtils.getValue("config.upload.samp.task").toString().replace("\\", File.separator);
	public static final String TEMP_PATH = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");

	final String VIEW_PATH = "/bus/task/cyd/cyd";
	@Autowired private ISampCydService sampCydService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<SampCydVo> baseService() {
		return sampCydService;
	}
	
	@Override
	public ModelAndView show(SampCydVo v) throws GlobalException {
		return super.show(v);
	}
	
	// 选择采样单模板页面 环境
	@RequestMapping(value = "selectCyd.do")
	public ModelAndView selectCyd(SampCydVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		SampCydVo vo = sampCydService.findById(v.getId());
		mav.addObject(VO, vo);
		mav.setViewName(getViewPath() + "_select");
		return mav;
	}
	// 选择采样单模板页面 职卫
	@RequestMapping(value = "selectZwCyd.do")
	public ModelAndView selectZwCyd(SampCydVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		SampCydVo vo = sampCydService.findById(v.getId());
		mav.addObject(VO, vo);
		mav.setViewName(getViewPath() + "_zw_select");
		return mav;
	}
	
	// 编辑采样单文件
	@RequestMapping(value = "editCydFile.do")
	public ModelAndView editCydFile(SampCydVo v) throws GlobalException, IOException {
		ModelAndView mav = new ModelAndView();
		SampCydVo vo = sampCydService.find4File(v.getId());
		if (StrUtils.isBlankOrNull(vo.getFilePath())) {
			vo = createFile(vo, v.getTemp());// 生成采样单
			vo.setTemp(v.getTemp());
			sampCydService.updateFile(vo);
		}
		File f = new File(SERVER_BASE + vo.getFilePath());
		if (!f.exists() || !f.isFile()) {
			vo = createFile(vo, v.getTemp());// 生成采样单
			vo.setTemp(v.getTemp());
			sampCydService.updateFile(vo);
		}
		mav.addObject("tempPath", vo.getFilePath());
		mav.addObject("id", vo.getId());
		mav.setViewName(getViewPath() + "_file_open");
		return mav;
	}

	// 生成采样单文件
	@RequestMapping(value = "createCydFile.do")
	public ModelAndView createCydFile(SampCydVo v) throws GlobalException, IOException {
		ModelAndView mav = new ModelAndView();
		SampCydVo vo = sampCydService.find4File(v.getId());
		vo = createFile(vo, v.getTemp());// 生成采样单
		vo.setTemp(v.getTemp());
		sampCydService.updateFile(vo);
		mav.addObject("tempPath", vo.getFilePath());
		mav.addObject("id", vo.getId());
		mav.setViewName(getViewPath() + "_file_open");
		return mav;
	}

	// 生成word 采样单文件
	public SampCydVo createFile(SampCydVo v, String tempId) throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(VO, v);
		String reportPath = EXPORT_PATH + File.separator + v.getTaskVo().getId();
		String targetPath = reportPath + File.separator + v.getId() + ".doc";// 生成的文件存放路径
		String filePath = SERVER_BASE + reportPath;// 生成的文件存放路径
		File file = new File(filePath);
		if (!file.exists())
			file.mkdirs();
		OutputStream os = null;
		OutputStreamWriter osw = null;
		try {
			Configuration cfg = new Configuration();
			cfg.setDefaultEncoding("utf-8");
			cfg.setDirectoryForTemplateLoading(new File(SERVER_BASE + TEMP_PATH));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate(tempId + ".ftl", "utf-8");
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
		v.setFilePath(targetPath.replace("\\", "/"));
		v.setFileName(v.getPointName() + "_采样单.doc");
		return v;
	}
	@ResponseBody
	@RequestMapping(value = "ajaxSaveFile.do")
	public void ajaxSaveFile(SampCydVo v) throws GlobalException {
		FileSaver fs = new FileSaver(request, response);
		SampCydVo vo = sampCydService.find4File(v.getId());
		String targetFile = SERVER_BASE + vo.getFilePath().replace("/", "\\");
		fs.saveToFile(targetFile);
		fs.close();
	}
}