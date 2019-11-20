package cn.demi.im.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.im.service.IMessageContentService;
import cn.demi.im.vo.MessageContentVo;
import net.sf.json.JSONArray;
/**
 * Create on : 2016年12月15日 下午8:42:53  <br>
 * Description :  获取即时通讯聊天记录<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Controller("im.messageContentAction")
@RequestMapping("/im/messageContent")
public class MessageContentAction extends BaseAction<MessageContentVo> {
	final String VIEW_PATH = "/im/message_content/message_content";
	@Autowired private IMessageContentService messageContentService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<MessageContentVo> baseService() {
		return messageContentService;
	}
	/**
	 * Create on : Paddy Zhang 2017年2月23日 下午1:40:08 <br>
	 * Description : 查询聊天记录  <br>
	 * @param toId
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="chatMessage.do")
	public Map<String,Object> getchatMessage(@RequestParam(value ="toId",required = true)String toId) throws GlobalException {
		String mineId = getCurrent().getAccountId();
		Map<String,Object> chatLog = messageContentService.getchatMessage(mineId,toId);
		return chatLog;
	}
	/**
	 * Description : 查询未读聊天记录  <br>
	 * @param toId
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="getNewMessage.do")
	public List<MessageContentVo> getNewMessage() throws GlobalException {
		String mineId = getCurrent().getAccountId();
		List<MessageContentVo> chatLog = messageContentService.listOffLine(mineId);
		return chatLog;
	}
	/**
	 * Create on : Paddy Zhang 2017年2月23日 下午1:34:48 <br>
	 * Description : 即时通讯聊天记录  <br>
	 * @param toId
	 * @param v
	 * @param pageResult
	 * @return
	 * @throws GlobalException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="chatLogPage.do")
	public ModelAndView chatLogPage(@RequestParam(value ="toId",required = true)String toId,
			MessageContentVo v,PageResult pageResult) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		String mineId = getCurrent().getAccountId();
		v.setMineId(mineId);
		v.setToId(toId);
		pageResult.getPageBean().setPageSize(5);
		pageResult = messageContentService.pageResult(v, pageResult);
		mav.addObject("pageResult", pageResult);
		Map<String, Object> data = messageContentService.getChatContent(v,(List<MessageContentVo>)pageResult.getResultList());
		mav.addObject("data",JSONArray.fromObject(data));
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_chatlog");
		return mav;
	}
}