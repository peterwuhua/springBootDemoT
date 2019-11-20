package cn.demi.app.offfice.service;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.offfice.vo.AppDg;
import cn.demi.app.offfice.vo.AppDgEdit;
import cn.demi.app.offfice.vo.kqshrUser;
import cn.demi.app.sys.vo.ObjVo;

import java.util.List;

public interface AppDgService {

	/**
	 * 当前登录人 补卡审核列表
	 *
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppDg> dgAuditList(ObjVo objVo) throws GlobalException;

	/**
	 * 当前登录人 已办补卡审核列表
	 *
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppDg> dgAuditEdList(ObjVo objVo) throws GlobalException;

	/**
	 * 打卡
	 *
	 * @param objVo
	 * @param latAndLng
	 * @param checkOrNoCkeck
	 * @return
	 * @throws GlobalException
	 */
	public boolean add2Dk(ObjVo objVo, String latAndLng, String checkOrNoCkeck) throws GlobalException;

	/**
	 * 验证打卡时间
	 *
	 * @param objVo
	 * @param checkOrNoCkeck
	 * @throws GlobalException
	 */
	public String checkTime(ObjVo objVo, String checkOrNoCkeck) throws GlobalException;

	/**
	 * 补卡审核人列表
	 *
	 * @return
	 * @throws GlobalException
	 */
	public List<kqshrUser> kqshrUserList() throws GlobalException;

	/**
	 * 获取 补卡 或者外勤列表
	 *
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppDgEdit> dgList(ObjVo objVo) throws GlobalException;

	/**
	 * 获取当天打卡
	 *
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<String> todayDk(ObjVo objVo) throws GlobalException;

	/**
	 * 申请补卡或者外勤
	 * 
	 * @param appDgEdit
	 * @return
	 * @throws GlobalException
	 */
	public boolean saveDgEdit(AppDgEdit appDgEdit, ObjVo objVo) throws GlobalException;
	
	
	/**
	 * 获取 动态列表
	 *
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppDgEdit> dgTypeList(ObjVo objVo,String type) throws GlobalException;
	
	public boolean updateAudit(String id,String AuditMsg,String isCommit,ObjVo objVo)throws GlobalException;


}
