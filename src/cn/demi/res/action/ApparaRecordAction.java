package cn.demi.res.action;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.demi.res.service.IApparaRecordService;
import cn.demi.res.service.IApparaService;
import cn.demi.res.vo.ApparaRecordVo;
import cn.demi.res.vo.ApparaVo;

/** <strong>Create on : 2017年2月28日 下午1:21:05 </strong> <br>
 * <strong>Description : </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
@Controller("res.apparaRecordAction")
@RequestMapping("/res/apparaRecord")
public class ApparaRecordAction extends BaseAction<ApparaRecordVo> {
	final String VIEW_PATH = "/res/appara/appara_record";
	public static final String SERVER_BASE = ApplicationUtils.getValue("config.server.base").toString()
			.replace("\\", File.separator);
	public static final String TEMP_PATH = ApplicationUtils.getValue("config.upload.path").toString()
			.replace("\\", File.separator);
	public static final String  s = ApplicationUtils.getValue("config.upload.sys.sign").toString().replace("\\", "/");
	
	@Autowired
	private IApparaRecordService apparaRecordService;
	@Autowired
	private IApparaService apparaService;
	
	@Override
	public ModelAndView page(ApparaRecordVo v,PageResult pageResult) throws GlobalException{
		ModelAndView mv = new ModelAndView();
		List<ApparaRecordVo> arVoList = null;
		if(null != v.getApparaVo()){
			if(StringUtils.isNotBlank(v.getApparaVo().getId()) && StringUtils.isNotBlank(v.getType())){
				arVoList = apparaRecordService.apparaRecordListByAppId(v.getApparaVo().getId() , v.getType());
			}
		}
		v.setApparaVo(apparaService.findById(v.getApparaVo().getId()));
		pageResult.setResultList(arVoList);
		mv.addObject("pageResult", pageResult);
		mv.addObject(VO, v);
		mv.setViewName(VIEW_PATH + "_" + v.getType() + "_page");
		return mv;
	}
	@Override
	public ModelAndView gridPage(ApparaRecordVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		//v.setApparaVo(apparaService.findById(v.getApparaVo().getId()));
		mav.addObject(VO, v);
		mav.setViewName(VIEW_PATH + "_" + v.getType() + "_page");
		return mav;
	}
	@Override
	public ModelAndView gridPaged(ApparaRecordVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null==v.getDate()) {
			v.setDate(DateUtils.getYear());
		}
		mav.addObject(VO, v);
		mav.setViewName(VIEW_PATH + "_ed_page");
		return mav;
	}
	@Override
	public ModelAndView edit(ApparaRecordVo v) throws GlobalException{
		ModelAndView mv = super.edit(v);
		if(StringUtils.isNotBlank(v.getApparaVo().getId())){
			ApparaVo appVo = apparaService.findById(v.getApparaVo().getId());
			if(v.getType().equals("test")){
				if(null != appVo.getVerificationCycle()){
					v.setCycle(appVo.getVerificationCycle().trim());
				}
			}else if(v.getType().equals("calibration")){
				if(null != appVo.getCalibrationCycle()){
					v.setCycle(appVo.getCalibrationCycle().trim());
				}
			}
		}
		if(null != v.getStatus() && "1".equals(v.getStatus())){
			mv.addObject("isShow",v.getStatus());
		}
		mv.addObject(VO, v);
		mv.setViewName(VIEW_PATH + "_" + v.getType() + "_edit");
		return mv;
	}
	
	/** <strong>Create on : Hans He 2017年3月1日 下午1:22:29 </strong> <br>
	 * <strong>Description :保存 </strong> <br>
	 * @param v
	 * @param attr
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping("/save2File")
	@Log(operation=Log.Operation.SAVE,content="保存记录",module="设备校验记录")
	public ModelAndView save2File(ApparaRecordVo v,RedirectAttributes attr,HttpServletRequest request,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException{
		if(null!=file && !file.isEmpty()){
			v.setTrueName(file.getOriginalFilename());
			v.setFileUrl(getPath(file.getOriginalFilename()));
			upload(file, v.getFileUrl());
		}
	     Date newDate = DateUtils.parseToDate(v.getDate());
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(newDate);
		 calendar.add(Calendar.MONTH, Integer.parseInt(v.getCycle()));
		 newDate = calendar.getTime();
		 String newDate1 = DateUtils.format(newDate);
	     v.setNextDate(newDate1);
	     try {
			baseService().save(v);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		//获取操作下的最新额下次维护时间存入仪器表中
		List<ApparaRecordVo> ApparaRecordVo = apparaRecordService.listOrderByDate(v.getType(),v.getApparaVo().getId());
		String nextDate = ApparaRecordVo.get(0).getNextDate();
		String date = ApparaRecordVo.get(0).getDate();
		ApparaVo appVo = null;
		if(StringUtils.isNotBlank(v.getApparaVo().getId())){
			appVo = apparaService.findById(v.getApparaVo().getId());
			if(v.getType().equals("test")){
				appVo.setVerificationDate(nextDate);
				appVo.setLastverificationDate(date);
				appVo.setSourceUnit(v.getUnit());
			}else if(v.getType().equals("calibration")){
				mav.addObject("cycle", appVo.getCalibrationCycle().trim());
				appVo.setCalibrationDate(nextDate);
				appVo.setLastcalibrationDate(date);
			}
			apparaService.update(appVo);
		}
		mav.addObject("id", v.getId());
		mav.setViewName(REDIRECT_2_EDIT+"?type="+v.getType()+"&apparaVo.id="+v.getApparaVo().getId());
		return mav;
	}
	
	/** <strong>Create on : Hans He 2017年2月28日 下午1:18:00 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param v
	 * @param attr
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping("/update2File")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新记录",module="设备校验记录")
	public Status update2File(ApparaRecordVo v,RedirectAttributes attr,HttpServletRequest request,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException{
		if(null!=file && !file.isEmpty()){
			v.setTrueName(file.getOriginalFilename());
			v.setFileUrl(getPath(file.getOriginalFilename()));
			upload(file, v.getFileUrl());
		}
	    Date newDate = DateUtils.parseToDate(v.getDate());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(newDate);
		calendar.add(Calendar.MONTH, Integer.parseInt(v.getCycle()));
		newDate = calendar.getTime();
		String newDate1 = DateUtils.format(newDate);
	    v.setNextDate(newDate1);
	    try {
			baseService().update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		if(null == v.getDate()){
			v.setNextDate(null);
		}
		//获取操作下的最新额下次维护时间存入仪器表中
		List<ApparaRecordVo> ApparaRecordVo = apparaRecordService.listOrderByDate(v.getType(),v.getApparaVo().getId());
		String nextDate = ApparaRecordVo.get(0).getNextDate();
		String date = ApparaRecordVo.get(0).getDate();
		ApparaVo appVo = null;
		if(StringUtils.isNotBlank(v.getApparaVo().getId())){
			appVo = apparaService.findById(v.getApparaVo().getId());
			if(v.getType().equals("test")){
				appVo.setVerificationDate(nextDate);
				appVo.setLastverificationDate(date);
			}else if(v.getType().equals("calibration")){
				//mv.addObject("cycle", appVo.getCalibrationCycle().trim());
				appVo.setCalibrationDate(nextDate);
				appVo.setLastcalibrationDate(date);
			}
			apparaService.update(appVo);
		}
		return status;
	}
	
	/** <strong>Create on : Hans He 2017年2月28日 下午1:19:01 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param v
	 * @param attr
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping("/add2File")
	@ResponseBody
	@Log(operation=Log.Operation.ADD,content="新增记录",module="设备校验记录")
	public Status add2File(ApparaRecordVo v,RedirectAttributes attr,HttpServletRequest request,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException{
		if(null!=file && !file.isEmpty()){
			v.setTrueName(file.getOriginalFilename());
			v.setFileUrl(getPath(file.getOriginalFilename()));
			upload(file, v.getFileUrl());
		}
	    if(null == v.getDate()){
			v.setNextDate(null);
		 }
	     Date newDate = DateUtils.parseToDate(v.getDate());
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(newDate);
		 calendar.add(Calendar.MONTH, Integer.parseInt(v.getCycle()));
		 newDate = calendar.getTime();
		 String newDate1 = DateUtils.format(newDate);
		 v.setNextDate(newDate1);
		 try {
			baseService().add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		//获取操作下的最新额下次维护时间存入仪器表中
		List<ApparaRecordVo> ApparaRecordVo = apparaRecordService.listOrderByDate(v.getType(),v.getApparaVo().getId());
		String nextDate = ApparaRecordVo.get(0).getNextDate();
		String date = ApparaRecordVo.get(0).getDate();
		ApparaVo appVo = null;
		if(StringUtils.isNotBlank(v.getApparaVo().getId())){
			appVo = apparaService.findById(v.getApparaVo().getId());
			if(v.getType().equals("test")){
				appVo.setVerificationDate(nextDate);
				appVo.setLastverificationDate(date);
				appVo.setSourceUnit(v.getUnit());
			}else if(v.getType().equals("calibration")){
				appVo.setCalibrationDate(nextDate);
				appVo.setLastcalibrationDate(date);
			}
			apparaService.update(appVo);
		}
		return status;
	}
	
	private String getPath(String fileName){
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		String path = TEMP_PATH;
		path = path+"/"+"apparaRecord"+DateUtils.getCurrDateTime()+fileType;
		return path.replace("\\", "/");
	}

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	

	@Override
	public IBaseService<ApparaRecordVo> baseService() {
		return apparaRecordService;
	}
	
	
	
}