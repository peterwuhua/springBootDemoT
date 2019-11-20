package cn.demi.zk.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.zk.vo.ZkTaskVo;

@Transactional
public interface IZkTaskShService extends IBaseService<ZkTaskVo> {
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,ZkTaskVo v) throws GlobalException;
}
