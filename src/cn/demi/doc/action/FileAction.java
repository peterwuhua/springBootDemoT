package cn.demi.doc.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.CollectionUtils;
import cn.demi.base.system.service.IRoleService;
import cn.demi.doc.service.IFileService;
import cn.demi.doc.service.IRoleDocumentService;
import cn.demi.doc.vo.DocumentVo;
import cn.demi.doc.vo.RoleDocumentVo;
/**
 * Create on : 2016年11月21日 下午3:03:27  <br>
 * Description : 文件赋权  <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Controller("document.fileAction")
@RequestMapping("/doc/file")
public class FileAction extends BaseAction<DocumentVo> {
	final String VIEW_PATH = "/doc/file/file";
	@Autowired private IFileService fileService;
	@Autowired private IRoleDocumentService roleDocumentService;
	@Autowired private IRoleService roleService;
	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<DocumentVo> baseService() {
		return fileService;
	}
	
	
	/**
	 * Create on : Dave Yu 2016年11月21日 下午5:31:09 <br>
	 * Description : 按角色赋权action <br>
	 * @param RoleDocumentVo
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@Override
	public ModelAndView edit(DocumentVo documentVo) throws GlobalException {
		ModelAndView mav = super.edit(documentVo);
		mav.addObject("roleList", roleService.list());
		List<RoleDocumentVo> roleDocumentList = roleDocumentService.listByDocumentIds(documentVo.getId());
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
	 * Description : 按文件赋权保存方法 <br>
	 * @param v v
	 * @param attr attr
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存权限",module="按文件夹赋权")
	public ModelAndView save(DocumentVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView  mav = super.save(v, attr);
		mav.addObject("id", v.getId());
		mav.addObject("dirIds", v.getDirIds());
		if(v.getReSave().equals("1")){
			mav.setViewName(REDIRECT_2_EDIT);
		}else{
			mav.setViewName(REDIRECT_2_GRID_PAGE);
		}
		return mav;
	}
	
}