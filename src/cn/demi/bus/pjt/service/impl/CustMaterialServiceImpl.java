package cn.demi.bus.pjt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.dao.ICustMaterialDao;
import cn.demi.bus.pjt.dao.ICustRoomDao;
import cn.demi.bus.pjt.po.CustMaterial;
import cn.demi.bus.pjt.po.CustRoom;
import cn.demi.bus.pjt.service.ICustMaterialService;
import cn.demi.bus.pjt.vo.CustMaterialVo;

@Service("bus.custMaterialService")
public class CustMaterialServiceImpl extends BaseServiceImpl<CustMaterialVo,CustMaterial> implements
		ICustMaterialService {
 
	@Autowired
	private ICustRoomDao custRoomDao;
	@Autowired
	private ICustMaterialDao custMaterialDao;
	
	@Override
	public void add(CustMaterialVo v) throws GlobalException {
		CustRoom room=custRoomDao.findById(v.getRoomVo().getId());
		CustMaterial p = vo2Po(v);
		p.setCust(room.getCust());
		p.setRoom(room);
		p.setProjectId(room.getProjectId());
		p.setUseType(room.getWorks()+"，"+room.getWorkWay());
		p.setSort(custMaterialDao.getMaxSort(room.getProjectId())+1);
		custMaterialDao.add(p);
		v.setId(p.getId());
	}

	@Override
	public List<CustMaterialVo> listByProjectId(String pjId) throws GlobalException {
		List<CustMaterial>  mtList=custMaterialDao.listByProjectId(pjId);
		return toVoList(mtList);
	}
	
}
