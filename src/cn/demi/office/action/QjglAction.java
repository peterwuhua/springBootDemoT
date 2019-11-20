package cn.demi.office.action;

import java.util.List;

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
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.ICodeService;
import cn.demi.office.service.IQjglService;
import cn.demi.office.vo.QjglVo;

/**
 * 
 * @ClassName: QjglAction
 * @Description: 请假申请
 * @author: 吴华
 * @date: 2019年3月11日 上午11:05:27
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Controller("office.qjglAction")
@RequestMapping("/office/qjgl")
public class QjglAction extends BaseAction<QjglVo> {
	final String VIEW_PATH = "/office/qj/sq/qjsq";
	@Autowired
	private IQjglService qjglService;
	@Autowired
	private ICodeService codeService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<QjglVo> baseService() {
		return qjglService;
	}

	/**
	 * 
	 * <p>
	 * Title: gridPage
	 * </p>
	 * <p>
	 * Description: 请假页面
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#gridPage(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value = GRID_PAGE)
	public ModelAndView gridPage(QjglVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Title: gridData
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#gridData(cn.core.framework.common.page.GridVo,
	 *      cn.core.framework.common.vo.Vo)
	 */
	@ResponseBody
	@RequestMapping(value = { GRID_DATA, GRID_DATA_JSON })
	public GridVo gridData(GridVo gridVo, QjglVo v) throws GlobalException {
		return baseService().gridData(gridVo, v);
	}

	/**
	 * 
	 * <p>
	 * Title: edit
	 * </p>
	 * <p>
	 * Description: 跳转到编辑页
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#edit(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value = EDIT)
	public ModelAndView edit(QjglVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
		}
		v = qjglService.organizeData(v); // 组织数据
		List<String> typeList = codeService.listByCode("qjgl-type"); // 查询请假类型
		mav.addObject("typeList", typeList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	/**
	 * 
	 * @Title: save4Data @Description: 保存方法 @param: @param v @param: @param
	 *         attr @param: @return @param: @throws GlobalException @return:
	 *         ModelAndView @throws
	 */
	@RequestMapping(value = "save4Data.do")
	@Log(operation = Log.Operation.SAVE, content = "请假申请保存", module = "请假管理模块：请假申请")
	public ModelAndView save4Data(QjglVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().save(v);
			status = new Status("保存成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败", e);
			status = new Status("保存失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", v.getId());
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}

	/**
	 * 
	 * @Title: update4Data @Description: 更新并提交 @param: @param v @param: @param
	 *         attr @param: @return @param: @throws GlobalException @return:
	 *         Status @throws
	 */
	@ResponseBody
	@RequestMapping(value = "update4Data.do")
	public Status update4Data(QjglVo v, RedirectAttributes attr) throws GlobalException {
		try {
			qjglService.update(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * 
	 * @Title: add4Data @Description: 新增并提交 @param: @param v @param: @param
	 *         attr @param: @return @param: @throws GlobalException @return:
	 *         Status @throws
	 */
	@ResponseBody
	@RequestMapping(value = "add4Data.do")
	public Status add4Data(QjglVo v, RedirectAttributes attr) throws GlobalException {
		try {
			qjglService.add(v);
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		return status;
	}

}