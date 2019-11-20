package cn.demi.bus.report.action;

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
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.export.ExportUtils;
import cn.demi.base.system.service.IAccountRoleService;
import cn.demi.base.system.service.ICodeService;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.report.service.IReportService;
import cn.demi.bus.report.vo.ReportVo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 报告编制
 * @author QuJunLong
 */
@Controller("bus.reportAction")
@RequestMapping("/bus/report")
public class ReportAction extends BaseAction<ReportVo> {

	public static final String REPORT_PATH =ApplicationUtils.getValue("config.upload.samp.report.report").toString().replace("\\", File.separator);
	public static final String SERVER_EXPORT_DIR = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");

	
	final String VIEW_PATH = "/bus/report/bz/report";
	
	@Autowired 
	private IReportService reportService;	
	@Autowired 
	private IProgressLogService progressLogService;
	@Autowired
	private IAccountRoleService accountRoleService;
	@Autowired
	private IFilesService filesService;
	@Autowired
	private ICodeService codeService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ReportVo> baseService() {
		return reportService;
	}
	
	@Override
	public ModelAndView edit(ReportVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=reportService.find(v.getId());
		}
		//若为退回项目，获取退回日志记录
		if(v.getIsBack().equals(Constants.Y)) {
			ProgressLogVo logVo=progressLogService.findBack(v.getTaskVo().getId());
			mav.addObject("logVo", logVo);
		}
		List<AccountVo> accountList = accountRoleService.listAccount(Constants.ROLE_REPORT_REPT);
		mav.addObject("userList", accountList);
		
//		List<String> fsList = codeService.listByCode("bgfs-lx"); //放射类型
//		mav.addObject("fsList", fsList);
		
		mav.addObject(VO, v);
		if(v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)||v.getSampType().equals(EnumBus.SAMP_TYPE_GW)) {
			mav.setViewName(getViewPath()+"_zw_edit");
		}else {
			mav.setViewName(getViewPath()+"_edit");
		}
		return mav;
	}
	@Override
	public ModelAndView show(ReportVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
		}
		//文件上传
		List<FilesVo> fileList=filesService.listByBusId(v.getTaskVo().getId());
		v.setFileList(fileList);
		mav.addObject(VO, v);
		ProgressLogVo logVo=progressLogService.find(v.getTaskVo().getId(), EunmTask.TASK_BZ.getStatus());
		mav.addObject("logVo", logVo);
		if(v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)||v.getSampType().equals(EnumBus.SAMP_TYPE_GW)) {
			mav.setViewName(getViewPath()+"_zw_show");
		}else {
			mav.setViewName(getViewPath()+"_show");
		}
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新报告信息",module="报告编制")
	public Status updateData(ReportVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	
	@RequestMapping(value ="updateBack.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="报告退回信息",module="报告编制")
	public Status updateBack(ReportVo v, RedirectAttributes attr) throws GlobalException {
		try {
			reportService.updateBack(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
	
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, ReportVo v) throws GlobalException {
		return reportService.gridData(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value ="gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, ReportVo v) throws GlobalException {
		return reportService.gridDatad(gridVo, v);
	}
	/**
	 * 已完成的报告列表
	 * 1、报告返工选择调用
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="gridData4Comp.do")
	public GridVo gridData4Comp(GridVo gridVo, ReportVo v) throws GlobalException {
		return reportService.gridData4Comp(gridVo, v);
	}
	/**
	 * Description : 生成word报告  <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @throws IOException
	 */
	@RequestMapping(value={"cearteWord.do"})
	public ModelAndView cearteWord(ReportVo v) throws GlobalException, IOException {
		ModelAndView mav =  new ModelAndView();
		String uuid=v.getUuid();
		v=reportService.find4Report(v);
		v.setUuid(uuid);
		v = createWordRepFile(v,v.getTemplate());//生成报告文件
		reportService.update4Report(v);
		mav.addObject("id",v.getId());
		if(!StrUtils.isBlankOrNull(uuid)&&uuid.equals("dq")) {
			mav.addObject("tempPath",v.getFilePath2());
		}else {
			mav.addObject("tempPath",v.getFilePath());
		}
		mav.addObject("uuid",uuid);
		mav.setViewName(getViewPath()+"_file_open");
		return mav;
	}
	/**
	 * Description : 修改报告  <br>
	 * @param v
	 * @param tempPath
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value={"editReport.do"})
	public ModelAndView editReport(ReportVo v) throws GlobalException {
		ModelAndView mav =  new ModelAndView();
		String uuid=v.getUuid();
		v = reportService.findById(v.getId());
		mav.addObject("id",v.getId());
		if(!StrUtils.isBlankOrNull(uuid)&&uuid.equals("dq")) {
			mav.addObject("tempPath",v.getFilePath2());
		}else {
			mav.addObject("tempPath",v.getFilePath());
		}
		mav.addObject("uuid",uuid);
		mav.setViewName(getViewPath()+"_file_open");
		return mav;
	}
	/**
	 * Description : 审核报告  <br>
	 * @throws GlobalException
	 */
	@RequestMapping(value={"auditReport.do"})
	public ModelAndView auditReport(ReportVo v) throws GlobalException {
		ModelAndView mav =  new ModelAndView();
		String uuid=v.getUuid();
		v = reportService.findById(v.getId());
		mav.addObject("id",v.getId());
		if(!StrUtils.isBlankOrNull(uuid)&&uuid.equals("dq")) {
			mav.addObject("tempPath",v.getFilePath2());
		}else {
			mav.addObject("tempPath",v.getFilePath());
		}
		mav.addObject("uuid",uuid);
		mav.setViewName(getViewPath()+"_file_audit");
		return mav;
	}
	/**
	 * Description :  <br>
	 * @param v ReportVo
	 * @param source String 报告模板的相对路径
	 * @return 生成的文件的路径
	 * @throws IOException
	 * @throws GlobalException 
	 */
	public ReportVo createWordRepFile(ReportVo v,String source) throws IOException, GlobalException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(VO, v);
		String reportPath =REPORT_PATH+File.separator+DateUtils.getYear()+DateUtils.getMonth();
		String targetPath =reportPath+File.separator+v.getId()+".doc";//生成的文件存放路径
		if(!StrUtils.isBlankOrNull(v.getUuid())&&v.getUuid().equals("dq")) {
			source="bus-report-z-dq.ftl";
			targetPath =reportPath+File.separator+v.getId()+"-dq.doc";//生成的文件存放路径
		}
		String filePath =SERVER_BASE+ reportPath;//生成的文件存放路径
		File file = new File(filePath);
		if (!file.exists()) 
			   file.mkdirs();
		OutputStream os = null;
		OutputStreamWriter osw=null;
		try {
			Configuration cfg = new Configuration();
			cfg.setDefaultEncoding("utf-8");
			cfg.setDirectoryForTemplateLoading(new File(SERVER_BASE+SERVER_EXPORT_DIR));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp  = cfg.getTemplate(source,"utf-8");
			
			os=new FileOutputStream( SERVER_BASE+targetPath);
			osw=new OutputStreamWriter(os);
			Writer docout = new BufferedWriter(osw);
			temp.process(dataMap, docout); 
		} catch (Exception e) {
			Logger.getLogger(ExportUtils.class).error("生成文件出错", e);
			throw new GlobalException("生成报告异常");
		}finally {
			if(null != osw){
				osw.flush();
				osw.close();
			}   
			if(null != os){
		        os.flush();
		        os.close();
			}    
		}
		if(!StrUtils.isBlankOrNull(v.getUuid())&&v.getUuid().equals("dq")) {
			v.setFileName2(v.getReportNo()+"-dq.doc");
			v.setFilePath2(targetPath.replace("\\", "/"));
		}else {
			v.setFileName(v.getReportNo()+".doc");
			v.setFilePath(targetPath.replace("\\", "/"));
		}
		return v;
	}
	/**
	 * Description : 检测报告文件是否存在 <br>
	 * @param v ReportVo
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value= "checkReportFile.do")
	public Map<String, Object> checkReportFile(ReportVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		v = reportService.findById(v.getId());
		if(StrUtils.isBlankOrNull(v.getFilePath())){
			map.put("type",Status.STATUS_ERROR);
			map.put("message","请先生成报告，再提交！！！");
			return map;
		}
		String filePath = SERVER_BASE +File.separator+v.getFilePath();
		File file =new File(filePath);
		if(!file.exists()){
			map.put("type",Status.STATUS_ERROR);
			map.put("message","请先生成报告，再提交！！！");
			return map;
		}
		map.put("type",Status.STATUS_SUCCESS);
		return map;
	}
	@RequestMapping(value={"showReport.do"})
	public ModelAndView showReport(ReportVo v) throws GlobalException {
		ModelAndView mav =  new ModelAndView();
		String uuid=v.getUuid();
		v = reportService.findById(v.getId());
		if(!StrUtils.isBlankOrNull(uuid)&&uuid.equals("dq")) {
			mav.addObject("tempPath",v.getFilePath2());
		}else {
			mav.addObject("tempPath",v.getFilePath());
		}
		mav.setViewName(getViewPath()+"_file_show");
		return mav;
	}
	@RequestMapping(value={"showReport4Pdf.do"})
	public ModelAndView showReport4Pdf(ReportVo v) throws GlobalException {
		ModelAndView mav =  new ModelAndView();
		String uuid=v.getUuid();
		v = reportService.findById(v.getId());
		if(!StrUtils.isBlankOrNull(uuid)&&uuid.equals("dq")) {
			mav.addObject("tempPath",v.getPdfPath2());
		}else {
			mav.addObject("tempPath",v.getPdfPath());
		}
		mav.setViewName(getViewPath()+"_pdf_show");
		return mav;
	}
	/**
	 * Description : 在线保存报告  <br>
	 * @param v ReportVo
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value={"ajaxSaveFile.do"})
	public void ajaxSaveFile(ReportVo v) throws GlobalException {
		String uuid=v.getUuid();
		ReportVo reportVo = reportService.findById(v.getId());
		FileSaver fs = new FileSaver(request,response);
		String targetFile = SERVER_BASE+reportVo.getFilePath().replace("/", "\\");
		if(fs.getFileExtName().equals(".pdf")) {
			targetFile=SERVER_BASE+reportVo.getPdfPath().replace("/", "\\");
		}
		if(!StrUtils.isBlankOrNull(uuid)&&uuid.equals("dq")) {
			targetFile = SERVER_BASE+reportVo.getFilePath2().replace("/", "\\");
			if(fs.getFileExtName().equals(".pdf")) {
				targetFile=SERVER_BASE+reportVo.getPdfPath2().replace("/", "\\");
			}
		}
		fs.saveToFile(targetFile);
		fs.close();
	}
}