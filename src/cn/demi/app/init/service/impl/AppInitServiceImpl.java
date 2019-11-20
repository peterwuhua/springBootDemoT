package cn.demi.app.init.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.demi.app.init.service.AppInitService;
import cn.demi.app.init.vo.AppInitItem;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.po.Item;

@Service("app.initService")
public class AppInitServiceImpl implements AppInitService {
	@Autowired
	private IItemDao itemDao;

	@Override
	public List<AppInitItem> itemList(ObjVo v,String name) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + itemDao.getEntityName(Item.class) + " WHERE isDel=" + Po.N);
		hql.append(" and name like '%"+name+"%' ");
		hql.append(" ORDER BY sort asc");
		Query query = itemDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Item> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppInitItem> outList = new ArrayList<>();
		for (Item item : list) {
			AppInitItem itemVo = new AppInitItem();
			itemVo.setId(item.getId());
			itemVo.setName(item.getName());
			itemVo.setUnit(item.getUnit());
			itemVo.setCode(item.getCode());
			itemVo.setIsNow(item.getIsNow());
			itemVo.setSampTypeNames(item.getSampTypeNames());
			itemVo.setStandNames(item.getStandNames());
			itemVo.setMinVal(String.valueOf(item.getMinVal()));
			itemVo.setMaxVal(String.valueOf(item.getMaxVal()));
			outList.add(itemVo);
		}
		return outList;
	}

}
