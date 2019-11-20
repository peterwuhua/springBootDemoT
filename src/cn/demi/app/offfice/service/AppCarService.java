package cn.demi.app.offfice.service;

import java.util.List;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.offfice.vo.AppCarAuditEdit;
import cn.demi.app.offfice.vo.AppCarAuditPage;
import cn.demi.app.offfice.vo.AppCarEdit;
import cn.demi.app.offfice.vo.AppCarPage;
import cn.demi.app.offfice.vo.AppCarUserEdit;
import cn.demi.app.offfice.vo.AppCarUserPage;
import cn.demi.app.sys.vo.ObjVo;

public interface AppCarService {
	/**
	 * 汽车列表
	 * 
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppCarPage> appcarList(ObjVo objVo) throws GlobalException;

	/**
	 * 车辆详情
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public AppCarEdit appCarEdit(String id) throws GlobalException;

	/**
	 * 车辆详情修改或者新增
	 * 
	 * @param appCarEdit
	 * @return
	 * @throws GlobalException
	 */
	public boolean saveCar(AppCarEdit appCarEdit) throws GlobalException;

	/**
	 * 车辆使用申请 列表
	 * 
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppCarUserPage> appCarUserList(ObjVo objVo) throws GlobalException;

	/**
	 * 车辆使用申请 详情
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public AppCarUserEdit appCarUserEdit(String id) throws GlobalException;

	/**
	 * 车辆使用申请 编辑
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public boolean saveCarUser(AppCarUserEdit appCarUserEdit, ObjVo objVo) throws GlobalException;

	/**
	 * 车辆使用审核 列表
	 * 
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppCarAuditPage> appCarAuditList(ObjVo objVo) throws GlobalException;

	/**
	 * 车辆使用审核已办 列表
	 * 
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public List<AppCarAuditPage> appCarAuditListEd(ObjVo objVo) throws GlobalException;

	/**
	 * 车辆使用审核 详情
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public AppCarAuditEdit appCarAuditEdit(String id, ObjVo objVo) throws GlobalException;

	/**
	 * 车辆使用审核 修改
	 * 
	 * @param appCarAuditEdit
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public boolean saveCarAudit(AppCarAuditEdit appCarAuditEdit, ObjVo objVo) throws GlobalException;

}
