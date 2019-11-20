package cn.demi.office.action;

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
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.office.service.IQjspService;
import cn.demi.office.vo.QjglVo;

/**
 * 
 * @ClassName:  QjspAction   
 * @Description:请假审批  
 * @author: 吴华 
 * @date:   2019年3月11日 上午11:16:38       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Controller("office.qjspAction")
@RequestMapping("/office/qjsp")
public class QjspAction extends BaseAction<QjglVo> {
	final String VIEW_PATH = "/office/qj/sp/qjsp";
	@Autowired private IQjspService qjspService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<QjglVo> baseService() {
		return qjspService;
	}
	
	/**
	 * 
	 * <p>Title: edit</p>   
	 * <p>Description: 编辑页</p>   
	 * @param v
	 * @return
	 * @throws GlobalException   
	 * @see cn.core.framework.common.action.BaseAction#edit(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value=EDIT)
	public ModelAndView edit(QjglVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
		}
		v.setSumUserId(getCurrent().getAccountId());
		v.setSumUserName(getCurrent().getUserName());
		v.setSumDate(DateUtils.getCurrDateStr());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	
	/**
	 * 
	 * @Title: auditSuccess   
	 * @Description: 审批通过   
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: Status      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "auditSuccess.do")
	public Status auditSuccess(QjglVo v) throws GlobalException {
		if (v != null && !StrUtils.isBlankOrNull(v.getId())) {
			try {
				v.setFstatus(Constants.QJGL_STATUS_SP_TG);
				v.setXjshow("0");
				qjspService.updateAuditInfo(v);
				status = new Status("提交成功", Status.STATUS_SUCCESS);
			} catch (GlobalException e) {
				log.info("提交失败", e);
				status = new Status("提交失败", Status.STATUS_ERROR);
			}
		}
		return status;
	}

	
	
	
	/**
	 * 
	 * @Title: auditFailure   
	 * @Description: 审批不通过  
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: Status      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "auditFailure.do")
	public Status auditFailure(QjglVo v) throws GlobalException {
		if (v != null && !StrUtils.isBlankOrNull(v.getId())) {
			try {
				v.setFstatus(Constants.QJGL_STATUS_SP_BTG);
				qjspService.updateAuditInfo(v);
				status = new Status("提交成功", Status.STATUS_SUCCESS);
			} catch (GlobalException e) {
				log.info("提交失败", e);
				status = new Status("提交失败", Status.STATUS_ERROR);
			}
		}
		return status;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "updateDatad.do")
	public Status updateDatad(QjglVo v, RedirectAttributes attr) throws GlobalException {
		try {
			qjspService.update(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}
	
	
	
}