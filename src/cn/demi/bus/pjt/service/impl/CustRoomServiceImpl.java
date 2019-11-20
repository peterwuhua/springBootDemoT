package cn.demi.bus.pjt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.dao.ICustMaterialDao;
import cn.demi.bus.pjt.dao.ICustPointDao;
import cn.demi.bus.pjt.dao.ICustRoomDao;
import cn.demi.bus.pjt.dao.ICustWorkDao;
import cn.demi.bus.pjt.dao.IProjectDao;
import cn.demi.bus.pjt.po.CustRoom;
import cn.demi.bus.pjt.po.Project;
import cn.demi.bus.pjt.service.ICustRoomService;
import cn.demi.bus.pjt.vo.CustRoomVo;

@Service("bus.custRoomService")
public class CustRoomServiceImpl extends BaseServiceImpl<CustRoomVo,CustRoom> implements
		ICustRoomService {
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private ICustRoomDao custRoomDao;
	@Autowired
	private ICustMaterialDao custMaterialDao;
	@Autowired
	private ICustWorkDao custWorkDao;
	@Autowired
	private ICustPointDao custPointDao;
	@Override
	public void add(CustRoomVo v) throws GlobalException {
		Project project=projectDao.findById(v.getProjectId());
		CustRoom p = vo2Po(v);
		p.setCust(project.getCust());
		p.setSort(custRoomDao.getMaxSort(project.getId())+1);
		custRoomDao.add(p);
		v.setId(p.getId());
	}
	
	@Override
	public void delete(String... ids) throws GlobalException {
		List<CustRoom> ptList=custRoomDao.listByIds(ids);
		if(null!=ptList) {
			for (CustRoom pt : ptList) {
				custWorkDao.deleteByRoomId(pt.getId());
				custMaterialDao.deleteByRoomId(pt.getId());
				custPointDao.deleteByRoomId(pt.getId());
				custRoomDao.delete(pt);
			}
		}
	}
}
