package cn.demi.init.st.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.st.po.SampType;

public interface ISampTypeDao extends IBaseDao<SampType> {
	
	
	public void updatePath(String oldPath, String newPath) throws GlobalException;
	/**
	 * 获取样品及父级id集合
	 * @param id
	 * @return
	 */
	public String findAllIds(String id);
	
	/**
	 * 获取一类样品id集合
	 * @param 水，气，声
	 * @return
	 */
	public String find4Type(String name);
	/**
	 * 获取样品类型
	 * @param name 样品名称
	 * @return
	 */
	public SampType findByName(String name);
	/**
	 * 获取ids 中 type 类型的样品
	 * @param ids
	 * @param type
	 * @return
	 */
	public List<SampType> listByType(String ids,String type);
	/**
	 * 获取样品及父级id集合
	 * @param id
	 * @return
	 */
	public List<String> findAllPids(String id);
	/**
	 * 获取样品及子集id集合
	 * @param id
	 * @return
	 */
	public List<String> findAllSubids(String id);
}

