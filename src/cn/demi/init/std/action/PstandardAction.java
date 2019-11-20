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
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.std.po.Pstandard;
import cn.demi.init.std.service.IPstandardService;
import cn.demi.init.std.vo.PstandardVo;

@Controller("init.pstandardAction")
@RequestMapping("/init/pstandard")
public class PstandardAction extends BaseAction<PstandardVo> {
	final String VIEW_PATH = "/init/pstandard/pstandard";
	public static final String SERVER_STD_DIR = ApplicationUtils.getValue("config.upload.std.pstandard").toString().replace("\\", "/");
	
	@Autowired 
	private IPstandardService pstandardService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<PstandardVo> baseService() {
		return pstandardService;
	}
	@Override
	public ModelAndView edit(PstandardVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=pstandardService.findById(v.getId());
		}else {
			v.setStatus(Pstandard.ST_RUN);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value= "add4Data.do")
	@Log(operation=Log.Operation.ADD,content="新增评价标准",module="评价标准管理")
	public Status add4Data(PstandardVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!= file && !file.isEmpty()){
			v.setFileName(file.getOriginalFilename());
			v.setFilePath(getPath(file.getOriginalFilename()));
			upload(file, v.getFilePath());
		}
		return super.addData(v, attr);
	}
	
	@RequestMapping(value= "save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存评价标准",module="评价标准管理")
	public ModelAndView save4Data(PstandardVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
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
	@Log(operation=Log.Operation.UPDATE,content="修改评价标准",module="评价标准管理")
	public Status update4Data(PstandardVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!= file && !file.isEmpty()){
			v.setFileName(file.getOriginalFilename());
			v.setFilePath(getPath(file.getOriginalFilename()));
			upload(file, v.getFilePath());
		}
		return super.updateData(v, attr);
	}
	@ResponseBody
	@RequestMapping(value = "gridData4Run.do")
	public GridVo gridData4Run(GridVo gridVo, PstandardVo v) throws GlobalException {
		v.setStatus(Pstandard.ST_RUN);
		return pstandardService.gridData(gridVo,v);
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
	
	@RequestMapping(value="openPdf.do")
	public ModelAndView act(@RequestParam("code") String code) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		PstandardVo vo = pstandardService.findByCode(code);
		mav.addObject(VO, vo);
		mav.setViewName(getViewPath()+"_pdf");
		return mav;
	}
	@RequestMapping(value = "ajaxCheckCode.do")
	@ResponseBody
	public Status ajaxCheckCode(String code, RedirectAttributes attr) throws GlobalException {
		try {
			PstandardVo v = pstandardService.findByCode(code);
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
	 * 根据项目Id获取标准集合
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxGetStand.do")
	public List<PstandardVo> ajaxGetStand(PstandardVo v) throws GlobalException {
		List<PstandardVo> standList = pstandardService.list4Ajax(v.getSampTypeId());
		return standList;
	}
	/**
	 *获取标准集合
	 *选择标准得弹窗时   右侧标准列表数据源
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="list4Sels.do")
	public List<PstandardVo> list4Sels(String ids) throws GlobalException {
		List<PstandardVo> itList=pstandardService.list4Sels(ids);
		return itList;
	}
}