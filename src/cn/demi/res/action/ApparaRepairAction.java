package cn.demi.res.action;

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
import cn.core.framework.utils.StrUtils;
import cn.demi.res.service.IApparaRepairService;
import cn.demi.res.service.IApparaService;
import cn.demi.res.vo.ApparaRepairVo;
import cn.demi.res.vo.ApparaVo;

@Controller("res.apparaRepairAction")
@RequestMapping("/res/apparaRepair")
public class ApparaRepairAction extends BaseAction<ApparaRepairVo> {
	final String VIEW_PATH = "/res/appara_repair/appara_repair";
	@Autowired private IApparaRepairService apparaRepairService;	
	@Autowired private IApparaService apparaService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ApparaRepairVo> baseService() {
		return apparaRepairService;
	}
	@Override
	public ModelAndView edit(ApparaRepairVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=apparaRepairService.findById(v.getId());
		}else if(null!=v.getApparaVo()&&StrUtils.isNotBlankOrNull(v.getApparaVo().getId())) {
			ApparaVo app=apparaService.findById(v.getApparaVo().getId());
			v.setApparaVo(app);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public Status addData(ApparaRepairVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().add(v);
			status = new Status("记录成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"记录失败",e);
			status = new Status("记录失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	public Status updateData(ApparaRepairVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
}