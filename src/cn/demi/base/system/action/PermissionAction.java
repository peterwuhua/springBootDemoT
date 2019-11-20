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
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFunctionService;
import cn.demi.base.system.service.IPermissionService;
import cn.demi.base.system.vo.FunctionVo;
import cn.demi.base.system.vo.PermissionVo;
/**
 * Create on : 2016年11月22日 下午2:06:35  <br>
 * Description :  权限action<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Controller("sys.permissionAction")
@RequestMapping("/sys/permission")
public class PermissionAction extends BaseAction<PermissionVo> {
	final String VIEW_PATH = "/sys/permission/permission";
	@Autowired
	private IPermissionService permissionService;
	@Autowired
	private IFunctionService functionService;
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<PermissionVo> baseService() {
		return permissionService;
	}

	@Override
	public ModelAndView edit(PermissionVo v) throws GlobalException {
		ModelAndView mav = super.edit(v);
		if(null == v){
			v=new PermissionVo();
		}
		FunctionVo functionVo = new FunctionVo();
		if(null != v.getFunctionVo() && StrUtils.isNotBlankOrNull(v.getFunctionVo().getId())){
			functionVo=functionService.findById(v.getFunctionVo().getId());
		}else {
			functionVo=functionService.findRoot();
		}
		v.setFunctionVo(functionVo);
		return mav;
	}

	@Override
	public ModelAndView update(PermissionVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = super.update(v, attr);
		mav.addObject("id", v.getId());
		mav.addObject("funId", v.getFunId());
		mav.setViewName(REDIRECT_2_GRID_PAGE);
		return mav;
	}

	@Override
	public ModelAndView add(PermissionVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = super.add(v, attr);
		mav.addObject("id", v.getId());
		mav.addObject("funId", v.getFunId());
		mav.setViewName(REDIRECT_2_GRID_PAGE);
		return mav;
	}
	

	@Override
	public ModelAndView update2del(PermissionVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav =  super.update2del(v, attr);
		mav.addObject("funId", v.getFunId());
		return mav;
	}

	@Override
	public ModelAndView page(PermissionVo v, PageResult pageResult) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		FunctionVo functionVo = new FunctionVo();
		if(StrUtils.isNotBlankOrNull(v.getFunId())){
			functionVo = functionService.findById(v.getFunId());
		}
		v.setFunctionVo(functionVo);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	
	/**
	 * Create on : Paddy Zhang 2016年11月23日 上午8:34:17 <br>
	 * Description : 功能树 <br>
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
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
	
	@ResponseBody
	@RequestMapping(value= "checkPermCode.do")
	public Map<String, Object> checkPermCode(PermissionVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StrUtils.isBlankOrNull(v.getCode())){
			map.put("type",Status.STATUS_ERROR);
			map.put("message","请输入正确的权限编码！！");
			return map;
		}
		String code = v.getCode().trim();
		PermissionVo oldVo = null;
		if(StrUtils.isNotBlankOrNull(v.getId())){
			PermissionVo permissionVo = permissionService.findById(v.getId());
			if(!code.equals(permissionVo.getCode())){
				oldVo= permissionService.findByCode(code);
				if(null != oldVo){
					map.put("type",Status.STATUS_ERROR);
					map.put("message","权限编码已存在："+oldVo.getCode());
					return map;
				}
			}
		}else{
			oldVo= permissionService.findByCode(code);
			if(null != oldVo){
				map.put("type",Status.STATUS_ERROR);
				map.put("message","权限编码已存在："+oldVo.getCode());
				return map;
			}
		}
		map.put("type",Status.STATUS_SUCCESS);
		map.put("message","权限编码可用");
		return map;
		
	}
	
}