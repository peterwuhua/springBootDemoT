package cn.demi.base.system.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.Action;
import cn.core.framework.common.action.Status;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IUserService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.UserVo;

/**
 * <strong>Create on : 2016年11月2日 下午2:01:44 </strong> <br>
 * <strong>Description : 后台首页action</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Controller("sys.avatarAction")
@RequestMapping("sys/avatar")
public class AvatarAction extends Action {
	@Autowired private IUserService userService;
	public static final String SERVER_TEMP_UPLOAD_DIR = ApplicationUtils.getValue("config.upload.sys.avatar").toString().replace("\\", "/");
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:06:39 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param id id
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="edit.do")
	public ModelAndView edit(String id) throws GlobalException {
		if(StrUtils.isBlankOrNull(id)) {
			id=getCurrent().getUserId();
		}
		ModelAndView mav = new ModelAndView();
		UserVo u = userService.findById(id);
		mav.addObject("vo", u);
		mav.setViewName("/sys/user/avatar");
		return mav;
	}
	/**
	 * <strong>Create on : Dave Yu  2016年11月2日 下午2:07:04 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param id id
	 * @param path path
	 * @return boolean
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value={"update.do","update.json"})
	public boolean update(String id,String path) throws GlobalException{
		try {
			userService.updateAvatar(id, path);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@RequestMapping(value="upload.do")
	public ModelAndView upload(AccountVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", v);
		mav.setViewName("/sys/user/avatar_upload");
		return mav;
	}

	//头像上传
	@ResponseBody
	@RequestMapping(value = {"uploadFile.do"})
	public Status fileUpload(MultipartHttpServletRequest multipartRequest) throws GlobalException {
		MultipartFile diskFileItem = multipartRequest.getFile("file");
		BufferedInputStream inputStream;
		BufferedOutputStream outputStream;
		String fileName = multipartRequest.getParameter("name");
		String busId = multipartRequest.getParameter("uid");
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		String vName = System.currentTimeMillis() + fileType;
		String filePath =SERVER_BASE+SERVER_TEMP_UPLOAD_DIR;
		Status status=null;
		try {
			inputStream = new BufferedInputStream(diskFileItem.getInputStream());
			File f = new File(filePath);
			if (!f.exists()) f.mkdirs();
			outputStream = new BufferedOutputStream(new FileOutputStream((f.getPath() + "/" + vName).replace("/", "\\")));
			Streams.copy(inputStream, outputStream, true);
			inputStream.close();
			outputStream.flush();
			outputStream.close();
			filePath = filePath + "/" + vName;
			filePath = filePath.replace("\\", "/");
			userService.updateAvatar(busId, "avatar/"+vName);
			status = new Status("文件上传成功", Status.STATUS_SUCCESS);
			status.setObject(filePath);
		} catch (FileNotFoundException e) {
			log.error("文件上传失败", e);
			status = new Status("文件上传失败", Status.STATUS_ERROR);
		} catch (IOException e) {
			log.error("文件上传失败", e);
			status = new Status("文件上传失败", Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:07:49 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param multipartRequest multipartRequest
	 * @return Result
	 * @throws GlobalException
	 * @throws FileUploadException
	 * @throws IOException
	 */
	/*@ResponseBody
	@RequestMapping(value={"upload.do","upload.json"})
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
				File f = new File(SERVER_BASE+SERVER_TEMP_UPLOAD_DIR);
				if(!f.exists()) f.mkdirs();
				
				outputStream = new BufferedOutputStream(new FileOutputStream(f.getPath()+"/"+virtualPath.replace("/", "\\")));
				Streams.copy(inputStream, outputStream, true);
				
				inputStream.close();
	            outputStream.flush();
	            outputStream.close();
	            result.sourceUrl="avatar/"+virtualPath;
			}
			result.success = true;
			result.msg = "Success!";
		return result;
	}*/
	
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