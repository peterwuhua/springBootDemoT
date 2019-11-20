package cn.demi.bi.customer.service;

import java.util.List;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.CustomerVo;

/**
 * <strong>创建信息:2015年7月8日 下午3:54:36 </strong> <br>
 * <strong>概要 : 客户service</strong> <br>
 */
public interface ICustomerService extends IBaseService<CustomerVo> {

	/**
	 * Create on : Danny Zhang 2016年11月14日 下午1:36:33 <br>
	 * Description : 通过属性获取group by下的list <br>
	 * @param pageType
	 * @return
	 * @throws GlobalException
	 */
	public List<String> selectByPageType(String pageType) throws GlobalException;

	/**
	 * Create on : Danny Zhang 2016年11月16日 下午6:07:59 <br>
	 * Description : 全部的客户级别 <br>
	 * @param saler
	 * @return
	 * @throws GlobalException
	 */
	public List<String> gradeList() throws GlobalException;

	/**
	 * Create on : Danny Zhang 2016年11月25日 上午10:33:14 <br>
	 * Description :  获取省市县数据<br>
	 * @param pageType 级别类型
	 * @param parentType 父级类别
	 * @param data	上级数据
	 * @return
	 */
	public List<String> selectByArea(String pageType, String data,String parentType) throws GlobalException;

	/**
	 * Create on : Danny Zhang 2016年11月29日 下午1:32:32 <br>
	 * Description :  通过级别，客户获取数量<br>
	 * @param saler
	 * @param grade
	 * @return
	 */
	public List<Integer> countBySalerAndGrade(String saler, String grade) throws GlobalException;
	/**
	 * Create on : Dave Yu 2017年2月6日 上午11:38:18 <br>
	 * Description : 查询客户集合 <br>
	 * @param vo 
	 * @return
	 * @throws GlobalException
	 */
	public List<String> salerList(CustomerVo vo) throws GlobalException;
	/**
	 * Create on : Dave Yu 2017年2月6日 下午12:59:34 <br>
	 * Description : 查询该客户下的级别个数  <br>
	 * @param vo 
	 * @param saler
	 * @return
	 * @throws GlobalException
	 */
	public List<String> findCountGradesBySaler(CustomerVo vo, String saler) throws GlobalException;
}
