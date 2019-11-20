package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.RegOutIn;
import cn.demi.res.vo.RegOutInVo;

public interface IRegOutInDao extends IBaseDao<RegOutIn> {
	
	public List<String[]> countReagent(String name);

	public List<RegOutInVo> listByOut();
}

