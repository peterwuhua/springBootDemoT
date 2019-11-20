package cn.demi.base.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFunctionService;
import cn.demi.base.system.vo.FunctionVo;
/**
 * <strong>Create on : 2016年11月2日 下午2:12:15 </strong> <br>
 * <strong>Description : 功能action</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Controller("sys.functionAction")
@RequestMapping("sys/function")
public class FunctionAction extends BaseAction<FunctionVo> {
	final String VIEW_PATH = "/sys/function/function";
	@Autowired
	private IFunctionService functionService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<FunctionVo> baseService() {
		return functionService;
	}
	
	@Override
	public ModelAndView edit(FunctionVo vo) throws GlobalException {
		FunctionVo parentVo = null;
		ModelAndView mav=null;
		//新增
		if(StrUtils.isBlankOrNull(vo.getId())){
			//是否出入父级
			if (StrUtils.isBlankOrNull(vo.getPid())) {
				parentVo = functionService.findRoot();
			}else{
				parentVo = functionService.findById(vo.getPid());
			}
			mav=super.edit(vo);
			vo.setParentVo(parentVo);
		}else{
			mav = super.edit(vo);
		}
		//mav.addObject("functionList", functionService.treeList());
		return mav;
	}

	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:12:39 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @return List
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = {"treeData","treeJson"})
	public List<Map<String, Object>> treeData() throws GlobalException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<FunctionVo> list = functionService.list();
		for (FunctionVo vo : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", vo.getId());
			map.put("pId", null != vo.getParentVo() ? vo.getParentVo().getId()
					: "");
			map.put("name", vo.getName());
			if(null == vo.getParentVo()){
				map.put("open", "true");
			}else {
				map.put("open", "false");
			}
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public ModelAndView add(FunctionVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = super.add(v, attr);
		mav.addObject("pid", v.getPid());
		return mav;
	}

	@Override
	public ModelAndView update(FunctionVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = super.update(v, attr);
		mav.addObject("pid", v.getPid());
		return mav;
	}

	@Override
	public ModelAndView save(FunctionVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav =  super.save(v, attr);
		mav.addObject("pid", v.getPid());
		return mav;
	}

	@Override
	public ModelAndView update2del(FunctionVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav =  super.update2del(v, attr);
		mav.addObject("pid", v.getPid());
		return mav;
	}
	

}
