package cn.demi.base.system.action;

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
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.CollectionUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IRoleFunService;
import cn.demi.base.system.service.IRoleService;
import cn.demi.base.system.vo.RoleFunVo;
import cn.demi.base.system.vo.RoleVo;

/**
 * <strong>Create on : 2016年11月2日 下午2:26:02 </strong> <br>
 * <strong>Description : 角色action</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Controller("sys.roleAction")
@RequestMapping("sys/role")
public class RoleAction extends BaseAction<RoleVo> {

	final String VIEW_PATH = "/sys/role/role";
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IRoleFunService roleFunService;


	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<RoleVo> baseService() {
		return roleService;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增角色",module="角色管理")
	public ModelAndView add(RoleVo v, RedirectAttributes attr) throws GlobalException {
		return super.add(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改角色",module="角色管理")
	public ModelAndView update(RoleVo v, RedirectAttributes attr) throws GlobalException {
		return super.update(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存角色",module="角色管理")
	public ModelAndView save(RoleVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="删除角色",module="角色管理")
	public ModelAndView update2del(RoleVo v, RedirectAttributes attr) throws GlobalException {
		return super.update2del(v, attr);
	}
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:26:17 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param roleId roleId
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value = "preSavePermission.do")
	public ModelAndView preSavePermission(String roleId) throws GlobalException {
		ModelAndView mav = new ModelAndView(getViewPath() + "_permission");
		mav.addObject(VO, roleService.findById(roleId));
		List<RoleFunVo> list = roleFunService.listByRoleId(roleId);
		if (CollectionUtils.isNotEmpty(list)) {
			String selectedIds = "";
			for (int i = 0,j=list.size(); i < j; i++) {
				if(0!=i) selectedIds+=",";
				selectedIds+=list.get(i).getFunctionVo().getId();
			}
			mav.addObject("selectedIds", selectedIds);
		}
		return mav;
	}
	/**
	 * <strong>Description : </strong> <br>
	 * @param roleId roleId
	 * @param functionIds functionIds
	 * @param attr attr
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxSavePermission.do")
	@Log(operation=Log.Operation.SAVE,content="角色赋权",module="角色管理")
	public Status ajaxSavePermission(String roleId, String functionIds,
			RedirectAttributes attr) throws GlobalException {
		try {
			roleFunService.save(roleId, functionIds);
			status = new Status("赋权成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"赋权成功",e);
			status = new Status("赋权失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		return status;
	}
	/**
	 * Create on : Paddy Zhang 2016年11月18日 下午4:29:15 <br>
	 * Description : 角色-账户编辑 <br>
	 * @param v
	 * @return
	 */
	@RequestMapping(value = "accountEdit.do")
	public ModelAndView accountEdit(String roleId) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		boolean isEdit = false; 
		RoleVo v = new RoleVo();
		if(!StrUtils.isBlankOrNull(roleId)){
			v = roleService.findById(roleId);
			isEdit = true;
		}
		mav.addObject(VO, v);
		mav.addObject(IS_EDIT, isEdit);
		mav.setViewName(getViewPath()+"_account");
		return mav;
	}

	@Override
	@Log(operation=Log.Operation.SHOW,content="角色管理编辑前",module="系统管理-角色管理")
	public ModelAndView edit(RoleVo v) throws GlobalException {
		return super.edit(v);
	}
	
}
