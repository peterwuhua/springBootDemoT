package cn.demi.app.init.service;

import java.util.List;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.init.vo.AppInitItem;
import cn.demi.app.sys.vo.ObjVo;

public interface AppInitService {
	
	/**
	 * 监测项目列表
	 * @return
	 * @throws GlobalException
	 */
	public List<AppInitItem> itemList(ObjVo v,String name) throws GlobalException;

}
