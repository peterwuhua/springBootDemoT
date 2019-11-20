package cn.demi.cus.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.vo.CustVo;
import cn.demi.cus.customer.vo.CustomerVo;
/**
 * Create on : 2016年11月15日 下午2:55:00  <br>
 * Description : 客户信息service <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Transactional
public interface ICustomerService extends IBaseService<CustomerVo> {
	/**
	 * Create on : Danlee Li 2016年12月26日 上午10:07:57 <br>
	 * Description : 判断当前客户信息是否有关联数据还没有删除 <br>
	 * @param ids
	 * @return
	 */
	public boolean isDelete4All(String ...ids) throws GlobalException ;
	/**
	 * Create on : Danlee Li 2017年4月1日 上午11:29:19 <br>
	 * Description : 未同步客户信息<br>
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDate4UnSysn(GridVo gridVo,CustomerVo v) throws GlobalException;
	
	/**
	 * Create on : Danlee Li 2017年4月1日 上午11:29:19 <br>
	 * Description : 已同步客户信息<br>
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDate4Sysn(GridVo gridVo,CustomerVo v) throws GlobalException;
	/**
	 * Create on : Danlee Li 2017年4月6日 上午10:42:52 <br>
	 * Description : 修改客户状态位已同步 <br>
	 * @param ids
	 * @throws GlobalException
	 */
	public void update4Sysn(String ...ids)throws GlobalException;
	/**
	 * Create on : Danlee Li 2017年4月6日 下午1:42:22 <br>
	 * Description : 根据客户名称查找客户信息 <br>
	 * @param loginName
	 * @return
	 * @throws GlobalException
	 */
	public CustomerVo find(String loginName)throws GlobalException;
	/**
	 * 客户跟踪列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Gz(GridVo gridVo,CustomerVo v) throws GlobalException;
	
	/**
	 * 自动补全
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<CustVo> list4Sim(CustomerVo v) throws GlobalException;
	
	/**
	 * 自动补全
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<CustVo> list4Full(CustomerVo v) throws GlobalException;
	
	
	public List<CustomerVo> fetchMycustomer(String id,String authName) throws GlobalException;
	

	public void updateFieldById(String id,String name) throws GlobalException;
	
	public Customer fetchCustomerById(String id) throws GlobalException;
	
	

}
