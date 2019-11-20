package cn.demi.base.system.service;

import java.util.List;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.OrgTreeVo;
import cn.demi.base.system.vo.OrgVo;

/**
 * <strong>Create on : 2016年11月2日 下午3:24:08 </strong> <br>
 * <strong>Description : 组织service </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IOrgService extends IBaseService<OrgVo> {
	/**
	 * Create on : Paddy Zhang 2017年3月3日 下午3:02:59 <br>
	 * Description : 根据code获取组织 <br>
	 * @param code
	 * @return
	 */
	public List<OrgVo> listByParentCode(String code) throws GlobalException;

	/**Create on : Paddy Zhang 2017年3月7日 下午3:04:28 <br>
	 * Description : 通过组织编码查询组织  <br>
	 * @param code 组织编码
	 * @return
	 * @throws GlobalException
	 */
	public OrgVo listByCode(String code) throws GlobalException;
	
	/**
	 * pid is null 时获取顶级部门集合
	 * pid 不为null时获取其第一个子集集合
	 */
	public List<OrgVo> lists(String pid) throws GlobalException;
	/**
	 * pid is null 时获取顶级部门集合
	 * pid 不为null时获取其第一个子集集合
	 */
	public List<OrgVo> lists(String pid,String type) throws GlobalException;
	/**
	 * type 根据类型获取部门集合
	 */
	public List<OrgVo> listByType(String type) throws GlobalException;
	/**
	 * 获取所有子集
	 * @param pid
	 * @return
	 * @throws GlobalException
	 */
	public List<OrgVo> listChild(String pid) throws GlobalException;
	/**
	 * 获取所有子集
	 * @param pid
	 * @return
	 * @throws GlobalException
	 */
	public List<OrgVo> listChild(String pid,String type) throws GlobalException;
	/**
	 * 获取json tree
	 * @param pid
	 * @return
	 * @throws GlobalException
	 */
	public OrgTreeVo find2JsonTree() throws GlobalException;
}
