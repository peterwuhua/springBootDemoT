package cn.demi.cus.customer.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.vo.CustomerVo;
/**
 * Create on : 2016年11月15日 下午2:48:07  <br>
 * Description :客户信息DAO  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public interface ICustomerDao extends IBaseDao<Customer> {
	/**
	 * Create on : Paddy Zhang 2017年1月5日 下午12:56:16 <br>
	 * Description :  <br>
	 * @param cusName 客户名称
	 * @return 
	 */
	public Customer findByName(String cusName);
	
	public String createCode();
	
	/**
	 * Create on : Danny Zhang 2016年11月14日 下午1:38:54 <br>
	 * Description :  通过属性获取group by下的list <br>
	 * @param pageType
	 * @return
	 * @throws GlobalException
	 */
	public List<String> selectByPageType(String pageType) throws GlobalException;

	/**
	 * Create on : Danny Zhang 2016年11月16日 下午6:09:58 <br>
	 * Description : 全部的客户级别 <br>
	 * @param saler
	 * @return
	 */
	public List<String> gradeList() throws GlobalException;

	/**
	 * Create on : Danny Zhang 2016年11月25日 上午10:34:35 <br>
	 * Description : 获取省市县数据 <br>
	 * @param pageType 级别类型
	 * @param parentType 父级类别
	 * @param data 上级数据
	 * @return
	 */
	public List<String> selectByArea(String pageType, String data,String parentType) throws GlobalException;

	/**
	 * Create on : Danny Zhang 2016年11月29日 下午1:33:20 <br>
	 * Description :  通过级别，客户获取数量<br>
	 * @param saler
	 * @param grade
	 * @return
	 */
	public List<Integer> countBySalerAndGrade(String saler, String grade) throws GlobalException;
	/**
	 * Create on : Dave Yu 2017年2月6日 上午11:40:01 <br>
	 * Description : 查询客户集合 <br>
	 * @param vo 
	 * @return
	 * @throws GlobalException
	 */
	public List<String> salerList(CustomerVo vo) throws GlobalException;
	/**
	 * Create on : Dave Yu 2017年2月6日 下午1:00:38 <br>
	 * Description : 查询该客户下的级别个数  <br>
	 * @param vo 
	 * @param saler
	 * @return
	 * @throws GlobalException
	 */
	public List<String> findCountGradesBySaler(CustomerVo vo, String saler) throws GlobalException;
	
}

