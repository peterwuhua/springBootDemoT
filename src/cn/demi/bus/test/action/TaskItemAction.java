package cn.demi.bus.test.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhuozhengsoft.pageoffice.FileSaver;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
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
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.task.service.ITaskService;
import cn.demi.bus.test.service.ITaskItemService;
import cn.demi.bus.test.service.ITestItemService;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.bus.test.vo.TestItemVo;
import cn.demi.init.std.service.IMethodService;
import cn.demi.init.std.vo.MethodVo;
import cn.demi.res.service.IApparaService;
import cn.demi.res.vo.ApparaVo;
import net.sf.jxls.transformer.XLSTransformer;

@Controller("bus.itemTestAction")
@RequestMapping("/bus/itemTest")
public class TaskItemAction extends BaseAction<TaskItemVo> {
	
	public static final String REPORT_PATH =ApplicationUtils.getValue("config.upload.samp.task").toString().replace("\\", File.separator);
	
	final String VIEW_PATH = "/bus/item/test/item_test";
	@Autowired private ITaskItemService taskItemService;	
	@Autowired 
	private IProgressLogService progressLogService;
	@Autowired
	private IFilesService filesService;
	@Autowired
	private IAccountRoleService accountRoleService;
	@Autowired
	private ITestItemService testItemService;
	@Autowired
	private IMethodService methodService;
	@Autowired 
	private ITaskService taskService;
	@Autowired 
	private IApparaService apparaService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<TaskItemVo> baseService() {
		return taskItemService;
	}
	
	@Override
	public ModelAndView edit(TaskItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=taskItemService.find(v.getId());
		}
		List<FilesVo> fileList=filesService.listByBusId(v.getId(),Constants.FILE_TYPE_ITEM);
		v.setFileList(fileList);
		//若为退回项目，获取退回日志记录
		if(null!=v.getIsBack()&&v.getIsBack().equals(Constants.Y)) {
			ProgressLogVo logVo=progressLogService.findBack(v.getId());
			mav.addObject("logVo", logVo);
		}
		mav.addObject(VO, v);
		if (v.getTaskVo().getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getTaskVo().getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
			mav.setViewName(getViewPath()+"_zw_edit");
		}else {
			mav.setViewName(getViewPath()+"_edit");
		}
		return mav;
	}
	//项目编辑保存
	@RequestMapping(value="update4Data.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新录入结果",module="数据录入")
	public Status update4Data(TaskItemVo v) throws GlobalException {
		try {
			taskItemService.update(v);
			taskService.update2St(null, v.getId());
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	//更新限值
	@RequestMapping(value="update4Limited.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新项目限值",module="数据录入")
	public Status update4Limited(TaskItemVo v) throws GlobalException {
		try {
			taskItemService.update4Limited(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@Log(operation=Log.Operation.DELETE,content="删除",module="数据录入")
	public ModelAndView update2del(TaskItemVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskItemService.update2del(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	@Override
	public ModelAndView show(TaskItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v=taskItemService.findById(v.getId());
		mav.addObject(VO, v);
		ProgressLogVo logVo=progressLogService.find(v.getId(), EunmTask.ITEM_LR.getStatus());
		mav.addObject("logVo", logVo);
		List<FilesVo> fileList=filesService.listByBusId(v.getId(),Constants.FILE_TYPE_ITEM);
		v.setFileList(fileList);
		if (v.getTaskVo().getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getTaskVo().getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
			mav.setViewName(getViewPath()+"_zw_show");
		}else {
			mav.setViewName(getViewPath()+"_show");
		}
		return mav;
	}
	//任务转移
	@RequestMapping(value="edit4Zy.do")
	public ModelAndView edit4Zy(TaskItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v=taskItemService.findById(v.getId());
		mav.addObject(VO, v);
		List<AccountVo> accountList = accountRoleService.listAccount(Constants.ROLE_TESTER);
		mav.addObject("userList", accountList);
		mav.setViewName(getViewPath()+"_edit_zy");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value="update4Zy.do")
	@Log(operation=Log.Operation.UPDATE,content="任务转移",module="数据录入")
	public Status update4Zy(TaskItemVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskItemService.update4Zy(v);
			status = new Status("转移成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败",e);
			status = new Status("转移失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping(value="edit4Zk.do")
	public ModelAndView edit4Zk(TaskItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v=taskItemService.findById(v.getId());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit_zk");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value="update4Zk.do")
	@Log(operation=Log.Operation.UPDATE,content="添加质控样",module="数据录入")
	public Status update4Zk(TestItemVo v, RedirectAttributes attr) throws GlobalException {
		try {
			boolean ids=testItemService.add4Zk(v);
			status = new Status("添加成功",Status.STATUS_SUCCESS);
			status.setObject(ids);
		} catch (GlobalException e) {
			log.info("保存失败",e);
			status = new Status("添加失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="delete4Zk.do")
	@Log(operation=Log.Operation.DELETE,content="删除质控样",module="数据录入")
	public Status delete4Zk(TestItemVo v, RedirectAttributes attr) throws GlobalException {
		try {
			boolean ids=testItemService.delete4Zk(v.getId());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
			status.setObject(ids);
		} catch (GlobalException e) {
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/*****原始记录单操作********************/
	/**
	 * Description :生成原始记录单 <br>
	 * @throws IOException 
	 */
	@RequestMapping(value={"createTemp.do"})
	public ModelAndView createTemp(TaskItemVo v) throws GlobalException, IOException {
		ModelAndView mav =  new ModelAndView();
		v = taskItemService.find4Temp(v.getId());
		v= createFile(v);//生成报告文件
		taskItemService.updateFile(v);
		mav.addObject("tempPath",v.getFilePath());
		mav.setViewName(getViewPath()+"_file_open");
		return mav;
	}
	/**
	 * Description : 修改原始记录单 <br>
	 * @throws IOException 
	 */
	@RequestMapping(value={"editTemp.do"})
	public ModelAndView editTemp(TaskItemVo v) throws GlobalException, IOException {
		ModelAndView mav =  new ModelAndView();
		v = taskItemService.findById(v.getId());
		if(StrUtils.isBlankOrNull(v.getFilePath())) {
			v= createFile(v);//生成文件
			taskItemService.updateFile(v);
		}else {
			File file = new File(SERVER_BASE+v.getFilePath());
			if(!file.exists()) {
				v= createFile(v);//生成文件
				taskItemService.updateFile(v);
			}
		}
		mav.addObject("tempPath",v.getFilePath());
		mav.setViewName(getViewPath()+"_file_open");
		return mav;
	}
	/**
	 * 查看原始记录文件
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value={"showTemp.do"})
	public ModelAndView showTemp(TaskItemVo v) throws GlobalException {
		ModelAndView mav =  new ModelAndView();
		v = taskItemService.findById(v.getId());
		mav.addObject("tempPath",v.getFilePath());
		mav.setViewName(getViewPath()+"_file_show");
		return mav;
	}
	/**
	 * Description : 生成文件 <br>
	 */
	public TaskItemVo createOrRepFile(TaskItemVo v) throws IOException {
		String reportPath =REPORT_PATH+File.separator+v.getTaskVo().getNo();
		String fileName =v.getId()+".doc";//生成的文件存放路径
		if(null!=v.getTemp()&&(v.getTemp().endsWith(".xls")||v.getTemp().endsWith(".xlsx"))) {
			fileName =v.getId()+".xls";//生成的文件存放路径
		}
		v.setFileName(fileName);
		String targetPath =reportPath+File.separator+fileName;//生成的文件存放路径
		v.setFilePath(targetPath.replace("\\", "/"));
		//新文件
		String filePath =SERVER_BASE+ reportPath;//生成的文件存放路径
		File file = new File(filePath);
		if (!file.exists()) 
			   file.mkdirs();
		
		//检测文件是否存在
		String tempFile =SERVER_BASE+ v.getTemp();//生成的文件存放路径
		File oldfile = new File(tempFile); 
		if (oldfile.exists()) {
			InputStream inStream =null;
			FileOutputStream fs =null;
			try {
				inStream = new FileInputStream(oldfile); //读入原文件  
	            fs = new FileOutputStream(SERVER_BASE+targetPath);  
	            byte[] buffer = new byte[1444];  
	            int byteread = 0;  
	            while ( (byteread = inStream.read(buffer)) != -1) {  
	                fs.write(buffer, 0, byteread);  
	            }  
			} catch (Exception e) {
				Logger.getLogger(ExportUtils.class).error("生成文件出错", e);
			}finally {
				if(null != inStream){
					 inStream.close(); 
				}   
				if(null != fs){
					fs.flush();
			        fs.close();
				}    
			}
		}
		return v;
	}
	/**
	 * 生成Excel 模板的文件
	 * @param v 数据源
	 * @param isRep 是否替换源文件  true false
	 */
	private TaskItemVo createFile(TaskItemVo v) throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(VO, v);
		String source =SERVER_BASE + File.separator + v.getTemp();//模板文件
		String subPath =REPORT_PATH+File.separator+v.getTaskVo().getNo();
		String fileName =v.getId()+".xls";//生成的文件存放路径
		String targetPath =subPath+File.separator+fileName;//生成的文件存放路径
		String filePath =SERVER_BASE+ subPath;//生成的文件存放路径
		if(StrUtils.isNotBlankOrNull(v.getFilePath())){
			String oldFilePath = SERVER_BASE + File.separator+v.getFilePath();
			File oldFile = new File(oldFilePath);
			if (oldFile.exists()) 
				oldFile.delete();
		}
		File file = new File(filePath);
		if (!file.exists()) 
			   file.mkdirs();
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		OutputStream os = null;
		try {
			 is = new BufferedInputStream(new FileInputStream(source));
	        Workbook workbook = transformer.transformXLS(is,dataMap);
	        os = new BufferedOutputStream(new FileOutputStream( SERVER_BASE+targetPath));
	        workbook.write(os);
		} catch (Exception e) {
			Logger.getLogger(ExportUtils.class).error("生成文件出错", e);
		}finally {
			if(null != is) {
				is.close();
			}
			if(null != os) {
				 os.flush();
			     os.close();
			}
		}
		v.setFilePath(targetPath.replace("\\", "/"));
		v.setFileName(v.getItemName()+"_"+DateUtils.getCurrDateStr()+".xls");
		return v;
	}
	/**
	 * Description : 检测原始记录文件是否存在 <br>
	 * @param v ReportVo
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value= "checkTempFile.do")
	public Map<String, Object> checkTempFile(TaskItemVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		v = taskItemService.findById(v.getId());
		if(StrUtils.isBlankOrNull(v.getFilePath())){
			map.put("type",Status.STATUS_ERROR);
			map.put("message","请先生成原始记录单，再提交！！！");
			return map;
		}
		String filePath = SERVER_BASE +File.separator+v.getFilePath();
		File file =new File(filePath);
		if(!file.exists()){
			map.put("type",Status.STATUS_ERROR);
			map.put("message","请先生成原始记录单，再提交！！！");
			return map;
		}
		map.put("type",Status.STATUS_SUCCESS);
		return map;
	}
	/**
	 * Description : 在线保存文件  <br>
	 */
	@ResponseBody
	@RequestMapping(value="ajaxSaveFile.do")
	public void ajaxSaveFile(TaskItemVo v) throws GlobalException {
		FileSaver fs = new FileSaver(request,response);
		String targetFile = SERVER_BASE+v.getFilePath().replace("/", "\\");
		fs.saveToFile(targetFile);
		fs.close();
	}
	//仪器选择页面
	@RequestMapping(value="showApp.do")
	public ModelAndView showApp(TaskItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<MethodVo> mList=methodService.listByIds(v.getMethodId());
		Set<String> appSet=new HashSet<String>();
		if(null!=mList) {
			for (MethodVo m: mList) {
				appSet.addAll(Arrays.asList(m.getAppIds().split(",")));
			}
		}
		List<ApparaVo> appList=apparaService.listByIds(String.join(",", appSet));
		mav.addObject(VO, v);
		mav.addObject("appList", appList);
		mav.setViewName(getViewPath()+"_app_select");
		return mav;
	}
	/**
	 * Description : 检测结果自动判定 <br>
	 * @param v ReportVo
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value= "checkValue.do")
	public Map<String, Object> checkValue(TestItemVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean b = testItemService.checkValue(v);
		if(!b){
			map.put("type",Status.STATUS_ERROR);
			return map;
		}
		map.put("type",Status.STATUS_SUCCESS);
		return map;
	}
	/***上传文件***/
	@ResponseBody
	@RequestMapping(value = { "uploadFile.do" })
	public Status uploadFile(MultipartHttpServletRequest multipartRequest) throws GlobalException {
		MultipartFile diskFileItem = multipartRequest.getFile("file");
		BufferedInputStream inputStream;
		BufferedOutputStream outputStream;
		String fileName = multipartRequest.getParameter("name");
		String busId = multipartRequest.getParameter("uid");
		TaskItemVo timVo = taskItemService.findById(busId);
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		String vName = System.currentTimeMillis() + fileType;
		String filePath = REPORT_PATH + File.separator + timVo.getTaskVo().getNo();
		try {
			inputStream = new BufferedInputStream(diskFileItem.getInputStream());
			File f = new File(SERVER_BASE + filePath);
			if (!f.exists())
				f.mkdirs();
			outputStream = new BufferedOutputStream(new FileOutputStream(f.getPath() + "/" + vName.replace("/", "\\")));
			Streams.copy(inputStream, outputStream, true);

			inputStream.close();
			outputStream.flush();
			outputStream.close();
			filePath = filePath + File.separator + vName;
			filePath = filePath.replace("\\", "/");
			FilesVo fileVo = new FilesVo();
			fileVo.setBusId(busId);
			fileVo.setBusType(Constants.FILE_TYPE_ITEM);
			fileVo.setFileName(fileName);
			fileVo.setFilePath(filePath);
			fileVo.setFileType(fileType);
			filesService.save(fileVo);
			status = new Status("文件上传成功", Status.STATUS_SUCCESS);
			status.setObject(filePath);
		} catch (FileNotFoundException e) {
			log.error("文件上传失败", e);
			status = new Status("文件上传失败", Status.STATUS_ERROR);
		} catch (IOException e) {
			log.error("文件上传失败", e);
			status = new Status("文件上传失败", Status.STATUS_ERROR);
		}
		return status;
	}
	
	

	
	
	
	
}