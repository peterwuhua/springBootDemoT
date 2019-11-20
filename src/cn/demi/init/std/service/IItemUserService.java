package cn.demi.init.std.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.init.std.vo.ItemUserVo;

@Transactional
public interface IItemUserService extends IBaseService<ItemUserVo> {
	
	public List<AccountVo> findTestUserList(String itemId) throws GlobalException;
	/**
	 * 新增数据
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public boolean addData(ItemUserVo v) throws GlobalException;
	
}
