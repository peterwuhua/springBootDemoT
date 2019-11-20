package cn.demi.res.action;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.res.service.ISupplierService;
import cn.demi.res.vo.SupplierVo;

/** <strong>Create on : 2017年2月28日 下午1:22:50 </strong> <br>
 * <strong>Description : </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
@Controller("res.supplierAction")
@RequestMapping("/res/supplier")
public class SupplierAction extends BaseAction<SupplierVo> {
	final String VIEW_PATH = "/res/supplier/supplier";
	public static final String SERVER_BASE = ApplicationUtils.getValue("config.server.base").toString()
			.replace("\\", File.separator);
	public static final String TEMP_PATH = ApplicationUtils.getValue("config.upload.path").toString()
			.replace("\\", File.separator);
	@Autowired
	private ISupplierService supplierService;
	@Autowired
	private IFilesService filesService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<SupplierVo> baseService() {
		return supplierService;
	}
	
	@RequestMapping("/evaluatePage")
	public ModelAndView recordPage(SupplierVo v) throws GlobalException{
		ModelAndView mv = new ModelAndView();
		mv.addObject(VO, v);
		mv.setViewName(VIEW_PATH + "_evaluate_page");
		return mv;
	}
	/** <strong>Create on : Hans He 2017年3月1日 上午9:46:10 </strong> <br>
	 * <strong>Description : 删除附件</strong> <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping("/removeFile")
	@Log(operation=Log.Operation.DELETE,content="删除信息",module="供应商管理")
	public ModelAndView removeFile(SupplierVo v) throws GlobalException{
		ModelAndView mv = new ModelAndView();
		supplierService.removeFile(v);
		v = supplierService.findById(v.getId());
		mv.addObject(VO, v);
		mv.setViewName(VIEW_PATH + "_edit");
		return mv;
	}
	@Override
	public ModelAndView edit(SupplierVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
			v.setFileList(filesService.listByBusId(v.getId()));
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	
	/** <strong>Create on : Hans He 2017年3月1日 下午1:22:29 </strong> <br>
	 * <strong>Description :保存 </strong> <br>
	 * @param v
	 * @param attr
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping("/save2File")
	@Log(operation=Log.Operation.SAVE,content="保存信息",module="供应商管理")
	public ModelAndView save2File(SupplierVo v,RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException{
	     try {
			baseService().save(v);
			//文件上传
			List<FilesVo> fileList=uploads(file,v.getId(),Constants.FILE_TYPE_SUPP);
			filesService.saveFiles(fileList);
			
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
	
	@RequestMapping("/add2File")
	@ResponseBody
	@Log(operation=Log.Operation.ADD,content="新增信息",module="供应商管理")
	public Status add2File(SupplierVo v,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException{
		try {
			baseService().add(v);
			//文件上传
			List<FilesVo> fileList=uploads(file,v.getId(),Constants.FILE_TYPE_SUPP);
			filesService.saveFiles(fileList);
			
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping("/update2File")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新信息",module="供应商管理")
	public Status update2File(SupplierVo v,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException{
		try {
			//文件上传
			List<FilesVo> fileList=uploads(file,v.getId(),Constants.FILE_TYPE_SUPP);
			filesService.saveFiles(fileList);
			
			baseService().update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
}
