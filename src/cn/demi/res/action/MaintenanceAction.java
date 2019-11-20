package cn.demi.res.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.demi.res.service.IApparaService;
import cn.demi.res.service.IMaintenanceService;
import cn.demi.res.vo.MaintenanceVo;

/** <strong>Create on : 2017年2月28日 下午1:21:54 </strong> <br>
 * <strong>Description : </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
@Controller("res.maintenanceAction")
@RequestMapping("/res/maintenance")
public class MaintenanceAction extends BaseAction<MaintenanceVo> {
	final String VIEW_PATH = "/res/appara/maintenance";
	@Autowired private IMaintenanceService maintenanceService;	
	@Autowired private IApparaService apparaService;
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<MaintenanceVo> baseService() {
		return maintenanceService;
	}
	
	@Override
	public ModelAndView page(MaintenanceVo v,PageResult pageResult) throws GlobalException{
		ModelAndView mv = super.page(v, pageResult);
		v.setApparaVo(apparaService.findById(v.getApparaVo().getId()));
		mv.addObject(VO, v);
		return mv;
	}
	@Override
	public ModelAndView gridPage(MaintenanceVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v.setApparaVo(apparaService.findById(v.getApparaVo().getId()));
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增存修保养",module="设备维修保养")
	public Status addData(MaintenanceVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}

	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改存修保养",module="设备维修保养")
	public Status updateData(MaintenanceVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
	
	
}