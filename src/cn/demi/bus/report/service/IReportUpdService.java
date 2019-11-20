package cn.demi.bus.report.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.report.vo.ReportUpdVo;

@Transactional
public interface IReportUpdService extends IBaseService<ReportUpdVo> {
	
	/**
	 *新增返工
	 */
	public void addReportUpd(String reportId) throws GlobalException;
	
}
