package cn.demi.bus.test.action;

import java.util.ArrayList;
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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.test.service.IStantSolutService;
import cn.demi.bus.test.vo.StantSolutMxVo;
import cn.demi.bus.test.vo.StantSolutVo;
import cn.demi.res.vo.ReagentVo;
import cn.demi.res.vo.StandardVo;

/**
 * 
 * @ClassName: StantSolutAction
 * @Description:标准品溶液Action层
 * @author: 吴华
 * @date: 2019年7月3日 下午12:08:57
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Controller("bus.stantSolutAction")
@RequestMapping("/bus/stantSolut")
public class StantSolutAction extends BaseAction<StantSolutVo> {
	final String VIEW_PATH = "/bus/stantSolut/stantSolut";
	@Autowired
	private IStantSolutService stantSolutService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<StantSolutVo> baseService() {
		return stantSolutService;
	}

	/**
	 * 
	 * @Title: editBZJLD @Description: 编辑页面 @param: @param
	 *         v @param: @return @param: @throws GlobalException @return:
	 *         ModelAndView @throws
	 */
	@RequestMapping(value = { "editBz.do" })
	public ModelAndView editBz(StantSolutVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (!StrUtils.isBlankOrNull(v.getTaskItemId())) {
			v = stantSolutService.find(v);
		}
		if (StrUtils.isBlankOrNull(v.getJzWz())) {
			v.setJzWz("是");
		}
		if (StrUtils.isBlankOrNull(v.getBzNd())) {
			v.setBzNd("1");
		}
		if (v.getStantMxList()== null) {
			List<StantSolutMxVo> stantMxList = new ArrayList<>();
			String index = "";
			for (int i = 0; i < 5; i++) {// 初标
				StantSolutMxVo vo = new StantSolutMxVo();
				if (i == 0) {
					vo.setDeme1("空白");
				} else {
					index = String.valueOf(i);
					vo.setDeme1(index);
				}
				stantMxList.add(vo);
			}
			index = "";
			for (int i = 0; i < 5; i++) {// 复标
				StantSolutMxVo vo = new StantSolutMxVo();
				if (i == 0) {
					vo.setDeme1("空白");
				} else {
					index = String.valueOf(i);
					vo.setDeme1(index);
				}
				stantMxList.add(vo);
			}
			v.setStantMxList(stantMxList);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	/**
	 * 
	 * @Title: select @Description: 标准品清单 @param: @param
	 * v @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@RequestMapping(value = { "select.do" })
	public ModelAndView select(StandardVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_standard_select");
		return mav;
	}

	/**
	 * 
	 * @Title: select1 @Description: 试剂清单 @param: @param
	 * v @param: @return @param: @throws GlobalException @return:
	 * ModelAndView @throws
	 */
	@RequestMapping(value = { "select1.do" })
	public ModelAndView select1(ReagentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_reagent_select");
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Title: updateData
	 * </p>
	 * <p>
	 * Description: 滴剂溶液配置记录保存
	 * </p>
	 * 
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#updateData(cn.core.framework.common.vo.Vo,
	 *      org.springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@RequestMapping(value = UPDATE_DATA)
	@ResponseBody
	public Status updateData(StantSolutVo v, RedirectAttributes attr) throws GlobalException {
		try {
			stantSolutService.save(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}

}