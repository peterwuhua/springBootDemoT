package cn.demi.bus.pjt.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.vo.CustPointVo;

@Transactional
public interface ICustPointService extends IBaseService<CustPointVo> {
	
	
	/**
	 * 获取监测点详情集合
	 * @param pjId
	 */
	public List<CustPointVo> listByProjectId(String pjId) throws GlobalException;
}
