package cn.demi.init.std.action;

import java.util.Date;
import java.util.List;

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
import cn.core.framework.utils.StrUtils;
import cn.demi.init.std.service.IMethodService;
import cn.demi.init.std.vo.MethodVo;

@Controller("init.methodAction")
@RequestMapping("/init/method")
public class MethodAction extends BaseAction<MethodVo> {
	public static final String SERVER_STD_DIR = ApplicationUtils.getValue("config.upload.std.method").toString().replace("\\", "/");
	
	final String VIEW_PATH = "/init/method/method";
	@Autowired 
	private IMethodService methodService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<MethodVo> baseService() {
		return methodService;
	}
	@ResponseBody
	@RequestMapping(value= "add4Data.do")
	@Log(operation=Log.Operation.ADD,content="新增方法",module="检测方法管理")
	public Status add4Data(MethodVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!= file && !file.isEmpty()){
			v.setFileName(file.getOriginalFilename());
			v.setFilePath(getPath(file.getOriginalFilename()));
			upload(file, v.getFilePath());
		}
		return super.addData(v, attr);
	}
	@RequestMapping(value= "save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存方法",module="检测方法管理")
	public ModelAndView save4Data(MethodVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!= file && !file.isEmpty()){
			v.setFileName(file.getOriginalFilename());
			v.setFilePath(getPath(file.getOriginalFilename()));
			upload(file, v.getFilePath());
		}
		ModelAndView mav = super.save(v, attr);
		return mav;
	}
	@ResponseBody
	@RequestMapping(value= "update4Data.do")
	@Log(operation=Log.Operation.UPDATE,content="修改方法",module="检测方法管理")
	public Status update4Data(MethodVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!= file && !file.isEmpty()){
			v.setFileName(file.getOriginalFilename());
			v.setFilePath(getPath(file.getOriginalFilename()));
			upload(file, v.getFilePath());
		}
		return super.updateData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.DELETE,content="删除检测方法",module="检测方法管理")
	public ModelAndView update2del(MethodVo v, RedirectAttributes attr) throws GlobalException {
		try {
			methodService.update2del(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		mav.addObject("standId", v.getStandId());
		return mav;
	}
	/**
	 * 根据项目Id获取关联方法
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxGetMethod.do")
	public List<MethodVo> ajaxGetMethod(MethodVo v) throws GlobalException {
		List<MethodVo> methodList = methodService.list(v);
		return methodList;
	}

	@RequestMapping(value = "ajaxCheckCode.do")
	@ResponseBody
	public Status ajaxCheckCode(String code, RedirectAttributes attr) throws GlobalException {
		try {
			MethodVo v = methodService.findByCode(code);
			if(v!=null&&v.getCode()!=null){
				status = new Status("编号已存在",Status.STATUS_ERROR);
			}else{
				status = new Status("编号验证成功，可以使用",Status.STATUS_SUCCESS);
			}
		} catch (GlobalException e) {
			log.info("验证编号异常",e);
			status = new Status("验证编号异常",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		return status;
	}
	/**
	 * 检测方法使用的仪器是否可用
	 * @param code
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value = "ajaxCheckMethodApp.do")
	@ResponseBody
	public Status ajaxCheckMethodApp(String ids, RedirectAttributes attr) throws GlobalException {
		try {
			String msg = methodService.checkMethodApp(ids);
			if(!StrUtils.isBlankOrNull(msg)){
				status = new Status(msg,Status.STATUS_ERROR);
			}else{
				status = new Status("",Status.STATUS_SUCCESS);
			}
		} catch (GlobalException e) {
			log.info("验证异常",e);
			status = new Status("验证异常",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		return status;
	}
	/**
	 * Description : 获取文件存储路径 <br>
	 * @param v
	 * @return
	 */
	private String getPath(String originalFilename){
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		String fileName = DateUtils.getYear()+DateUtils.getMonth()+DateUtils.getDay()+new Date().getTime();
		String path = SERVER_STD_DIR;
		path = path+"/"+fileName + suffix;
		return path.replace("\\", "/");
	}
}