package cn.demi.office.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.office.vo.KqVo;

@Transactional
public interface IKqService extends IBaseService<KqVo> {
	
	/**
	 * 考勤查询
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Query(GridVo gridVo, KqVo v) throws GlobalException;
	/**
	 * 考勤销假
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Xj(GridVo gridVo, KqVo v) throws GlobalException;
	
	public void update4Xj(KqVo v) throws GlobalException;
	/**
	 *有效请假列表展示
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Show(GridVo gridVo, KqVo v) throws GlobalException;
}
