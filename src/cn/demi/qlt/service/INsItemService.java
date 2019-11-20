package cn.demi.qlt.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.qlt.vo.NsItemVo;

@Transactional
public interface INsItemService extends IBaseService<NsItemVo> {
	
	/**
	 * 要素集合
	 * 去除ids
	 * @param ids
	 * @return
	 * @throws GlobalException
	 */
	List<NsItemVo> list4Ys(String ids) throws GlobalException;
}
