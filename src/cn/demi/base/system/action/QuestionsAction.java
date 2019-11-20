package cn.demi.base.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.service.IQuestionsService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.base.system.vo.QuestionsVo;
/**
 * Create on : 2017年2月21日 下午1:14:36  <br>
 * Description :常见问题Action  <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Controller("sys.questionsAction")
@RequestMapping("/sys/questions")
public class QuestionsAction extends BaseAction<QuestionsVo> {
	final String VIEW_PATH = "/sys/questions/questions";
	public static final String questions_filePpth = ApplicationUtils.getValue("config.upload.sys.questions").toString().replace("\\", "/");
	@Autowired private IQuestionsService questionsService;	
	@Autowired
	private IFilesService filesService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<QuestionsVo> baseService() {
		return questionsService;
	}
	@Override
	public ModelAndView edit(QuestionsVo v) throws GlobalException {
		
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=questionsService.findById(v.getId());
			List<FilesVo> fileList=filesService.listByBusId(v.getId());
			v.setFileList(fileList);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = {"treeData.do","treeData.json"})
	public List<Map<String, Object>> treeData() throws GlobalException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<String[]> listModelName = questionsService.listModule();
		for (Object[] str : listModelName) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name",str[1]);
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * Create on : Paddy Zhang 2017年2月21日 下午1:15:01 <br>
	 * Description :  常见问题搜索<br>
	 * @param v
	 * @param pageResult
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="questionDetail.do")
	public ModelAndView questionDetail(QuestionsVo v,PageResult pageResult) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		pageResult.getPageBean().setPageSize(10);
		pageResult = questionsService.getQuestionsList(v, pageResult);
		mav.addObject("pageResult", pageResult);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_detail");
		return mav;
	}
	@RequestMapping(value="questionShow.do")
	public ModelAndView questionShow(QuestionsVo v,PageResult pageResult) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			BeanUtils.copyProperties(baseService().findById(v.getId()),v);
			List<FilesVo> fileList=filesService.listByBusId(v.getId());
			v.setFileList(fileList);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_detail");
		return mav;
	}
	@RequestMapping(value="update4Data.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新信息",module="用户体验")
	public Status update4Data(QuestionsVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException {
		try {
			questionsService.update(v);
			//附件上传
			List<FilesVo> fileList=uploads(file, v.getId(),Constants.FILE_TYPE_APP_QUEST);
			filesService.saveFiles(fileList);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping(value="add4Data.do")
	@ResponseBody
	@Log(operation=Log.Operation.ADD,content="保存信息",module="用户体验")
	public Status add4Data(QuestionsVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException {
		try {
			questionsService.add(v);
			//附件上传
			List<FilesVo> fileList=uploads(file, v.getId(),Constants.FILE_TYPE_APP_QUEST);
			filesService.saveFiles(fileList);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
	/**
	 * Create on : Paddy Zhang 2017年2月21日 下午1:15:17 <br>
	 * Description :  常见问题搜索ajax获取数据<br>
	 * @param v
	 * @param pageResult
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value="ajaxDetail.do")
	public List<?> ajaxDetail(QuestionsVo v,PageResult pageResult) throws GlobalException{
		pageResult.getPageBean().setPageSize(10);
		pageResult = questionsService.getQuestionsList(v, pageResult);
		return pageResult.getResultList();
	}
	/**
	 * Create on : Paddy Zhang 2017年2月21日 下午1:15:49 <br>
	 * Description :  常见问题编辑答案时上传文件<br>
	 * @param file
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value="uploadFile.do")
	public String uploadFile(MultipartFile file) throws GlobalException {
		String pString = "";
		if(!file.isEmpty()){
			pString = getPath(file,questions_filePpth);
			upload(file,pString);
		}
		return pString;
	}
	/**
	 * Create on : Paddy Zhang 2017年2月21日 下午1:16:30 <br>
	 * Description : 上传文件生成文件路径 <br>
	 * @param file
	 * @param path
	 * @return
	 */
	private String getPath(MultipartFile file,String path){
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		Current current = getCurrent();
		String fileName =current.getLoginName()+"_"+System.currentTimeMillis()+fileType; 
		String pString = path.replace("\\", "/")+"/"+fileName;
		return pString;
	}
	
}