package cn.demi.bus.report.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.file.vo.ArchiveFileVo;
import cn.demi.bus.report.vo.ReportVo;

@Transactional
public interface IReportFileService extends IBaseService<ReportVo> {
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,ReportVo v) throws GlobalException;
	
	/**
	 * 获取任务所有文件集合
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<ArchiveFileVo> listFile(ReportVo v) throws GlobalException;
}
