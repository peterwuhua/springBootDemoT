package cn.demi.init.car.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.car.service.ICarUseService;
import cn.demi.init.car.vo.CarUseVo;
/**
 * 车辆申请
 * @author QuJunLong
 *
 */
@Controller("init.carUseAction")
@RequestMapping("/init/carUse")
public class CarUseAction extends BaseAction<CarUseVo> {
	final String VIEW_PATH = "/init/car/use/car_use";
	@Autowired private ICarUseService carUseService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<CarUseVo> baseService() {
		return carUseService;
	}
	@Override
	public ModelAndView edit(CarUseVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=carUseService.findById(v.getId());
		}else {
			v.setUserId(getCurrent().getAccountId());
			v.setUserName(getCurrent().getUserName());
			v.setDate(DateUtils.getCurrDateTimeStr());
			v.setDestRynum(1);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增申请信息",module="车辆使用申请")
	public Status addData(CarUseVo v, RedirectAttributes attr) throws GlobalException {
		try {
			carUseService.add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新申请信息",module="车辆使用申请")
	public Status updateData(CarUseVo v, RedirectAttributes attr) throws GlobalException {
		try {
			carUseService.update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@Log(operation=Log.Operation.DELETE,content="保存申请信息",module="车辆使用申请")
	public ModelAndView save(CarUseVo v, RedirectAttributes attr) throws GlobalException {
		try {
			carUseService.save(v);
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
	/**
	 * 采样安排调用车辆使用情况
	 * ***/
	@ResponseBody
	@RequestMapping(value ="listData.do")
	public List<CarUseVo> listData(CarUseVo v) throws GlobalException {
		return carUseService.listData(v);
	}
	
	
	@ResponseBody
	@RequestMapping(value ="deleteOne.do")
	public Status deleteOne(CarUseVo v) throws GlobalException {
		try {
			carUseService.delete(v.getId());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
}