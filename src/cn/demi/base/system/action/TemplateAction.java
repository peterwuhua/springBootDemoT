package cn.demi.base.system.action;

import java.io.File;
import java.io.IOException;

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
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.demi.base.system.service.ITemplateService;
import cn.demi.base.system.vo.TemplateVo;
@Controller("sys.templateAction")
@RequestMapping("sys/template")
public class TemplateAction extends BaseAction<TemplateVo>{
	final String VIEW_PATH = "/sys/template/template";
	final String EXPORT = "export";
	public static final String SERVER_IMPORT_DIR = ApplicationUtils.getValue("config.upload.import.template").toString().replace("\\", "/");
	public static final String SERVER_IMPORT_TEMP_DIR = ApplicationUtils.getValue("config.upload.import.temp").toString().replace("\\", "/");
	public static final String SERVER_EXPORT_DIR = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");
	public static final String SERVER_EXPORT_TMP_DIR = ApplicationUtils.getValue("config.upload.export.temp").toString().replace("\\", "/");
	public static Logger log = Logger.getLogger(TemplateAction.class);
	static{
		try {
			org.apache.commons.io.FileUtils.deleteDirectory(new File(SERVER_BASE+SERVER_EXPORT_TMP_DIR));
		} catch (IOException e) {
			log.error("清除导出，导出临时文件出错  "+SERVER_BASE+SERVER_EXPORT_TMP_DIR,e);
		}
		
		/*try {
			FileUtils.deleteDirectory(new File(SERVER_BASE+SERVER_IMPORT_TEMP_DIR));
		} catch (IOException e) {
			log.error("清除导入，导入临时文件出错 "+SERVER_BASE+SERVER_IMPORT_TEMP_DIR,e);
		}*/
	}
	
	@Autowired private ITemplateService templateService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<TemplateVo> baseService(){
		return templateService;
	}
	 
	@ResponseBody
	@RequestMapping(value= "add4Data.do")
	@Log(operation=Log.Operation.ADD,content="新增模板",module="模板管理")
	public Status add4Data(TemplateVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!=file && !file.isEmpty()){
			v.setPath(getPath(v.getCode(),v.getType()));
			upload(file, v.getPath());
		}
		return super.addData(v, attr);
	}
	@ResponseBody
	@RequestMapping(value= "update4Data.do")
	@Log(operation=Log.Operation.UPDATE,content="修改模板",module="模板管理")
	public Status update4Data(TemplateVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!=file && !file.isEmpty()){
			v.setPath(getPath(v.getCode(),v.getType()));
			upload(file, v.getPath());
		}
		return super.updateData(v, attr);
	}
	@RequestMapping(value= "save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存模板",module="模板管理")
	public ModelAndView save4Data(TemplateVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		if(null!=file && !file.isEmpty()){
			v.setPath(getPath(v.getCode(),v.getType()));
			upload(file, v.getPath());
		}
		return super.save(v, attr);
	}
	/**
	 * <strong>Create on : 2016年11月17日 下午2:31:14 </strong> <br>
	 * <strong>Description :获取文件存储路径 </strong> <br>
	 * @param v
	 * @return
	 */
	private String getPath(String code,String type){
		String path = EXPORT.equals(type)?SERVER_EXPORT_DIR : SERVER_IMPORT_DIR; 
		path = path+"/"+code;
		return path.replace("\\", "/");
	}
	
	/**
	 * <strong>Create on : 2016年11月17日 下午1:50:03 </strong> <br>
	 * <strong>Description : 查看模板文件</strong> <br>
	 * @param vo 
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="open.do")
	public ModelAndView open(TemplateVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.addObject("tempPath", getPath(v.getCode(),v.getType()));
		mav.setViewName(getViewPath()+"_open");
		return mav;
	}
	
	/**
	 * <strong>Create on : 2016年11月17日 下午1:50:03 </strong> <br>
	 * <strong>Description : 保存模板文件</strong> <br>
	 * @param vo 
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value={"ajaxSaveFile.do","ajaxSaveFile.json"})
	public void ajaxSaveFile(TemplateVo v) throws GlobalException {
		FileSaver fs = new FileSaver(request,response);
		String targetFile = SERVER_BASE+getPath(v.getCode(),v.getType());
		fs.saveToFile(targetFile);
		fs.close();
		
		templateService.updateFile(v.getId());
	}
}
