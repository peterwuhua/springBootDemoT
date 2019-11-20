package cn.demi.init.std.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.std.vo.PstandardVo;

@Transactional
public interface IPstandardService extends IBaseService<PstandardVo> {
	
	
	public PstandardVo findByCode(String code) throws GlobalException;
	
	/**
	 * 获取标准集合
	 * 根源样品类型Id 获取符合的第一个标准
	 * @param sampTypeId 样品类型
	 * @return
	 * @throws GlobalException
	 */
	public List<PstandardVo> list4Ajax(String sampTypeId) throws GlobalException;
	/**
	 *获取标准集合
	 *选择标准得弹窗时   右侧标准列表数据源
	 * @throws GlobalException
	 */
	public List<PstandardVo> list4Sels(String ids) throws GlobalException;
	
}
