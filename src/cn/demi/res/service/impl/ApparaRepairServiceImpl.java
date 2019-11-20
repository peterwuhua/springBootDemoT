package cn.demi.res.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.dao.IApparaRepairDao;
import cn.demi.res.po.Appara;
import cn.demi.res.po.ApparaRepair;
import cn.demi.res.service.IApparaRepairService;
import cn.demi.res.vo.ApparaRepairVo;

@Service("res.apparaRepairService")
public class ApparaRepairServiceImpl extends BaseServiceImpl<ApparaRepairVo,ApparaRepair> implements
		IApparaRepairService {
	@Autowired
	private IApparaDao apparaDao;
	@Autowired
	private IApparaRepairDao apparaRepairDao;
	@Override
	public void add(ApparaRepairVo v) throws GlobalException {
		ApparaRepair p = vo2Po(v);
		Appara app=apparaDao.findById(v.getApparaVo().getId());
		p.setAppara(app);
		apparaRepairDao.add(p);
		v.setId(p.getId());
		app.setState(v.getResult());
		apparaDao.update(app);
	}
	
	@Override
	public void update(ApparaRepairVo v) throws GlobalException {
		ApparaRepair p = apparaRepairDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		apparaRepairDao.update(p);
		Appara app=p.getAppara();
		app.setState(v.getResult());
		apparaDao.update(app);
	}
	
}
