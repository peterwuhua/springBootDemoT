package cn.demi.base.system.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IUnitService;
import cn.demi.base.system.vo.UnitVo;
/**
 * <strong>Create on : Dave Yu 2016年11月2日 下午2:28:04 </strong> <br>
 * <strong>Description : 岗位action </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Controller("sys.unitAction")
@RequestMapping("sys/unit")
public class UnitAction extends BaseAction<UnitVo>{
	final String VIEW_PATH = "/sys/unit/unit";
	@Autowired private IUnitService unitService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<UnitVo> baseService(){
		return unitService;
	}
	@ResponseBody
	@RequestMapping(value= {"ajaxUnit.json","getUnit.do"})
	public UnitVo getUnit(String id) throws GlobalException {
		return unitService.findById("0");
	}
	@Override
	public ModelAndView edit(UnitVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null==v || StrUtils.isBlankOrNull(v.getId())){
			v=unitService.findById("0");
		}else {
			v=unitService.findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
}
