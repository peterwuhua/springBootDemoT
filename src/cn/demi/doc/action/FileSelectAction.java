package cn.demi.doc.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.doc.service.IFileSelectService;
import cn.demi.doc.vo.DocumentVo;

/**
 * Create on : 2016年11月25日 上午8:56:22 <br>
 * Description : 文件查询 <br>
 * 
 * @version v 1.0.0 <br>
 * @author Dave Yu<br>
 */
@Controller("document.fileselectAction")
@RequestMapping("/doc/fileselect")
public class FileSelectAction extends BaseAction<DocumentVo> {
	final String VIEW_PATH = "/doc/file_select/file_select";
	@Autowired private IFileSelectService fileSelectService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<DocumentVo> baseService() {
		return fileSelectService;
	}

	@Override
	@RequestMapping(value = GRID_PAGE)
	public ModelAndView gridPage(DocumentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (!StrUtils.isBlankOrNull(v.getSelectName()) && !v.getSelectName().trim().equals("")) {
			mav.addObject("fileList", fileSelectService.findByAll(v.getSelectName()));
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}
}