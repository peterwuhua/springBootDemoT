package cn.demi.bus.test.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.test.vo.TestResultVo;

@Transactional
public interface ITestResultService extends IBaseService<TestResultVo> {
	public String queryTestResult(String sampid,String itemNames) throws GlobalException;
	/**
	 * 获取样品的项目检测结果
	 * @param sampCode
	 * @param itemNames
	 * @return
	 * @throws GlobalException
	 */
	public String findVal4Cyd(String sampCode,String itemNames) throws GlobalException;
}
