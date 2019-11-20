package cn.demi.init.std.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.base.system.po.Org;
import cn.demi.init.std.po.ItemUser;

public interface IItemUserDao extends IBaseDao<ItemUser> {
	
	ItemUser findUser(String itemId,String orgId);
	/**
	 * 获取项目得检测科室
	 * @param itemId
	 * @return
	 */
	List<Org> find4Org(String itemId);
}

