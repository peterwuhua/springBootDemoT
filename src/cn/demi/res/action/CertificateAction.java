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
import cn.demi.res.service.ICertificateService;
import cn.demi.res.vo.CertificateVo;

/** <strong>Create on : 2017年2月28日 下午1:21:15 </strong> <br>
 * <strong>Description : </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
@Controller("res.certificateAction")
@RequestMapping("/res/certificate")
public class CertificateAction extends BaseAction<CertificateVo> {
	final String VIEW_PATH = "/res/certificate/certificate";
	public static final String SERVER_BASE = ApplicationUtils.getValue("config.server.base").toString()
			.replace("\\", File.separator);
	public static final String TEMP_PATH = ApplicationUtils.getValue("config.upload.path").toString()
			.replace("\\", File.separator);
	@Autowired private ICertificateService certificateService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<CertificateVo> baseService() {
		return certificateService;
	}
	
	/** <strong>Create on : Hans He 2017年3月1日 上午9:46:10 </strong> <br>
	 * <strong>Description : 删除附件</strong> <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping("/removeFile")
	@Log(operation=Log.Operation.DELETE,content="删除资质",module="资质管理")
	public ModelAndView removeFile(CertificateVo v) throws GlobalException{
		ModelAndView mv = new ModelAndView();
		certificateService.removeFile(v);
		v = certificateService.findById(v.getId());
		mv.addObject(VO, v);
		mv.setViewName(VIEW_PATH + "_edit");
		return mv;
	}
	
	@RequestMapping("/save2File")
	@Log(operation=Log.Operation.SAVE,content="保存资质",module="资质管理")
	public ModelAndView save2File(CertificateVo v,RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException{
		if(null!=file && !file.isEmpty()){
			v.setFileName(file.getOriginalFilename());
			v.setFilePath(getPath(file.getOriginalFilename()));
			upload(file, v.getFilePath());
		}
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
	
	@RequestMapping("/add2File")
	@ResponseBody
	@Log(operation=Log.Operation.ADD,content="新增资质",module="资质管理")
	public Status add2File(CertificateVo v,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException{
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
	@Log(operation=Log.Operation.UPDATE,content="修改资质",module="资质管理")
	public Status update2File(CertificateVo v,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException{
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
		String fileType =fileName.substring(fileName.lastIndexOf("."));
		String path = TEMP_PATH;
		path = path+"/"+"certificate"+DateUtils.getCurrDateTime()+fileType;
		return path.replace("\\", "/");
	}
}