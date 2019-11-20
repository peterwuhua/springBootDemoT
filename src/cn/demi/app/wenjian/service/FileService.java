package cn.demi.app.wenjian.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.wenjian.vo.AppFile;
import cn.demi.office.vo.DtVo;
import net.sf.json.JSONArray;

public interface FileService {
	/**
	 * app获取文件查看列表
	 * 
	 * @param v
	 * @param type 1: 登记列表 2:签发列表3:下发 4：查兰
	 *
	 * @return
	 * @throws GlobalException
	 */
	public List<AppFile> list(ObjVo v, int type,String searchValue) throws GlobalException;

	/**
	 * app文件详情
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public AppFile findfile(String id) throws GlobalException;

	/**
	 * app 签发功能
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public String updateAuditfile(DtVo v,ObjVo objVo) throws GlobalException;

	/**
	 * app 下发功能
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public String updateSendfile(DtVo v,ObjVo objVo) throws GlobalException;

	/**
	 * app 提供所有人信息
	 * 
	 * @return
	 * @throws GlobalException
	 */
	public JSONArray userList() throws GlobalException;

	/**
	 * app添加文件信息
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public String saveorupdateFile(String appFileId, String title,String code,String appFileName,String source,String dj,String  path,String auditId ,
			String auditName,String date,String content,String remark,String userId,String status,String filePath,String type,String  categoryId,ObjVo objVo) throws GlobalException;

	/**
	 * app 修改文件信息
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public String updatefileView(DtVo v,ObjVo objVo) throws GlobalException;
	
	/**
	 * App  搜索框 输入查询
	 * @param selectvalue
	 * @return
	 * @throws GlobalException
	 */
	public List<AppFile> selectList(ObjVo v,String selectvalue,int type) throws GlobalException;
	
	
	public JSONArray uploadFile(MultipartHttpServletRequest multiRequest,Object temppath,String twoFilePath) throws GlobalException;
}

