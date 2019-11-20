package cn.demi.res.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.demi.res.service.IConsumableService;
import cn.demi.res.vo.ConsumableVo;

/** 
 * <strong>Description : </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 */
@Controller("res.consumableAction")
@RequestMapping("/res/consumable")
public class ConsumableAction extends BaseAction<ConsumableVo> {
	final String VIEW_PATH = "/res/consumable/consumable";
	@Autowired private IConsumableService consumableService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ConsumableVo> baseService() {
		return consumableService;
	}
	
	@Override
	@Log(operation=Log.Operation.ADD,content="新增耗材信息",module="耗材管理")
	public Status addData(ConsumableVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改耗材信息",module="耗材管理")
	public Status updateData(ConsumableVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@ResponseBody
	@RequestMapping(value="copyData.do")
	@Log(operation=Log.Operation.ADD,content="复制信息",module="耗材管理")
	public Status copyData(ConsumableVo v, RedirectAttributes attr) throws GlobalException {
		try {
			String targetId = consumableService.copy(v);
			status = new Status("copy成功",Status.STATUS_SUCCESS);
			status.setObject(targetId);
		} catch (GlobalException e) {
			log.info("copy失败",e);
			status = new Status("copy成功",Status.STATUS_ERROR);
		}
		return status;
	}
}