package cn.demi.doc.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.doc.po.Category;
/**
 * Create on : 2016年11月23日 下午4:02:13  <br>
 * Description :  <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public interface ICategoryDao extends IBaseDao<Category> {
	/**
	 * Create on : Dave Yu 2016年11月23日 下午4:02:29 <br>
	 * Description :  <br>
	 * @param path
	 * @return
	 */
	public List<Category> listChildByPath(String path) throws GlobalException;
	
	/**
	 * Create on : Dave Yu 2016年11月23日 下午6:20:07 <br>
	 * Description :  <br>
	 * @param prePath
	 * @param path
	 * @throws GlobalException
	 */
	public void updateCategoryPath(String prePath,String path) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年12月22日 下午2:33:41 <br>
	 * Description : 根据文件夹Id查询此文件夹下子文件夹信息 <br>
	 * @param id
	 * @return List
	 * @throws GlobalException
	 */
	public List<Category> findByPid(String id) throws GlobalException;
}

