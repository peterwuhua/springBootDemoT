package cn.demi.base.system.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.service.IMessageDetailService;
import cn.demi.base.system.service.IMessageService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.base.system.vo.MessageDetailVo;
import cn.demi.base.system.vo.MessageVo;
/**
 * Create on : 2016年11月3日 上午10:45:19  <br>
 * Description : 邮件action  <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Controller("sys.messageAction")
@RequestMapping("sys/message")
public class MessageAction extends BaseAction<MessageVo>{
	final String VIEW_PATH = "/sys/message/message";
	@Autowired private IMessageService messageService;
	@Autowired private IMessageDetailService messageDetailService;
	@Autowired
	private IFilesService filesService;
	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<MessageVo> baseService(){
		return messageService;
	}
	@Override
	public ModelAndView edit(MessageVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=messageService.findById(v.getId());
		}
		mav.addObject(VO, v);
		MessageVo vo=messageService.count4Category();
		mav.addObject("messageVo", vo);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:14:18 </strong> <br>
	 * <strong>Description : 收件箱</strong> <br>
	 * @param v v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="inbox.do")//收件箱
	public ModelAndView inbox(MessageVo v,@RequestParam(value = "flag", required = false)String flag) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_inbox");
		mav.addObject("flag", flag);
		mav.addObject("list", messageDetailService.list(getCurrent().getAccountId(),1, v.getSubject()));
		MessageVo vo=messageService.count4Category();
		vo.setSubject(v.getSubject());
		mav.addObject("messageVo", vo);
		return mav;
	}
	
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:14:57 </strong> <br>
	 * <strong>Description : 发件箱</strong> <br>
	 * @param v v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="sentbox.do")//发件箱
	public ModelAndView sentbox(MessageVo v,@RequestParam(value = "flag", required = false)String flag) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_sentbox");
		mav.addObject("flag", flag);
		mav.addObject("list", messageService.list(getCurrent().getAccountId(),1, v.getSubject()));
		MessageVo vo=messageService.count4Category();
		mav.addObject("messageVo", vo);
		return mav;
	}
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:15:16 </strong> <br>
	 * <strong>Description : 垃圾箱</strong> <br>
	 * @param v v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="trash.do")//垃圾箱
	public ModelAndView trash(MessageVo v,@RequestParam(value = "flag", required = false)String flag) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_trashbox");
		mav.addObject("flag", flag);
		mav.addObject("list", messageDetailService.list(getCurrent().getAccountId(),-1, v.getSubject()));
		MessageVo vo=messageService.count4Category();
		mav.addObject("messageVo", vo);
		return mav;
	}
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:15:38 </strong> <br>
	 * <strong>Description : 草稿箱</strong> <br>
	 * @param v v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="draft.do")//草稿箱
	public ModelAndView draft(MessageVo v,@RequestParam(value = "flag", required = false)String flag) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_draftbox");
		mav.addObject("flag", flag);
		mav.addObject("list", messageService.list(getCurrent().getAccountId(),-2, v.getSubject()));
		MessageVo vo=messageService.count4Category();
		mav.addObject("messageVo", vo);
		return mav;
	}
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:15:56 </strong> <br>
	 * <strong>Description : 重要</strong> <br>
	 * @param v v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="important.do")//重要
	public ModelAndView important(MessageVo v,@RequestParam(value = "flag", required = false)String flag) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_importantbox");
		mav.addObject("flag", flag);
		mav.addObject("list", messageDetailService.list(getCurrent().getAccountId(),3, v.getSubject()));
		MessageVo vo=messageService.count4Category();
		mav.addObject("messageVo", vo);
		return mav;
	}
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:16:26 </strong> <br>
	 * <strong>Description : 查看</strong> <br>
	 *  <strong> v 1.0.0 </strong> <br>
	 *  <strong>Dave Yu</strong><br>
	 * @param v v
	 * @param detailId detailId
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="detail.do")//查看
	public ModelAndView detail(MessageVo v,@RequestParam(value = "detailId", required = false)String detailId,@RequestParam(value = "flag", required = false)String flag) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		if(!StrUtils.isBlankOrNull(detailId)){
			MessageDetailVo detailVo=new MessageDetailVo();
			detailVo =  messageDetailService.findById(detailId);
			detailVo.setReadFlag(1);
			detailVo.setReadTime(DateUtils.getCurrDateTimeStr());
			messageDetailService.update(detailVo);	
			
			List<FilesVo> fileList=filesService.listByBusId(detailVo.getMessageVo().getId());
			detailVo.setFileList(fileList);
			
			mav.addObject(VO,detailVo);
			mav.addObject("flag", flag);
		}
		MessageVo vo=messageService.count4Category();
		mav.addObject("messageVo", vo);
		mav.setViewName(getViewPath()+"_detail");
		return mav;
	}
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:17:01 </strong> <br>
	 * <strong>Description : 回复</strong> <br>
	 * @param v v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="messageAnswer.do")//回复
	public ModelAndView answer(MessageVo v,@RequestParam(value = "flag", required = false)String flag) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		v=messageService.findById(v.getId());
		List<FilesVo> fileList=filesService.listByBusId(v.getId());
		v.setFileList(fileList);
		mav.addObject(VO, v);
		mav.addObject("flag",flag);
		MessageVo vo=messageService.count4Category();
		mav.addObject("messageVo", vo);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	
	@RequestMapping(value = "update4Data.do")
	public ModelAndView  update4Data(MessageVo v, RedirectAttributes attr, @RequestParam(value = "files", required = false) MultipartFile[] files)
			throws GlobalException {
		try {
			messageService.save(v);
			//文件上传
			List<FilesVo> fileList = uploads(files, v.getId(), Constants.FILE_TYPE_MSG);
			filesService.saveFiles(fileList);		
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", v.getId());
		mav.setViewName("redirect:inbox.do");
		return mav;
	}
	
	 
	 
	@RequestMapping(value="drop.do")
	@Log(operation=Log.Operation.DELETE,content="删除信件",module="个人信箱")
	public ModelAndView drop(@RequestParam(value = "ids", required = true)String ids,@RequestParam(value = "flag", required = false)String flag,@RequestParam(value = "from", required = true)String from,RedirectAttributes attr)
			throws GlobalException {
		if("draft".equals(from)||"sentbox".equals(from)){
			messageService.update2del(ids);
		}else{
			messageDetailService.update2del(ids);
		}
		ModelAndView mav = new ModelAndView("redirect:"+from+".do");
		mav.addObject("flag",flag);
		/*MessageVo vo=messageService.count4Category();
		mav.addObject("messageVo", vo);*/
		return mav;
	}
	 
	@RequestMapping(value="updatePosition")
	@Log(operation=Log.Operation.UPDATE,content="修改信件状态",module="个人信箱")
	public ModelAndView updatePosition(MessageVo v,@RequestParam(value = "from", required = true)String from, RedirectAttributes attr)
			throws GlobalException {
		messageDetailService.updatePosition(v.getIds(), v.getPosition());
		return new ModelAndView("redirect:"+from+".do");
	}
}
