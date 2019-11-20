package cn.demi.bus.report.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.report.vo.ReportVo;

@Transactional
public interface IReportSignService extends IBaseService<ReportVo> {
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,ReportVo v) throws GlobalException;
	
	/**
	 * 保存pdf文件
	 * @param v
	 * @throws GlobalException
	 */
	public void update4Pdf(ReportVo v) throws GlobalException;
}
