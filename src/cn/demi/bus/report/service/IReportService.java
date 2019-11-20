package cn.demi.bus.report.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.report.vo.ReportVo;
import cn.core.framework.common.action.Status;

@Transactional
public interface IReportService extends IBaseService<ReportVo> {
	
	/**
	 * 编辑页面获取值
	 */
	public ReportVo find(String id) throws GlobalException;
	
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,ReportVo v) throws GlobalException;
	/**
	 * 完成的报告列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Comp(GridVo gridVo,ReportVo v) throws GlobalException;
	
	/**
	 *报告生成
	 */
	public ReportVo update4Report(ReportVo v) throws GlobalException;
	/**
	 *生成报告信息
	 */
	public ReportVo find4Report(ReportVo v) throws GlobalException;

	/**
	 * 报告退回
	 */
	public void updateBack(ReportVo v) throws GlobalException;
	
}
