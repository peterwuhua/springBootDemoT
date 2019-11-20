package cn.demi.pfm.action;

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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.pfm.service.ISetService;
import cn.demi.pfm.vo.SetVo;

@Controller("pfm.setAction")
@RequestMapping("/pfm/set")
public class SetAction extends BaseAction<SetVo> {
    final String VIEW_PATH = "/pfm/set/set";


    @Autowired
    private ISetService setService;
 

    @Override
    public String getViewPath() {
        return VIEW_PATH;
    }

    @Override
    public IBaseService<SetVo> baseService() {
        return setService;
    }

    @ResponseBody
    @RequestMapping(value = "addPlan")
    @Log(operation = Log.Operation.ADD, content = "新增考核规定", module = "考核规定")
    public Status addPlan(SetVo v, RedirectAttributes attr) throws GlobalException {
        try {
            setService.add(v);
            status = new Status("新增成功", Status.STATUS_SUCCESS);
        } catch (GlobalException e) {
            log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
            status = new Status("新增失败", Status.STATUS_ERROR);
        }
        return status;
    }

    @ResponseBody
    @RequestMapping(value = "updatePlan")
    @Log(operation = Log.Operation.UPDATE, content = "修改考核规定", module = "考核规定")
    public Status updatePlan(SetVo v, RedirectAttributes attr) throws GlobalException {
        try {
            setService.update(v);
            status = new Status("修改成功", Status.STATUS_SUCCESS);
        } catch (GlobalException e) {
            log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
            status = new Status("修改失败", Status.STATUS_ERROR);
        }
        return status;
    }

    @Override
    @Log(operation = Log.Operation.DELETE, content = "删除考核规定", module = "考核规定")
    public ModelAndView delete(SetVo v, RedirectAttributes attr) throws GlobalException {
        return super.delete(v, attr);
    }

    @Override
    @Log(operation = Log.Operation.UPDATE, content = "删除考核规定", module = "考核规定")
    public ModelAndView update2del(SetVo v, RedirectAttributes attr) throws GlobalException {
        return super.update2del(v, attr);
    }

    @Override
    public ModelAndView edit(SetVo v) throws GlobalException {
        ModelAndView mav = new ModelAndView();
        if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
            v = setService.findById(v.getId());
        } else {
            /*if(!StrUtils.isBlankOrNull(v.getSelectdate())) {
                v.setStartDate(v.getSelectdate());
                v.setEndDate(v.getSelectdate());
            }else {
                v.setStartDate(DateUtils.getCurrDateStr());
                v.setEndDate(DateUtils.getCurrDateStr());
            }
            v.setUserId(getCurrent().getAccountId());
            v.setUserName(getCurrent().getUserName());
            v.setStartTime(DateUtils.getCurrTimeStr().substring(0, 5));
            v.setEndTime("23:59");*/
        }
        mav.addObject(VO, v);
        mav.setViewName(getViewPath() + "_edit");
        return mav;
    }
}
