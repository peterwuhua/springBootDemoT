package cn.demi.office.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.office.dao.IOfficeSuppliesDao;
import cn.demi.office.po.OfficeSupplies;
import cn.demi.office.service.IOfficeSuppliesService;
import cn.demi.office.vo.OfficeSuppliesVo;

@Service("office.suppliesService")
public class OfficeSuppliesServiceImpl extends BaseServiceImpl<OfficeSuppliesVo,OfficeSupplies> implements
		IOfficeSuppliesService {
	@Autowired IOfficeSuppliesDao iOfficeSuppliesDao;
	
	/**
	 * 
	 * <p>Title: saveBgyp</p>   
	 * <p>Description:保存方法 </p>   
	 * @param vo
	 * @throws GlobalException   
	 * @see cn.demi.office.service.IOfficeSuppliesService#saveBgyp(cn.demi.office.vo.OfficeSuppliesVo)
	 */
	@Override
	public void saveBgyp(OfficeSuppliesVo vo) throws GlobalException {
		OfficeSupplies po = vo2Po(vo);
	}
}
