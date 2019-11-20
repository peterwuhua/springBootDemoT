package cn.demi.base.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFunctionService;
import cn.demi.base.system.service.IRoleFunService;
import cn.demi.base.system.service.IRolePermService;
import cn.demi.base.system.service.IRoleService;
import cn.demi.base.system.vo.FunctionVo;
import cn.demi.base.system.vo.RoleFunVo;
import cn.demi.base.system.vo.RolePermVo;
import cn.demi.base.system.vo.RoleVo;
/**
 * Create on : 2016年11月22日 下午3:59:08  <br>
 * Description :  RolePermAction<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Controller("sys.role_permAction")
@RequestMapping("/sys/rolePerm")
public class RolePermAction extends BaseAction<RolePermVo> {
	final String VIEW_PATH = "/sys/role_perm/role_perm";
	@Autowired
	private IRolePermService rolePermService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IRoleFunService roleFunService;
	@Autowired
	private IFunctionService functionService;
	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<RolePermVo> baseService() {
		return rolePermService;
	}
	/**
	 * Create on : Paddy Zhang 2016年11月21日 下午4:32:21 <br>
	 * Description :  角色  >　功能　给功能赋权<br>
	 * @param roleId
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value = "preSaveFunPerm.do")
	public ModelAndView preSaveFunPerm(String roleId,String funId) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, roleService.findById(roleId));//查询角色
		FunctionVo functionVo = new FunctionVo();
		if(StrUtils.isNotBlankOrNull(funId)){
			functionVo = functionService.findById(funId);
			mav.addObject("functionVo", functionVo);
		}
		/*List<RolePermVo> rolePermList = rolePermService.listByRoleId(roleId);
		String selectPerIds = "";
		for (int i = 0,j=rolePermList.size(); i < j; i++) {
			if(0!=i) selectPerIds+=",";
			selectPerIds+=rolePermList.get(i).getPermissionVo().getId();
		}
		mav.addObject("selectPerIds", selectPerIds);*/
		mav.setViewName("/sys/role/role_function_perm");
		return mav;
	}
	/**
	 * Create on : Paddy Zhang 2016年12月2日 下午2:00:40 <br>
	 * Description : 获取某个功能下一有的权限的ids <br>
	 * @param roleId
	 * @param funId
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxgetExistPermIds.do")
	public Map<String, String> ajaxgetExistPermIds(@RequestParam(value = "roleId", required = true)String roleId,
			@RequestParam(value = "funId", required = true)String funId,RedirectAttributes attr) throws GlobalException {
		List<RolePermVo> rolePermList = rolePermService.listByRoleAndFunId(funId, roleId);
		String selectPerIds = "";
		for (int i = 0,j=rolePermList.size(); i < j; i++) {
			if(0!=i) selectPerIds+=",";
			selectPerIds+=rolePermList.get(i).getPermissionVo().getId();
		}
		Map<String, String> map = new HashMap<String,String>();
		map.put("selectPerIds", selectPerIds);
		return map;
		
	}
	/**
	 * Create on : Paddy Zhang 2016年11月22日 上午9:05:40 <br>
	 * Description : 保存 角色 》  功能 下面 的权限 <br>
	 * @param roleId
	 * @param funId
	 * @param permIds
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxSaveFunPerm.do")
	public Status saveFunPerm(@RequestParam(value = "roleId", required = true)String roleId,
			@RequestParam(value = "funId", required = true)String funId,@RequestParam(value = "permIds", required = false) String permIds,
			RedirectAttributes attr) throws GlobalException {
		try {
			rolePermService.saveRolePerm(roleId,funId,permIds);
			status = new Status("赋权成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"赋权成功",e);
			status = new Status("赋权失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * Create on : Paddy Zhang 2016年11月21日 下午4:32:15 <br>
	 * Description : 获取角色  权限 下面  拥有的功能树 <br>
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = {"treeData4fun.do","treeData4fun"})
	public List<Map<String, Object>> treeData4fun(String roleId) throws GlobalException {
		RoleVo roleVo = null;
		if (!StrUtils.isBlankOrNull(roleId)) {
			roleVo = roleService.findById(roleId);
		}
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (null != roleVo) {
			RoleFunVo roleFunVo = new RoleFunVo();
			roleFunVo.setRoleVo(roleVo);
			List<RoleFunVo> list = roleFunService.list(roleFunVo);
			for (RoleFunVo vo : list) {
				if (null != vo.getFunctionVo()) {
					FunctionVo functionVo = vo.getFunctionVo();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", functionVo.getId());
					map.put("pId", null != functionVo.getParentVo() ? functionVo.getParentVo().getId()
							: "");
					map.put("name", functionVo.getName());
					if(null == functionVo.getParentVo()){
						map.put("open", "true");
					}else {
						map.put("open", "false");
					}
					mapList.add(map);
				}
			}
		}
		return mapList;
	}
}