package cn.demi.doc.action;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.doc.service.IFileListService;
import cn.demi.doc.vo.DocumentVo;
/**
 * Create on : 2016年11月24日 下午2:40:33  <br>
 * Description : 文件一览表 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Controller("document.filelistAction")
@RequestMapping("/doc/filelist")
public class FileListAction extends BaseAction<DocumentVo> {
	final String VIEW_PATH = "/doc/file_list/file_list";
	@Autowired private IFileListService fileListService;
	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<DocumentVo> baseService() {
		return fileListService;
	}
}