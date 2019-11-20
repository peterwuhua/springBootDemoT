package cn.demi.qlt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.qlt.vo.NsRecordVo;

@Transactional
public interface INsReportService extends IBaseService<NsRecordVo> {
	/**
	 * 获取内审信息
	 * 报告文件数据源
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public NsRecordVo find4Report(String id) throws GlobalException;
	/**
	 * 更新报告文件
	 * @param v
	 * @throws GlobalException
	 */
	public void update4Report(NsRecordVo v) throws GlobalException;
}
