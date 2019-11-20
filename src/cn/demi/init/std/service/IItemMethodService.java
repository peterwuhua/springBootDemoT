package cn.demi.init.std.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.init.std.vo.ItemMethodVo;
import cn.demi.init.std.vo.MethodVo;

@Transactional
public interface IItemMethodService extends IBaseService<ItemMethodVo> {
	
	public boolean addMethod(ItemMethodVo v) throws GlobalException;
	
	/**
	 * 获取 ItemUserVo 为null 的方法
	 * 用于维护所有用户公用方法的项目
	 * @param itemId
	 * @return
	 * @throws GlobalException
	 */
	public List<ItemMethodVo> listMethod4AllUser(String itemId) throws GlobalException;
	
	public List<MethodVo> findMethodList(String itemId) throws GlobalException;
	/**
	 * 获取项目关系方法集合
	 * @param itemIds  项目Ids
	 * @return
	 * @throws GlobalException
	 */
	public List<ItemMethodVo> listByItemIds(String itemIds) throws GlobalException;
	
	/**
	 * 
	 * @Title: getEnvAndDesc   
	 * @Description: 获取环境或运输条件以及备注   
	 * @param: @param id
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: ItemMethodVo      
	 * @throws
	 */
	public List<TaskPointVo> getEnvAndDesc(List<TaskPointVo> taskPs) throws GlobalException;
	
	
	
	
}
