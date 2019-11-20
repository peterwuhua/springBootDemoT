package cn.demi.office.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.office.vo.DgCfgVo;
import cn.demi.office.vo.DgVo;

@Transactional
public interface IDgService extends IBaseService<DgVo> {
	
	/**
	 * 打卡
	 * @throws GlobalException
	 */
	public void add2Dk() throws GlobalException;
	/**
	 * 获取上班时间
	 * @param v
	 * @throws GlobalException
	 */
	public DgCfgVo find() throws GlobalException;
	
	/**
	 * 上班时间配置
	 * @param v
	 * @throws GlobalException
	 */
	public void updateCfg(DgCfgVo v) throws GlobalException;
	/**
	 *验证打卡时间
	 * @param v
	 * @throws GlobalException
	 */
	public String checkTime() throws GlobalException;
}
