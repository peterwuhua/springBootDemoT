package cn.demi.bus.report.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.bus.report.vo.ReportDetailVo;

@Transactional
public interface IReportDetailService extends IBaseService<ReportDetailVo> {
	
}
