package cn.demi.zk.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.zk.vo.ZkItemTestVo;

@Transactional
public interface IZkItemTestService extends IBaseService<ZkItemTestVo> {
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,ZkItemTestVo v) throws GlobalException;
}
