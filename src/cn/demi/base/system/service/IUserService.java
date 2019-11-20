package cn.demi.base.system.service;

import java.util.List;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.UserVo;
import cn.demi.cus.customer.vo.Saler;

/**
 * <strong>Create on : 2016年11月2日 下午3:28:32 </strong> <br>
 * <strong>Description : 用户service</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IUserService extends IBaseService<UserVo> {
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午3:28:49 </strong> <br>
	 * <strong>Description : 图像修改</strong> <br>
	 * @param id id
	 * @param path 路径
	 * @throws GlobalException
	 */
	public void updateAvatar(String id,String path) throws GlobalException;
	/**
	 * 修改主题
	 * @param id
	 * @param theme
	 * @throws GlobalException
	 */
	public void updateTheme(String id,String theme) throws GlobalException;
	/**
	 * 个人信息保存
	 * @param v
	 * @throws GlobalException
	 */
	public void saveUserInfo(UserVo v) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2017年3月7日 下午2:52:54 <br>
	 * Description :  <br>
	 * @param orgCode 组织编码
	 * @param roleCode 角色名称
	 * @return
	 * @throws GlobalException
	 */
	public List<UserVo> list(GridVo gridVo,UserVo v) throws GlobalException;
	/**
	 * 
	 * @Title: fetchSaleRler   
	 * @Description: 获取所有销售人员   id,name ,accountid
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: List<String>      
	 * @throws
	 */
	public List<Saler> fetchSaleRler() throws GlobalException;
	
	
	/**
	 * 
	 * @Title: saleJLAuth   
	 * @Description: 判断当前登录用户是销售经理 还是销售人员 
	 * @param: @param accountId
	 * @param: @return XSRY  or  XSJL
	 * @param: @throws GlobalException      
	 * @return: int      
	 * @throws
	 */
	public String saleJLAuth(String accountId) throws GlobalException;
	
	
	
}
