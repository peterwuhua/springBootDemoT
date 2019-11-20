package cn.demi.init.ti.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.demi.init.ti.service.IRxNdService;
import cn.demi.init.ti.vo.RxNdVo;

@Controller("init.rxNdAction")
@RequestMapping("/init/rxNd")
public class RxNdAction extends BaseAction<RxNdVo> {
	final String VIEW_PATH = "/init/rx_nd/rx_nd";
	@Autowired private IRxNdService rxNdService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<RxNdVo> baseService() {
		return rxNdService;
	}
	
	@Override
	public ModelAndView edit(RxNdVo v) throws GlobalException {
		return super.edit(v);
	}
	
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存信息",module="容许浓度")
	public ModelAndView save(RxNdVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增信息",module="容许浓度")
	public Status addData(RxNdVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改信息",module="容许浓度")
	public Status updateData(RxNdVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.DELETE,content="删除	信息",module="容许浓度")
	public ModelAndView update2del(RxNdVo v, RedirectAttributes attr) throws GlobalException {
		return super.update2del(v, attr);
	}
}