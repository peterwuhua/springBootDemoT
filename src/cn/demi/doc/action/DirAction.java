package cn.demi.doc.action;

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
import cn.core.framework.utils.CollectionUtils;
import cn.demi.base.system.service.IRoleService;
import cn.demi.doc.service.IDirService;
import cn.demi.doc.service.IRoleDocumentService;
import cn.demi.doc.vo.CategoryVo;
import cn.demi.doc.vo.RoleDocumentVo;
/**
 * Create on : 2016年11月21日 下午3:03:27  <br>
 * Description : 文件夹赋权  <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Controller("document.dirAction")
@RequestMapping("/doc/dir")
public class DirAction extends BaseAction<CategoryVo> {
	final String VIEW_PATH = "/doc/dir/dir";
	@Autowired private IRoleDocumentService roleDocumentService;
	@Autowired private IRoleService roleService;
	@Autowired private IDirService dirService;
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<CategoryVo> baseService() {
		return dirService;
	}
	
	/**
	 * Create on : Dave Yu 2016年11月21日 下午5:31:09 <br>
	 * Description : 按文加件赋权action <br>
	 * @param RoleDocumentVo
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@Override
	public ModelAndView edit(CategoryVo categoryVo) throws GlobalException {
		ModelAndView mav = super.edit(categoryVo);
		mav.addObject("roleList", roleService.list());
		List<RoleDocumentVo> roleDocumentList = roleDocumentService.listByPerDirId(categoryVo.getId());
		if(CollectionUtils.isNotEmpty(roleDocumentList)){
			String roleSelectIds = "";
			for (RoleDocumentVo roleDocumentVo : roleDocumentList) {
				roleSelectIds += roleDocumentVo.getRoleVo().getId() + ",";
			}
			mav.addObject("roleSelectIds", roleSelectIds);
		}
		return mav;
	}
	
	/**
	 * Create on : Dave Yu 2016年11月21日 下午5:31:39 <br>
	 * Description : 按角色赋权保存方法 <br>
	 * @param v 角色对象
	 * @param attr attr
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="saveRoleDocument.do")
	@Log(operation=Log.Operation.SAVE,content="保存",module="按文件夹赋权")
	public ModelAndView saveRoleDocument(RoleDocumentVo v, RedirectAttributes attr) throws GlobalException {
		try {
			dirService.save(v);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", v.getId());
		mav.addObject("pid",v.getDirIds());
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}
	/**
	 * Create on : Dave Yu 2016年11月21日 下午5:31:39 <br>
	 * Description : 按角色赋权保存方法 <br>
	 * @param v 角色对象
	 * @param attr attr
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="updateRoleDocument.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新",module="按文件夹赋权")
	public Status updateRoleDocument(RoleDocumentVo v, RedirectAttributes attr) throws GlobalException {
		try {
			dirService.save(v);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		return status;
	}
}