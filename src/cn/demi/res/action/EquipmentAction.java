package cn.demi.res.action;
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
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.res.po.Equipment;
import cn.demi.res.service.IEquipmentService;
import cn.demi.res.vo.EquipmentVo;

/**
 * 仪器设备 view层
 * @author QuJunLong
 *
 */
@Controller("res.equipmentAction")
@RequestMapping("/res/equipment")
public class EquipmentAction extends BaseAction<EquipmentVo> {
	final String VIEW_PATH = "/res/equipment/equipment";
	public static final String SERVER_UPLOAD_DIR = ApplicationUtils.getValue("config.upload.path").toString().replace("\\", "/");

	@Autowired
	private IEquipmentService equiptService;
	@Autowired
	private IFilesService filesService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<EquipmentVo> baseService() {
		return equiptService;
	}
	 
	@Override
	public ModelAndView edit(EquipmentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=equiptService.findById(v.getId());
			List<FilesVo> fileList=filesService.listByBusId(v.getId());
			v.setFileList(fileList);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	
	@RequestMapping("/save4Data")
	@Log(operation=Log.Operation.SAVE,content="保存设备信息",module="设备管理")
	public ModelAndView save4Data(EquipmentVo v, RedirectAttributes attr,@RequestParam(value="files", required=false)MultipartFile[] files,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		try {
			//logo
			if(!file.isEmpty()){
				v.setLogo(getPath(file.getOriginalFilename()));
				upload(file, v.getLogo());
			}
			baseService().save(v);
			//文件上传
			List<FilesVo> fileList=uploads(files, v.getId(),Constants.FILE_TYPE_APP);
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
	@ResponseBody
	@RequestMapping("/add4Data")
	@Log(operation=Log.Operation.ADD,content="新增设备信息",module="设备管理")
	public Status add4Data(EquipmentVo v, RedirectAttributes attr,@RequestParam(value="files", required=false)MultipartFile[] files,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		try {
			//logo
			if(null!= file && !file.isEmpty()){
				v.setLogo(getPath(file.getOriginalFilename()));
				upload(file, v.getLogo());
			}
			baseService().add(v);
			//文件上传
			List<FilesVo> fileList=uploads(files, v.getId(),Constants.FILE_TYPE_APP);
			filesService.saveFiles(fileList);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = {"gridData4Select.do"})
	public GridVo gridData4Select(GridVo gridVo, EquipmentVo v) throws GlobalException {
		v.setStatus(Equipment.ST_0);
		return baseService().gridData(gridVo,v);
	}
	@ResponseBody
	@RequestMapping("/update4Data")
	@Log(operation=Log.Operation.UPDATE,content="修改设备信息",module="设备管理")
	public Status update4Data(EquipmentVo v, RedirectAttributes attr,@RequestParam(value="files", required=false)MultipartFile[] files,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		try {
			//logo
			if(null!=file&&!file.isEmpty()){
				v.setLogo(getPath(file.getOriginalFilename()));
				upload(file, v.getLogo());
			}
			baseService().update(v);
			//文件上传
			List<FilesVo> fileList=uploads(files, v.getId(),Constants.FILE_TYPE_APP);
			filesService.saveFiles(fileList);
			
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * 获取仪器列表 ajax方法
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajax4AppList.do")
	public List<EquipmentVo> ajax4AppList(EquipmentVo v) throws GlobalException {
		List<EquipmentVo> appList = equiptService.list(v);
		return appList;
		
	}
	@ResponseBody
	@RequestMapping(value = "listAll")
	public List<EquipmentVo> listAll(EquipmentVo v) throws GlobalException {
		return equiptService.listAll(v);
	}
	@ResponseBody
	@RequestMapping(value ="listData4Out.do")
	public List<EquipmentVo> listData4Out(GridVo gridVo, EquipmentVo v) throws GlobalException {
		return equiptService.listData4Out(gridVo,v);
	}
	@ResponseBody
	@RequestMapping(value = "ajax4Show.do")
	public EquipmentVo ajax4Show(String id) throws GlobalException {
		EquipmentVo v = equiptService.findById(id);
		return v;
	}
	private String getPath(String fileName){
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		String path = SERVER_UPLOAD_DIR+Constants.FILE_TYPE_APP_LOGO;
		path = path+"/"+DateUtils.getCurrDateTime()+fileType;
		return path.replace("\\", "/");
	}
	 
}