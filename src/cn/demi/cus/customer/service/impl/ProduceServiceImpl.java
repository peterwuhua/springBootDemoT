package cn.demi.cus.customer.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.demi.cus.customer.po.Produce;
import cn.demi.cus.customer.service.IProduceService;
import cn.demi.cus.customer.vo.ProduceVo;

/**
 * Create on : 2016年11月15日 下午3:01:23  <br>
 * Description : LevelServiceImpl <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Service("cus.produceService")
public class ProduceServiceImpl extends BaseServiceImpl<ProduceVo,Produce> implements
		IProduceService {
}
