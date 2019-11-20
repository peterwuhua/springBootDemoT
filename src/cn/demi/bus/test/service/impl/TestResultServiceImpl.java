package cn.demi.bus.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.test.dao.ITestResultDao;
import cn.demi.bus.test.po.TestResult;
import cn.demi.bus.test.service.ITestResultService;
import cn.demi.bus.test.vo.TestResultVo;

@Service("bus.testSampService")
public class TestResultServiceImpl extends BaseServiceImpl<TestResultVo, TestResult> implements ITestResultService {
	@Autowired
	private ITestResultDao testResultDao;

	@Override
	public String queryTestResult(String sampid, String itemNames) throws GlobalException {
		String sql = "SELECT t0.id,t0.value from v_bus_test_result t0 left JOIN v_bus_sampling t1 on t0.samp_id = t1.id left JOIN v_bus_test_item t2 ON t0.it_id = t2.id "
				+ "WHERE t1.id='" + sampid + "' and t2.item_name like '%" + itemNames + "%' ";
		List<Object[]> tList = testResultDao.queryBySql(sql);
		String v1 = "/";
		if (null != tList) {
			for (Object[] obj : tList) {
				v1 = (String)obj[1];
			}
		}
		return v1;
	}

	@Override
	public String findVal4Cyd(String sampCode, String itemNames) throws GlobalException {
		String sql = "SELECT t0.id,t0.value from v_bus_test_result t0 left JOIN v_bus_sampling t1 on t0.samp_id = t1.id and t1.is_del='"+Po.N+"' left JOIN v_bus_test_item t2 ON t0.it_id = t2.id and t2.is_del='"+Po.N+"' "
				+ "WHERE t1.samp_code='" + sampCode + "' and t2.item_name like '%" + itemNames + "%' ";
		List<Object[]> tList = testResultDao.queryBySql(sql);
		String v1 = "/";
		if (null != tList) {
			for (Object[] obj : tList) {
				v1 = (String)obj[1];
			}
		}
		return v1;
	}
}
