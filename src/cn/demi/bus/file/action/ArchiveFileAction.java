package cn.demi.bus.file.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhuozhengsoft.pageoffice.FileSaver;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.file.service.IArchiveFileService;
import cn.demi.bus.file.vo.ArchiveFileVo;

@Controller("bus.archiveFileAction")
@RequestMapping("/bus/archiveFile")
public class ArchiveFileAction extends BaseAction<ArchiveFileVo> {
	public static final String SERVER_UPLOAD_DIR = ApplicationUtils.getValue("config.upload.path").toString().replace("\\", "/");
	
	final String VIEW_PATH = "/bus/archive/file/archive_file";
	@Autowired private IArchiveFileService archiveFileService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ArchiveFileVo> baseService() {
		return archiveFileService;
	}
	
	@RequestMapping(value="update4Data.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="修改文档",module="档案管理")
	public Status update4Data(ArchiveFileVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		if (null!=file && !file.isEmpty()) {
			v.setFilePath(getPath(v,file));
			upload(file, v.getFilePath());
		}
		return super.updateData(v, attr);
	}
	@ResponseBody
	@RequestMapping(value="addFile.do")
	@Log(operation=Log.Operation.ADD,content="增加档案文件",module="档案清单")
	public Status addFile(ArchiveFileVo v, RedirectAttributes attr) throws GlobalException {
		try {
			archiveFileService.add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * Description : 更新文件页面保存方法 <br>
	 * @param v
	 * @param attr
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value = "addfile.do")
	public ModelAndView addFile(ArchiveFileVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile file) throws GlobalException {
		try {
			if (null!=file && !file.isEmpty()) {
				v.setFilePath(getPath(v,file));
				upload(file, v.getFilePath());
			}
			archiveFileService.update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		ModelAndView mav = new ModelAndView();
		attr.addFlashAttribute(STATUS, status);
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	@RequestMapping(value="updateDel.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="删除文档",module="档案管理")
	public Status updateDel(ArchiveFileVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update2del(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * Description : 在线保存文件  <br>
	 */
	@ResponseBody
	@RequestMapping(value="ajaxSaveFile.do")
	public void ajaxSaveFile(ArchiveFileVo v) throws GlobalException {
		FileSaver fs = new FileSaver(request,response);
		String targetFile = SERVER_BASE+v.getFilePath().replace("/", "\\");
		fs.saveToFile(targetFile);
		fs.close();
	}
	/**
	 * Description : 打开文件方法 <br>
	 * @throws GlobalException
	 */
	@RequestMapping(value="open.do")
	public ModelAndView open(ArchiveFileVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.addObject("id",v.getId());
		mav.addObject("tempPath",v.getFilePath());
		if(v.getFilePath().endsWith(".pdf")) {
			mav.setViewName(getViewPath() + "_pdf_show");
		}else {
			mav.setViewName(getViewPath() + "_file_show");
		}
		return mav;
	}
	/**
	 * Create on : Dave Yu 2016年11月24日 上午10:49:41 <br>
	 * Description : 获取文件存储路径  <br>
	 * @param v
	 * @return String
	 */
	private String getPath(ArchiveFileVo v,MultipartFile file){
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		v.setSize(convertFileSize(file.getSize()));
		v.setOriginalSize(file.getSize());
		v.setType(fileType);
		String path = SERVER_UPLOAD_DIR+Constants.FILE_TYPE_ARCHV+File.separator+v.getArchiveVo().getCode();
		path = path+"/"+getCurrent().getLoginName()+DateUtils.getCurrDateTime()+fileType;
		return path.replace("\\", "/");
	}
	
	/**
	 * Description : 计算文件大小工具方法 <br>
	 * @param size
	 * @return String
	 */
	public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
 
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }
	@ResponseBody
	@RequestMapping(value={"fileUpload.do"})
	public Status fileUpload(MultipartHttpServletRequest multipartRequest)throws GlobalException{
		String busId=multipartRequest.getParameter("uid");
		String fileName=multipartRequest.getParameter("name");
		ArchiveFileVo infoVo=archiveFileService.findById(busId);
		infoVo.setFileName(fileName);
		MultipartFile diskFileItem =multipartRequest.getFile("file");
		BufferedInputStream	inputStream;
		BufferedOutputStream outputStream;
		String fileType=fileName.substring(fileName.lastIndexOf("."));
		String vName =System.currentTimeMillis() + fileType;
		String filePath=UPLOAD_DIR+Constants.FILE_TYPE_ARCHV+File.separator+infoVo.getArchiveVo().getCode();
		try {
			inputStream = new BufferedInputStream(diskFileItem.getInputStream());
			File f = new File(SERVER_BASE+filePath);
			if(!f.exists()) f.mkdirs();
			outputStream = new BufferedOutputStream(new FileOutputStream(f.getPath()+"/"+vName.replace("/", "\\")));
			Streams.copy(inputStream, outputStream, true);
			inputStream.close();
			outputStream.flush();
			outputStream.close();

			filePath=filePath+"/"+vName;
			filePath=filePath.replace("\\", "/");
			infoVo.setFilePath(filePath);
			infoVo.setOriginalSize(diskFileItem.getSize());
			infoVo.setSize(convertFileSize(infoVo.getOriginalSize()));
			infoVo.setType(fileType);
			archiveFileService.update4File(infoVo);
			status = new Status("文件上传成功",Status.STATUS_SUCCESS);
		} catch (FileNotFoundException e) {
			log.error("文件上传失败",e);
			status = new Status("文件上传失败",Status.STATUS_ERROR);
		} catch (IOException e) {
			log.error("文件上传失败",e);
			status = new Status("文件上传失败",Status.STATUS_ERROR);
		}
		return status;
    }
}