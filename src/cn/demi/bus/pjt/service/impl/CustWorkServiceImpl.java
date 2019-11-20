package cn.demi.bus.pjt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.dao.ICustPointDao;
import cn.demi.bus.pjt.dao.ICustWorkDao;
import cn.demi.bus.pjt.po.CustPoint;
import cn.demi.bus.pjt.po.CustWork;
import cn.demi.bus.pjt.service.ICustWorkService;
import cn.demi.bus.pjt.vo.CustWorkVo;

@Service("bus.custWorkService")
public class CustWorkServiceImpl extends BaseServiceImpl<CustWorkVo,CustWork> implements
		ICustWorkService {
	@Autowired
	private ICustWorkDao custWorkDao;
	@Autowired
	private ICustPointDao custPointDao;
	
	@Override
	public void add(CustWorkVo v) throws GlobalException {
		CustPoint point=custPointDao.findById(v.getPointVo().getId());
		CustWork p = vo2Po(v);
		p.setCust(point.getCust());
		p.setRoom(point.getRoom());
		p.setPoint(point);
		p.setProjectId(point.getProjectId());
		p.setSort(custWorkDao.getMaxSort(point.getProjectId())+1);
		custWorkDao.add(p);
		v.setId(p.getId());
	}

	@Override
	public List<CustWorkVo> listByProjectId(String pjId) throws GlobalException {
		List<CustWork> workList=custWorkDao.listByProjectId(pjId);
		return toVoList(workList);
	}
	
}
