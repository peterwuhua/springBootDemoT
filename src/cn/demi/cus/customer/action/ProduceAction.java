package cn.demi.cus.customer.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.cus.customer.service.IProduceService;
import cn.demi.cus.customer.vo.ProduceVo;

/**
 * Create on : 2016年11月15日 下午3:03:48  <br>
 * Description : 生产单位Action <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Controller("cus.produceAction")
@RequestMapping("/cus/produce")
public class ProduceAction extends BaseAction<ProduceVo> {
	final String VIEW_PATH = "/cus/produce/produce";
	@Autowired private IProduceService produceService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ProduceVo> baseService() {
		return produceService;
	}
	
}