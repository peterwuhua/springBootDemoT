package cn.demi.res.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.StandardVo;

@Transactional
public interface IStandardService extends IBaseService<StandardVo> {

	/**
	 * <strong>创建信息:Hans 2016年7月25日 上午9:53:51 </strong> <br>
	 * <strong>概要 : 获取效期列表</strong> <br>
	 * <strong>修改记录 : </strong> <br>
	 *@param v
	 *@param pageResult
	 *@return
	 */
	public PageResult effectivePage(StandardVo v, PageResult pageResult) throws GlobalException;

	public GridVo effectivePageData(GridVo gridVo, StandardVo v) throws GlobalException;

}
