package cn.demi.init.sp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.sp.vo.SampPointVo;

@Transactional
public interface ISampPointService extends IBaseService<SampPointVo> {
	/**
	 * 监测点位验证
	 * @param name
	 * @return
	 */
	public boolean checkName(String name,String sampId) throws GlobalException;
	
	public List<SampPointVo> list(SampPointVo v,GridVo gridVo) throws GlobalException;
}
