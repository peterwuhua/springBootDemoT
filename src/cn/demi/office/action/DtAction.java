package cn.demi.office.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.fileupload.FileUploadException;
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

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.office.service.IDtService;
import cn.demi.office.vo.DtVo;
/**
 * 文档登记
 * @author QuJunLong
 *
 */
@Controller("office.dtAction")
@RequestMapping("/office/dt")
public class DtAction extends BaseAction<DtVo> {
	final String VIEW_PATH = "/office/dt/dt";
	public static final String SERVER_UPLOAD_DIR = ApplicationUtils.getValue("config.upload.doc.document").toString().replace("\\", "/");
	@Autowired 
	private IDtService dtService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<DtVo> baseService() {
		return dtService;
	}
	@Override
	public ModelAndView edit(DtVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=dtService.findById(v.getId());
		}else {
			v.setDate(DateUtils.getCurrDateTimeStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@RequestMapping(value="save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存申请单",module="文件申请")
	public ModelAndView save4Data(DtVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		try {
			if(null!= file && !file.isEmpty()){
				v.setFileName(file.getOriginalFilename());
				v.setFilePath(getPath(file.getOriginalFilename()));
				v.setSize(convertFileSize(file.getSize()));
				v.setOriginalSize(file.getSize());
				String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				v.setType(fileType);
				upload(file, v.getFilePath());
			}
			dtService.save(v);
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
	@RequestMapping(value="update4Data.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新申请单",module="文件申请")
	public Status update4Data(DtVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		try {
			if(null!= file && !file.isEmpty()){
				v.setFileName(file.getOriginalFilename());
				v.setFilePath(getPath(file.getOriginalFilename()));
				v.setSize(convertFileSize(file.getSize()));
				v.setOriginalSize(file.getSize());
				String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				v.setType(fileType);
				upload(file, v.getFilePath());
			}
			dtService.update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping(value="add4Data.do")
	@ResponseBody
	@Log(operation=Log.Operation.ADD,content="新增申请单",module="文件申请")
	public Status add4Data(DtVo v, RedirectAttributes attr,@RequestParam(value="file", required=false) MultipartFile file) throws GlobalException {
		try {
			if(null!= file && !file.isEmpty()){
				v.setFileName(file.getOriginalFilename());
				v.setFilePath(getPath(file.getOriginalFilename()));
				v.setSize(convertFileSize(file.getSize()));
				v.setOriginalSize(file.getSize());
				String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				v.setType(fileType);
				upload(file, v.getFilePath());
			}
			dtService.add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * Description : 获取文件存储路径 <br>
	 * @param v
	 * @return
	 */
	private String getPath(String originalFilename){
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		String fileName =String.valueOf(new Date().getTime());
		String path = SERVER_UPLOAD_DIR+"/dt/"+DateUtils.getYear()+DateUtils.getMonth()+DateUtils.getDay();
		path = path+"/"+fileName + suffix;
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
	/**
	 * Description : 打开文件方法 <br>
	 * @throws GlobalException
	 */
	@RequestMapping(value="open.do")
	public ModelAndView open(DtVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.addObject("tempPath", v.getFilePath());
		mav.setViewName(getViewPath()+"_open");
		return mav;
	}
	/*****在线拍照*****/
	/**
	 * @param multipartRequest multipartRequest
	 * @return Result
	 * @throws GlobalException
	 * @throws FileUploadException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value={"upload.do"})
	public Result upload(MultipartHttpServletRequest multipartRequest) throws GlobalException, FileUploadException, IOException {
		MultipartFile diskFileItem = multipartRequest.getFile("__avatar1");
		Result result = new Result();
		result.success = false;
		result.msg = "Failure!";
		result.sourceUrl = "";	
		//基于原图的初始化参数
		BufferedInputStream	inputStream;
		BufferedOutputStream outputStream;
			
		String fieldName = diskFileItem.getName();
		//是否是原始图片 file 域的名称（默认的 file 域的名称是__source，可在插件配置参数中自定义。参数名：src_field_name）
			Boolean isSourcePic = fieldName.equals("__source");
			//文件名，如果是本地或网络图片为原始文件名（不含扩展名）、如果是摄像头拍照则为 *FromWebcam
			//String name = fileItem.getName();
			//当前头像基于原图的初始化参数（即只有上传原图时才会发送该数据），用于修改头像时保证界面的视图跟保存头像时一致，提升用户体验度。
			//修改头像时设置默认加载的原图url为当前原图url+该参数即可，可直接附加到原图url中储存，不影响图片呈现。
			if ( fieldName.equals("__initParams") )
			{
				inputStream = new BufferedInputStream(diskFileItem.getInputStream());
				byte[] bytes = new byte [inputStream.available()];
				inputStream.read(bytes);
				inputStream.close();
			}
			//如果是原始图片 file 域的名称或者以默认的头像域名称的部分“__avatar”打头
			else if ( isSourcePic || fieldName.startsWith("__avatar") )
			{
				String virtualPath = System.currentTimeMillis() + ".jpg";
				inputStream = new BufferedInputStream(diskFileItem.getInputStream());
				String path = SERVER_UPLOAD_DIR+"/dt/"+DateUtils.getYear()+DateUtils.getMonth()+DateUtils.getDay();
				File f = new File(SERVER_BASE+path);
				if(!f.exists()) f.mkdirs();
				
				outputStream = new BufferedOutputStream(new FileOutputStream(f.getPath()+"/"+virtualPath.replace("/", "\\")));
				Streams.copy(inputStream, outputStream, true);
				
				inputStream.close();
	            outputStream.flush();
	            outputStream.close();
	            result.sourceUrl=path.replace("\\","/")+"/"+virtualPath;
			}
			result.success = true;
			result.msg = "Success!";
		return result;
	}
	private class Result
	{
		/**
		* 表示图片是否已上传成功。
		*/
		@SuppressWarnings("unused")
		public Boolean success;
		/**
		* 自定义的附加消息。
		*/
		@SuppressWarnings("unused")
		public String msg;
		/**
		* 表示原始图片的保存地址。
		*/
		@SuppressWarnings("unused")
		public String sourceUrl;
	}
}