package cn.demi.cus.customer.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.cus.customer.service.ILevelService;
import cn.demi.cus.customer.vo.LevelVo;

/**
 * Create on : 2016年11月15日 下午3:03:34  <br>
 * Description : 客户等级Action<br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Controller("cus.levelAction")
@RequestMapping("/cus/level")
public class LevelAction extends BaseAction<LevelVo> {
	final String VIEW_PATH = "/cus/level/level";
	@Autowired private ILevelService levelService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<LevelVo> baseService() {
		return levelService;
	}
}