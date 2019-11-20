package cn.demi.bus.sample.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.sample.vo.SampUsedVo;

@Transactional
public interface ISampUsedService extends IBaseService<SampUsedVo> {
	/**
	 * 获取样品使用记录集合
	 * @param sampId
	 * @return
	 * @throws GlobalException 
	 */
	List<SampUsedVo> listBySampId(String sampId) throws GlobalException;
}
