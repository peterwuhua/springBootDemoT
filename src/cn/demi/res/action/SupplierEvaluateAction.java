package cn.demi.res.action;

import java.io.File;

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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.demi.res.service.ISupplierEvaluateService;
import cn.demi.res.service.ISupplierService;
import cn.demi.res.vo.SupplierEvaluateVo;
import cn.demi.res.vo.SupplierVo;

/** <strong>Create on : 2017年2月28日 下午1:23:11 </strong> <br>
 * <strong>Description : </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
@Controller("res.supplierEvaluateAction")
@RequestMapping("/res/supplierEvaluate")
public class SupplierEvaluateAction extends BaseAction<SupplierEvaluateVo> {
	final String VIEW_PATH = "/res/supplier/supplier_evaluate";
	public static final String SERVER_BASE = ApplicationUtils.getValue("config.server.base").toString()
			.replace("\\", File.separator);
	public static final String TEMP_PATH = ApplicationUtils.getValue("config.upload.path").toString()
			.replace("\\", File.separator);
	
	@Autowired
	private ISupplierEvaluateService supplierEvaluateService;
	@Autowired
	private ISupplierService supplierService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<SupplierEvaluateVo> baseService() {
		return supplierEvaluateService;
	}
	@Override
	public ModelAndView edit(SupplierEvaluateVo v) throws GlobalException {
		ModelAndView mv = super.edit(v);
		if(StringUtils.isNotBlank(v.getSupplierVo().getId())){
			SupplierVo supplierVo = supplierService.findById(v.getSupplierVo().getId());
			v.setSupplierVo(supplierVo);
		}
		mv.addObject(VO, v);
		mv.setViewName(VIEW_PATH + "_edit");
		return mv;
	}
	
	@Override
	@Log(operation=Log.Operation.UPDATE,content="删除评价信息",module="供应商评价管理")
	public ModelAndView update2del(SupplierEvaluateVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update2del(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/res/supplier/evaluatePage.do?id="+v.getSupplierVo().getId());
		return mav;
	}
	
	@RequestMapping("/add2File")
	@ResponseBody
	@Log(operation=Log.Operation.ADD,content="新增评价信息",module="供应商评价管理")
	public Status add2File(SupplierEvaluateVo v,RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException{
		if(null!=file && !file.isEmpty()){
			v.setFileName(file.getOriginalFilename());
			v.setFilePath(getPath(file.getOriginalFilename()));
			upload(file, v.getFilePath());
		}
		try {
			baseService().add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping("/update2File")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新评价信息",module="供应商评价管理")
	public Status update2File(SupplierEvaluateVo v,RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException{
		if(null!=file && !file.isEmpty()){
			v.setFileName(file.getOriginalFilename());
			v.setFilePath(getPath(file.getOriginalFilename()));
			upload(file, v.getFilePath());
		}
		try {
			baseService().update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	private String getPath(String fileName){
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		String path = TEMP_PATH;
		path = path+"/"+"supplierEvalute"+DateUtils.getCurrDateTime()+fileType;
		return path.replace("\\", "/");
	}
}