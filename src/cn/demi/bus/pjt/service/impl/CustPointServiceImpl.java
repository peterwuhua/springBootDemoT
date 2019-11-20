package cn.demi.bus.pjt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.dao.ICustPointDao;
import cn.demi.bus.pjt.dao.ICustRoomDao;
import cn.demi.bus.pjt.dao.ICustWorkDao;
import cn.demi.bus.pjt.po.CustPoint;
import cn.demi.bus.pjt.po.CustRoom;
import cn.demi.bus.pjt.service.ICustPointService;
import cn.demi.bus.pjt.vo.CustPointVo;

@Service("bus.custPointService")
public class CustPointServiceImpl extends BaseServiceImpl<CustPointVo,CustPoint> implements
		ICustPointService {
 
	@Autowired
	private ICustRoomDao custRoomDao;
	@Autowired
	private ICustPointDao custPointDao;
	@Autowired
	private ICustWorkDao custWorkDao;
	@Override
	public void add(CustPointVo v) throws GlobalException {
		CustRoom room=custRoomDao.findById(v.getRoomVo().getId());
		CustPoint p = vo2Po(v);
		p.setCust(room.getCust());
		p.setRoom(room);
		p.setProjectId(room.getProjectId());
		p.setSort(custPointDao.getMaxSort(room.getProjectId())+1);
		custPointDao.add(p);
		v.setId(p.getId());
	}
	
	@Override
	public void delete(String... ids) throws GlobalException {
		List<CustPoint> ptList=custPointDao.listByIds(ids);
		if(null!=ptList) {
			for (CustPoint pt : ptList) {
				custWorkDao.deleteByPointId(pt.getId());
				custPointDao.delete(pt);
			}
		}
	}

	@Override
	public List<CustPointVo> listByProjectId(String pjId) throws GlobalException {
		List<CustPoint> cpList=custPointDao.listByProjectId(pjId);
		return toVoList(cpList);
	}
	
	
}
