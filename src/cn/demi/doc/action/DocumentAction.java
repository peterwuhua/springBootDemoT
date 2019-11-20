package cn.demi.doc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhuozhengsoft.pageoffice.FileSaver;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.doc.service.ICategoryService;
import cn.demi.doc.service.IDocumentService;
import cn.demi.doc.vo.CategoryVo;
import cn.demi.doc.vo.DocumentVo;
/**
 * Create on : 2016年11月24日 下午4:36:40  <br>
 * Description :  <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Controller("doc.documentAction")
@RequestMapping("/doc/document")
public class DocumentAction extends BaseAction<DocumentVo> {
	final String VIEW_PATH = "/doc/document/document";
	public static final String SERVER_UPLOAD_DIR = ApplicationUtils.getValue("config.upload.doc.document").toString().replace("\\", "/");
	@Autowired private IDocumentService documentService;
	@Autowired private ICategoryService categoryService;
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<DocumentVo> baseService() {
		return documentService;
	}
	
	@Override
	public ModelAndView gridPage(DocumentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		CategoryVo categoryVo = new CategoryVo();
		if(StrUtils.isNotBlankOrNull(v.getDirIds())){
			categoryVo = categoryService.findById(v.getDirIds());
		}
		v.setCategoryVo(categoryVo);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	
	@Override
	public ModelAndView edit(DocumentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dirIds", v.getDirIds());
		CategoryVo categoryVo = new CategoryVo();
		if(StrUtils.isNotBlankOrNull(v.getId()))
			v=documentService.findById(v.getId());
		if(StrUtils.isNotBlankOrNull(v.getDirIds())){
			categoryVo=categoryService.findById(v.getDirIds());
			v.setSign(categoryVo.getCode());
		}else if(StrUtils.isBlankOrNull(v.getDirIds()) && StrUtils.isNotBlankOrNull(v.getId())){
			v=documentService.findById(v.getId());
		}else{
			categoryVo=categoryService.findRoot();
		}
		v.setCategoryVo(categoryVo);
		boolean isEdit = false; 
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
			isEdit = true;
		}
		mav.addObject(VO, v);
		mav.addObject(IS_EDIT, isEdit);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	
	/**
	 * @param gridVo
	 * @param v
	 * @return GridVo
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4Tab.do")
	public GridVo gridData4Tab(GridVo gridVo,DocumentVo v) throws GlobalException{
		return documentService.gridData4Tab(gridVo, v);
	}
	
	@RequestMapping(value="save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存文档",module="文档管理")
	public ModelAndView save4Data(DocumentVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		ModelAndView mav = null;
		try {
			if (null!=file && !file.isEmpty()) {
				v.setRelativePath(getPath(v,file));
				upload(file, v.getRelativePath());
			}
			if (StrUtils.isBlankOrNull(v.getId())) {
				v.setState("现行");
				mav = super.add(v, attr);
			} else {
				mav = super.update(v, attr);
			}
			status = new Status("保存成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败", e);
			status = new Status("保存失败", Status.STATUS_ERROR);
		}
		mav.addObject("id", v.getId());
		mav.addObject("dirIds", v.getCategoryVo().getId());
		attr.addFlashAttribute(STATUS, status);
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}
	@RequestMapping(value="add4Data.do")
	@ResponseBody
	@Log(operation=Log.Operation.ADD,content="新增文档",module="文档管理")
	public Status add4Data(DocumentVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		if (null!=file && !file.isEmpty()) {
			v.setRelativePath(getPath(v,file));
			upload(file, v.getRelativePath());
		}
		v.setTime(DateUtils.getCurrDateStr());
		v.setState("现行");
		return super.addData(v, attr);
	}
	@RequestMapping(value="update4Data.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="修改文档",module="文档管理")
	public Status update4Data(DocumentVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		if (null!=file && !file.isEmpty()) {
			v.setRelativePath(getPath(v,file));
			upload(file, v.getRelativePath());
		}
		return super.updateData(v, attr);
	}
	
	/**
	 * Description : 打开文件方法 <br>
	 * @throws GlobalException
	 */
	@RequestMapping(value="open.do")
	public ModelAndView open(DocumentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.addObject("tempPath", v.getRelativePath());
		mav.setViewName(getViewPath()+"_open");
		return mav;
	}
	
	/**
	 * Create on : Dave Yu 2016年11月24日 上午10:49:41 <br>
	 * Description : 获取文件存储路径  <br>
	 * @param v
	 * @return String
	 */
	private String getPath(DocumentVo v,MultipartFile file){
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		v.setSize(convertFileSize(file.getSize()));
		v.setOriginalSize(file.getSize());
		v.setType(fileType);
		String path = SERVER_UPLOAD_DIR;
		if(StrUtils.isNotBlankOrNull(v.getSign())) {
			path = path+"/"+v.getSign()+"/"+getCurrent().getLoginName()+DateUtils.getCurrDateTime()+fileType;
		}else {
			path = path+"/"+getCurrent().getLoginName()+DateUtils.getCurrDateTime()+fileType;
		}
		return path.replace("\\", "/");
	}
	
	/**
	 * Description : 计算文件大小工具方法 <br>
	 * @param size
	 * @return String
	 */
	public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
 
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }
	
	/**
	 * Description : 更新文件页面action <br>
	 * @throws GlobalException
	 */
	@RequestMapping(value = "updateFile.do")
	public ModelAndView updateFile(DocumentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			BeanUtils.copyProperties(documentService.findById(v.getId()), v);
		}
		mav.addObject(VO, v);
		mav.addObject("dirIds", v.getCategoryVo().getId());
		mav.setViewName(getViewPath()+"_update_file");
		return mav;
	}
	
	/**
	 * Description : 更新文件页面保存方法 <br>
	 * @param v
	 * @param attr
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value = "addfile.do")
	public ModelAndView addFile(DocumentVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		try {
			if (null!=file && !file.isEmpty()) {
				v.setRelativePath(getPath(v,file));
				upload(file, v.getRelativePath());
			}
			DocumentVo documentVo = documentService.findById(v.getId());
			documentVo.setState("作废");//原数据作废
			baseService().update(documentVo);
			v.setReplaceId(v.getId());//把哪个文件夹更新了
			v.setState("现行");
			documentService.addUpdateFile(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("dirIds", v.getDirIds());
		attr.addFlashAttribute(STATUS, status);
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	
	/**
	 * Description : 文件历史action <br>
	 * @throws GlobalException
	 */
	@RequestMapping(value = "fileHistory.do")
	public ModelAndView fileHistory(DocumentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<DocumentVo> fileHistoryList = documentService.listByDocId(v.getId());
		mav.addObject("fileHistoryList", fileHistoryList);
		mav.addObject(VO, v);
		mav.addObject("dirIds", v.getDirIds());
		mav.setViewName(getViewPath()+"_file_history");
		return mav;
	}
	
	/**
	 * Description : ajax动态筛选文档类型数据 <br>
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/ajaxSeachType.do")
	public Map<String, Integer> ajaxSeachType(DocumentVo vo) throws GlobalException {
		List<DocumentVo> list = documentService.list(vo);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (DocumentVo v : list) {
			if (StrUtils.isNotBlankOrNull(v.getType()) && !map.containsKey(v.getType())) {
				map.put(v.getType(), 1);
			} else if(StrUtils.isNotBlankOrNull(v.getType())){
				map.put(v.getType(), map.get(v.getType()) + 1);
			}
		}
		return map;
	}
	
	/**
	 * Description : 跳转弹窗页面 <br>
	 * @throws GlobalException
	 */
	@RequestMapping("/selectSeachValue.do")
	public ModelAndView selectSeachValue(String pageType,String pageValue,String inputValue) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		List<String> list = documentService.selectByPageType(pageType);
		mav.addObject("list", list);
		mav.addObject("type", pageType);
		mav.addObject("value", pageValue);
		mav.addObject("inputValue", inputValue);
		mav.setViewName(getViewPath() + "_select_layer");
		return mav;
	}
	@Override
	public ModelAndView update2del(DocumentVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update2del(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("dirIds", v.getDirIds());
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	/**
	 * 为其他模块 选择文件 
	 * 数据源
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="gridData4Select.do")
	public GridVo gridData4Select(GridVo gridVo, DocumentVo v) throws GlobalException {
		return documentService.gridData4Select(gridVo, v);
	}
	/**
	 * Description : 在线保存文件  <br>
	 */
	@ResponseBody
	@RequestMapping(value="ajaxSaveFile.do")
	public void ajaxSaveFile(DocumentVo v) throws GlobalException {
		FileSaver fs = new FileSaver(request,response);
		String targetFile = SERVER_BASE+v.getRelativePath().replace("/", "\\");
		fs.saveToFile(targetFile);
		fs.close();
	}
}