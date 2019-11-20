package cn.demi.base.system.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;

@Controller("sys.filesAction")
@RequestMapping("/sys/files")
public class FilesAction extends BaseAction<FilesVo> {
	final String VIEW_PATH = "/sys/files/files";
	@Autowired private IFilesService filesService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<FilesVo> baseService() {
		return filesService;
	}
	
	@ResponseBody
	@RequestMapping(value="deleteOne.do")
	@Log(operation=Log.Operation.DELETE,content="根据ID删除附件",module="附件管理")
	public Status deleteOne(FilesVo v) throws GlobalException {
		try {
			filesService.deleteById(v.getId());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * Description : 查看文件  <br>
	 * @param v
	 * @param tempPath
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value={"showFile.do"})
	public ModelAndView showInfo(String id) throws GlobalException {
		ModelAndView mav =  new ModelAndView();
		FilesVo v=filesService.findById(id);
		mav.addObject("id",v.getId());
		mav.addObject("tempPath",v.getFilePath());
		if(v.getFilePath().endsWith(".pdf")) {
			mav.setViewName(getViewPath() + "_pdf_show");
		}else {
			mav.setViewName(getViewPath() + "_file_show");
		}
		return mav;
	}
}