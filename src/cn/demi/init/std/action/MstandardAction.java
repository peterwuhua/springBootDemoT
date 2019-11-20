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
import cn.demi.init.std.service.IMstandardService;
import cn.demi.init.std.vo.MstandardVo;

@Controller("init.mstandardAction")
@RequestMapping("/init/mstandard")
public class MstandardAction extends BaseAction<MstandardVo> {
	final String VIEW_PATH = "/init/mstandard/mstandard";
	public static final String SERVER_STD_DIR = ApplicationUtils.getValue("config.upload.std.mstandard").toString().replace("\\", "/");
	@Autowired 
	private IMstandardService mstandardService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<MstandardVo> baseService() {
		return mstandardService;
	}
	 
	@RequestMapping(value= "save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存检测标准",module="检测标准管理")
	public ModelAndView save4Data(MstandardVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!=file && !file.isEmpty()){
			v.setFileName(file.getOriginalFilename());
			v.setFilePath(getPath(file.getOriginalFilename()));
			upload(file, v.getFilePath());
		}
		ModelAndView mav = super.save(v, attr);
		return mav;
	}
	@ResponseBody
	@RequestMapping(value= "add4Data.do")
	@Log(operation=Log.Operation.ADD,content="保存检测标准",module="检测标准管理")
	public Status add4Data(MstandardVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!=file && !file.isEmpty()){
			v.setFileName(file.getOriginalFilename());
			v.setFilePath(getPath(file.getOriginalFilename()));
			upload(file, v.getFilePath());
		}
		return super.addData(v, attr);
	}
	@ResponseBody
	@RequestMapping(value= "update4Data.do")
	@Log(operation=Log.Operation.UPDATE,content="保存检测标准",module="检测标准管理")
	public Status update4Data(MstandardVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!=file && !file.isEmpty()){
			v.setFileName(file.getOriginalFilename());
			v.setFilePath(getPath(file.getOriginalFilename()));
			upload(file, v.getFilePath());
		}
		return super.updateData(v, attr);
	}
	 
	/**
	 * Create on : Eason Qin 2016年11月29日 上午10:57:36 <br>
	 * Description : 获取文件存储路径 <br>
	 * @param v
	 * @return
	 */
	private String getPath(String originalFileName){
		String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
		String fileName = DateUtils.getYear()+DateUtils.getMonth()+DateUtils.getDay()+new Date().getTime();
		String path = SERVER_STD_DIR;
		path = path+"/"+fileName + suffix;
		return path.replace("\\", "/");
	}
	@RequestMapping(value="openPdf.do")
	public ModelAndView act(@RequestParam("code") String code) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		MstandardVo vo = mstandardService.findByCode(code);
		mav.addObject(VO, vo);
		mav.setViewName(getViewPath()+"_pdf");
		return mav;
	}
	
	@RequestMapping(value = "ajaxCheckCode.do")
	@ResponseBody
	public Status ajaxCheckCode(String code, RedirectAttributes attr) throws GlobalException {
		try {
			MstandardVo v = mstandardService.findByCode(code);
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
	 * 根据项目Id获取关联方法
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxGetMstand.do")
	public List<MstandardVo> ajaxGetMstand(MstandardVo v) throws GlobalException {
		List<MstandardVo> methodList = mstandardService.list(v);
		return methodList;
	}
}