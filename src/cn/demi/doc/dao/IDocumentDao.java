package cn.demi.doc.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.doc.po.Document;

public interface IDocumentDao extends IBaseDao<Document> {
	/**
	 * Create on : Dave Yu 2016年11月23日 下午4:08:25 <br>
	 * Description :  <br>
	 * @param path
	 * @return
	 * @throws GlobalException
	 */
	public List<Document> listChildByPath(String path) throws GlobalException;
	
	/**
	 * Create on : Dave Yu 2016年11月23日 下午6:20:23 <br>
	 * Description : 更新文件路径 <br>
	 * @param prePath
	 * @param path
	 * @throws GlobalException
	 */
	public void updateDocumentPath(String prePath,String path) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年11月25日 下午3:21:39 <br>
	 * Description : 根据Id查询替换id得到把文件替换的文件集合 <br>
	 * @param id
	 * @return Document
	 * @throws GlobalException
	 */
	public List<Document> findBereplaced(String id) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年11月26日 上午11:27:00 <br>
	 * Description : 根据名字查询文件数据 <br>
	 * @param name
	 * @return List
	 * @throws GlobalException
	 */
	public List<Document> findByAll(String name) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年12月21日 上午10:55:30 <br>
	 * Description : 根据文件夹Id查询该文件夹下的文件信息 <br>
	 * @return 
	 * @throws GlobalException
	 */
	public List<Document> listDocumentByCId(String id) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年12月14日 上午9:27:53 <br>
	 * Description : 通过属性获取group by下的list <br>
	 * @param pageType
	 * @return List
	 * @throws GlobalException
	 */
	public List<String> selectByPageType(String pageType) throws GlobalException;
}

