package cn.demi.init.car.action;

import java.io.File;
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
import cn.core.framework.utils.StrUtils;
import cn.demi.init.car.po.Car;
import cn.demi.init.car.service.ICarService;
import cn.demi.init.car.vo.CarVo;
/**
 * 车辆信息
 * @author QuJunLong
 *
 */
@Controller("init.carAction")
@RequestMapping("/init/car")
public class CarAction extends BaseAction<CarVo> {
	final String VIEW_PATH = "/init/car/list/car";
	@Autowired private ICarService carService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<CarVo> baseService() {
		return carService;
	}
	@Override
	public ModelAndView edit(CarVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=carService.findById(v.getId());
		}
		v.setStatus(Car.ST_WSY);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@RequestMapping(value="update4Data.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新车辆信息",module="车辆管理")
	public Status update4Data(CarVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		try {
			if(null!=file && !file.isEmpty()) {
				v.setFileName(file.getOriginalFilename());
				v.setFilePath(getPath(file.getOriginalFilename()));
				upload(file, v.getFilePath());
			}
			carService.update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping(value="add4Data.do")
	@ResponseBody
	@Log(operation=Log.Operation.ADD,content="添加车辆信息",module="车辆管理")
	public Status add4Data(CarVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		try {
			if(null!=file && !file.isEmpty()) {
				v.setFileName(file.getOriginalFilename());
				v.setFilePath(getPath(file.getOriginalFilename()));
				upload(file, v.getFilePath());
			}
			carService.add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping(value="save4Date.do")
	@Log(operation=Log.Operation.SAVE,content="保存车辆信息",module="车辆管理")
	public ModelAndView save4Date(CarVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		try {
			if(null!=file && !file.isEmpty()) {
				v.setFileName(file.getOriginalFilename());
				v.setFilePath(getPath(file.getOriginalFilename()));
				upload(file, v.getFilePath());
			}
			carService.save(v);
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
	private String getPath(String originalFileName){
		String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
		String path = UPLOAD_DIR+Constants.FILE_TYPE_CAR+File.separator;
		path = path + suffix;
		return path.replace("\\", "/");
	}
	/**
	 * 采样安排调用车辆使用情况
	 * ***/
	@ResponseBody
	@RequestMapping(value ="gridData4Select.do")
	public GridVo gridData4Select(GridVo gridVo, CarVo v) throws GlobalException {
		return carService.gridData4Select(gridVo,v);
	}
	/**
	 * 采样安排调用车辆使用情况
	 * ***/
	@ResponseBody
	@RequestMapping(value ="list4Select.do")
	public List<CarVo> list4Select(GridVo gridVo, CarVo v) throws GlobalException {
		return carService.list4Select(gridVo,v);
	}
	
	
	/**
	 * 
	 * @Title: showUseCars   
	 * @Description: 车辆使用弹出框   
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping(value ="showUseCars.do")
	public ModelAndView showUseCars(CarVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName("/office/congress/car_selects");
		return mav;
	}
	
	

	
	
	
}