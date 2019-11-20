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
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IAccountRoleService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.office.service.IDgService;
import cn.demi.office.vo.DgCfgVo;
import cn.demi.office.vo.DgVo;
/**
 * 打卡
 * @author QuJunLong
 *
 */
@Controller("office.dgAction")
@RequestMapping("/office/dg")
public class DgAction extends BaseAction<DgVo> {
	final String VIEW_PATH = "/office/dg/dg";
	@Autowired 
	private IDgService dgService;	
	@Autowired
	private IAccountRoleService accountRoleService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<DgVo> baseService() {
		return dgService;
	}
	@Override
	public ModelAndView edit(DgVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=dgService.findById(v.getId());
		}else {
			v.setUserId(getCurrent().getUserId());
			v.setUserName(getCurrent().getUserName());
		}
		List<AccountVo> accountList = accountRoleService.listAccount(Constants.ROLE_KQ_KQSHR);
		mav.addObject("userList", accountList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value="add2Dk.do")
	@Log(operation=Log.Operation.ADD,content="打卡",module="打卡记录")
	public Status add2Dk() throws GlobalException {
		try {
			dgService.add2Dk();
			status = new Status("打卡成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"打卡失败",e);
			status = new Status("打卡失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="add2Bk.do")
	@Log(operation=Log.Operation.ADD,content="补卡",module="打卡记录")
	public Status add2Bk(DgVo v, RedirectAttributes attr) throws GlobalException {
		try {
			dgService.add(v);
			status = new Status("补卡成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"补卡失败",e);
			status = new Status("补卡失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="update2Bk.do")
	@Log(operation=Log.Operation.UPDATE,content="补卡",module="打卡记录")
	public Status update2Bk(DgVo v, RedirectAttributes attr) throws GlobalException {
		try {
			dgService.update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="save2Bk.do")
	@Log(operation=Log.Operation.SAVE,content="补卡",module="打卡记录")
	public Status save2Bk(DgVo v, RedirectAttributes attr) throws GlobalException {
		try {
			dgService.save(v);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="deleteOne.do")
	@Log(operation=Log.Operation.DELETE,content="删除",module="打卡记录")
	public Status deleteOne(DgVo v, RedirectAttributes attr) throws GlobalException {
		try {
			dgService.delete(v.getId());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}  
		return status;
	}
	@RequestMapping(value="gridPage4Query.do")
	public ModelAndView gridPage4Query(DgVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<DgVo> dgList=dgService.list(v);
		mav.addObject("list",dgList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page_query");
		return mav;
	}
	@Override
	public ModelAndView gridPage(DgVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		boolean isEdit = false; 
		if(getCurrent().getRoleNames().contains("管理员")) {
			isEdit = true; 
		}
		mav.addObject(VO, v);
		mav.addObject(IS_EDIT, isEdit);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@RequestMapping(value={"editCfg.do"})
	public ModelAndView editCfg() throws GlobalException {
		ModelAndView mav = new ModelAndView();
		DgCfgVo v=dgService.find();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_cfg_edit");
		return mav;
	}
	
	@RequestMapping(value="updateCfg.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新配置",module="考勤管理")
	public Status updateCfg(DgCfgVo v) throws GlobalException {
		try {
			dgService.updateCfg(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value="checkTime.do")
	public Status checkTime() throws GlobalException {
		try {
			String msg=dgService.checkTime();
			status = new Status(msg,Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("验证打卡时间异常",Status.STATUS_ERROR);
		}  
		return status;
	}
}