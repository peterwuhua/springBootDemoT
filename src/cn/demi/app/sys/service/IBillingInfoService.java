package cn.demi.app.sys.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.vo.AppBillingInfoVo;
import cn.demi.app.sys.vo.BillingInfoVo;
import cn.demi.app.sys.vo.ObjVo;

@Transactional
public interface IBillingInfoService extends IBaseService<BillingInfoVo> {
	/**
	 * 	工作计划 列表
	 */

	List<AppBillingInfoVo> listByUserId(String userId);
	/**
	 * 	工作计划新增
	 */

	String addBillingInfo(BillingInfoVo v,ObjVo objVo) throws GlobalException;
	/**
	 * 	工作计划 修改
	 */

	String updateBillingInfo(BillingInfoVo v) throws GlobalException;
}
