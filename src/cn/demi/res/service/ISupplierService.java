package cn.demi.res.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.SupplierVo;

@Transactional
public interface ISupplierService extends IBaseService<SupplierVo> {

	/**
	 * <strong>创建信息:Hans 2016年7月25日 上午9:53:51 </strong> <br>
	 * <strong>概要 : 获取效期列表</strong> <br>
	 * <strong>修改记录 : </strong> <br>
	 *@param v
	 *@param pageResult
	 *@return
	 */
	public PageResult effectivePage(SupplierVo v, PageResult pageResult) throws GlobalException;

	public GridVo gridDatad(GridVo gridVo, SupplierVo v) throws GlobalException;

	public void removeFile(SupplierVo v) throws GlobalException;
	
}
