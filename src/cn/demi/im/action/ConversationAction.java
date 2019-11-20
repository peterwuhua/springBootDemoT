package cn.demi.im.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.current.Current;
import cn.demi.im.service.IConversationService;
import cn.demi.im.vo.ConversationVo;
import net.sf.json.JSONObject;

@Controller("im.conversationAction")
@RequestMapping("/im/conversation")
public class ConversationAction extends BaseAction<ConversationVo> {
	final String VIEW_PATH = "/im/conversation/conversation";
	
	public static final String SERVER_IM_IMG = ApplicationUtils.getValue("config.upload.sys.im.img").toString().replace("\\", "/");
	public static final String SERVER_IM_FILE = ApplicationUtils.getValue("config.upload.sys.im.file").toString().replace("\\", "/");
	
	@Autowired private IConversationService conversationService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ConversationVo> baseService() {
		return conversationService;
	}
	
	@ResponseBody
	@RequestMapping(value ="listFriend.do")
	public Map<String,Object> listFriend() throws GlobalException {
		Map<String,Object> friendMap = conversationService.listFriend();
		return friendMap;
	}
	/**
	 * Create on : Paddy Zhang 2016年12月29日 下午5:16:13 <br>
	 * Description :  发送图片<br>
	 * @param file
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="uploadImage.do")
	public String uploadImage(MultipartFile file) throws GlobalException {
		Map<String, Object> datas = new HashMap<>();
		try {
			if(!file.isEmpty()){
				String path = getPath(file,SERVER_IM_IMG);
				upload(file,path);
				datas.put("code","0");
				datas.put("msg","");
				Map<String, Object> data = new HashMap<>();
				data.put("src","/"+path);
				datas.put("data",data);	
			}
		} catch (Exception e) {
			datas.put("code","1");
			datas.put("msg","发送图片失败");
		}
		JSONObject jsonObject = JSONObject.fromObject(datas);
		return jsonObject.toString();
	}
	/**
	 * Create on : Paddy Zhang 2016年12月30日 上午9:33:12 <br>
	 * Description :  上传文件<br>
	 * @param file
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="uploadFile.do")
	public String uploadFile(MultipartFile file) throws GlobalException {
		Map<String, Object> datas = new HashMap<>();
		try {
			if(!file.isEmpty()){
				String path = getPath(file,SERVER_IM_FILE);
				upload(file,path);
				datas.put("code","0");
				datas.put("msg","");
				Map<String, Object> data = new HashMap<>();
				data.put("src","/"+path);
				data.put("name",file.getOriginalFilename());
				datas.put("data",data);	
			}
		} catch (Exception e) {
			datas.put("code","1");
			datas.put("msg","文件发送失败");
		}
		JSONObject jsonObject = JSONObject.fromObject(datas);
		return jsonObject.toString();
	}
	/**
	 * Create on : Paddy Zhang 2016年12月29日 下午5:16:27 <br>
	 * Description :  获取路径<br>
	 * @param file
	 * @param path
	 * @return
	 */
	private String getPath(MultipartFile file,String path){
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		Current current = getCurrent();
		String p = path+"/"+current.getLoginName()+"_"+System.currentTimeMillis()+fileType;
		return p.replace("\\", "/");
	}

	@Override
	public ResponseEntity<byte[]> download(
			@RequestParam(value = "filePath", required = true) String filePath,
			@RequestParam(value = "trueName", required = true) String trueName) throws GlobalException {
		//String ctxPath = getRealPath(request);
		String downLoadPath = SERVER_BASE + filePath.replace("/", File.separator);  
        return down(trueName, downLoadPath);
	}
	
}