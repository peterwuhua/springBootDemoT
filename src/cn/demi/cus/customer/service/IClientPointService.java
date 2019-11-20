package cn.demi.cus.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.ClientPointVo;

@Transactional
public interface IClientPointService extends IBaseService<ClientPointVo> {
	/**
	 * Description :根据客户ID获取JSON分页对象  <br>
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Tab(GridVo gridVo,ClientPointVo v) throws GlobalException;
	
	public List<ClientPointVo> listByCustId(String clientId)throws GlobalException;
	
	
	public List<ClientPointVo> listByCustName(String clientName)throws GlobalException;
}
