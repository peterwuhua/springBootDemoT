package cn.demi.qlt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.qlt.vo.NsVo;

@Transactional
public interface INsSumService extends IBaseService<NsVo> {
	/**
	 * 获取内审信息
	 * 报告文件数据源
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public NsVo find4Report(String id) throws GlobalException;
	/**
	 * 更新报告文件
	 * @param v
	 * @throws GlobalException
	 */
	public void update4Report(NsVo v) throws GlobalException;
}
