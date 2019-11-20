package cn.demi.init.st.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.st.vo.SampTypeVo;

@Transactional
public interface ISampTypeService extends IBaseService<SampTypeVo> {
	
	/**
	 * 返回样品类型 tree 
	 * @param rootId
	 * @return
	 */
	public List<SampTypeVo> tree(String rootId)throws GlobalException;
	/**
	 * 获取样品类型集合
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<SampTypeVo> listData(SampTypeVo v) throws GlobalException;
	
	/**
	 * 返回样品类型 tree 
	 * 包含 name 样品
	 * @param rootId
	 * @return
	 */
	public List<SampTypeVo> treeData4Type(String name)throws GlobalException;
	
	/**
	 * 返回样品 某一类 类型集合 
	 * @param type 大类
	 * @return
	 */
	public String find4Type(String type)throws GlobalException;
	
	/**
	 * 根据名称获取样 
	 * @param type 大类
	 * @return
	 */
	public SampTypeVo findByName(String name)throws GlobalException;
	/**
	 * 根据样品大类 
	 * @param type 大类
	 * @return
	 */
	public SampTypeVo find4SampType(String id)throws GlobalException;
}
