package cn.demi.init.std.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.std.vo.SampSourceVo;

@Transactional
public interface ISampSourceService extends IBaseService<SampSourceVo> {
	
	public SampSourceVo findByCode(String code) throws GlobalException;
	/**
	 * 方法 选择 弹窗 左侧 待选 集合
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Sels(GridVo gridVo, SampSourceVo v) throws GlobalException;
	
	/**
	 * 方法 选择 弹窗 右侧已选 集合
	 * @param ids
	 * @return
	 * @throws GlobalException
	 */
	public List<SampSourceVo> list4Sels(String ids) throws GlobalException;
	
	
	
	
	
	
}
