package cn.demi.res.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.dao.ISupplierDao;
import cn.demi.res.po.Supplier;
import cn.demi.res.service.ISupplierService;
import cn.demi.res.vo.SupplierVo;

@Service("res.supplierService")
public class SupplierServiceImpl extends BaseServiceImpl<SupplierVo,Supplier> implements
		ISupplierService {

	@Autowired
	private ISupplierDao supplierDao;
	@Override
	public PageResult effectivePage(SupplierVo v, PageResult pageResult) throws GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GridVo gridDatad(GridVo gridVo, SupplierVo v) throws GlobalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeFile(SupplierVo v) throws GlobalException {
		Supplier p = supplierDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.setFileName(null);
		p.setFilePath(null);
		supplierDao.update(p);
	}
	

	
	
	
}
