package cn.demi.bi.res.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bi.res.service.IEquipmentUsedService;
import cn.demi.res.vo.EquipmentUsedVo;

/**
 * 关于仪器相关信息统计
 * @author QuJunLong
 *
 */
@Controller("bi.equipmentAction")
@RequestMapping("/bi/equipment")
public class EquipmentAction extends BaseAction<EquipmentUsedVo> {
	
	final String VIEW_PATH = "/bi/equipment/equipment";
	
	@Autowired
	private IEquipmentUsedService equipUsedService;
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<EquipmentUsedVo> baseService() {
		return equipUsedService;
	}
 
	@RequestMapping(value="gridPage4Used.do")
	public ModelAndView gridPage4Used(EquipmentUsedVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_used_page");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value ="gridData4Used.do")
	public GridVo gridData4Used(GridVo gridVo, EquipmentUsedVo v) throws GlobalException {
		return equipUsedService.gridData(gridVo,v);
	}
	
}