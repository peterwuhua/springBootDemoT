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
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.vo.RoleVo;
import cn.demi.doc.service.ICategoryService;
import cn.demi.doc.service.IDocumentService;
import cn.demi.doc.service.IRoleDocumentService;
import cn.demi.doc.service.IRoleService;
import cn.demi.doc.vo.DocumentVo;
import cn.demi.doc.vo.RoleDocumentVo;
/**
 * Create on : 2016年11月21日 下午3:03:27  <br>
 * Description : 文件赋权  <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Controller("document.roleAction")
@RequestMapping("/doc/role")
public class RoleAction extends BaseAction<RoleVo> {
	final String VIEW_PATH = "/doc/role/role";
	@Autowired private IRoleService docRoleService;
	@Autowired private IRoleDocumentService roleDocumentService;
	@Autowired private IDocumentService documentService;
	@Autowired private ICategoryService categoryService;
	@Autowired private cn.demi.base.system.service.IRoleService roleService;
	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<RoleVo> baseService() {
		return roleService;
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
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="保存权限",module="按角色赋权")
	public Status saveRoleDocument(RoleDocumentVo v, RedirectAttributes attr) throws GlobalException {
		try {
			if(StrUtils.isBlankOrNull(v.getPid())){
				v.setPid(categoryService.findRoot().getId());
			}
			docRoleService.save(v);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * Create on : Dave Yu 2016年11月29日 下午9:03:00 <br>
	 * Description : 按角色赋权二级页面文件数据Grid表单action <br>
	 * @param gridVo
	 * @param v
	 * @return GridVo
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = {"docGridData.do"})
	public GridVo docGridData(GridVo gridVo,DocumentVo v) throws GlobalException{
		return documentService.gridData4Tab(gridVo,v);
	}
	
	@Override
	public ModelAndView edit(RoleVo v) throws GlobalException {
		ModelAndView mav = super.edit(v);
		List<RoleDocumentVo> roleDocumentList = roleDocumentService.listByRoleIds(v.getId());
		String documentSelectIds = "";
		for (int i=0,j=roleDocumentList.size();i<j;i++) {
			if(null != roleDocumentList.get(i).getDocumentVo() && StrUtils.isNotBlankOrNull(roleDocumentList.get(i).getDocumentVo().getId())){
				if(null != roleDocumentList.get(i).getDocumentVo() 
						&& StrUtils.isNotBlankOrNull(roleDocumentList.get(i).getDocumentVo().getId())){
					documentSelectIds += roleDocumentList.get(i).getDocumentVo().getId()+",";
				}
			}
		}
		mav.addObject("documentSelectIds",documentSelectIds);
		return mav;
	}
}