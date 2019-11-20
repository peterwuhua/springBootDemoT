package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.ext.common.vo.TxVo;
import cn.demi.res.vo.ConsumableVo;

@Transactional
public interface IConsumableService extends IBaseService<ConsumableVo> {
	
	/**
	 *获取首页提醒集合
	 * @param list
	 * @return
	 * @throws GlobalException
	 */
	List<TxVo> list4Tx(List<TxVo> list) throws GlobalException;
}
