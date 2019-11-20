package cn.demi.base.system.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.INoticeService;
import cn.demi.base.system.vo.NoticeVo;
/**
 * <strong>Create on : Dave Yu 2016年11月2日 下午2:19:29 </strong> <br>
 * <strong>Description : 公告action</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Controller("sys.noticeAction")
@RequestMapping("sys/notice")
public class NoticeAction extends BaseAction<NoticeVo>{
	final String VIEW_PATH = "/sys/notice/notice";
	@Autowired private INoticeService noticeService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<NoticeVo> baseService(){
		return noticeService;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增公告通知",module="公告通知")
	public ModelAndView add(NoticeVo v, RedirectAttributes attr) throws GlobalException {
		return super.add(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.DELETE,content="删除公告通知",module="公告通知")
	public ModelAndView delete(NoticeVo v, RedirectAttributes attr) throws GlobalException {
		return super.delete(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="删除公告通知",module="公告通知")
	public ModelAndView update2del(NoticeVo v, RedirectAttributes attr) throws GlobalException {
		return super.update2del(v, attr);
	}
	
	@Override
	public ModelAndView edit(NoticeVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=noticeService.findById(v.getId());
		}else {
			v.setSendTime(DateUtils.getCurrDateStr());
			v.setUserId(getCurrent().getAccountId());
			v.setUserName(getCurrent().getUserName());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = "ajaxGetList.do")
	public List<NoticeVo> ajaxGetList(NoticeVo v) throws GlobalException{
		List<NoticeVo> list=new ArrayList<NoticeVo>();
		list=noticeService.list(v);
		return list;
	}
}
