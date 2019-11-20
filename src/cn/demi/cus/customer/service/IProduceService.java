package cn.demi.cus.customer.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.cus.customer.vo.ProduceVo;
/**
 * Create on : 2016年11月15日 下午2:52:24  <br>
 * Description :  生产单位service<br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Transactional
public interface IProduceService extends IBaseService<ProduceVo> {
	
}
