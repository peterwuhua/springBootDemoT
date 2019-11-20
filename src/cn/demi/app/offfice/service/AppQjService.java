package cn.demi.app.offfice.service;

import java.util.List;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.offfice.vo.AppQjAuditEdit;
import cn.demi.app.offfice.vo.AppQjAuditPage;
import cn.demi.app.offfice.vo.AppQjShEdit;
import cn.demi.app.offfice.vo.AppQjShPage;
import cn.demi.app.offfice.vo.AppQjSpEdit;
import cn.demi.app.offfice.vo.AppQjSpPage;
import cn.demi.app.offfice.vo.AppQjSqEdit;
import cn.demi.app.offfice.vo.AppQjSqPage;
import cn.demi.app.sys.vo.ObjVo;

public interface AppQjService {
	/**
	 * 请假申请列表
	 * 
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppQjSqPage> qjSqList(ObjVo objVo) throws GlobalException;

	/**
	 * 请假申请详情
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public AppQjSqEdit appQjSqEdit(String id) throws GlobalException;

	/**
	 * 请假申请保存或提交
	 * 
	 * @param appQjSqEdit
	 * @return
	 * @throws GlobalException
	 */
	public boolean appQjSqSave(AppQjSqEdit appQjSqEdit, ObjVo objVo) throws GlobalException;

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	/**
	 * 请假审核 列表
	 * 
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppQjAuditPage> qjAuditList(ObjVo objVo) throws GlobalException;

	/**
	 * 请假审核 已办列表
	 * 
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppQjAuditPage> qjAuditListEd(ObjVo objVo) throws GlobalException;

	/**
	 * 请假审核详情
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public AppQjAuditEdit appQjAuditEdit(String id) throws GlobalException;

	/**
	 * 请假审核修改
	 * 
	 * @param appQjAuditEdit
	 * @return
	 * @throws GlobalException
	 */
	public boolean qjAuditUpdate(AppQjAuditEdit appQjAuditEdit, ObjVo objVo) throws GlobalException;

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/**
	 * 副总审核 列表
	 * 
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppQjShPage> appQjShList(ObjVo objVo) throws GlobalException;

	/**
	 * 副总审核 已办列表
	 * 
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppQjShPage> appQjShListEd(ObjVo objVo) throws GlobalException;

	/**
	 * 副总审核 详情
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public AppQjShEdit appQjShEdit(String id) throws GlobalException;
	
	/**
	 * 副总审核修改
	 * 
	 * @param appQjAuditEdit
	 * @return
	 * @throws GlobalException
	 */
	public boolean qjShUpdate(AppQjShEdit appQjShEdit, ObjVo objVo) throws GlobalException;
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/**
	 * 总经理审核 列表
	 * 
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppQjSpPage> AppQjSpList(ObjVo objVo) throws GlobalException;

	/**
	 * 总经理审核 已办列表
	 * 
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppQjSpPage> AppQjSpListEd(ObjVo objVo) throws GlobalException;

	/**
	 * 总经理审核 详情
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public AppQjSpEdit appQjSpEdit(String id) throws GlobalException;
	/**
	 * 总经理审核修改
	 * 
	 * @param appQjAuditEdit
	 * @return
	 * @throws GlobalException
	 */
	public boolean qjSpUpdate(AppQjSpEdit appQjSpEdit, ObjVo objVo) throws GlobalException;
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	
}
