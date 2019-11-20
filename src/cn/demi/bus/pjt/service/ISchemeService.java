package cn.demi.bus.pjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.vo.SchemeVo;

@Transactional
public interface ISchemeService extends IBaseService<SchemeVo> {
	
	/**
	 * 定时任务 
	 * 自动检测并执行方案
	 * 开始日期前一个工作日
	 * @param v
	 * @return
	 */
	public void executeSchedule()throws GlobalException;
	
	
	
}
