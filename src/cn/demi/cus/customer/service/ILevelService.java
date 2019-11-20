package cn.demi.cus.customer.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.cus.customer.vo.LevelVo;

/**
 * Create on : 2016年11月15日 下午2:55:25  <br>
 * Description : 客户等级service <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Transactional
public interface ILevelService extends IBaseService<LevelVo> {
	
}
