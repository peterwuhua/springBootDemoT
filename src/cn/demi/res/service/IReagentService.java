package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.ext.common.vo.TxVo;
import cn.demi.res.vo.ReagentVo;

@Transactional
public interface IReagentService extends IBaseService<ReagentVo> {
	/**
	 *获取首页提醒集合
	 * @param list
	 * @return
	 * @throws GlobalException
	 */
	List<TxVo> list4Tx(List<TxVo> list) throws GlobalException;
}
