package cn.demi.cus.customer.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.CusProduceVo;
/**
 * Create on : 2016年11月15日 下午2:54:40  <br>
 * Description : 客户生产单位service <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Transactional
public interface ICusProduceService extends IBaseService<CusProduceVo> {
	
	/**
	 * Create on : Danlee Li 2016年11月17日 上午10:41:39 <br>
	 * Description :  gridData4Tab获取JSON分页信息<br>
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Tab(GridVo gridVo, CusProduceVo v) throws GlobalException;
}
