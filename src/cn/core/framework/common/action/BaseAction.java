package cn.core.framework.common.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.common.vo.Vo;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.export.ExportUtils;
import cn.core.framework.utils.importx.ImportUtils;
import cn.demi.base.system.vo.FilesVo;

/**
 * <strong>Create on : 2016年11月2日 上午10:09:44 </strong> <br>
 * <strong>Description : 通用baseAction</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
public abstract class BaseAction<V extends Vo<V>> extends Action {
	
	protected static final String UPLOAD_DIR = ApplicationUtils.getValue("config.upload.path").toString().replace("\\", "/");
	
	/**
	 * status
	 */
	protected static final String STATUS = "status";
	/**
	 * vo
	 */
	protected static final String VO = "vo";
	/**
	 * isEdit
	 */
	protected static final String IS_EDIT = "isEdit";
	/**
	 * 待办
	 */
	protected static final String PAGE = "page.do";
	/**
	 * 已办
	 */
	protected static final String PAGED = "paged.do";
	/**
	 * 不带分页的数据列表
	 */
	protected static final String LIST = "list.do";
	/**
	 * 修改前
	 */
	protected static final String EDIT = "edit.do";
	/**
	 * 查看前
	 */
	protected static final String SHOW = "show.do";
	/**
	 * 弹窗查看前
	 */
	protected static final String VIEW = "view.do";
	/**
	 * 添加
	 */
	protected static final String ADD = "add.do";
	/**
	 * 添加数据
	 */
	protected static final String ADD_DATA = "addData.do";
	/**
	 * copy
	 */
	protected static final String COPY = "copy.do";
	/**
	 * 修改
	 */
	protected static final String UPDATE = "update.do";
	/**
	 * 修改数据
	 */
	protected static final String UPDATE_DATA = "updateData.do";
	/**
	 * 逻辑删除
	 */
	protected static final String UPDATE_2_DEL = "update2del.do";
	/**
	 * 物理删除
	 */
	protected static final String DELETE = "delete.do";
	/**
	 * 保存（保存后默认返回原页面）
	 */
	protected static final String SAVE = "save.do";
	/**
	 * 审核（用于弹窗确认）
	 */
	protected static final String AUDIT = "audit.do";
	/**
	 * 导出
	 */
	protected static final String EXPORT = "export.do";
	/**
	 * 导出
	 */
	protected static final String EXPORT_GRID_DATA = "exportGridData.do";
	
	/**
	 * 导出
	 */
	protected static final String COLUMN = "column.do";
	
	/**
	 * 导入
	 */
	protected static final String IMPORT = "import.do";
	/**
	 * 下载
	 */
	protected static final String DOWNLOAD = "download.do";
	/**
	 * 下载
	 */
	protected static final String DOWNTEMP = "downtemp.do";
	/**
	 * left
	 */
	protected static final String LEFT = "left.do";
	/**
	 * frame
	 */
	protected static final String FRAME = "frame.do";
	/**
	 * tree
	 */
	protected static final String TREE = "tree.do";
	/**
	 * middle
	 */
	protected static final String MIDDLE = "middle.do";
	/**
	 * select （弹窗选择）
	 */
	protected static final String SELECT = "select.do";
	
	/**
	 * Ajax获取符合JqGrid的数据格式
	 */
	protected static final String GRID_DATA = "gridData.do";
	/**
	 * Ajax获取符合JqGrid的数据格式
	 */
	protected static final String GRID_DATAD = "gridDatad.do";
	/**
	 * jqgride待办数据页
	 */
	protected static final String GRID_PAGE = "gridPage.do";
	/**
	 * jqgride已办数据页
	 */
	protected static final String GRID_PAGED = "gridPaged.do";
	/**
	 * jqgride通用编辑页
	 */
	protected static final String GRID_EDIT = "gridEdit.do";
	/**
	 * 跳转至page页
	 */
	protected static final String REDIRECT_2_PAGE = "redirect:"+PAGE;
	/**
	 * 跳转至gridpage页
	 */
	protected static final String REDIRECT_2_GRID_PAGE = "redirect:"+GRID_PAGE;
	/**
	 * 跳转至gridpage页
	 */
	protected static final String REDIRECT_2_GRID_PAGED = "redirect:"+GRID_PAGED;
	/**
	 * 跳转至修改页
	 */
	protected static final String REDIRECT_2_EDIT = "redirect:"+EDIT;
	/**
	 * 跳转至查看页 
	 */
	protected static final String REDIRECT_2_SHOW = "redirect:"+SHOW;
	/**
	 * 跳转至LIST页 
	 */
	protected static final String REDIRECT_2_LIST = "redirect:"+LIST;
	/**
	 * 跳转至下载页 
	 */
	protected static final String REDIRECT_2_DOWNLOAD = "redirect:"+DOWNLOAD;
	/**
	 * Ajax获取符合JqGrid的数据格式
	 */
	protected static final String GRID_DATA_JSON = "gridData.json";
	/**
	 * jqgride通用编辑页
	 */
	protected static final String GRID_EDIT_JSON = "gridEdit.json";
	/**
	 * 拖拽行保存序号
	 */
	protected static final String UPDATE_PULL = "update4Pull.do";
	/**
	 * 拖拽行保存序号
	 */
	protected static final String UPDATE_SORT = "update4Sort.do";
	/**
	 * 获取返回页面路径
	 */
	public abstract String getViewPath();
	/**
	 * 获取通用baseService
	 */
	public abstract IBaseService<V> baseService();
	/**
	 * 跳转至page页 .默认为 REDIRECT_2_GRID_PAGE ，可以定义为 REDIRECT_2_PAGE
	 */
	protected String REDIRECT_PAGE = REDIRECT_2_GRID_PAGE;
	/**
	 * 状态回执
	 */
	protected Status status = null;
	/**
	 * log
	 */
	public Logger log = Logger.getLogger(this.getClass());
	/**
	 * <strong>Create on :Carson Liu 2016年11月2日 上午10:19:34 </strong> <br>
	 * <strong>Description : 全局通用返回，根据页面请求直接返回对应JSP</strong> <br>
	 * @param v 通用action返回
	 * @param act 请求action 例如 edit.do 则返回 edit.jsp  
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="{act}.do")
	public ModelAndView act(V v,@PathVariable("act") String act) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_"+act);
		return mav;
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午10:22:07 </strong> <br>
	 * <strong>Description : gridPage</strong> <br>
	 * @param v V
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=GRID_PAGE)
	public ModelAndView gridPage(V v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	
	/**
	 * <strong>Create on : 2016年11月2日 上午10:22:33 </strong> <br>
	 * <strong>Description : gridPaged</strong> <br>
	 * @param v V
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=GRID_PAGED)
	public ModelAndView gridPaged(V v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_ed_page");
		return mav;
	}
	
	/**
	 * <strong>Create on : 2016年11月2日 上午10:22:55 </strong> <br>
	 * <strong>Description : page</strong> <br>
	 * @param v V
	 * @param pageResult PageResult
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=PAGE)
	public ModelAndView page(V v,PageResult pageResult) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		//pageResult = baseService().pageResult(v, pageResult);
		mav.addObject("pageResult", pageResult);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	
	/**
	 * <strong>Create on : 2016年11月2日 上午10:23:19 </strong> <br>
	 * <strong>Description : paged</strong> <br>
	 * @param v V 
	 * @param pageResult PageResult
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=PAGED)
	public ModelAndView paged(V v,PageResult pageResult) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		pageResult = baseService().pageResulted(v, pageResult);
		mav.addObject("pageResult", pageResult);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_ed_page");
		return mav;
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午10:23:41 </strong> <br>
	 * <strong>Description : 通用LIST</strong> <br>
	 * @param v V
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=LIST)
	public ModelAndView list(V v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<V> list = baseService().list(v);
		mav.addObject("list", list);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_list");
		return mav;
	}
	/**	
	 * <strong>Create on : 2016年11月2日 上午10:24:17 </strong> <br>
	 * <strong>Description : 通用编辑前方法</strong> <br>
	 * @param v V
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=EDIT)
	public ModelAndView edit(V v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
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
	 * <strong>Create on : 2016年11月2日 上午10:24:42 </strong> <br>
	 * <strong>Description : show</strong> <br>
	 * @param v V
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=SHOW)
	public ModelAndView show(V v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	
	/**	
	 * <strong>Create on : 2016年11月2日 上午10:24:42 </strong> <br>
	 * <strong>Description : view</strong> <br>
	 * @param v V
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=VIEW)
	public ModelAndView view(V v) throws GlobalException {
		ModelAndView mav = edit(v);
		mav.setViewName(getViewPath()+"_view");
		return mav;
	}
	
	/**	
	 * <strong>Create on : 2016年11月2日 上午10:24:42 </strong> <br>
	 * <strong>Description : audit</strong> <br>
	 * @param v V
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=AUDIT)
	public ModelAndView audit(V v) throws GlobalException {
		ModelAndView mav = edit(v);
		mav.setViewName(getViewPath()+"_audit");
		return mav;
	}
	/**	
	 * <strong>Create on : 2016年11月2日 上午10:25:21 </strong> <br>
	 * <strong>Description : 保存，成功后跳转至列表页面</strong> <br>
	 * @param v V
	 * @param attr RedirectAttributes
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=ADD)
	public ModelAndView add(V v,RedirectAttributes attr) throws GlobalException {
		try {
			baseService().add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		ModelAndView mav = new ModelAndView();
		attr.addFlashAttribute(STATUS, status);
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	
	/**	
	 * <strong>Create on : 2016年11月2日 上午10:25:21 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param v V
	 * @param attr RedirectAttributes
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=ADD_DATA)
	@ResponseBody
	public Status addData(V v,RedirectAttributes attr) throws GlobalException {
		try {
			baseService().add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**	
	 * <strong>Create on : 2016年11月2日 上午10:25:38 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param v V
	 * @param attr RedirectAttributes
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=UPDATE_2_DEL)
	public ModelAndView update2del(V v,RedirectAttributes attr) throws GlobalException{
		try {
			baseService().update2del(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	
	/**	
	 * <strong>Create on : 2016年11月2日 上午10:25:54 </strong> <br>
	 * <strong>Description :保存，成功后跳转至修改页面 </strong> <br>
	 * @param v V 
	 * @param attr RedirectAttributes
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=SAVE)
	public ModelAndView save(V v,RedirectAttributes attr) throws GlobalException{
		try {
			baseService().save(v);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", v.getId());
		
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}
	
	/**
	 * <strong>Create on : 2016年11月2日 上午10:45:27 </strong> <br>
	 * <strong>Description : 修改方法，跳转至列表页面</strong> <br>
	 * @param v V
	 * @param attr RedirectAttributes
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=UPDATE)
	public ModelAndView update(V v,RedirectAttributes attr) throws GlobalException{
		try {
			baseService().update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	
	/**
	 * <strong>Create on : 2016年11月2日 上午10:45:27 </strong> <br>
	 * <strong>Description : update</strong> <br>
	 * @param v V
	 * @param attr RedirectAttributes
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=UPDATE_DATA)
	@ResponseBody
	public Status updateData(V v,RedirectAttributes attr) throws GlobalException{
		try {
			baseService().update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
	/**
	 * <strong>Create on : 2016年11月2日 上午10:45:27 </strong> <br>
	 * <strong>Description : 复制</strong> <br>
	 * @param v V
	 * @param attr RedirectAttributes
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value=COPY)
	public ModelAndView copy(V v,RedirectAttributes attr) throws GlobalException{
		String targetId = null;
		try {
			targetId = baseService().copy(v);
			status = new Status("copy成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("copy失败",e);
			status = new Status("copy成功",Status.STATUS_ERROR);
		}
 		ModelAndView mav = new ModelAndView();
 		attr.addFlashAttribute(STATUS, status);
		mav.addObject("id", targetId);
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午10:45:27 </strong> <br>
	 * <strong>Description : update</strong> <br>
	 * @param v V
	 * @param attr RedirectAttributes
	 * @return ModelAndView
	 * @throws GlobalException
	 */
 	@RequestMapping(value=DELETE)
	public ModelAndView delete(V v,RedirectAttributes attr) throws GlobalException{
		try {
			baseService().delete(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}  
		attr.addFlashAttribute(STATUS, status);
 		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
 	}
 	
 	/**
 	 * <strong>Create on : 2016年11月2日 下午12:45:01 </strong> <br>
 	 * <strong>Description : </strong> <br>
 	 * @param v v
 	 * @param pageResult 分页参数
 	 * @param type 类型  (0全部，1选，其他)
 	 * @param source 模板源文件
 	 * @param target  导出文件名
 	 * @return ResponseEntity
 	 * @throws GlobalException
 	 */
 	@RequestMapping(value = EXPORT)
	public ResponseEntity<byte[]> export(V v,PageResult pageResult,
			@RequestParam(value = "_type", required = true)String type,
			@RequestParam(value = "_source", required = true)String source,
			@RequestParam(value = "_target", required = true)String target) throws GlobalException {
		String path = ExportUtils.export(source, target, getExportData(v, pageResult, type));
		return down(target, path);
	}
	
	/**
 	 * <strong>Create on : 2016年11月2日 下午12:45:01 </strong> <br>
 	 * <strong>Description : </strong> <br>
 	 * @param GridVo gridVo
 	 * @param V v
 	 * @param source 模板源文件
 	 * @param target  导出文件名
 	 * @return ResponseEntity
 	 * @throws GlobalException
 	 */
 	@RequestMapping(value = EXPORT_GRID_DATA)
	public ResponseEntity<byte[]> export(GridVo gridVo,V v,
			@RequestParam(value = "_type", required = true)String type,
			@RequestParam(value = "_source", required = true)String source,
			@RequestParam(value = "_target", required = true)String target) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", baseService().gridData(gridVo,v).getDatas());
		String path = ExportUtils.export(source, target, map);
		return down(target, path);
	}
 	
	/**
	 * <strong>Create on : 2016年11月2日 下午12:45:36 </strong> <br>
	 * <strong>Description : 获取导出数据,默认调用 xxService.list(v)方法,(map.put("list", baseService().list(v)))</strong> <br>
	 * @param v 获取导出数据
	 * @param pageResult pageResult
	 * @param type type
	 * @return 带出数据map
	 * @throws GlobalException
	 */
	private Map<String, Object> getExportData(V v,PageResult pageResult,String type) throws GlobalException{
 		Map<String, Object> map = new HashMap<String, Object>();
 		if("0".equals(type)){//全部数据
 			List<V> exportDatas = baseService().list(v);
 			map.put("list", exportDatas);
 		}else if("1".equals(type)){//选择的数据
 			List<V> exportDatas = baseService().listByIds(v.getIds(),pageResult.getOrderColumn(),pageResult.getOrder());
 			map.put("list", exportDatas);
 		}else{
 			map = getExportData(map, v, pageResult, type);
 		}
 		return map;
 	}
	
	/**
	 * <strong>Create on : 2016年11月2日 下午12:41:33 </strong> <br>
	 * <strong>Description : getExportData</strong> <br>
	 * @param map Map<String, Object>
	 * @param v V 
	 * @param pageResult PageResult
	 * @param type type
	 * @return Map<String, Object>
	 * @throws GlobalException
	 */
	protected Map<String, Object> getExportData(Map<String, Object> map,V v,PageResult pageResult,String type) throws GlobalException{
 		log.info("自定义导出类型，请重写此方法 type " +type);
 		return map;
 	}
 	
	/**
	 * <strong>Create on : 2016年11月2日 下午12:42:43 </strong> <br>
	 * <strong>Description : 文件下载</strong> <br>
	 * @param filePath 文件下载相对路径
	 * @param trueName 文件下载后保存文件名
	 * @return ResponseEntity
	 * @throws GlobalException
	 */
	@RequestMapping(value = DOWNLOAD)
	public ResponseEntity<byte[]> download(
			@RequestParam(value = "filePath", required = true) String filePath,
			@RequestParam(value = "trueName", required = true) String trueName) throws GlobalException {
		String ctxPath = getRealPath(request);
		String downLoadPath = ctxPath + filePath.replace("/", File.separator);  
        return down(trueName, downLoadPath);
	}
 
	/**
	 * <strong>Create on : 2016年11月2日 下午12:43:04 </strong> <br>
	 * <strong>Description :  导入/导出模板下载方法</strong> <br>
	 * @param type 类型
	 * @param templateName 模板名称
	 * @param trueName 下载后文件名称
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value = DOWNTEMP)
 	public ResponseEntity<byte[]> downTemp(
 			@RequestParam(value = "type", required = true)String type,
 			@RequestParam(value = "templateName", required = true)String templateName,
 			@RequestParam(value = "trueName", required = true)String trueName) throws GlobalException {
 		String downLoadPath = null;
 		if("import".equals(type)){
 			downLoadPath = ImportUtils.getTemplatePath(templateName);
		}else{
			downLoadPath = ExportUtils.getTemplatePath(templateName);
		}
		return down(trueName, downLoadPath);   
	}
 	/**
 	 * <strong>Create on : 2016年11月17日 上午8:21:38 </strong> <br>
 	 * <strong>Description : 构建下载文件</strong> <br>
 	 * @param trueName 真实文件名
 	 * @param downLoadPath 文件路径
 	 * @return ResponseEntity
 	 */
 	protected ResponseEntity<byte[]> down(String trueName, String downLoadPath) throws GlobalException{
		File file=new File(downLoadPath);  
	    HttpHeaders headers = new HttpHeaders();    
	    String fileName = null;
		try {
			String userAgent = request.getHeader("User-Agent");
			if(userAgent.toUpperCase().indexOf("MSIE") > 0 || userAgent.toUpperCase().indexOf("TRIDENT")>0 ){//判断是否为IE
				fileName = URLEncoder.encode(trueName, "UTF-8");
			}else {
				fileName = new String(trueName.getBytes("UTF-8"),"iso-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			throw new GlobalException("文件名转码失败",e);
		}//为了解决中文名称乱码问题  
	    headers.setContentDispositionFormData("attachment", fileName);   
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
	    byte[] o = null;
		try {
			o = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			throw new GlobalException("读取文件失败",e);
		}
	    return new ResponseEntity<byte[]>(o,headers,HttpStatus.OK);
	}

	/**
	 * <strong>Create on : 2016年11月2日 下午12:43:26 </strong> <br>
	 * <strong>Description : 数据导入</strong> <br>
	 * @param v V
	 * @param param param
	 * @param type type
	 * @param file file
	 * @param attr attr
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value = IMPORT,method = RequestMethod.POST)
	public ModelAndView importExcel(V v,@RequestParam(value = "param") String param,@RequestParam(value = "type", required = false) String type,@RequestParam(value = "file", required = false) MultipartFile file,RedirectAttributes attr) throws GlobalException {
		try {
			baseService().importData(v,type,param,ImportUtils.importData(file));
			status = new Status("导入成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.error("数据导入失败 ",e);
			status = new Status("导入失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		attr.addAttribute("stat",status.getStatus());
		attr.addAttribute("msg",status.getMessage());
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	
	/**
	 * <strong>Create on : 2016年11月2日 下午12:42:02 </strong> <br>
	 * <strong>Description :  数据导入</strong> <br>
	 * @param v V
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value = IMPORT,method = RequestMethod.GET)
	public ModelAndView preImport(V v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_import");
		return mav;
	}
	
	/**
	 * <strong>Create on : 2016年11月2日 下午12:42:02 </strong> <br>
	 * <strong>Description :  数据导出</strong> <br>
	 * @param v V
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value = COLUMN,method = RequestMethod.GET)
	public ModelAndView column(V v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.addObject("columnMap", v.getExportMap(v.getClass(), ""));
		mav.setViewName(getViewPath()+"_column");
		return mav;
	}
	
	/**
	 * <strong>Create on : 2016年11月17日 下午2:31:27 </strong> <br>
	 * <strong>Description : 上传文件</strong> <br>
	 * @param file
	 * @param targetPath
	 * @throws GlobalException
	 */
	public void upload(MultipartFile file,String targetPath) throws GlobalException{
		if(null==file) return;
		File targetFile = new File(SERVER_BASE+targetPath);
		if (!targetFile.exists()) 
			targetFile.mkdirs();
		try {// 保存
			file.transferTo(targetFile);
		} catch (IllegalStateException e) {
			throw new GlobalException(e);
		} catch (IOException e) {
			throw new GlobalException(e);
		}
	}
	/**
	 * <strong>Create on : 2016年11月17日 下午2:31:27 </strong> <br>
	 * <strong>Description : 上传文件</strong> <br>
	 * @param file
	 * @param targetPath
	 * @throws GlobalException
	 */
	public List<FilesVo> uploads(MultipartFile[] files,String busId,String busType) throws GlobalException{
		List<FilesVo> filesList=null;
		if(null!=files) {
			filesList=new ArrayList<FilesVo>();
			for (MultipartFile file : files) {
				 if(file != null){  
	                String myFileName = file.getOriginalFilename();  //取得当前上传文件的文件名称  
	                if(myFileName.trim() !=""){ //如果名称不为“”,说明该文件存在，否则说明该文件不存在   
	                	String suffix = myFileName.substring(myFileName.lastIndexOf("."));
	            		String fileName = DateUtils.getYear()+DateUtils.getMonth()+DateUtils.getDay()+"_"+new Date().getTime()+suffix;
	            		String subPath=UPLOAD_DIR+busType+File.separator+busId+File.separator;
	            		if(StrUtils.isBlankOrNull(busId)) {
	            			subPath=UPLOAD_DIR+busType+File.separator;
	            		}
	            		subPath=subPath.replace("\\", "/");
	            		File targetFile = new File(SERVER_BASE+subPath);
	            		if (!targetFile.exists()) //检测目录
	            			targetFile.mkdirs();
	                    try {// 保存
	                    	File tempFile = new File(targetFile.getPath()+File.separator+fileName);
	                    	file.transferTo(tempFile); 
	                    	FilesVo fileVo=new FilesVo();
	 	                    fileVo.setBusId(busId);
	 	                    fileVo.setBusType(busType);
	 	                    fileVo.setFileName(myFileName);
	 	                    fileVo.setFilePath(subPath+"/"+fileName);
	 	                    fileVo.setFileType(suffix);
	 	                    filesList.add(fileVo);
	            		} catch (IllegalStateException e) {
	            			throw new GlobalException(e);
	            		} catch (IOException e) {
	            			throw new GlobalException(e);
	            		}
	                }  
	            }  
			}
		}
		return filesList;
	}
	/**
	 * <strong>Description : 获取gridData</strong> <br>
	 * @param gridVo  GridVo
	 * @param v  V
	 * @return GridVo
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = {GRID_DATA,GRID_DATA_JSON})
	public GridVo gridData(GridVo gridVo,V v) throws GlobalException{
		return baseService().gridData(gridVo,v);
	}
	/**
	 * <strong>Description : 获取gridData</strong> <br>
	 * @param gridVo  GridVo
	 * @param v  V
	 * @return GridVo
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = {GRID_DATAD})
	public GridVo gridDatad(GridVo gridVo,V v) throws GlobalException{
		return baseService().gridDatad(gridVo,v);
	}
	/**
	 * <strong>Create on : 2016年11月2日 上午10:47:00 </strong> <br>
	 * <strong>Description : gridEdit</strong> <br>
	 * @param v V
	 * @return Status
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = {GRID_EDIT,GRID_EDIT_JSON})
	public Status gridEdit(V v) throws GlobalException{
		String act = request.getParameter("oper");
		try {
			if("edit".equals(act)){
				baseService().update4Grid(v);
				status = new Status("修改成功",Status.STATUS_SUCCESS);
			}else if("add".equals(act)){
				baseService().add4Grid(v);
				status = new Status("添加成功",Status.STATUS_SUCCESS);
			}
			else if("del".equals(act)){
				baseService().update2del(v.getId());
				status = new Status("删除成功",Status.STATUS_SUCCESS);
			}
		} catch (GlobalException e) {
			log.warn(e);
			status = new Status("操作失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * Create on : Paddy Zhang 2017年4月21日 下午1:17:22 <br>
	 * Description :  拖拽行保存数据<br>
	 * @param selectId 选择的行id
	 * @param placeId 替换行id
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value= {UPDATE_PULL})
	public Status update4Pull(String selectId,String placeId) throws GlobalException {
		try {
			baseService().update4Pull(selectId,placeId);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.warn(e);
			status = new Status("操作失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * Create on : Paddy Zhang 2017年4月21日 下午1:18:20 <br>
	 * Description : 点击排序序号修改sort <br>
	 * @param id 选择行id
	 * @param sort 修改后的sort
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value= {UPDATE_SORT})
	public Status update4Sort(String id,String sort) throws GlobalException {
		try {
			 baseService().update4Sort(id,sort);
			 status = new Status("修改成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.warn(e);
			status = new Status("操作失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
}