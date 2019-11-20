package cn.demi.bus.test.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.test.po.TestResult;

public interface ITestResultDao extends IBaseDao<TestResult> {
	/**
	 * 根据测试项目删除结果信息
	 * @param itId 测试项目ID
	 */
	public void deleteByItId(String itId);
	/**
	 * 根据样品Id删除其下的测试结果信息
	 * @param sampId 样品Id
	 */
	public void deleteBySamp(String sampId);
	/**
	 * 根据测试项目获取结果集合
	 * @param itId 测试项目Id
	 */
	public List<TestResult> listByItId(String itId);
	/**
	 * 根据样品获取测试项目结果集合
	 * @param sampId 样品Id
	 */
	public List<TestResult> listBySampId(String sampId);
	/**
	 * 根据样品和项目名获取测试结果
	 * @param sampId 样品id
	 * @param itemId 项目Id
	 */
	public TestResult findBySampAndItem(String sampId,String itemId);
	
	/**
	 * <strong>Description :获取最大序号</strong> <br>
	 * @return int
	 */
	public int getMaxSort(String itId);
	 
}

