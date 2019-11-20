package cn.demi.res.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.demi.res.service.IStandardService;
import cn.demi.res.vo.StandardVo;

/** <strong>Create on : 2017年2月28日 下午1:22:10 </strong> <br>
 * <strong>Description : 标准品管理</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
@Controller("res.standardAction")
@RequestMapping("/res/standard")
public class StandardAction extends BaseAction<StandardVo> {
	final String VIEW_PATH = "/res/standard/standard";
	@Autowired private IStandardService standardService;
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<StandardVo> baseService() {
		return standardService;
	}
	/** <strong>Create on : Hans He 2017年2月28日 下午1:22:17 </strong> <br>
	 * <strong>Description : 跳转到按效期列表</strong> <br>
	 * @param v
	 * @param pageResult
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping("/effectivePage")
	public ModelAndView effectivePage(StandardVo v,PageResult pageResult) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject("pageResult", pageResult);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_effective_page");
		return mav;
	}
	
	/** <strong>Create on : Hans He 2017年3月2日 下午4:34:28 </strong> <br>
	 * <strong>Description : 获取按效期列表data</strong> <br>
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping("/effectivePageData")
	public GridVo effectivePageData(GridVo gridVo,StandardVo v) throws GlobalException{
		return standardService.effectivePageData(gridVo,v);
	}
	
	@Override
	@Log(operation=Log.Operation.ADD,content="新增",module="标准品管理")
	public Status addData(StandardVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改",module="标准品管理")
	public Status updateData(StandardVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@ResponseBody
	@RequestMapping(value="copyData.do")
	@Log(operation=Log.Operation.ADD,content="复制信息",module="标准品管理")
	public Status copyData(StandardVo v, RedirectAttributes attr) throws GlobalException {
		try {
			String targetId = standardService.copy(v);
			status = new Status("copy成功",Status.STATUS_SUCCESS);
			status.setObject(targetId);
		} catch (GlobalException e) {
			log.info("copy失败",e);
			status = new Status("copy成功",Status.STATUS_ERROR);
		}
		return status;
	}
}