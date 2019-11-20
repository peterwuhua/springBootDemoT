package cn.demi.res.action;
import java.util.Calendar;
import java.util.Date;
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
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.init.std.vo.SampSourceVo;
import cn.demi.res.service.IApparaService;
import cn.demi.res.vo.ApparaVo;

/**
 * 仪器设备 view层
 * @author QuJunLong
 *
 */
@Controller("res.apparaAction")
@RequestMapping("/res/appara")
public class ApparaAction extends BaseAction<ApparaVo> {
	final String VIEW_PATH = "/res/appara/appara";
	public static final String SERVER_UPLOAD_DIR = ApplicationUtils.getValue("config.upload.path").toString().replace("\\", "/");

	@Autowired
	private IApparaService apparaService;
	@Autowired
	private IFilesService filesService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<ApparaVo> baseService() {
		return apparaService;
	}
	/** <strong>Create on : Hans He 2017年2月28日 下午1:15:56 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param v
	 * @param type
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping("/recordPage")
	public ModelAndView recordPage(ApparaVo v,String type) throws GlobalException{
		ModelAndView mv = new ModelAndView();
		mv.addObject(VO, v);
		//mv.setViewName(VIEW_PATH + "_" + type + "_record_page");
		mv.setViewName(VIEW_PATH +"_record_page");
		return mv;
	}
	/**
	 * <strong>Description :仪器设备管理：待检定列表、待校准列表 </strong> <br>
	 * @param gridVo
	 * @param v
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/gridData4Record")
	public GridVo gridData4Record(GridVo gridVo,ApparaVo v) throws GlobalException{
		return apparaService.gridData4Record(gridVo,v);
	}
	@Override
	public ModelAndView edit(ApparaVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=apparaService.findById(v.getId());
			List<FilesVo> fileList=filesService.listByBusId(v.getId());
			v.setFileList(fileList);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}

	/**
	 * <strong>Description :仪器设备管理：待检定列表、待校准列表、待核查列表数据获取 </strong> <br>
	 * @param gridVo
	 * @param v
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
	 * @throws GlobalException
	 */
//	@ResponseBody
//	@RequestMapping("/gridData4type")
//	public GridVo gridDatad(GridVo gridVo,ApparaVo v,String startDate,String endDate,String type) throws GlobalException{
//		return apparaService.gridDatad(gridVo,v,startDate,endDate,type);
//	}
	
	@RequestMapping("/save4Data")
	@Log(operation=Log.Operation.SAVE,content="保存设备信息",module="设备管理")
	public ModelAndView save4Data(ApparaVo v, RedirectAttributes attr,@RequestParam(value="files", required=false)MultipartFile[] files,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		if(StringUtils.isNotBlank(v.getLastcalibrationDate())){
			v.setCalibrationDate(nextDate(v.getLastcalibrationDate(),v.getCalibrationCycle()));
		}
		 
		if(StringUtils.isNotBlank(v.getLastverificationDate())){
			v.setVerificationDate(nextDate(v.getLastverificationDate(),v.getVerificationCycle()));
		}
		try {
			//logo
			if(!file.isEmpty()){
				v.setLogo(getPath(file.getOriginalFilename()));
				upload(file, v.getLogo());
			}
			baseService().save(v);
			//文件上传
			List<FilesVo> fileList=uploads(files, v.getId(),Constants.FILE_TYPE_APP);
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
	@ResponseBody
	@RequestMapping("/add4Data")
	@Log(operation=Log.Operation.ADD,content="新增设备信息",module="设备管理")
	public Status add4Data(ApparaVo v, RedirectAttributes attr,@RequestParam(value="files", required=false)MultipartFile[] files,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		if(StringUtils.isNotBlank(v.getLastcalibrationDate())){
			v.setCalibrationDate(nextDate(v.getLastcalibrationDate(),v.getCalibrationCycle()));
		}
		 
		if(StringUtils.isNotBlank(v.getLastverificationDate())){
			v.setVerificationDate(nextDate(v.getLastverificationDate(),v.getVerificationCycle()));
		}
		try {
			//logo
			if(null!= file && !file.isEmpty()){
				v.setLogo(getPath(file.getOriginalFilename()));
				upload(file, v.getLogo());
			}
			baseService().add(v);
			//文件上传
			List<FilesVo> fileList=uploads(files, v.getId(),Constants.FILE_TYPE_APP);
			filesService.saveFiles(fileList);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}


	@ResponseBody
	@RequestMapping("/update4Data")
	@Log(operation=Log.Operation.UPDATE,content="修改设备信息",module="设备管理")
	public Status update4Data(ApparaVo v, RedirectAttributes attr,@RequestParam(value="files", required=false)MultipartFile[] files,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		if(StringUtils.isNotBlank(v.getLastcalibrationDate())){
			v.setCalibrationDate(nextDate(v.getLastcalibrationDate(),v.getCalibrationCycle()));
		}
		if(StringUtils.isNotBlank(v.getLastverificationDate())){
			v.setVerificationDate(nextDate(v.getLastverificationDate(),v.getVerificationCycle()));
		}
		try {
			//logo
			if(null!=file&&!file.isEmpty()){
				v.setLogo(getPath(file.getOriginalFilename()));
				upload(file, v.getLogo());
			}
			baseService().update(v);
			//文件上传
			List<FilesVo> fileList=uploads(files, v.getId(),Constants.FILE_TYPE_APP);
			filesService.saveFiles(fileList);
			
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/** <strong>Create on : Hans He 2017年3月7日 下午4:14:29 </strong> <br>
	 * <strong>Description :通过周期计算下次检定、核查、校准日期 </strong> <br>
	 * @param date
	 * @param cycle
	 * @return
	 */
	public String nextDate(String date,String cycle){
		Date newDate = DateUtils.parseToDate(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(newDate);
		calendar.add(Calendar.MONTH, Integer.parseInt(cycle));
		newDate = calendar.getTime();
		String newDate1 = DateUtils.format(newDate);
		return newDate1;
	}
	/**
	 * 获取仪器列表 ajax方法
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajax4AppList.do")
	public List<ApparaVo> ajax4AppList(ApparaVo v) throws GlobalException {
		List<ApparaVo> appList = apparaService.list(v);
		return appList;
		
	}
	@ResponseBody
	@RequestMapping(value = "listAll")
	public List<ApparaVo> listAll(ApparaVo v) throws GlobalException {
		return apparaService.listAll(v);
	}
	
	
	/**
	 * 
	 * @Title: gridData4Out   
	 * @Description: 选择设备弹窗，获取左侧设备数据源   
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value ="gridData4Out.do")
	public GridVo gridData4Out(GridVo gridVo, ApparaVo v) throws GlobalException {
		return apparaService.gridData4Out(gridVo,v);
	}
	

	@ResponseBody
	@RequestMapping(value ="listData4Out.do")
	public List<ApparaVo> listData4Out(GridVo gridVo, ApparaVo v) throws GlobalException {
		return apparaService.listData4Out(gridVo,v);
	}
	/**
	 * 
	 * @Title: listData4Sels   
	 * @Description: 选择设备弹窗，获取右侧设备数据源 
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: List<ApparaVo>      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value ="listData4Sels.do")
	public List<ApparaVo> listData4Sels(String ids) throws GlobalException {
		return apparaService.listData4Sels(ids);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "ajax4Show.do")
	public ApparaVo ajax4Show(String id) throws GlobalException {
		ApparaVo v = apparaService.findById(id);
		return v;
	}
	private String getPath(String fileName){
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		String path = SERVER_UPLOAD_DIR+Constants.FILE_TYPE_APP_LOGO;
		path = path+"/"+DateUtils.getCurrDateTime()+fileType;
		return path.replace("\\", "/");
	}
	@ResponseBody
	@RequestMapping(value = "gridData4Reppair.do")
	public GridVo gridData4Reppair(GridVo gridVo, ApparaVo v) throws GlobalException {
		return apparaService.gridData4Reppair(gridVo,v);
	}
}