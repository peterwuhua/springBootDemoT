package cn.demi.init.sp.action;

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
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.sp.service.ISampPointService;
import cn.demi.init.sp.vo.PcUnit;
import cn.demi.init.sp.vo.SampPointVo;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;

@Controller("init.samp_pointAction")
@RequestMapping("/init/sampPoint")
public class SampPointAction extends BaseAction<SampPointVo> {
	final String VIEW_PATH = "/init/samp_point/samp_point";
	
	@Autowired 
	private ISampPointService sampPointService;	
	@Autowired 
	private ISampTypeService sampTypeService;	
	 
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<SampPointVo> baseService() {
		return sampPointService;
	}
	
	
	@Override
	public ModelAndView edit(SampPointVo v)throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=sampPointService.findById(v.getId());
		}else {
			v.setPc(1);
			v.setPcUnit(PcUnit.CY.getName());
		}
		List<SampTypeVo> sampTypeList=sampTypeService.listByPid(sampTypeService.findRoot().getId());
		mav.addObject("sampTypeList", sampTypeList);
		List<SampTypeVo> sampList=null;
		if(StrUtils.isNotBlankOrNull(v.getSampTypeId())) {
			sampList=sampTypeService.listByPid(v.getSampTypeId());
		}else if(sampTypeList.size()>0){
			sampList=sampTypeService.listByPid(sampTypeList.get(0).getId());
		}
		mav.addObject("sampList", sampList);
		mav.addObject("pcUnitList", PcUnit.getLxList());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = "ajaxSave.do")
	@Log(operation=Log.Operation.SAVE,content="保存检测点位",module="检测点位管理")
	public Status ajaxSave(SampPointVo v) throws GlobalException{
		try {
			if(sampPointService.checkName(v.getName(),v.getSampId())) {
				v.setOrgId(getCurrent().getOrgId());
				v.setOrgName(getCurrent().getOrgName());
				v.setCode(DateUtils.getCurrDateTime());
				sampPointService.add(v);
				status = new Status("保存成功",Status.STATUS_SUCCESS);
			}else {
				status = new Status("保存失败,点位已经存在",Status.STATUS_ERROR);
			}
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增检测点位",module="检测点位管理")
	public Status addData(SampPointVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改检测点位",module="检测点位管理")
	public Status updateData(SampPointVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存检测点位",module="检测点位管理")
	public ModelAndView save(SampPointVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.DELETE,content="删除检测点位",module="检测点位管理")
	public ModelAndView delete(SampPointVo v, RedirectAttributes attr) throws GlobalException {
		return super.delete(v, attr);
	}
	@ResponseBody
	@RequestMapping(value = "listPoint.do")
	public List<SampPointVo> listPoint(SampPointVo v,GridVo gridVo) throws GlobalException {
		return sampPointService.list(v,gridVo);
	}
	
}