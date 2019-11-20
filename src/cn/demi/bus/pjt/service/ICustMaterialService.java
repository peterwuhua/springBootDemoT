package cn.demi.bus.pjt.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.vo.CustMaterialVo;

@Transactional
public interface ICustMaterialService extends IBaseService<CustMaterialVo> {
	
	
	/**
	 * 获取物料详情集合
	 * @param pjId
	 */
	public List<CustMaterialVo> listByProjectId(String pjId) throws GlobalException;
}
