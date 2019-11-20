package cn.demi.cus.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.ClientVo;
import cn.demi.cus.customer.vo.CustVo;

@Transactional
public interface IClientService extends IBaseService<ClientVo> {

	public ClientVo find(String name) throws GlobalException;
	
	/**
	 *自动补全方法
	 */
	public List<CustVo> list4Sim(ClientVo v) throws GlobalException;
}
