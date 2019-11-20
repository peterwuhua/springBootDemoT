package cn.demi.office.action;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.office.service.ICmViewService;
import cn.demi.office.vo.CmVo;

@Controller("office.cmViewAction")
@RequestMapping("/office/cmView")
public class CmViewAction extends BaseAction<CmVo> {
	protected static final String DOWNLOAD_DIR = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");
	final String VIEW_PATH = "/office/cm/cm_view";
	@Autowired 
	private ICmViewService cmViewService;	
	@Autowired
	private IFilesService filesService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<CmVo> baseService() {
		return cmViewService;
	}
	@Override
	public ModelAndView show(CmVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = cmViewService.findById(v.getId());
			if(null==v.getViewEdIds()||!v.getViewEdIds().contains(getCurrent().getAccountId())) {
				//第一次打开 更新已查看人信息
				cmViewService.update(v);
			}
			List<FilesVo> fileList=filesService.listByBusId(v.getId());
			v.setFileList(fileList);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@Override
	public GridVo gridData(GridVo gridVo, CmVo v) throws GlobalException {
		return super.gridData(gridVo, v);
	}
	@RequestMapping(value = DOWNLOAD)
	public ResponseEntity<byte[]> download(
			@RequestParam(value = "filePath", required = true) String filePath,
			@RequestParam(value = "trueName", required = true) String trueName) throws GlobalException {
		String ctxPath = DOWNLOAD_DIR + filePath;
		String downLoadPath = ctxPath.replace("/", File.separator);
        return down(trueName, downLoadPath);
	}
  
}