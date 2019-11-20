package cn.demi.bus.report.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.demi.bus.report.po.ReportDetail;
import cn.demi.bus.report.service.IReportDetailService;
import cn.demi.bus.report.vo.ReportDetailVo;

@Service("bus.reportDetailService")
public class ReportDetailServiceImpl extends BaseServiceImpl<ReportDetailVo,ReportDetail> implements
		IReportDetailService {
}
