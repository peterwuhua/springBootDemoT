package cn.demi.qlt.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhuozhengsoft.pageoffice.FileSaver;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.export.ExportUtils;
import cn.demi.qlt.constant.QltEunm;
import cn.demi.qlt.service.INsSumService;
import cn.demi.qlt.vo.NsVo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

@Controller("qlt.nsSumAction")
@RequestMapping("/qlt/nsSum")
public class NsSumAction extends BaseAction<NsVo> {
	public static final String REPORT_PATH =ApplicationUtils.getValue("config.upload.qlt.ns").toString().replace("\\", File.separator);
	public static final String SERVER_EXPORT_DIR = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");

	
	final String VIEW_PATH = "/qlt/ns/sum/ns_sum";
	@Autowired private INsSumService nsSumService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<NsVo> baseService() {
		return nsSumService;
	}
	@Override
	public ModelAndView edit(NsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=nsSumService.findById(v.getId());
			v.setSumDate(DateUtils.getCurrDateStr());
			v.setSumId(getCurrent().getAccountId());
			v.setSumName(getCurrent().getUserName());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新汇总信息",module="汇总报告")
	public Status updateData(NsVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
			status.setObject(v.getId());
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	public GridVo gridData(GridVo gridVo, NsVo v) throws GlobalException {
		v.setStatus(QltEunm.QLT_20.getStatus());
		return nsSumService.gridData(gridVo,v);
	}
	@Override
	public GridVo gridDatad(GridVo gridVo, NsVo v) throws GlobalException {
		v.setStatus(QltEunm.QLT_20.getStatus());
		return nsSumService.gridDatad(gridVo,v);
	}
	

	/**
	 * Description : 修改报告  <br>
	 * @param v
	 * @param tempPath
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value={"editReport.do"})
	@Log(operation=Log.Operation.UPDATE,content="更新报告文件内容",module="报告编制")
	public ModelAndView editReport(NsVo v) throws GlobalException {
		ModelAndView mav =  new ModelAndView();
		v = nsSumService.findById(v.getId());
		mav.addObject("id",v.getId());
		mav.addObject("tempPath",v.getFilePath());
		mav.setViewName(getViewPath()+"_file_open");
		return mav;
		
	}
	
	/**
	 * Create on : Paddy Zhang 2017年6月1日 上午10:47:56 <br>
	 * Description : 生成word报告  <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @throws IOException
	 */
	@RequestMapping(value={"updateRep4Word.do"})
	@Log(operation=Log.Operation.UPDATE,content="生成报告文件内容",module="报告编制")
	public ModelAndView updateRep4Word(NsVo v) throws GlobalException, IOException {
		ModelAndView mav =  new ModelAndView();
		v=nsSumService.find4Report(v.getId());
		String path = createWordRepFile(v,"ns-year-report.ftl").replace("\\", "/");//生成报告文件
		v.setFilePath(path);
		v.setFileName(v.getDeptName()+"-"+v.getYear()+".doc");
		nsSumService.update4Report(v);
		mav.addObject("id",v.getId());
		mav.addObject("tempPath",path);
		mav.setViewName(getViewPath()+"_file_open");
		return mav;
	}
	/**
	 * Create on : Paddy Zhang 2017年6月1日 上午9:43:29 <br>
	 * Description :  <br>
	 * @param v NsVo
	 * @param source String 报告模板的相对路径
	 * @return 生成的文件的路径
	 * @throws IOException
	 */
	public String createWordRepFile(NsVo v,String source) throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(VO, v);
		String reportPath =REPORT_PATH+File.separator+DateUtils.getCurrDateStr();
		String fileName =v.getId()+".doc";//生成的文件存放路径
		v.setFileName(fileName);
		String targetPath =reportPath+File.separator+fileName;//生成的文件存放路径
		String filePath =SERVER_BASE+ reportPath;//生成的文件存放路径
		File file = new File(filePath);
		if (!file.exists()) 
			   file.mkdirs();
		OutputStream os = null;
		OutputStreamWriter osw=null;
		
		File docFile = new File(SERVER_BASE+targetPath);
		if(!docFile.exists()){
			docFile.createNewFile();
		}else {
			docFile.delete();
			docFile.createNewFile();
		}
		
		try {
			
			Configuration cfg = new Configuration();
			cfg.setDefaultEncoding("utf-8");
			cfg.setDirectoryForTemplateLoading(new File(SERVER_BASE+SERVER_EXPORT_DIR));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp  = cfg.getTemplate(source,"utf-8");
			
			os=new FileOutputStream( SERVER_BASE+targetPath);
			osw=new OutputStreamWriter(os);
			Writer docout = new BufferedWriter(osw);
			temp.process(dataMap, docout); 
		} catch (Exception e) {
			Logger.getLogger(ExportUtils.class).error("生成文件出错", e);
		}finally {
			if(null != osw){
				osw.flush();
				osw.close();
			}   
			if(null != os){
		        os.flush();
		        os.close();
			}    
		}
		return targetPath;
	}
	/**Create on : Paddy Zhang 2017年3月18日 下午6:51:10 <br>
	 * Description : 检测报告文件是否存在 <br>
	 * @param v NsVo
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value= "checkReportFile.do")
	public Map<String, Object> checkReportFile(NsVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		v = nsSumService.findById(v.getId());
		if(StrUtils.isBlankOrNull(v.getFilePath())){
			map.put("type",Status.STATUS_ERROR);
			map.put("message","请先生成报告，再提交！！！");
			return map;
		}
		String filePath = SERVER_BASE +File.separator+v.getFilePath();
		File file =new File(filePath);
		if(!file.exists()){
			map.put("type",Status.STATUS_ERROR);
			map.put("message","请先生成报告，再提交！！！");
			return map;
		}
		map.put("type",Status.STATUS_SUCCESS);
		return map;
	}
	@RequestMapping(value={"showReport.do"})
	public ModelAndView showReport(NsVo v) throws GlobalException {
		ModelAndView mav =  new ModelAndView();
		v = nsSumService.findById(v.getId());
		mav.addObject("tempPath",v.getFilePath());
		mav.setViewName(getViewPath()+"_file_show");
		return mav;
	}
	/**
	 * Description : 在线保存报告  <br>
	 * @param v NsVo
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value={"ajaxSaveFile.do"})
	public void ajaxSaveFile(NsVo v) throws GlobalException {
		NsVo NsVo = nsSumService.findById(v.getId());
		FileSaver fs = new FileSaver(request,response);
		String targetFile = SERVER_BASE+NsVo.getFilePath().replace("/", "\\");
		fs.saveToFile(targetFile);
		fs.close();
	}
}