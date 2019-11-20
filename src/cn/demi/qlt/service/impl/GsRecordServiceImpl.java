package cn.demi.qlt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.qlt.dao.IGsDao;
import cn.demi.qlt.dao.IGsRecordDao;
import cn.demi.qlt.po.GsRecord;
import cn.demi.qlt.service.IGsRecordService;
import cn.demi.qlt.vo.GsRecordVo;

@Service("qlt.gsRecordService")
public class GsRecordServiceImpl extends BaseServiceImpl<GsRecordVo,GsRecord> implements
		IGsRecordService {
	@Autowired
	private IGsDao gsDao;
	@Autowired
	private IGsRecordDao gsRecordDao;
	@Override
	public GsRecordVo findById(String id) throws GlobalException {
		GsRecord po = gsRecordDao.findById(id);
		GsRecordVo vo=po2Vo(po);
		return vo;
	}
	
	@Override
	public void add(GsRecordVo v) throws GlobalException {
		GsRecord p = vo2Po(v);
		p.setGs(gsDao.findById(v.getGsVo().getId()));
		gsRecordDao.add(p);
		v.setId(p.getId());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, GsRecordVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = gsRecordDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<GsRecord>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	
}
