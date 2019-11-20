package cn.demi.init.car.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.car.service.ICarAuditService;
import cn.demi.init.car.vo.CarUseVo;
/**
 * 车辆申请审核
 * @author QuJunLong
 *
 */
@Controller("init.carAuditAction")
@RequestMapping("/init/carAudit")
public class CarAuditAction extends BaseAction<CarUseVo> {
	final String VIEW_PATH = "/init/car/audit/car_audit";
	@Autowired private ICarAuditService carAuditService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<CarUseVo> baseService() {
		return carAuditService;
	}
	@Override
	public ModelAndView edit(CarUseVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=carAuditService.findById(v.getId());
		}
		v.setAuditDate(DateUtils.getCurrDateTimeStr());
		v.setAuditId(getCurrent().getAccountId());
		v.setAuditName(getCurrent().getUserName());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新申请信息",module="车辆使用审核")
	public Status updateData(CarUseVo v, RedirectAttributes attr) throws GlobalException {
		try {
			carAuditService.update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
}