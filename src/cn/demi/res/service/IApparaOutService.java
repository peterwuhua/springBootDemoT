package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.ext.common.vo.TxVo;
import cn.demi.res.vo.ApparaOutVo;
import cn.demi.res.vo.ApparaVo;

@Transactional
public interface IApparaOutService extends IBaseService<ApparaOutVo> {
	
	/**
	 * 获取当天之后的仪器出库记录
	 * 用于采样安排环节数据输出
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	List<ApparaOutVo> listData(ApparaOutVo v) throws GlobalException;
	/**
	 * 获取出库仪器同型号仪器列表
	 * 用于仪器出库更换仪器
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	List<ApparaVo> listApp(ApparaOutVo v) throws GlobalException;
	/**
	 *获取首页提醒集合
	 * @param list
	 * @return
	 * @throws GlobalException
	 */
	List<TxVo> list4Tx(List<TxVo> list) throws GlobalException;
}
