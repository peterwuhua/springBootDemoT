package cn.demi.doc.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.doc.vo.DocumentVo;
/**
 * Create on : 2016年11月24日 下午4:47:20  <br>
 * Description : 文件接口 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Transactional
public interface IDocumentService extends IBaseService<DocumentVo> {
	/**
	 * Create on : Dave Yu 2016年11月18日 下午3:58:00 <br>
	 * Description : 根据文档ID获取JSON分页对象 <br>
	 * @param gridVo
	 * @param v
	 * @return GridVo
	 * @throws GlobalException
	 */
	public GridVo gridData4Tab(GridVo gridVo, DocumentVo v)throws GlobalException;
	 /**
	  * 为其他模块 选择文件 
	  * 数据源
	  * @param gridVo
	  * @param v
	  * @return
	  * @throws GlobalException
	  */
	public GridVo gridData4Select(GridVo gridVo, DocumentVo v) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年11月25日 下午2:18:48 <br>
	 * Description : 得到把文件替换的文件名 <br>
	 * @param id
	 * @return String
	 * @throws GlobalException
	 */
	public String getReplaceTheName(String id) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年11月25日 下午3:13:12 <br>
	 * Description : 得到被文件替换的对象 <br>
	 * @param id
	 * @return List
	 * @throws GlobalException
	 */
	public List<DocumentVo> getBereplaced(String id) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年12月1日 上午11:18:36 <br>
	 * Description : 通过文件id查询出文件被更新的历史 <br>
	 * @param id
	 * @return List
	 * @throws GlobalException
	 */
	public List<DocumentVo> listByDocId(String id) throws GlobalException;
	/**
	 * Create on : Dave Yu 2016年12月14日 上午9:26:18 <br>
	 * Description : 通过属性获取group by下的list <br>
	 * @param pageType
	 * @return List
	 * @throws GlobalException
	 */
	public List<String> selectByPageType(String pageType) throws GlobalException;
	
	public void addUpdateFile(DocumentVo v) throws GlobalException;
	
}
