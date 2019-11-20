package cn.demi.cus.customer.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.demi.cus.customer.po.Level;
import cn.demi.cus.customer.service.ILevelService;
import cn.demi.cus.customer.vo.LevelVo;

/**
 * Create on : 2016年11月15日 下午3:01:12  <br>
 * Description : LevelServiceImpl <br>
 *
 * @author Danlee Li<br>
 * @version v 1.0.0  <br>
 */
@Service("cus.levelService")
public class LevelServiceImpl extends BaseServiceImpl<LevelVo, Level> implements
    ILevelService {
}
