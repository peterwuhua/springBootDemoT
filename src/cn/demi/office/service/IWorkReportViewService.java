package cn.demi.office.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.office.vo.WorkReportVo;

@Transactional
public interface IWorkReportViewService extends IBaseService<WorkReportVo> {

	public void addWorkReport(WorkReportVo v) throws GlobalException;
	
	public void updateWorkReport(WorkReportVo v) throws GlobalException;
	
}
