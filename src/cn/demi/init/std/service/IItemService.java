package cn.demi.init.std.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.std.vo.ItemVo;

@Transactional
public interface IItemService extends IBaseService<ItemVo> {
	
	public ItemVo findByName(String name) throws GlobalException;
	/**
	 * 获取项目集合
	 * 有子项的子项代替父项
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Select(GridVo gridVo, ItemVo v) throws GlobalException;
	/**
	 * 获取项目集合
	 * 选择项目和方法得弹窗使用
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Im(GridVo gridVo, ItemVo v) throws GlobalException;
	
	/**
	 *获取项目集合
	 *选择项目和方法得弹窗时   右侧项目方法列表数据源
	 * @param ids 项目Ids集合
	 * @return
	 * @throws GlobalException
	 */
	public List<ItemVo> list4Im(String ids) throws GlobalException;
	/**
	 * 获取项目集合
	 *评价标准 限值维护 获取项目集合
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4StandItem(GridVo gridVo, ItemVo v) throws GlobalException;
	


}
