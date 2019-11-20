package cn.demi.base.system.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.base.system.service.IDutyService;
import cn.demi.base.system.vo.DutyVo;
/**
 * <strong>Create on : 2016年11月2日 下午2:11:10 </strong> <br>
 * <strong>Description : 岗位action</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Controller("sys.dutyAction")
@RequestMapping("sys/duty")
public class DutyAction extends BaseAction<DutyVo> {
	final String VIEW_PATH = "/sys/duty/duty";
	@Autowired
	private IDutyService dutyService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<DutyVo> baseService() {
		return dutyService;
	}
}
