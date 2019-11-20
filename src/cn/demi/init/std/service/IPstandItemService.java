package cn.demi.init.std.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.std.vo.PstandItemVo;

@Transactional
public interface IPstandItemService extends IBaseService<PstandItemVo> {
	/**
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<PstandItemVo> listByStandId(PstandItemVo v) throws GlobalException;
	/**
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<PstandItemVo> listItem(GridVo gridVo,PstandItemVo v) throws GlobalException;
	/**
	 * 现场采样 现场监测项目获取大气 速率 指标集合
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<PstandItemVo> listItem4Cy(PstandItemVo v) throws GlobalException;
	/**
	 * 保存标准关联项目
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public boolean savePstandItem(PstandItemVo v) throws GlobalException;
	/**
	 * 保存标准关联项目
	 *  
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public boolean savePstandItem4Zw(PstandItemVo v) throws GlobalException;
	/**
	 * <strong>Description : 修改 标准关联项目</strong> <br>
	 *保存标准关联项目
	 * @throws GlobalException
	 */
	public void update4Grid4Zw(PstandItemVo v) throws GlobalException;
	/**
	 * 修改时验证重复
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public boolean checkPstandItem(PstandItemVo v) throws GlobalException;
}
