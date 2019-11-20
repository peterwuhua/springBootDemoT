package cn.demi.init.std.action;

import java.util.Date;
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
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.demi.init.std.service.ISampSourceService;
import cn.demi.init.std.vo.SampSourceVo;

@Controller("init.sampSourceAction")
@RequestMapping("/init/sampSource")
public class SampSourceAction extends BaseAction<SampSourceVo> {
	public static final String SERVER_STD_DIR = ApplicationUtils.getValue("config.upload.std.sampSource").toString().replace("\\", "/");
	final String VIEW_PATH = "/init/samp_source/samp_source";
	@Autowired private ISampSourceService sampSourceService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<SampSourceVo> baseService() {
		return sampSourceService;
	}
	@ResponseBody
	@RequestMapping(value= "add4Data.do")
	@Log(operation=Log.Operation.ADD,content="保存",module="采样方法管理")
	public Status add4Data(SampSourceVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		try {
			if(null!=file && !file.isEmpty()){
				v.setFileName(file.getOriginalFilename());
				v.setFilePath(getPath(file.getOriginalFilename()));
				upload(file, v.getFilePath());
			}
			baseService().add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value= "update4Data.do")
	@Log(operation=Log.Operation.UPDATE,content="修改方法",module="采样方法")
	public Status update4Data(SampSourceVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		try {
			if(null!=file && !file.isEmpty()){
				v.setFileName(file.getOriginalFilename());
				v.setFilePath(getPath(file.getOriginalFilename()));
				upload(file, v.getFilePath());
			}
			baseService().update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping(value= "save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存方法",module="采样标准")
	public ModelAndView save4Data(SampSourceVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!=file && !file.isEmpty()){
			v.setFileName(file.getOriginalFilename());
			v.setFilePath(getPath(file.getOriginalFilename()));
			upload(file, v.getFilePath());
		}
		ModelAndView mav = super.save(v, attr);
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="删除方法",module="采样标准")
	public ModelAndView update2del(SampSourceVo v, RedirectAttributes attr) throws GlobalException {
		return super.update2del(v, attr);
	}
	/**
	 * 获取方法集合
	 * 选择方法得弹窗时   左侧方法集合数据源
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="gridData4Sels.do")
	public GridVo gridData4Sels(GridVo gridVo, SampSourceVo v) throws GlobalException {
		return sampSourceService.gridData4Sels(gridVo,v);
	}
	/**
	 *获取方法集合
	 *选择方法得弹窗时   右侧方法列表数据源
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="list4Sels.do")
	public List<SampSourceVo> list4Sels(String ids) throws GlobalException {
		List<SampSourceVo> itList=sampSourceService.list4Sels(ids);
		return itList;
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
		SampSourceVo vo = sampSourceService.findByCode(code);
		mav.addObject(VO, vo);
		mav.setViewName(getViewPath()+"_pdf");
		return mav;
	}
}