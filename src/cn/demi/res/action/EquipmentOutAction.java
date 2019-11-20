package cn.demi.res.action;

import java.util.List;

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
import cn.demi.res.po.EquipmentOut;
import cn.demi.res.service.IEquipmentOutService;
import cn.demi.res.vo.EquipmentOutVo;

@Controller("res.equipmentOutAction")
@RequestMapping("/res/equipmentOut")
public class EquipmentOutAction extends BaseAction<EquipmentOutVo> {
	final String VIEW_PATH = "/res/equipment_out/equipment_out";
	@Autowired private IEquipmentOutService equiptOutService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<EquipmentOutVo> baseService() {
		return equiptOutService;
	}
	@Override
	public ModelAndView edit(EquipmentOutVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=equiptOutService.findById(v.getId());
			v.setUseTime(DateUtils.getCurrDateTimeStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView show(EquipmentOutVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v=equiptOutService.findById(v.getId());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新出库信息",module="出库管理")
	public Status updateData(EquipmentOutVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增出库信息",module="出库管理")
	public Status addData(EquipmentOutVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@ResponseBody
	@RequestMapping(value ="listData.do")
	public List<EquipmentOutVo> listData(EquipmentOutVo v) throws GlobalException {
		return equiptOutService.listData(v);
	}
	@Override
	public ModelAndView gridPage(EquipmentOutVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v.setStatus(EquipmentOut.ST_0);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@Override
	public ModelAndView gridPaged(EquipmentOutVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v.setStatus(EquipmentOut.ST_1);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_ed_page");
		return mav;
	}
	@Override
	@RequestMapping(value="update2del.do")
	public ModelAndView update2del(EquipmentOutVo v, RedirectAttributes attr) throws GlobalException {
		try {
			equiptOutService.update2del(v.getIds());
			status = new Status("取消成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("取消失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
}