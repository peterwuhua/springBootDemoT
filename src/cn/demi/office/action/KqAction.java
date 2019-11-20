package cn.demi.office.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.ICodeService;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.office.service.IKqService;
import cn.demi.office.vo.KqVo;
/**
 * 考勤
 * @author QuJunLong
 *
 */
@Controller("office.kqAction")
@RequestMapping("/office/kq")
public class KqAction extends BaseAction<KqVo> {
	final String VIEW_PATH = "/office/kq/kq";
	@Autowired 
	private IKqService kqService;	
	@Autowired
	private ICodeService codeService;
	@Autowired 
	private IProgressLogService progressLogService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<KqVo> baseService() {
		return kqService;
	}
	@Override
	public ModelAndView edit(KqVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=kqService.findById(v.getId());
			List<ProgressLogVo> logList=progressLogService.findList(null, v.getId());
			mav.addObject("logList", logList);
		}else {
			v.setUserId(getCurrent().getUserId());
			v.setUserName(getCurrent().getUserName());
			v.setDate(DateUtils.getCurrDateTimeStr());
			v.setDeptId(getCurrent().getDepId());
			v.setDeptName(getCurrent().getDepName());
		}
		List<String> typeList = codeService.listByCode("qjgl-type"); // 查询请假类型
		mav.addObject("typeList", typeList);
//		List<AccountVo> accountList = accountRoleService.listAccount(Constants.ROLE_KQ_KQSHR);
//		mav.addObject("userList", accountList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView show(KqVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
			List<ProgressLogVo> logList=progressLogService.findList(null, v.getId());
			mav.addObject("logList", logList);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value="addData.do")
	@Log(operation=Log.Operation.ADD,content="新增记录",module="考勤记录")
	public Status addData(KqVo v, RedirectAttributes attr) throws GlobalException {
		try {
			kqService.add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="updateData.do")
	@Log(operation=Log.Operation.UPDATE,content="修改记录",module="考勤记录")
	public Status updateData(KqVo v, RedirectAttributes attr) throws GlobalException {
		try {
			kqService.update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="saveData.do")
	@Log(operation=Log.Operation.SAVE,content="保存记录",module="考勤记录")
	public Status saveData(KqVo v, RedirectAttributes attr) throws GlobalException {
		try {
			kqService.save(v);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="deleteOne.do")
	@Log(operation=Log.Operation.DELETE,content="删除",module="考勤记录")
	public Status deleteOne(KqVo v, RedirectAttributes attr) throws GlobalException {
		try {
			kqService.delete(v.getId());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}  
		return status;
	}
	/********销假**********/
	@RequestMapping(value="gridPage4Xj.do")
	public ModelAndView gridPage4Xj(KqVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_xj_page");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = "gridData4Xj.do")
	public GridVo gridData4Xj(GridVo gridVo, KqVo v) throws GlobalException {
		return kqService.gridData4Xj(gridVo,v);
	}
	@ResponseBody
	@RequestMapping(value="update4Xj.do")
	@Log(operation=Log.Operation.UPDATE,content="更新销假记录",module="考勤记录")
	public Status update4Xj(KqVo v, RedirectAttributes attr) throws GlobalException {
		try {
			kqService.update4Xj(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/********有效请假列表展示**********/
	@RequestMapping(value="gridPage4Show.do")
	public ModelAndView gridPage4Show(KqVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page_show");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = "gridData4Show.do")
	public GridVo gridData4Show(GridVo gridVo, KqVo v) throws GlobalException {
		return kqService.gridData4Show(gridVo,v);
	}
	/*************考勤查询*************/
	@RequestMapping(value="gridPage4Query.do")
	public ModelAndView gridPage4Query(KqVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page_query");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = "gridData4Query.do")
	public GridVo gridData4Query(GridVo gridVo, KqVo v) throws GlobalException {
		return kqService.gridData4Query(gridVo,v);
	}
}