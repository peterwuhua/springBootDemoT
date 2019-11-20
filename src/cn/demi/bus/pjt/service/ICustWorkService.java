package cn.demi.bus.pjt.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.vo.CustWorkVo;

@Transactional
public interface ICustWorkService extends IBaseService<CustWorkVo> {
	
	
	/**
	 * 获取写实调查详情集合
	 * @param pjId	
	 */
	public List<CustWorkVo> listByProjectId(String pjId) throws GlobalException;
}
